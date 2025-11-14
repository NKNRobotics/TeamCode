package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveScoopHandler implements NKNComponent {


// this state makes the scoop go up and down
    class ScoopActionState extends StateCore.State{
        final double SCOOPACTIONTIMEMS = 500;

        private boolean scoopResting = false;
        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

            if (runtime.milliseconds() > (startTime + SCOOPACTIONTIMEMS)) {

                stateCore.stopAnonymous(this);

            }
            // halfway through the scoop will start coming back down
            if ((runtime.milliseconds() > (startTime + (SCOOPACTIONTIMEMS/2))) && !scoopResting) {
                scoopServo.setPosition(SERVO_REST_POS);
                // a flag just in case telling the scoop to rest over and over would create problems
                scoopResting = true;
            }
        }

        @Override
        protected void started() {
            scoopServo.setPosition(SERVO_LAUNCH_POS);
            scoopResting = false;
        }

        @Override
        protected void stopped() {

        }
    }
    // this state is essentially a timer for the microwave moving
    class MicrowaveActionState extends StateCore.State{
        final double MICROWAVEACTIONTIMEMS = 500;
        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (runtime.milliseconds() > (startTime + MICROWAVEACTIONTIMEMS)) {
                stateCore.stopAnonymous(this);
            }

        }

        @Override
        protected void started() {
            // the intake has to spin during this for some reason
            intakeHandler.toggleIntake(true);
        }

        @Override
        protected void stopped() {
            intakeHandler.toggleIntake(false);
        }
    }

    private BallColorInterpreter colourSensorInterpreter;
    final private String microwaveServoName = "Spin";
    final private String scoopServoName = "Scoop";
    private ScoopActionState scoopActionState = new ScoopActionState();
    private MicrowaveActionState microwaveActionState = new MicrowaveActionState();

    private MicrowaveState microwavePos;
    Servo microwaveServo;
    Servo scoopServo;
    private IntakeHandler intakeHandler;
    private StateCore stateCore;
    private static final double SERVO_REST_POS = 0.5;
    private static final double SERVO_LAUNCH_POS = 1;

    public boolean setMicrowaveState(MicrowaveState position) {
        if (!isDone()){
            return false;
        }
        microwaveServo.setPosition(position.microPosition);
        microwavePos = position;
        stateCore.startAnonymous(microwaveActionState);
        return true;
    }
    public boolean doScoopLaunch() {
        if (!isDone()){
            return false;
        }
        stateCore.startAnonymous(scoopActionState);
        return true;
    }
    public boolean isDone() {
        // to ensure that things aren't happening before starting them
        return !(scoopActionState.isRunning() || microwaveActionState.isRunning());
    }


    public MicrowaveState getMicrowaveState() {
        return microwavePos;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        microwaveServo = hardwareMap.servo.get(microwaveServoName);
        scoopServo = hardwareMap.servo.get(scoopServoName);
        scoopServo.setPosition(SERVO_REST_POS);
        microwaveServo.setPosition(MicrowaveState.FIRE0.microPosition);
        microwavePos = MicrowaveState.FIRE0;
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
        return "MicrowaveScoopHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Microwave", microwavePos.name());
    }

    public void link(IntakeHandler intakeHandler) {
        this.intakeHandler = intakeHandler;
    }

    public void link(StateCore stateCore) {
        this.stateCore = stateCore;
    }

}