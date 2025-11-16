package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.MicrowaveState;
import org.nknsd.teamcode.components.handlers.SlotTracker;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;

@TeleOp(name = "MicrowaveTester", group = "Tests")
public class MicrowaveTester extends NKNProgram {

    class SlotSwitchState extends TimerState {

        final MicrowaveScoopHandler microwaveScoopHandler;
        final MicrowaveState state;

        public SlotSwitchState(MicrowaveScoopHandler microwaveScoopHandler, MicrowaveState state,
                               double timerMS, String toStartOnEnd) {
            super(timerMS, new String[]{}, new String[]{toStartOnEnd}, new String[]{});
            this.microwaveScoopHandler = microwaveScoopHandler;
            this.state = state;
        }

        @Override
        protected void internalStarted() {
            microwaveScoopHandler.setMicrowaveState(state);
        }

    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);
        telemetryEnabled.add(microwaveScoopHandler);

        ColorReader colorReader  = new ColorReader("ColorSensor");
        components.add(colorReader);
        telemetryEnabled.add(colorReader);

        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10,0.01);
        components.add(ballColorInterpreter);
        telemetryEnabled.add(ballColorInterpreter);

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);
        telemetryEnabled.add(slotTracker);

        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        ballColorInterpreter.link(colorReader);

        StateCore stateCore = new StateCore();
        components.add(stateCore);

        stateCore.addState("load0", new SlotSwitchState(microwaveScoopHandler, MicrowaveState.LOAD0, 5000, "load1"));
        stateCore.addState("load1", new SlotSwitchState(microwaveScoopHandler, MicrowaveState.LOAD1, 5000, "load2"));
        stateCore.addState("load2", new SlotSwitchState(microwaveScoopHandler, MicrowaveState.LOAD2, 5000, "fire0"));
        stateCore.addState("fire0", new SlotSwitchState(microwaveScoopHandler, MicrowaveState.FIRE0, 5000, "fire1"));
        stateCore.addState("fire1", new SlotSwitchState(microwaveScoopHandler, MicrowaveState.FIRE1, 5000, "fire2"));
        stateCore.addState("fire2", new SlotSwitchState(microwaveScoopHandler, MicrowaveState.FIRE2, 5000, "load0"));

        stateCore.startState("load0");
    }
}