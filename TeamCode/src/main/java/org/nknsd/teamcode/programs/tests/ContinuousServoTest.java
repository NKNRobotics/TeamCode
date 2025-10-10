package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "ContServoTest", group = "Tests")
public class ContinuousServoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        CRServo spinny = hardwareMap.get(CRServo.class, "spinny");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_trigger > 0.5) {
                spinny.setPower(1);
            } else {
                spinny.setPower(0);
            }
        }
    }
}
