package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;

@TeleOp(name = "StateMachine Test", group = "Tests")
public class StateMachineTest extends NKNProgram {

    private int myCounter = 0;

    class State1 extends StateCore.State {


        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            telemetry.addLine("1b");
        }

        @Override
        protected void started() {
            telemetry.addLine("making timer");
            TimerState timer = new TimerState(6000, new String[]{"2b"}, new String[]{"1b"});
            telemetry.addLine("starting timer");
            stateCore.startAnonymous(timer);
        }

        @Override
        protected void stopped() {

        }
    }

    static class State2 extends StateCore.State {

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            telemetry.addLine("2b");
        }

        @Override
        protected void started() {
            TimerState timer = new TimerState(6000, new String[]{"1b"}, new String[]{"2b"});
            stateCore.startAnonymous(timer);
        }

        @Override
        protected void stopped() {

        }
    }

    static class State1ExtTimer extends StateCore.State {


        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            telemetry.addLine("1x");
        }

        @Override
        protected void started() {
            stateCore.startState("1Timer");
        }

        @Override
        protected void stopped() {

        }
    }

    static class State2ExtTimer extends StateCore.State {

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            telemetry.addLine("2x");
        }

        @Override
        protected void started() {
            stateCore.startState("2Timer");
        }

        @Override
        protected void stopped() {

        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateCore stateCore = new StateCore();
        components.add(stateCore);
        telemetryEnabled.add(stateCore);

        stateCore.addState("1", new State1ExtTimer());
        stateCore.addState("2", new State2ExtTimer());
        stateCore.addState("1Timer",new TimerState(4000,new String[]{"2"}, new String[]{"1"}));
        stateCore.addState("2Timer",new TimerState(4000,new String[]{"1"}, new String[]{"2"}));
        stateCore.startState("1");

        stateCore.addState("1b", new State1());
        stateCore.addState("2b", new State2());
        stateCore.startState("1b");
    }
}
