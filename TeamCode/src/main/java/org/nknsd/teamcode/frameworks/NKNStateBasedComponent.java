package org.nknsd.teamcode.frameworks;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class NKNStateBasedComponent implements NKNComponent {
    protected State currentState;

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        currentState.run(runtime);
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        currentState.doTelemetry();
    }

    protected boolean switchState(State state) {
        if (currentState == null) {
            currentState = state;
            currentState.onStart();
        }
        if (currentState.canSwitchToState(state)) {
            currentState.onStop();
            currentState = state;
            currentState.onStart();
            return true;
        }
        return false;
    }

    protected abstract class State {
        public abstract boolean canSwitchToState(State state);
        public void run(ElapsedTime runtime){}
        public void onStart(){}
        public void onStop(){}
        // provides a default so that lower states don't need to setup telem if they don't want to
        public void doTelemetry() {}
    }

    protected abstract class TimingState extends State {
        private double startTime = -1;
        private final double resetTime;
        protected TimingState(double resetTime) {
            this.resetTime = resetTime;
        }

        abstract protected void finishTimer();
        public void run(ElapsedTime runtime){
            if (startTime == -1) {
                startTime = runtime.milliseconds();
            }

            if (runtime.milliseconds() > startTime + resetTime) {
                finishTimer();
            }
        }
    }

}
