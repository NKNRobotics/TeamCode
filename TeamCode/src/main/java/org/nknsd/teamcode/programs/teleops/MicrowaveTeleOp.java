package org.nknsd.teamcode.programs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.drivers.MicrowaveDriver;
import org.nknsd.teamcode.components.drivers.WheelDriver;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.ColourSensor;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;
@TeleOp (name = "MicrowaveTeleOp")
public class MicrowaveTeleOp extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);

        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);

        WheelDriver wheelDriver = new WheelDriver(0.1, 1, 3, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(wheelDriver);

        WheelControlScheme wheelControlScheme = new WheelControlScheme();
        wheelControlScheme.link(gamePadHandler);
        wheelDriver.link(gamePadHandler,wheelHandler,wheelControlScheme);

        //setup for the microwave
//        ColourSensor colourSensor = new ColourSensor("ColorSensor");
//        components.add(colourSensor);

        MicrowaveHandler microwaveHandler = new MicrowaveHandler("Spin");
//        microwaveHandler.link(colourSensor);
        components.add(microwaveHandler);

        MicrowaveDriver microwaveDriver = new MicrowaveDriver();
        components.add(microwaveHandler);

        MicrowaveControlScheme microwaveControlScheme = new MicrowaveControlScheme();
        microwaveDriver.link(gamePadHandler, microwaveHandler, microwaveControlScheme);
        microwaveControlScheme.link(gamePadHandler);
    }
}