package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.utility.StateCore;

public class FindAllColors extends StateCore.State {

    final MicrowaveHandler microwaveHandler;

    public FindAllColors(MicrowaveHandler microwaveHandler) {
        this.microwaveHandler = microwaveHandler;
    }

    double startTime;
    double timeDelay = 700;
    boolean initializing = true;
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

        if (initializing){
            startTime = runtime.milliseconds();
            microwaveHandler.setMicrowaveState(MicrowaveHandler.MicrowaveState.LOAD0);
            initializing = false;
        }

        if (runtime.milliseconds() >= startTime + (3 * timeDelay)) {
            stateCore.stopAnonymous(this);
        } else if(runtime.milliseconds() >= startTime + (2 * timeDelay)){

            microwaveHandler.setMicrowaveState(MicrowaveHandler.MicrowaveState.LOAD2);
        } else if (runtime.milliseconds() >= startTime + timeDelay){
            microwaveHandler.setMicrowaveState(MicrowaveHandler.MicrowaveState.LOAD1);
        }

    }

    @Override
    protected void started() {

    }

    @Override
    protected void stopped() {

    }
}
