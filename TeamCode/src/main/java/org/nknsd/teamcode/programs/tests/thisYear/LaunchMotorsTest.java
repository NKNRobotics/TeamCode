package org.nknsd.teamcode.programs.tests.thisYear;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.launch.LaunchHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Launch Motors Test", group = "Tests")
public class LaunchMotorsTest extends NKNProgram {

    static class StartLaunchState extends StateMachine.State {

        final LaunchHandler launchHandler;
        final ConveyorHandler conveyorHandler;

        StartLaunchState(LaunchHandler launchHandler, ConveyorHandler conveyorHandler) {
            this.launchHandler = launchHandler;
            this.conveyorHandler = conveyorHandler;
        }
        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
        }

        @Override
        protected void started() {
            launchHandler.setTargetTicks(1500);
            launchHandler.setEnabled(true);
            conveyorHandler.startUp();
        }

        @Override
        protected void stopped() {

        }
    }
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        LaunchHandler launchHandler = new LaunchHandler(0.95, 1.05);
        components.add(launchHandler);

        ConveyorHandler conveyorHandler = new ConveyorHandler();
        components.add(conveyorHandler);
        telemetryEnabled.add(conveyorHandler);

        telemetryEnabled.add(launchHandler);

        components.add(StateMachine.INSTANCE);

        StateMachine.INSTANCE.startAnonymous(new StartLaunchState(launchHandler, conveyorHandler));
    }
}
