package org.nknsd.teamcode.components.handlers.launch;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LauncherHandler implements NKNComponent {
    private static final double TICK_COUNT_INTERVAL_SECONDS = .05;
    private DcMotor wMotor;
    private boolean enabled;
    private double wPower;

    private double targetTps;
    private double currentTps;
    private double tpsError;

    private int confidenceNum;
    private boolean powerLocked;

    private int wMotorPositionPrevious;
    private double previousRunTime = -1;
    private double ticks; // Idk what units ticks are in
    private double pFactor = 0.0002;

    public LauncherHandler(double lowerThreshold, double upperThreshold) {
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
    }

    public void setTargetTps(double targetTps) {
        this.targetTps = targetTps;
    }

    private final double lowerThreshold;
    private final double upperThreshold;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        wMotor = hardwareMap.dcMotor.get("LM");
        wMotor.setPower(0);
        wPower = 1; // initial wPower is set to 1 so that the speed up is more aggressive initially
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
        return "LauncherHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        if (!enabled) {
            wMotor.setPower(0);
            //wPower = 0;
            return;
        }

        if (previousRunTime == -1) {
            previousRunTime = runtime.seconds();
            wMotorPositionPrevious = wMotor.getCurrentPosition();
            return;
        }

        double timeElapsed = runtime.seconds() - previousRunTime;
        if (timeElapsed < TICK_COUNT_INTERVAL_SECONDS) {
            return;
        }


        int wMotorPosition = wMotor.getCurrentPosition();

        ticks = Math.abs(wMotorPosition - wMotorPositionPrevious);
        currentTps = ticks / timeElapsed;

        tpsError = currentTps - targetTps;

        if (currentTps > targetTps * lowerThreshold && currentTps < targetTps * upperThreshold) {
            confidenceNum++;
        } else {
            confidenceNum = 0;
        }

        if (!powerLocked) {
            wPower = wPower + (pFactor * (targetTps - currentTps));
        }

        wMotorPositionPrevious = wMotorPosition;
        previousRunTime = runtime.seconds();

        if (wPower < 0) {
            wPower = 0;
        }
        if (wPower > 1) {
            wPower = 1;
        }

//        Automatically sets the speed to full or none if off by a lot. It should be noted that this doesn't change wPower
        if (targetTps * upperThreshold  * upperThreshold < currentTps) {
            wMotor.setPower(0);
//            RobotLog.v("too high! no power");
        } else if (targetTps * lowerThreshold * lowerThreshold  > currentTps) {
            wMotor.setPower(1);
//            RobotLog.v("too low! full power");
        } else {
            wMotor.setPower(wPower);
//            RobotLog.v("calculating speeds to set");
        }

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("wPower", wPower);
        telemetry.addData("currentTps", currentTps);
        telemetry.addData("targetTps", targetTps);
        telemetry.addData("confidence", confidenceNum);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            wPower = 1;
        } else {
            wPower = 0;
        }
    }

    public double getCurrentTps() {
        return currentTps;
    }

    public double getTpsError() {
        return tpsError;
    }

//    how many times in a row the wheel has been good to launch
    public int launchConfidence() {
        return confidenceNum;
    }

    public void resetConfidence() {
        confidenceNum = 0;
    }

//    stops changing the wPower and just keeps it at whatever it was
    public void lockPower(boolean toggleLock) {
        powerLocked = toggleLock;
    }
}
