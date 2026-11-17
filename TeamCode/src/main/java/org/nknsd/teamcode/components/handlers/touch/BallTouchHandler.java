package org.nknsd.teamcode.components.handlers.touch;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.sensors.TouchSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class BallTouchHandler implements NKNComponent {

    private final static int SAMPLE_TIME_MS = 25;

    final TouchSensor sensor1;
    final TouchSensor sensor2;
    private double lastReadTime;

    public BallTouchHandler(TouchSensor sensor1, TouchSensor sensor2) {
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
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
        return "BallTouchHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        if (runtime.milliseconds() - lastReadTime < SAMPLE_TIME_MS)
            return;


        double interval = runtime.milliseconds() - lastReadTime;
        lastReadTime = runtime.milliseconds();


    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("ball", "none");
    }


}
