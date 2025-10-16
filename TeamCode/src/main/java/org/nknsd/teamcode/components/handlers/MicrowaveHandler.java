package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveHandler implements NKNComponent {
    final private String servoName;
    public MicrowaveHandler(String servoName){
        this.servoName = servoName;
    }
    Servo servo;
    enum MicroState {
        LOAD1(-1),
        LOAD2(-0.3),
        LOAD3(0.4),
        FIRE1(0.05),
        FIRE2(0.75),
        FIRE3(-0.95);
        public final double microPosition;
        MicroState(double microPosition){
            this.microPosition = microPosition;
        }
    }
    public boolean setState(MicroState state) {
        servo.setPosition(state.microPosition);
        return true;
    }
    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        hardwareMap.servo.get(servoName);
        servo.setPosition(MicroState.LOAD1.microPosition);
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

    }
}
