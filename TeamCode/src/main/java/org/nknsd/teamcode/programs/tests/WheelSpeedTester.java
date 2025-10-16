package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.RailHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.handlers.statemachine.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.ArmPosWithin;

import java.util.List;

@TeleOp(name = "Launcher Tester", group = "Tests")
public class WheelSpeedTester extends NKNProgram {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {

//        RailHandler railHandler = new RailHandler();
//        components.add(railHandler);
//        telemetryEnabled.add(railHandler);

        LauncherHandler launcherHandler = new LauncherHandler();
        components.add(launcherHandler);
        telemetryEnabled.add(launcherHandler);

        launcherHandler.setEnabled(true);
        launcherHandler.setTargetTps(1800);

    }
}
