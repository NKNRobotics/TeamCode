package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveHandler implements NKNComponent {


    private BallColorInterpreter colourSensorInterpreter;
    final private String servoName = "Spin";
    private MicrowaveState microwavePos;
    Servo servo;
    private IntakeHandler intakeHandler;
    private ScoopHandler scoopHandler;

    public enum MicrowaveState {
        LOAD0(0.22, false),
        LOAD1(0.61, false),
        LOAD2(0.99, false),
        FIRE0(0.8, true),
        FIRE1(0.03, true),
        FIRE2(0.42, true);
        public final double microPosition;
        public final boolean isAFirePosition;

        MicrowaveState(double microPosition, boolean isAFirePosition) {
            this.microPosition = microPosition;
            this.isAFirePosition = isAFirePosition;
        }
    }

    public void setMicrowaveState(MicrowaveState state) {
        if (microwavePos == state) {
            return;
        }

        // if we failed to lock out the servo, return
        if (scoopHandler != null && !scoopHandler.lockOutServo()) {
            return;
        }

        servo.setPosition(state.microPosition);
        microwavePos = state;

        if (intakeHandler != null) {
            intakeHandler.toggleIntake(true);
            intakeHandler.setDisableFlag();
        }
    }

    public MicrowaveState getMicrowaveState() {
        return microwavePos;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        servo = hardwareMap.servo.get(servoName);
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {
        setMicrowaveState(MicrowaveState.LOAD0);
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "MicrowaveHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("hi", "hello good friend");
        telemetry.addData("Microwave", microwavePos.name());
    }

    public void link(IntakeHandler intakeHandler) {
        this.intakeHandler = intakeHandler;
    }

    public void link(ScoopHandler scoopHandler) {
        this.scoopHandler = scoopHandler;
    }
}