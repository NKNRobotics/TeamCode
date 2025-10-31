package org.nknsd.teamcode.states;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.FlowAverager;
//import org.nknsd.teamcode.components.sensors.FlowHandler;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.sensors.WufSpotSensor;
import org.nknsd.teamcode.components.utility.StateCore;

public class WufSpinner extends StateCore.State {

    static public String STATE_NAME = "WUF_SPINNER";

    final WufSpotSensor wufSpotSensor;
    final MotorDriver motorDriver;
    final FlowAverager flowHandler;

    public WufSpinner(WufSpotSensor wufSpotSensor, MotorDriver motorDriver, FlowAverager flowHandler) {
        this.wufSpotSensor = wufSpotSensor;
        this.motorDriver = motorDriver;
        this.flowHandler = flowHandler;
    }

    double lastTime;
    SparkFunOTOS.Pose2D pos = new SparkFunOTOS.Pose2D();
    double startH;

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (wufSpotSensor.doesWufExist()) {
            stateCore.stopAnonymous(this);
        }

        pos.h = (runtime.milliseconds() - startTime) / 10000 * Math.PI * 2 + startH;
        motorDriver.setTarget(pos);

    }

    @Override
    protected void started() {
        pos = flowHandler.getAvPos();
        startH = pos.h;
    }

    @Override
    protected void stopped() {
        stateCore.startState(WufHunter.STATE_NAME);
    }
}
