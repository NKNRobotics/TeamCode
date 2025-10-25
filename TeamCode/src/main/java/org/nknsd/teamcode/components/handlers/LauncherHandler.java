package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LauncherHandler implements NKNComponent {
    private TimedControlFlags timingFlag = TimedControlFlags.NO_FLAG; // flags that allow delayed control over the intake

    private DcMotor wMotor;
    private Servo scoopServo;
    private boolean enabled;
    private double wPower;

    private double targetTps;
    private double currentTps;
    private int wMotorPositionPrevious;
    private double previousRunTime = -1;
    private double ticks; // Idk what units ticks are in
    private double pFactor = 0.00001;
    private double timeToDisable;
    private MicrowaveHandler microwaveHandler;

    public void setTargetTps(double targetTps) {
       this.targetTps = targetTps;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        wMotor = hardwareMap.dcMotor.get("LM");
        scoopServo = hardwareMap.servo.get("Scoop");
        wMotor.setPower(0);
        wPower = 1; // initial wPower is set to 1 so that the speed up is more aggressive initially
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {
        setScoopToLaunch(false);
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "LauncherHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        switch (timingFlag) {
            case NO_SCOOP_CONTROL_START:
                timeToDisable = runtime.milliseconds() + 1000;
                timingFlag = TimedControlFlags.NO_SCOOP_CONTROL;
                break;

            case NO_SCOOP_CONTROL:
                if (runtime.milliseconds() > timeToDisable) {
                    timingFlag = TimedControlFlags.NO_FLAG;
                }
                break;

            default:
                break;
        }

        if (!enabled) {
            wMotor.setPower(0);
            //wPower = 0;
            return;
        }
        if (previousRunTime == -1){
            previousRunTime = runtime.seconds();
            wMotorPositionPrevious = wMotor.getCurrentPosition();
            return;
        }
        double runTimeVar = runtime.seconds();
        double timeElapsed = runTimeVar - previousRunTime;

        if (timeElapsed < 0.05){
            return;
        }


        int wMotorPosition = wMotor.getCurrentPosition();

        ticks = Math.abs(wMotorPosition - wMotorPositionPrevious) ; // I don't know what ticks actually are
        currentTps = ticks / timeElapsed;

        if ((targetTps*0.75)<currentTps){
            if (currentTps < targetTps && wPower == 1){
                wPower = .62;
            }
            wPower = wPower + (pFactor*(targetTps - currentTps));
        }

        wMotorPositionPrevious = wMotorPosition;
        previousRunTime = runTimeVar;

        if (wPower < 0){
            wPower = 0;
        }
        if (wPower > 1){
            wPower = 1;
        }
        wMotor.setPower(wPower);
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("wPower", wPower);
        telemetry.addData("currentTps", currentTps);
        telemetry.addData("targetTps", targetTps);
        telemetry.addData("scoop pos", scoopServo.getPosition());
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
        if (enabled){
            wPower = 1;
        } else{
            wPower = 0;
        }
    }

    public boolean isScoopInLaunchPosition() {
        return scoopServo.getPosition() == 1;
    }

    /**
     * Controls the servo, allowing for the launching or defaulting of the scoop servo.
     * @param launch = If true, sets the scoop to the launch position. If false, resets the scoop.
     */
    public void setScoopToLaunch(boolean launch) {
        if (timingFlag == TimedControlFlags.NO_SCOOP_CONTROL || timingFlag == TimedControlFlags.NO_SCOOP_CONTROL_START) {
            return;
        }
        if (microwaveHandler != null && !microwaveHandler.isInFirePosition()) {
            return;
        }
        scoopServo.setPosition(launch ? 1 : 0.5);
    }

    public double getCurrentTps() {
        return currentTps;
    }

    public void setDisableFlag() {
        timingFlag = TimedControlFlags.NO_SCOOP_CONTROL_START;
    }

    private enum TimedControlFlags {
        NO_FLAG,
        NO_SCOOP_CONTROL_START,
        NO_SCOOP_CONTROL
    }

    public void link(MicrowaveHandler microwaveHandler) {
        this.microwaveHandler = microwaveHandler;
    }
}
