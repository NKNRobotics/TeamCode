package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="ServoExtensionTest")
public class ExtensionTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        CRServo lift0 = hardwareMap.crservo.get("BRlift");
        CRServo lift1 = hardwareMap.crservo.get("FLlift");
        CRServo lift2 = hardwareMap.crservo.get("BLlift");

        lift0.setDirection(DcMotorSimple.Direction.REVERSE);
        lift1.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        boolean extend, retract;
        while (opModeIsActive()) {
            extend = gamepad2.right_bumper; retract = gamepad2.left_bumper;

            if (extend) {
                lift0.setPower(0.2);
                lift1.setPower(0.2);
                lift2.setPower(0.2);
            } else if (retract) {
                lift0.setPower(-0.2);
                lift1.setPower(-0.2);
                lift2.setPower(-0.2);
            } else {
                lift0.setPower(0);
                lift1.setPower(0);
                lift2.setPower(0);
            }
        }
    }
}
