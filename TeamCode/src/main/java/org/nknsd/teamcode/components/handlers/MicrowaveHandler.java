package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.ColourSensorInterpreter;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveHandler implements NKNComponent {


    //I don't know why David added this, I already made the enumerator down there, please have david remove this enum later
    public enum MicrowavePositions {
        FIRE_ONE,FIRE_TWO,FIRE_THREE,LOAD_ONE,LOAD_TWO,LOAD_THREE;
    }
    private ColourSensorInterpreter colourSensorInterpreter;
    final private String servoName = "Spin";
    private MicrowaveState servoState;
    Servo servo;
    ColourSensorInterpreter.BallColor[] slotColors = new ColourSensorInterpreter.BallColor[3];
    private IntakeHandler intakeHandler;
    private LauncherHandler launcherHandler;

    public enum MicrowaveState {
        LOAD0(0.22),
        LOAD1(0.61),
        LOAD2(0.99),
        FIRE0(0.8),
        FIRE1(0.03),
        FIRE2(0.42);
        public final double microPosition;
        MicrowaveState(double microPosition){
            this.microPosition = microPosition;
        }
    }

    public void setMicrowaveState(MicrowaveState state) {
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
    public void findIntakeColour(ColourSensorInterpreter.BallColor color){
        if (servoState.ordinal() < 3) {
            slotColors[servoState.ordinal()] = color;
        }
    }
    public void prepLoad() {
        int i = 0;
        boolean foundState = false;
        while( i < 3 || !foundState){
            if(slotColors[i] == ColourSensorInterpreter.BallColor.NOTHING){
                setMicrowaveState(MicrowaveState.values()[i]);
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
        setMicrowaveState(MicrowaveState.FIRE0);
    }
    public void fireTwo() {
        setMicrowaveState(MicrowaveState.FIRE1);
    }
    public void fireThree() {
        setMicrowaveState(MicrowaveState.FIRE2);
    }
    public void intakeOne() {
        setMicrowaveState(MicrowaveState.LOAD0);
    }
    public void intakeTwo() {
        setMicrowaveState(MicrowaveState.LOAD1);
    }
    public void intakeThree() {
        setMicrowaveState(MicrowaveState.LOAD2);
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
        if(!isInFirePosition()){
            if(colourSensorInterpreter.isReady());
            slotColors[servoState.ordinal()] = colourSensorInterpreter.getColorGuess();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Microwave", servoState.name());

    }

    public void link(ColourSensorInterpreter colourSensorInterpreter) {this.colourSensorInterpreter = colourSensorInterpreter;}
    public void link(IntakeHandler intakeHandler) {
        this.intakeHandler = intakeHandler;
    }
    public void link(LauncherHandler launcherHandler) {
        this.launcherHandler = launcherHandler;
    }

    public boolean isInFirePosition() {
        return servoState == MicrowaveState.FIRE0 || servoState == MicrowaveState.FIRE1 || servoState == MicrowaveState.FIRE2;
    }
}