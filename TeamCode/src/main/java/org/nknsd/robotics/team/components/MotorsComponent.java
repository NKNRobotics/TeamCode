package org.nknsd.robotics.team.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.robotics.framework.NKNComponent;

public class MotorsComponent implements NKNComponent {
    private float motorSpeed;
    private double disableEndTimeSeconds;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        //get motor from the hardware map
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "MotorsComponent";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        if (runtime.seconds() > disableEndTimeSeconds) {
            telemetry.addData("Motor", motorSpeed);

        } else {
            telemetry.addData("Motor", 0);

        }
    }

    public void setSpeed(float speed) {
        motorSpeed = speed;
    }

    public void disableMotors(double endTimeSeconds) {
        disableEndTimeSeconds = endTimeSeconds;
    }
}
