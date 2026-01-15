package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class BalancedLiftHandler implements NKNComponent {
    CRServo blLift;
    CRServo brLift;
    CRServo flLift;

    private final double blInitial = 0.39;
    private final double brInitial = 0.33;
    private final double flInitial = 0.36;

    private final double rollTarget = 2;
    private final double pitchTarget = -19;

    private final double kpfl = 0.05, kpbl = -0.1, kpbr = -0.05;
    private final double krfl = 0.05, krbl = 0.1, krbr = -0.05;
    private IMUSensor imuSensor;
    private boolean isLifting = false;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        blLift = hardwareMap.crservo.get("BLlift");
        brLift = hardwareMap.crservo.get("BRlift");
        flLift = hardwareMap.crservo.get("FLlift");

        blLift.setDirection(DcMotorSimple.Direction.REVERSE);

        brLift.setPower(0);
        flLift.setPower(0);
        blLift.setPower(0);


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
        return "BalancedLiftHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        if (isLifting){
            double pitch = imuSensor.getPitch() - pitchTarget;
            double roll = imuSensor.getRoll() - rollTarget;

            double flPower = flInitial + (pitch * kpfl + roll * krfl);
            double blPower = blInitial + (pitch * kpbl + roll * krbl);
            double brPower = brInitial + (pitch * kpbr + roll * krbr);

            flLift.setPower(Range.clip(flPower, 0, 1));
            blLift.setPower(Range.clip(blPower, 0, 1));
            brLift.setPower(Range.clip(brPower, 0, 1));
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("lifting", isLifting);
        if(isLifting){
            telemetry.addData("FLlift",flLift.getPower());
            telemetry.addData("BLlift",blLift.getPower());
            telemetry.addData("BRlift",brLift.getPower());
        }
        telemetry.addData("imu roll", imuSensor.getRoll());
        telemetry.addData("imu pitch", imuSensor.getPitch());
    }

    public void startLift(){
        isLifting = true;
//        imuSensor.initIMU();
//        imuSensor.resetIMU();
    }

    public void stopLift(){
        isLifting = false;
        brLift.setPower(0);
        flLift.setPower(0);
        blLift.setPower(0);
    }

    public void link(IMUSensor imuSensor){
        this.imuSensor = imuSensor;
    }
}

