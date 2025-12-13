package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.opencv.core.Mat;

public class AutoIntakeFromLoadingZoneState extends StateMachine.State {
    private final AutoPositioner autoPositioner;
    private final int maxTries;
    private final double stepDist;
    private final PidController pidX;
    private final PidController pidY;
    private final PidController pidH;
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private int ballTries = 0;
    private double lastRunTime;
    private int allianceMult;


    public AutoIntakeFromLoadingZoneState(AutoPositioner autoPositioner, int maxTries, double stepDist, ID alliance, PidController pidX, PidController pidY, PidController pidH, String[] toStopOnEnd, String[] toStartOnEnd) {
        this.pidX = pidX;
        this.pidY = pidY;
        this.pidH = pidH;
        switch (alliance.ordinal()) {
            case 3:
                allianceMult = 1;
                break;
            case 4:
                allianceMult = -1;
                break;

        }
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

            autoPositioner.setTargetY(8 - ballTries * stepDist, pidY);
            if (ballTries > maxTries) {
                StateMachine.INSTANCE.stopAnonymous(this);
            }
            ballTries++;
        }
    }


    @Override
    protected void started() {
        lastRunTime = startTimeMS;
        autoPositioner.enableAutoPositioning(true);
        autoPositioner.setTargetX((-42) * allianceMult, pidX);
        autoPositioner.setTargetH((Math.PI / 2 - 0.35) * allianceMult, pidH);
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
