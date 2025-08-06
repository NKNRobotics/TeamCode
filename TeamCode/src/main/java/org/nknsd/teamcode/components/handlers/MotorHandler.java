package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MotorHandler implements NKNComponent {

    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;


    public void setPowers(double x, double y, double theta) {
        double magnitude;
        magnitude = Math.abs(y) + Math.abs(x) + Math.abs(theta);
        if (magnitude > 1) {
            y = y / magnitude;
            x = x / magnitude;
            theta = theta / magnitude;
        }
        double flPower = y - x + theta;
        double frPower = y + x - theta;
        double blPower = y + x + theta;
        double brPower = y - x - theta;

        flMotor.setPower(flPower);
        frMotor.setPower(frPower);
        blMotor.setPower(blPower);
        brMotor.setPower(brPower);
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        flMotor = hardwareMap.dcMotor.get("flMotor");
        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frMotor = hardwareMap.dcMotor.get("frMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        brMotor = hardwareMap.dcMotor.get("brMotor");
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
        return null;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }
}
