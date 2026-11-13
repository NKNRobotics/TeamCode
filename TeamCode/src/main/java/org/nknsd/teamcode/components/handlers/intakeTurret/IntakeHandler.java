package org.nknsd.teamcode.components.handlers.intakeTurret;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class IntakeHandler implements NKNComponent {

    private DcMotor intakeMotor;
    private final double power = .7;


    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        intakeMotor = hardwareMap.dcMotor.get("intake");
        intakeMotor.setPower(0);
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

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Intake forward ", intakeForward);
    }

    private boolean intakeForward = true;

    //this is made kinda poorly (start intake and reverse intake).
    //We should probably just have an startIntake(bool forward) and stopIntake()
    public void startIntake(boolean start) {
        if (start) {
            if (intakeForward) {
                intakeMotor.setPower(power);
            } else {
                intakeMotor.setPower(-power);
            }
        } else {
            intakeMotor.setPower(0);
        }
    }

    public void reverseIntake(boolean reverse) {
        intakeForward = reverse;
        if (intakeMotor.getPower() != 0) {
            startIntake(true);
        }
    }

    public boolean getDirection() {
        return intakeForward;
    }

    public boolean intakeRunning() {
        return intakeMotor.getPower() != 0;
    }
}