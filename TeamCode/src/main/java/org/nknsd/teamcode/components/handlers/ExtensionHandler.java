package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class ExtensionHandler implements NKNComponent {




    CRServo liftBLServo;
    CRServo liftFLServo;
    CRServo liftBRServo;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        liftBLServo = hardwareMap.crservo.get("BLlift");
        liftFLServo = hardwareMap.crservo.get("FLlift");
        liftBRServo = hardwareMap.crservo.get("BRlift");
        liftBLServo.setDirection(DcMotorSimple.Direction.FORWARD);
        liftFLServo.setDirection(DcMotorSimple.Direction.REVERSE);
        liftBRServo.setDirection(DcMotorSimple.Direction.REVERSE);
        liftBLServo.setPower(0);
        liftFLServo.setPower(0);
        liftBRServo.setPower(0);
        return true;
    }

    public void goUp(){
        liftBLServo.setPower(1);
        liftFLServo.setPower(1);
        liftBRServo.setPower(1);
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
        return "";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }
}
