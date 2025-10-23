package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.ColourSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveHandler implements NKNComponent {
    private ColourSensor colourSensor;
    final private String servoName = "Spin";
    private MicroState servoState;
    Servo servo;
    ColourSensor.BallColor[] slotColors = new ColourSensor.BallColor[3];
    private IntakeHandler intakeHandler;
    private LauncherHandler launcherHandler;

    enum MicroState {
        LOAD0(0.22),
        LOAD1(0.61),
        LOAD2(0.99),
        FIRE0(0.8),
        FIRE1(0.03),
        FIRE2(0.42);
        public final double microPosition;
        MicroState(double microPosition){
            this.microPosition = microPosition;
        }
    }

    private void setState(MicroState state) {
        if (launcherHandler != null && launcherHandler.isScoopInLaunchPosition()) {
            return;
        }


        servo.setPosition(state.microPosition);
        servoState = state;

        if (intakeHandler != null) {
            intakeHandler.toggleIntake(true);
            intakeHandler.setDisableFlag();
        }

        if (launcherHandler != null) {
            launcherHandler.setScoopToLaunch(false);
            launcherHandler.setDisableFlag();
        }
    }
    public void findIntakeColour(ColourSensor.BallColor color){
        if (servoState.ordinal() < 3) {
            slotColors[servoState.ordinal()] = color;
        }
    }
    public void prepLoad() {
        int i = 0;
        boolean foundState = false;
        while( i < 3 || !foundState){
            if(slotColors[i] == ColourSensor.BallColor.NOTHING){
                setState(MicroState.values()[i]);
                foundState = true;
            }
            i++;
        }
    }

    public void prepFirePurple() {
        // until proper color sensor implementation, just pick a slot that has a ball and fire that

    }

    public void prepFireGreen() {
        // for now, until proper color sensor implementation, just do the same as prepFirePurple()
        prepFirePurple();
    }
    public void fireOne() {
        setState(MicroState.FIRE0);
    }
    public void fireTwo() {
        setState(MicroState.FIRE1);
    }
    public void fireThree() {
        setState(MicroState.FIRE2);
    }
    public void intakeOne() {
        setState(MicroState.LOAD0);
    }
    public void intakeTwo() {
        setState(MicroState.LOAD1);
    }
    public void intakeThree() {
        setState(MicroState.LOAD2);
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
        setState(MicroState.LOAD0);
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

    }
    public void link(ColourSensor colourSensor){
        this.colourSensor = colourSensor;
    }

    public void link(IntakeHandler intakeHandler) {
        this.intakeHandler = intakeHandler;
    }

    public void link(LauncherHandler launcherHandler) {
        this.launcherHandler = launcherHandler;
    }

    public boolean isInFirePosition() {
        return servoState == MicroState.FIRE0 || servoState == MicroState.FIRE1 || servoState == MicroState.FIRE2;
    }
}