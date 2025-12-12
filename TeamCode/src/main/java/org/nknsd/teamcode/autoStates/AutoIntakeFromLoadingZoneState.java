package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoIntakeFromLoadingZoneState extends StateMachine.State {
    private final AutoPositioner autoPositioner;
    private final int maxTries;
    private final double stepDist;
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private int ballTries = 0;
    private double lastRunTime;
    private boolean selfStopped = false;


    public AutoIntakeFromLoadingZoneState(AutoPositioner autoPositioner, int maxTries, double stepDist, String[] toStopOnEnd, String[] toStartOnEnd) {
        this.autoPositioner = autoPositioner;
        this.maxTries = maxTries;
        this.stepDist = stepDist;
        this.toStopOnEnd = toStopOnEnd;
        this.toStartOnEnd = toStartOnEnd;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (runtime.milliseconds() - lastRunTime > 500) {
            lastRunTime = runtime.milliseconds();

            autoPositioner.setTargetX(45);
            autoPositioner.setTargetY(7 - ballTries * stepDist);
            if (ballTries > maxTries) {
                StateMachine.INSTANCE.stopAnonymous(this);
                selfStopped = true;
            }
            ballTries++;
        }
    }


    @Override
    protected void started() {
        lastRunTime = startTimeMS;
        autoPositioner.enableAutoPositioning(true);
        autoPositioner.setTargetH(-Math.PI / 2 - 0.05);
    }

    @Override
    protected void stopped() {
        if (selfStopped) {
            for (String stateName : this.toStopOnEnd) {
                StateMachine.INSTANCE.stopState(stateName);
            }
        }
        for (String stateName : this.toStartOnEnd) {
            StateMachine.INSTANCE.startState(stateName);
        }
    }
}
