package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;

@TeleOp(name = "MicrowaveTester", group = "Tests")
public class MicrowaveTester extends NKNProgram {

    class TimedSlotSwitchState extends TimerState {

        final MicrowaveScoopHandler microwaveScoopHandler;
        final MicrowavePositions slot;

        public TimedSlotSwitchState(MicrowaveScoopHandler microwaveScoopHandler, MicrowavePositions slot, double timerMS, String toStartOnEnd) {
            super(timerMS, new String[]{}, new String[]{toStartOnEnd}, new String[]{});
            this.microwaveScoopHandler = microwaveScoopHandler;
            this.slot = slot;
        }

        @Override
        protected void internalStarted() {
            microwaveScoopHandler.setMicrowavePosition(slot);
        }

    }

    class IsDoneSlotSwitchState extends StateCore.State {

        final MicrowaveScoopHandler microwaveScoopHandler;
        final MicrowavePositions slot;

        IsDoneSlotSwitchState(MicrowaveScoopHandler microwaveScoopHandler, MicrowavePositions slot) {
            this.microwaveScoopHandler = microwaveScoopHandler;
            this.slot = slot;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (microwaveScoopHandler.isDone()) {
                stateCore.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
            microwaveScoopHandler.setMicrowavePosition(slot);
        }

        @Override
        protected void stopped() {
            if (slot != MicrowavePositions.FIRE2) {
                stateCore.startAnonymous(new IsDoneSlotSwitchState(microwaveScoopHandler,  MicrowavePositions.values()[slot.ordinal()+1]));
            } else {
                stateCore.startAnonymous(new IsDoneSlotSwitchState(microwaveScoopHandler, MicrowavePositions.LOAD0));
            }
        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);
        telemetryEnabled.add(microwaveScoopHandler);

//        ColorReader colorReader = new ColorReader("ColorSensor");
//        components.add(colorReader);
//        telemetryEnabled.add(colorReader);
//
//        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.01);
//        components.add(ballColorInterpreter);
//        telemetryEnabled.add(ballColorInterpreter);

//        SlotTracker slotTracker = new SlotTracker();
//        components.add(slotTracker);
//        telemetryEnabled.add(slotTracker);

        StateCore stateCore = new StateCore();
        components.add(stateCore);

        microwaveScoopHandler.link(stateCore);
//        ballColorInterpreter.link(colorReader);
//        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);

//        stateCore.addState("load0", new TimedSlotSwitchState(microwaveScoopHandler, MicrowavePositions.LOAD0, 5000, "load1"));
//        stateCore.addState("load1", new TimedSlotSwitchState(microwaveScoopHandler, MicrowavePositions.LOAD1, 5000, "load2"));
//        stateCore.addState("load2", new TimedSlotSwitchState(microwaveScoopHandler, MicrowavePositions.LOAD2, 5000, "fire0"));
//        stateCore.addState("fire0", new TimedSlotSwitchState(microwaveScoopHandler, MicrowavePositions.FIRE0, 5000, "fire1"));
//        stateCore.addState("fire1", new TimedSlotSwitchState(microwaveScoopHandler, MicrowavePositions.FIRE1, 5000, "fire2"));
//        stateCore.addState("fire2", new TimedSlotSwitchState(microwaveScoopHandler, MicrowavePositions.FIRE2, 5000, "load0"));
//        stateCore.startState("load0");

        stateCore.startAnonymous(new IsDoneSlotSwitchState(microwaveScoopHandler, MicrowavePositions.LOAD0));
    }
}