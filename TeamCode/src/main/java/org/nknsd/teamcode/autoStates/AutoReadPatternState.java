package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoReadPatternState extends StateMachine.State {
    private final AprilTagSensor aprilTagSensor;
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private final FiringSystem firingSystem;
    private ID pattern;

    public AutoReadPatternState(AprilTagSensor aprilTagSensor, FiringSystem firingSystem, String[] toStopOnEnd, String[] toStartOnEnd) {
        this.aprilTagSensor = aprilTagSensor;
        this.toStopOnEnd = toStopOnEnd;
        this.toStartOnEnd = toStartOnEnd;
        this.firingSystem = firingSystem;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        pattern = aprilTagSensor.getVisionResultPattern().id;
        if (pattern == ID.GPP || pattern == ID.PGP || pattern == ID.PPG) {
            firingSystem.setPattern(pattern);

            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {

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
