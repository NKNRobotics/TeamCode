package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Microwave Scoop Tester", group = "Tests")
public class MicrowaveScoopTester extends NKNProgram {

    class SlotSwitchState extends StateMachine.State {

        final MicrowaveScoopHandler microwaveHandler;
        final MicrowavePositions state;
        private final boolean doLaunch;
        private final String toStartOnEnd;

        public SlotSwitchState(MicrowaveScoopHandler microwaveHandler, MicrowavePositions state, boolean doLaunch,
                               String toStartOnEnd) {
            this.microwaveHandler = microwaveHandler;
            this.state = state;
            this.doLaunch = doLaunch;
            this.toStartOnEnd = toStartOnEnd;
        }


        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(microwaveHandler.isDone()) {
                StateMachine.INSTANCE.startState(toStartOnEnd);
                StateMachine.INSTANCE.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
            if (doLaunch){
                microwaveHandler.doScoopLaunch();
            }else{
                microwaveHandler.setMicrowavePosition(state);
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


        components.add(StateMachine.INSTANCE);



        StateMachine.INSTANCE.addState("load0", new SlotSwitchState(microwaveHandler, MicrowavePositions.LOAD0, false, "load1"));
        StateMachine.INSTANCE.addState("load1", new SlotSwitchState(microwaveHandler, MicrowavePositions.LOAD1, false, "load2"));
        StateMachine.INSTANCE.addState("load2", new SlotSwitchState(microwaveHandler, MicrowavePositions.LOAD2, false, "fire0"));
        StateMachine.INSTANCE.addState("fire0", new SlotSwitchState(microwaveHandler, MicrowavePositions.FIRE0, false, "fire0launch"));
        StateMachine.INSTANCE.addState("fire1", new SlotSwitchState(microwaveHandler, MicrowavePositions.FIRE1, false, "fire1launch"));
        StateMachine.INSTANCE.addState("fire2", new SlotSwitchState(microwaveHandler, MicrowavePositions.FIRE2, false, "fire2launch"));
        StateMachine.INSTANCE.addState("fire0launch", new SlotSwitchState(microwaveHandler, MicrowavePositions.FIRE0, true, "fire1"));
        StateMachine.INSTANCE.addState("fire1launch", new SlotSwitchState(microwaveHandler, MicrowavePositions.FIRE1, true, "fire2"));
        StateMachine.INSTANCE.addState("fire2launch", new SlotSwitchState(microwaveHandler, MicrowavePositions.FIRE2, true, "load0"));

        StateMachine.INSTANCE.startState("load0");
    }
}