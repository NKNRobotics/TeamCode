package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LiftHandler implements NKNComponent {
    CRServo BLlift;
    CRServo BRlift;
    CRServo FLlift;
    IMUSensor imuSensor;
    private final double threshold = 0.3;

    private boolean isLifting = false;

    private double lastLoopTime = 0;


    public void startRaise(ElapsedTime runtime){

        isLifting = true;

    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        BLlift = hardwareMap.crservo.get("BLlift");
        BRlift = hardwareMap.crservo.get("BRlift");
        FLlift = hardwareMap.crservo.get("FLlift");

        BRlift.setDirection(DcMotorSimple.Direction.REVERSE);
        FLlift.setDirection(DcMotorSimple.Direction.REVERSE);
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
        return "";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

        if (isLifting) {
            if (runtime.milliseconds()>lastLoopTime + 100){
                lastLoopTime = runtime.milliseconds();
            } else {
                return;
            }
            double pitch = imuSensor.getPitch() / 360;
            double roll = imuSensor.getRoll() / 360;

            FLlift.setPower(.5);
            BLlift.setPower(.5);
            BRlift.setPower(.5);

            if (-pitch < -threshold && roll < threshold) {
                BLlift.setPower(0);
            }
            else if (-pitch < -threshold && -roll < -threshold){
                BRlift.setPower(-.3);
                FLlift.setPower(-.3);
            }
            else {
                if (pitch > threshold) {
                    FLlift.setPower(0);
                } else if (-pitch < -threshold) {
                    BLlift.setPower(0);
                    BRlift.setPower(0);
                }
                if (roll > threshold) {
                    BRlift.setPower(0);
                } else if (-roll < -threshold) {
                    BLlift.setPower(0);
                    FLlift.setPower(0);
                }
            }
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Fl", FLlift.getPower());
        telemetry.addData("BR", BRlift.getPower());
        telemetry.addData("BL", BLlift.getPower());
    }
    public void link(IMUSensor imuSensor){
        this.imuSensor = imuSensor;
    }
}