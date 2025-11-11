package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;


@TeleOp(name = "Wheel Speed Tester", group = "Tests")
public class WheelSpeedTester extends NKNProgram {

    class ScoopState extends StateCore.State {

        final LauncherHandler launcherHandler;
        final boolean position;

        public ScoopState(LauncherHandler launcherHandler, boolean position) {
            this.launcherHandler = launcherHandler;
            this.position = position;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
//            launcherHandler.setScoopToLaunch(position);

        }

        @Override
        protected void started() {
//            launcherHandler.setScoopToLaunch(position);

        }

        @Override
        protected void stopped() {
        }
    }

    class MicroState extends StateCore.State {

        final MicrowaveHandler microwaveHandler;
        final MicrowaveHandler.MicrowavePositions position;

        public MicroState(MicrowaveHandler microwaveHandler, MicrowaveHandler.MicrowavePositions position) {
            this.microwaveHandler = microwaveHandler;
            this.position = position;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            // This code should live in microwave handler post meet 0
            // Scratch that, use the enum karsten already made but as a input to one function

            switch (position){
                case FIRE_ONE:
                    microwaveHandler.fireOne();
                    break;
                case FIRE_TWO:
                    microwaveHandler.fireTwo();
                    break;
                case FIRE_THREE:
                    microwaveHandler.fireThree();
                    break;
                case LOAD_ONE:
                    microwaveHandler.intakeOne();
                    break;
                case LOAD_TWO:
                    microwaveHandler.intakeTwo();
                    break;
                case LOAD_THREE:
                    microwaveHandler.intakeThree();
                    break;
            }
        }

        @Override
        protected void started() {

        }

        @Override
        protected void stopped() {
        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
//
//        LauncherHandler launcherHandler = new LauncherHandler();
//        components.add(launcherHandler);
//        telemetryEnabled.add(launcherHandler);
//
//        MicrowaveHandler microwaveHandler = new MicrowaveHandler();
//        components.add(microwaveHandler);
//        telemetryEnabled.add(microwaveHandler);
//
//        StateCore stateCore = new StateCore();
//        components.add(stateCore);
//        telemetryEnabled.add(stateCore);
//
//        launcherHandler.setEnabled(true);
//        launcherHandler.setTargetTps(1500);
//
//        StateCore.State timer1 = new TimerState(5000,new String[]{"microwave intake"},new String[]{ "state 2"}, new String[]{"microwave intake"} );
//        StateCore.State timer2 = new TimerState(500,new String[]{"microwave fire"},new String[]{ "state 3"}, new String[]{"microwave fire"} );
//        StateCore.State timer3 = new TimerState(500,new String[]{"scoop up"},new String[]{ "state 4"}, new String[]{"scoop up"} );
//        StateCore.State timer4 = new TimerState(500,new String[]{"scoop down"},new String[]{ "state 1"}, new String[]{"scoop down"} );
//
//
//        stateCore.addState("microwave intake", new MicroState(microwaveHandler, MicrowaveHandler.MicrowavePositions.LOAD_ONE));
//        stateCore.addState("microwave fire", new MicroState(microwaveHandler, MicrowaveHandler.MicrowavePositions.FIRE_ONE));
//        stateCore.addState("scoop up", new ScoopState(launcherHandler,true));
//        stateCore.addState("scoop down", new ScoopState(launcherHandler,false));
//        stateCore.addState("state 1", timer1);
//        stateCore.addState("state 2", timer2);
//        stateCore.addState("state 3", timer3);
//        stateCore.addState("state 4", timer4);
//        stateCore.startState("state 1");
    }
}
