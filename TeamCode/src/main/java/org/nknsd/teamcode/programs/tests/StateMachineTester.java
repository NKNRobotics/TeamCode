package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;


@TeleOp(name = "State Machine Tester", group = "Tests") @Disabled
public class StateMachineTester extends NKNProgram {

    String lastStartName = "";
    String lastStopName = "";

    class NameState extends StateCore.State {

        final String name;

        public NameState(String name) {
            this.name = name;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            telemetry.addData("RunningName",name);
            telemetry.addData("LastStartName",lastStartName);
            telemetry.addData("LastStoppedName",lastStopName);
        }

        @Override
        protected void started() {
            lastStartName = this.name;
        }

        @Override
        protected void stopped() {
            lastStopName = this.name;
        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {



        StateCore stateCore = new StateCore();
        components.add(stateCore);
        telemetryEnabled.add(stateCore);

        StateCore.State timer1 = new TimerState(5000,new String[]{"A"},new String[]{ "state 2"}, new String[]{"A"} );
        StateCore.State timer2 = new TimerState(5000,new String[]{"B"},new String[]{ "state 3"}, new String[]{"B"} );
        StateCore.State timer3 = new TimerState(5000,new String[]{"C"},new String[]{ "state 4"}, new String[]{"C"} );
        StateCore.State timer4 = new TimerState(5000,new String[]{"D"},new String[]{ "state 1"}, new String[]{"D"} );


        stateCore.addState("A", new NameState("A"));
        stateCore.addState("B", new NameState("B"));
        stateCore.addState("C", new NameState("C"));
        stateCore.addState("D", new NameState("D"));
        stateCore.addState("state 1", timer1);
        stateCore.addState("state 2", timer2);
        stateCore.addState("state 3", timer3);
        stateCore.addState("state 4", timer4);
        stateCore.startState("state 1");
    }
}
