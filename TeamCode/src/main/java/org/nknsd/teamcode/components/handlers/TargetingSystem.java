package org.nknsd.teamcode.components.handlers;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;

public class TargetingSystem implements NKNComponent {

    private BasketLocator basketLocator;
    PowerInputMixer powerInputMixer;

    ID targetingColor;

    double lastRunTime;
    double lastOffset;

    double vel;

    final private PidController pidController;

    public TargetingSystem(PidController pidController) {
        this.pidController = pidController;
    }


    public boolean targetAcquired() {
        return Math.abs(basketLocator.getOffset(targetingColor).xOffset - 0.5) < 0.1 && Math.abs(vel) < 0.05;
    }

    public void enableAutoTargeting(boolean enable) {
        if (enable) {
            powerInputMixer.setAutoEnabled(new boolean[]{true, true, true});
        } else {
            powerInputMixer.setAutoEnabled(new boolean[]{false, false, false});
        }
    }

    public double getDistance() {
        return basketLocator.getOffset(targetingColor).distance;
    }

    public void setTargetingColor(ID color) {
        if (color == ID.RED || color == ID.BLUE) {
            targetingColor = color;
        }
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
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
        if (runtime.milliseconds() - lastRunTime > 50) {
            double[] targetingPowers = new double[]{0, 0, 0};

            if (basketLocator.getOffset(targetingColor).distance != -1) {
                vel = basketLocator.getOffset(targetingColor).xOffset - lastOffset;
                targetingPowers[2] = pidController.findOutput(basketLocator.getOffset(targetingColor).xOffset - 0.5, 0, vel, 50);
//            RobotLog.v("xOffset" + basketLocator.getOffset(targetingColor).xOffset);
                lastOffset = basketLocator.getOffset((targetingColor)).xOffset;
            }
            powerInputMixer.setAutoPowers(targetingPowers, true);
            lastRunTime = runtime.milliseconds();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(BasketLocator basketLocator, PowerInputMixer powerInputMixer) {
        this.basketLocator = basketLocator;
        this.powerInputMixer = powerInputMixer;
    }
}
