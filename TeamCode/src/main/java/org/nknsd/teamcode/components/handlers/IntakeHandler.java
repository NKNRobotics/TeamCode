package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class IntakeHandler implements NKNComponent {
    CRServo spinner;
    private TimedControlFlags timingFlag; // flags that allow delayed control over the intake
    private double timeToDisable = -1;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        spinner = hardwareMap.crservo.get("Intake");
        spinner.setDirection(DcMotorSimple.Direction.REVERSE);
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
        return "IntakeHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        switch (timingFlag) {
            case TO_BE_DISABLED:
                timeToDisable = runtime.milliseconds() + 500;
                timingFlag = TimedControlFlags.WAITING_TO_DISABLE;
                break;

            case WAITING_TO_DISABLE:
                if (runtime.milliseconds() > timeToDisable) {
                    toggleIntake(false);
                    timingFlag = TimedControlFlags.NO_FLAG;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Intake Spinner Speed: ", spinner.getPower());
    }

    public void toggleIntake(boolean startSpinning) {
        spinner.setPower(startSpinning ? 1 : 0);
    }

    public boolean isSpinning() {
        return spinner.getPower() != 0;
    }

    /// Flags the spinner to disable itself after a delay
    public void setDisableFlag() {
        if (timingFlag == TimedControlFlags.DO_NOT_DISABLE) {
            return;
        }

        timingFlag = TimedControlFlags.TO_BE_DISABLED;
    }

    /// Flags the spinner to NOT be able to disable itself
    public void setDontDisableFlag() {
        timingFlag = TimedControlFlags.DO_NOT_DISABLE;
    }

    public void releaseDontDisableFlag() {
        if (timingFlag == TimedControlFlags.DO_NOT_DISABLE) {
            timingFlag = TimedControlFlags.NO_FLAG;
        }
    }

    private enum TimedControlFlags {
        NO_FLAG,
        TO_BE_DISABLED,
        WAITING_TO_DISABLE,
        DO_NOT_DISABLE
    }
}
