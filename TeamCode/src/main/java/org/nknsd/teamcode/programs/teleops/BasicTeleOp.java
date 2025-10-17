package org.nknsd.teamcode.programs.teleops;

import org.nknsd.teamcode.components.drivers.WheelDriver;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

public class BasicTeleOp extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);

        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);

        WheelDriver wheelDriver = new WheelDriver(0.1, 1, 3, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(wheelDriver);

        WheelControlScheme wheelControlScheme = new WheelControlScheme();
        wheelDriver.link(gamePadHandler,wheelHandler,wheelControlScheme);
        // E - Ty Scalabrin
    }
}
