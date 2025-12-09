package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoReadPatternState extends StateMachine.State {
    private final AprilTagSensor aprilTagSensor;
    private ID pattern;
    public AutoReadPatternState(AprilTagSensor aprilTagSensor) {
        this.aprilTagSensor = aprilTagSensor;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    protected void started() {
        pattern = aprilTagSensor.getVisionResultPattern().id;
    }

    @Override
    protected void stopped() {

    }
}
