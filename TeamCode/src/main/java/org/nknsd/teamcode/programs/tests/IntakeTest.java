package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "intake test", group = "Tests")
public class IntakeTest extends NKNProgram {
    MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
    class IntakeSpin extends StateCore.State{

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            microwaveScoopHandler.toggleIntake(true);
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

        components.add(microwaveScoopHandler);
        telemetryEnabled.add(microwaveScoopHandler);

        StateCore stateCore = new StateCore();
        components.add(stateCore);

        microwaveScoopHandler.link(stateCore);

        stateCore.addState("intake", new IntakeSpin());
        stateCore.startState("intake");
    }
}
