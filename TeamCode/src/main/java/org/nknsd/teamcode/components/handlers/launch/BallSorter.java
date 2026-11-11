package org.nknsd.teamcode.components.handlers.launch;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.TouchSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class BallSorter implements NKNComponent {
    private Ball currentBall;
    private TouchSensor noneSens;
    private TouchSensor nectarSens;

    public Ball getCurrentBall() {
        return currentBall;
    }


    public void link(TouchSensor noneSens, TouchSensor nectarSens) {
        this.noneSens = noneSens;
        this.nectarSens = nectarSens;
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
        if (noneSens.isTouching()) {
            currentBall = Ball.NONE;
        } else if (nectarSens.isTouching()) {
            currentBall = Ball.NECTAR;
        } else {
            currentBall = Ball.POLLEN;
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addLine("Current ball: " + currentBall);
    }
}