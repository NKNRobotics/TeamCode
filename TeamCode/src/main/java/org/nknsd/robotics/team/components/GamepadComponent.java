package org.nknsd.robotics.team.components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.robotics.framework.NKNComponent;

public class GamepadComponent implements NKNComponent {
    Gamepad gamepad1;
    Gamepad gamepad2;

    MotorsComponent motorManager;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
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
        return "GamepadComponent";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        telemetry.addData("Left Stick X", gamepad1.left_stick_x);
        telemetry.addData("Left Stick Y", gamepad1.left_stick_y);

        if (gamepad1.a) {
            motorManager.disableMotors(runtime.seconds() + 2);
        }

        motorManager.setSpeed(gamepad1.right_stick_y);
    }

    public void setMotor(MotorsComponent motorManager) {
        this.motorManager = motorManager;
    }
}
