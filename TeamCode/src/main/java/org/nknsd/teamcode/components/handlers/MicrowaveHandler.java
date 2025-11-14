package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.states.FindAllColors;

public class MicrowaveHandler implements NKNComponent {


    private BallColorInterpreter colourSensorInterpreter;
    final private String servoName = "Spin";
    private MicrowaveState microwavePos;
    Servo servo;
    private IntakeHandler intakeHandler;
    private ScoopHandler scoopHandler;
    private SlotTracker slotTracker;
    private StateCore stateCore;

    public enum MicrowaveState {
        LOAD0(0.22),
        LOAD1(0.61),
        LOAD2(0.99),
        FIRE0(0.8),
        FIRE1(0.03),
        FIRE2(0.42);
        public final double microPosition;

        MicrowaveState(double microPosition) {
            this.microPosition = microPosition;
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
    public void findAllColors(){
        stateCore.addState("findAllColors", new FindAllColors(this));
        stateCore.startState("findAllColors");
    }
    public void loadBall(){
        int i = 0;
        boolean fired = false;
        while(i < 3 && !fired) {
            if(slotTracker.getSlotColor(i) == BallColor.NOTHING || slotTracker.getSlotColor(i) == BallColor.UNSURE){
                if(i == 0) {
                    setMicrowaveState(MicrowaveState.LOAD0);
                } else if (i == 1) {
                    setMicrowaveState(MicrowaveState.LOAD1);
                } else {
                    setMicrowaveState(MicrowaveState.LOAD2);
                }
                fired = true;
            }
            i++;
        }
    }
    public void firePurple(){
        int i = 0;
        boolean fired = false;
        while(i < 3 && !fired) {
            if(slotTracker.getSlotColor(i) == BallColor.PURPLE){
                if(i == 0) {
                    setMicrowaveState(MicrowaveState.FIRE0);
                } else if (i == 1) {
                    setMicrowaveState(MicrowaveState.FIRE1);
                } else {
                    setMicrowaveState(MicrowaveState.FIRE2);
                }
                fired = true;
            }
            i++;
        }
    }
    public void fireGreen(){
        int i = 0;
        boolean fired = false;
        while(i < 3 && !fired) {



            if(slotTracker.getSlotColor(i) == BallColor.GREEN){
                if(i == 0) {
                    setMicrowaveState(MicrowaveState.FIRE0);
                } else if (i == 1) {
                    setMicrowaveState(MicrowaveState.FIRE1);
                } else {
                    setMicrowaveState(MicrowaveState.FIRE2);
                }
                fired = true;
            }
            i++;
        }
    }
    public MicrowaveState getMicrowaveState() {
        return microwavePos;
    }
    public boolean isInFirePosition() {
        return (microwavePos == MicrowaveState.FIRE0 || microwavePos == MicrowaveState.FIRE1 || microwavePos == MicrowaveState.FIRE2);
    }
    /// BAD CODE PLEASE DELETE ONCE DONE PLEASE PLEASE PLEASE
    public void resetLaunchedColor() {
        if (isInFirePosition()) {
            slotTracker.clearOneSlotColor(getMicrowaveState().ordinal() - 3);
        }
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
//        telemetry.addData("hi", "hello good friend");
        telemetry.addData("Microwave", microwavePos.name());
    }

    public void link(IntakeHandler intakeHandler) {
        this.intakeHandler = intakeHandler;
    }

    public void link(ScoopHandler scoopHandler) {
        this.scoopHandler = scoopHandler;
    }
    public void link(SlotTracker slotTracker) {
        this.slotTracker = slotTracker;
    }
    public void link(StateCore stateCore){
        this.stateCore = stateCore;
    }
}