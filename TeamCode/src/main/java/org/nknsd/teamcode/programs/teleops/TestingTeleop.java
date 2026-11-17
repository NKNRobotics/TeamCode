package org.nknsd.teamcode.programs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TestingTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx dcmotorl = (DcMotorEx)hardwareMap.get("motorLeft");
        DcMotorEx dcmotorr = (DcMotorEx)hardwareMap.get("motorRight");

        dcmotorl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dcmotorr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double rF = 32767.0 / 2480;
        double lF = 32767.0 / 2600;
        double rP = .4 * rF;
        double lP = .4 * lF;
        double rI = .05 * rP;
        double lI = .05 * lP;
        dcmotorl.setVelocityPIDFCoefficients(rP, rI, 0, rF);
        dcmotorr.setVelocityPIDFCoefficients(lP, lI, 0, lF);
        dcmotorl.setPositionPIDFCoefficients(5);
        dcmotorr.setPositionPIDFCoefficients(5);
        dcmotorr.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();


        double motorpower = 1000;
        while (opModeIsActive()) {
            dcmotorl.setVelocity (motorpower);
            dcmotorr.setVelocity (motorpower);

            dcmotorr.setPower(motorpower);
            if (gamepad1.xWasPressed()) {
                motorpower = motorpower + 100;
            }
            if (gamepad1.yWasPressed()){
                motorpower = motorpower - 100;
            }
            if (gamepad1.bWasPressed()) {
                motorpower = motorpower + 25;
            }
            if (gamepad1.aWasPressed()){
                motorpower = motorpower - 25;
            }

            telemetry.addData("Motor Speed", motorpower);
            telemetry.addData("Left Speed", dcmotorl.getVelocity());
            telemetry.addData("Right Speed", dcmotorr.getVelocity());
            telemetry.update();
        }

    }
}