package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoIntakeFromLoadingZoneState extends StateMachine.State {
    private final ArtifactSystem artifactSystem;
    private final AutoPositioner autoPositioner;
    private final int maxTries;
    private final double stepDist;
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private int ballNum = 0;
    private int ball0tries = 0;
    private double ball1tries = 0;
    private double ball2tries = 0;
    private double lastRunTime;
    private int slotNum = 0;


    public AutoIntakeFromLoadingZoneState(ArtifactSystem artifactSystem, AutoPositioner autoPositioner, int maxTries, double stepDist, String[] toStopOnEnd, String[] toStartOnEnd) {
        this.artifactSystem = artifactSystem;
        this.autoPositioner = autoPositioner;
        this.maxTries = maxTries;
        this.stepDist = stepDist;
        this.toStopOnEnd = toStopOnEnd;
        this.toStartOnEnd = toStartOnEnd;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(runtime.milliseconds() - lastRunTime > 500)
            lastRunTime = runtime.milliseconds();

        if (ballNum == 0) {
            autoPositioner.setTargetX(40 + ball0tries * stepDist);
            autoPositioner.setTargetY(5);
            if(artifactSystem.getContents()[slotNum] != BallColor.NOTHING && artifactSystem.getContents()[slotNum] != BallColor.UNSURE){
                ballNum = 1;
                slotNum ++;
                return;
            } else if(ball0tries > maxTries){
                ballNum = 1;
                return;
            }
            ball0tries ++;
        }

        if (ballNum == 1) {
            autoPositioner.setTargetX(40 + ball1tries * stepDist);
            autoPositioner.setTargetY(1);
            if(artifactSystem.getContents()[slotNum] != BallColor.NOTHING && artifactSystem.getContents()[slotNum] != BallColor.UNSURE){
                ballNum = 2;
                slotNum ++;
                return;
            } else if(ball1tries > maxTries){
                ballNum = 2;
                return;
            }
            ball1tries ++;
        }

        if (ballNum == 2) {
            autoPositioner.setTargetH(2);
            autoPositioner.setTargetX(40 + ball2tries * stepDist);
            autoPositioner.setTargetY(0);
            if((artifactSystem.getContents()[slotNum] != BallColor.NOTHING && artifactSystem.getContents()[slotNum] != BallColor.UNSURE) || ball2tries > maxTries){
                autoPositioner.enableAutoPositioning(false);
                StateMachine.INSTANCE.stopAnonymous(this);
            }
            ball2tries ++;
        }
    }

    @Override
    protected void started() {
        lastRunTime = startTimeMS;
        autoPositioner.enableAutoPositioning(true);
        autoPositioner.setTargetH(Math.PI/2);
    }

    @Override
    protected void stopped() {
        for (String stateName : this.toStopOnEnd) {
            StateMachine.INSTANCE.stopState(stateName);
        }
        for (String stateName : this.toStartOnEnd) {
            StateMachine.INSTANCE.startState(stateName);
        }
    }
}
