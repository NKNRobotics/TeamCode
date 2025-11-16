package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.MicrowaveState;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "MicrowaveTester", group = "Tests")
public class MicrowaveScoopTester extends NKNProgram {

    class SlotSwitchState extends StateCore.State {

        final MicrowaveScoopHandler microwaveHandler;
        final MicrowaveState state;
        private final boolean doLaunch;
        private final String toStartOnEnd;

        public SlotSwitchState(MicrowaveScoopHandler microwaveHandler, MicrowaveState state, boolean doLaunch,
                               String toStartOnEnd) {
            this.microwaveHandler = microwaveHandler;
            this.state = state;
            this.doLaunch = doLaunch;
            this.toStartOnEnd = toStartOnEnd;
        }


        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(microwaveHandler.isDone()) {
                stateCore.startState(toStartOnEnd);
                stateCore.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
            if (doLaunch){
                microwaveHandler.doScoopLaunch();
            }else{
                microwaveHandler.setMicrowaveState(state);
            }
        }

        @Override
        protected void stopped() {

        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        MicrowaveScoopHandler microwaveHandler = new MicrowaveScoopHandler();
        components.add(microwaveHandler);
        telemetryEnabled.add(microwaveHandler);

        ColorReader colorReader  = new ColorReader("ColorSensor");
        components.add(colorReader);
        telemetryEnabled.add(colorReader);

        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10,0.01);
        components.add(ballColorInterpreter);
        telemetryEnabled.add(ballColorInterpreter);

//        SlotTracker slotTracker = new SlotTracker();
//        components.add(slotTracker);
//        telemetryEnabled.add(slotTracker);

       // slotTracker.link(microwaveHandler, ballColorInterpreter);
        ballColorInterpreter.link(colorReader);

        StateCore stateCore = new StateCore();
        components.add(stateCore);

        microwaveHandler.link(stateCore);

        stateCore.addState("load0", new SlotSwitchState(microwaveHandler, MicrowaveState.LOAD0, false, "load1"));
        stateCore.addState("load1", new SlotSwitchState(microwaveHandler, MicrowaveState.LOAD1, false, "load2"));
        stateCore.addState("load2", new SlotSwitchState(microwaveHandler, MicrowaveState.LOAD2, false, "fire0"));
        stateCore.addState("fire0", new SlotSwitchState(microwaveHandler, MicrowaveState.FIRE0, false, "fire0launch"));
        stateCore.addState("fire1", new SlotSwitchState(microwaveHandler, MicrowaveState.FIRE1, false, "fire1launch"));
        stateCore.addState("fire2", new SlotSwitchState(microwaveHandler, MicrowaveState.FIRE2, false, "fire2launch"));
        stateCore.addState("fire0launch", new SlotSwitchState(microwaveHandler, MicrowaveState.FIRE0, true, "fire1"));
        stateCore.addState("fire1launch", new SlotSwitchState(microwaveHandler, MicrowaveState.FIRE1, true, "fire2"));
        stateCore.addState("fire2launch", new SlotSwitchState(microwaveHandler, MicrowaveState.FIRE2, true, "load0"));

        stateCore.startState("load0");
    }
}