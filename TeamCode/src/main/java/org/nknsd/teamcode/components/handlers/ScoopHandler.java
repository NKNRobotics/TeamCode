package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNStateBasedComponent;

public class ScoopHandler extends NKNStateBasedComponent {
    static final double SERVO_REST_POS = 0.5;
    static final double SERVO_LAUNCH_POS = 1;

    private Servo scoopServo;
    private MicrowaveHandler microwaveHandler;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        scoopServo = hardwareMap.servo.get("Scoop");
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {
        returnScoopToRest();
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "Scoop Handler";

    }

    public void link(MicrowaveHandler microwaveHandler) {
        this.microwaveHandler = microwaveHandler;
    }

    // SETTERS
    void returnScoopToRest() {
        switchState(new RestState(scoopServo));
    }
    public boolean triggerScoopToLaunch() {
        return switchState(new LaunchState(scoopServo, this));
    }
    public boolean lockOutServo() {
        return switchState(new LockedState(this));
    }


    // GETTERS
    public boolean isInLaunch() {
        return currentState.getClass() == LaunchState.class;
    }

    public boolean isLocked() {
        return  currentState.getClass() == LockedState.class;
    }


    // STATES
    private class RestState extends State {
        private final Servo servo;
        RestState(Servo servo){
            this.servo = servo;
        }
        @Override
        public boolean canSwitchToState(State state) {
            return state.getClass() == LaunchState.class || state.getClass() == LockedState.class;
        }

        @Override
        public void onStart() {
            servo.setPosition(ScoopHandler.SERVO_REST_POS);
        }
    }
    private class LaunchState extends TimingState {
        private final Servo servo;
        private final ScoopHandler master;

        LaunchState(Servo servo, ScoopHandler master){
            super(100);
            this.servo = servo;
            this.master = master;
        }

        @Override
        public boolean canSwitchToState(State state) {
            return state.getClass() == RestState.class;
        }

        @Override
        public void onStart() {
            // if the microwave wasn't in the right position for firing, quickly go back to rest lol
            if (!master.microwaveHandler.isInFirePosition()) {
                master.returnScoopToRest();
            }
            servo.setPosition(SERVO_LAUNCH_POS);
        }

        @Override
        public void onStop() {
            servo.setPosition(SERVO_REST_POS);
        }

        @Override
        protected void finishTimer() {
            master.returnScoopToRest();
        }
    }
    private class LockedState extends TimingState {
        private final ScoopHandler master;

        LockedState(ScoopHandler master){
            super(500);
            this.master = master;
        }

        @Override
        public boolean canSwitchToState(State state) {
            return state.getClass() == RestState.class || state.getClass() == LockedState.class;
        }

        @Override
        protected void finishTimer() {
            master.returnScoopToRest();
        }
    }

}
