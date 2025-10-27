package org.nknsd.teamcode.programs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.drivers.IntakeDriver;
import org.nknsd.teamcode.components.drivers.LauncherDriver;
import org.nknsd.teamcode.components.drivers.MicrowaveDriver;
import org.nknsd.teamcode.components.drivers.WheelDriver;
import org.nknsd.teamcode.components.handlers.IntakeHandler;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.LauncherControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name="Basic Tele Op (USE ME)")
public class BasicTeleOp extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        // SPECIAL
        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);


        // WHEELS
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);

        WheelDriver wheelDriver = new WheelDriver(0, 1, 5, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(wheelDriver);


        // SENSORS
//        ColourSensor colourSensor = new ColourSensor("ColorSensor");
//        components.add(colourSensor);


        // MICROWAVE
        MicrowaveHandler microwaveHandler = new MicrowaveHandler();
        components.add(microwaveHandler);

        MicrowaveDriver microwaveDriver = new MicrowaveDriver();
        components.add(microwaveDriver);


        // INTAKE
        IntakeHandler intakeHandler = new IntakeHandler();
        components.add(intakeHandler);

        IntakeDriver intakeDriver = new IntakeDriver();
        components.add(intakeDriver);


        // LAUNCHER
        LauncherHandler launcherHandler = new LauncherHandler();
        components.add(launcherHandler);

        LauncherDriver launcherDriver = new LauncherDriver();
        components.add(launcherDriver);


        // CONTROL SCHEME
        WheelControlScheme wheelControlScheme = new WheelControlScheme();
        wheelControlScheme.link(gamePadHandler);

        MicrowaveControlScheme microwaveControlScheme = new MicrowaveControlScheme();
        microwaveControlScheme.link(gamePadHandler);

        LauncherControlScheme launcherControlScheme = new LauncherControlScheme();
        launcherControlScheme.link(gamePadHandler);


        // LINK
//        microwaveHandler.link(colourSensor);
        microwaveHandler.link(intakeHandler);
        microwaveHandler.link(launcherHandler);
        launcherHandler.link(microwaveHandler);

        wheelDriver.link(gamePadHandler,wheelHandler,wheelControlScheme);
        microwaveDriver.link(gamePadHandler, microwaveHandler, microwaveControlScheme);
        intakeDriver.link(gamePadHandler, intakeHandler, microwaveControlScheme);
        launcherDriver.link(gamePadHandler, launcherHandler, launcherControlScheme);


        // TELEMETRY
        telemetryEnabled.add(launcherHandler);
        telemetryEnabled.add(microwaveDriver);
    }
}
