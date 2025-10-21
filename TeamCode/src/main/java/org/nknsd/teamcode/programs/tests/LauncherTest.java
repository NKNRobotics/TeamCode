package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.drivers.AdvancedWheelDriver;
import org.nknsd.teamcode.components.drivers.LauncherDriver;
import org.nknsd.teamcode.components.drivers.WheelDriver;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.LauncherControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Launcher Test", group="Tests")
public class LauncherTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        // Gamepad Handler
        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);
        //telemetryEnabled.add(gamePadHandler);

        // Wheel Handler
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);
        //telemetryEnabled.add(wheelHandler);

        WheelControlScheme wheelController = new WheelControlScheme();
        wheelController.link(gamePadHandler);

        // Wheel Driver
        WheelDriver wheelDriver = new WheelDriver(0, 1, 5, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(wheelDriver);
        //telemetryEnabled.add(wheelDriver);
        wheelDriver.link(gamePadHandler, wheelHandler, wheelController);

        // Launcher Handler
        LauncherHandler launcherHandler = new LauncherHandler();
        components.add(launcherHandler);

        // Launcher Driver
        LauncherDriver launcherDriver = new LauncherDriver();
        components.add(launcherDriver);

        // Link
        LauncherControlScheme launcherControlScheme = new LauncherControlScheme();
        launcherDriver.link(gamePadHandler, launcherHandler, launcherControlScheme);
    }
}
