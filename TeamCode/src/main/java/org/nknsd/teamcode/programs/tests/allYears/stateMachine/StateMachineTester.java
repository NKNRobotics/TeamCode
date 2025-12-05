package org.nknsd.teamcode.programs.tests.allYears.stateMachine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.components.utility.states.TimerState;

import java.util.List;


@TeleOp(name = "State Machine Tester", group = "Tests") @Disabled
public class StateMachineTester extends NKNProgram {

    String lastStartName = "";
    String lastStopName = "";

    class NameState extends StateMachine.State {

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

        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);

        StateMachine.State timer1 = new TimerState(5000,new String[]{"A"},new String[]{ "state 2"}, new String[]{"A"} );
        StateMachine.State timer2 = new TimerState(5000,new String[]{"B"},new String[]{ "state 3"}, new String[]{"B"} );
        StateMachine.State timer3 = new TimerState(5000,new String[]{"C"},new String[]{ "state 4"}, new String[]{"C"} );
        StateMachine.State timer4 = new TimerState(5000,new String[]{"D"},new String[]{ "state 1"}, new String[]{"D"} );


        StateMachine.INSTANCE.addState("A", new NameState("A"));
        StateMachine.INSTANCE.addState("B", new NameState("B"));
        StateMachine.INSTANCE.addState("C", new NameState("C"));
        StateMachine.INSTANCE.addState("D", new NameState("D"));
        StateMachine.INSTANCE.addState("state 1", timer1);
        StateMachine.INSTANCE.addState("state 2", timer2);
        StateMachine.INSTANCE.addState("state 3", timer3);
        StateMachine.INSTANCE.addState("state 4", timer4);
        StateMachine.INSTANCE.startState("state 1");
    }
}
