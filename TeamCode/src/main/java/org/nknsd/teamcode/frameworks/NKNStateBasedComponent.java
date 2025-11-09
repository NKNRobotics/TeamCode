package org.nknsd.teamcode.frameworks;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class NKNStateBasedComponent implements NKNComponent {
    protected State state;

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        state.run(runtime);
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        state.doTelemetry();
    }

    public abstract class State {
        abstract boolean canSwitchToState(Class<State> state);
        abstract void run(ElapsedTime runtime);
        abstract void onStart(ElapsedTime runtime);
        abstract void onStop(ElapsedTime runtime);
        // provides a default so that lower states don't need to setup telem if they don't want to
        public void doTelemetry() {}
    }


}
