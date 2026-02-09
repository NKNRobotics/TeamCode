package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.artifact.states.IntakeBallState;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class SRSIntakeState extends StateMachine.State {

    private static final double MULT = 0.005;
    final private PeakPointer peakPointer;
    final private AbsolutePosition position;
    final private AutoPositioner autoPositioner;
    final private boolean eat;
    final private boolean killself;

    private MicrowaveScoopHandler microwaveScoopHandler;
    private SlotTracker slotTracker;
    private ArtifactSystem artifactSystem;


    private double lastRunTime = 0;
    private short dist = 0;
    private Double offset = 0.0;
    final private PidController pid = new PidController(0.1, .2, 0.05, .1, true, 0.01, 0.1);


    public SRSIntakeState(PeakPointer peakPointer, AbsolutePosition position, AutoPositioner autoPositioner, boolean killSelf) {
        this.peakPointer = peakPointer;
        this.position = position;
        this.autoPositioner = autoPositioner;
        this.killself = killSelf;
        eat = false;
    }

    public SRSIntakeState(PeakPointer peakPointer, AbsolutePosition position, AutoPositioner autoPositioner, boolean killSelf, MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker, ArtifactSystem artifactSystem) {
        this.peakPointer = peakPointer;
        this.position = position;
        this.autoPositioner = autoPositioner;
        this.killself = killSelf;
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.slotTracker = slotTracker;
        this.artifactSystem = artifactSystem;
        eat = true;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (killself && peakPointer.targetAcquired()) {
            StateMachine.INSTANCE.stopAnonymous(this);
            peakPointer.enableTargeting(false);
        }

        if (eat && runtime.milliseconds() - lastRunTime > 100) {
            if (peakPointer.getDist() != null && peakPointer.getOffset() != null) {
                dist = peakPointer.getDist();
                offset = peakPointer.getOffset();
            }
            double x = (Math.sin(offset) + dist) * MULT;
            double y = (Math.cos(offset) + dist) * MULT;

            autoPositioner.setTargetX(position.getPosition().x - x, pid);
            autoPositioner.setTargetY(position.getPosition().y - y, pid);

            lastRunTime = runtime.milliseconds();
        }
    }

    @Override
    protected void started() {
        peakPointer.enableTargeting(true);
        if (eat) {
            int slot = 0;
            for (int i = 0; i < 3; i++){
                BallColor color = slotTracker.getSlotColor(i);
                if(color == BallColor.NOTHING){
                    slot = i;
                }
            }
            if(killself){
            StateMachine.INSTANCE.startAnonymous(new IntakeBallState(microwaveScoopHandler, slotTracker, artifactSystem, slot, false, new String[]{name}, new String[]{}));}
            else{
                StateMachine.INSTANCE.startAnonymous(new IntakeBallState(microwaveScoopHandler, slotTracker, artifactSystem, slot, false, new String[]{}, new String[]{}));}
        }
    }

    @Override
    protected void stopped() {

    }
}
