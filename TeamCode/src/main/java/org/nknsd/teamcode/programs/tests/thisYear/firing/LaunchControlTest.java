package org.nknsd.teamcode.programs.tests.thisYear.firing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.launch.LaunchHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Launch Control Test", group = "Tests")
public class LaunchControlTest extends NKNProgram {

    static class LaunchState extends StateMachine.State {

        final LaunchHandler launchHandler;
        final int power;
        final int timeMS;
        final String callNext;

        LaunchState(LaunchHandler launchHandler, int power, int timeMS, String callNext) {
            this.launchHandler = launchHandler;
            this.power = power;
            this.timeMS = timeMS;
            this.callNext = callNext;
        }
        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(runtime.milliseconds() - startTimeMS >= timeMS){
                StateMachine.INSTANCE.stopState(name);
            }
        }

        @Override
        protected void started() {
            launchHandler.setTargetTicks(power);
            launchHandler.setEnabled(true);
        }

        @Override
        protected void stopped() {
            StateMachine.INSTANCE.startState(callNext);
        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        LaunchHandler launchHandler = new LaunchHandler(0.95, 1.05);
        components.add(launchHandler);
        telemetryEnabled.add(launchHandler);

        components.add(StateMachine.INSTANCE);

        StateMachine.INSTANCE.addState("launch", new LaunchState(launchHandler, 1100, 3000, "load"));
        StateMachine.INSTANCE.addState("load", new LaunchState(launchHandler, -200, 2000, "launch"));
        StateMachine.INSTANCE.startState("launch");
    }
}
