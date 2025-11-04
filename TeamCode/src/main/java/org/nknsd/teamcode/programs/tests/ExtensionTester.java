package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.ExtensionHandler;
import org.nknsd.teamcode.components.handlers.ServoHandler;
import org.nknsd.teamcode.components.handlers.statemachine.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;

@TeleOp(name = "Extension Tester", group = "Tests")
public class ExtensionTester extends NKNProgram {
    ExtensionHandler extensionHandler;
    class Move extends StateMachine.State{





        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        protected void started() {
            extensionHandler.goUp();
        }

        @Override
        protected void stopped() {

        }
    }





    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        extensionHandler = new ExtensionHandler();
        components.add(extensionHandler);


        StateMachine stateMachine = new StateMachine();
        components.add(stateMachine);
        telemetryEnabled.add(stateMachine);

        stateMachine.addState("run",new Move());
        stateMachine.startState("run");

    }
}
