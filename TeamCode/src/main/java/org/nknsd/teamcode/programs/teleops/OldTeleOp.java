package org.nknsd.teamcode.programs.teleops;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.drivers.FiringDriver;
import org.nknsd.teamcode.components.drivers.LauncherDriver;
import org.nknsd.teamcode.components.drivers.MicrowaveDriver;
import org.nknsd.teamcode.components.drivers.MixedInputWheelDriver;
import org.nknsd.teamcode.components.drivers.WheelDriver;
import org.nknsd.teamcode.components.handlers.WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.handlers.launch.LaunchSystem;
import org.nknsd.teamcode.components.handlers.launch.LauncherHandler;
import org.nknsd.teamcode.components.handlers.launch.TrajectoryHandler;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.vision.BasketLocator;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.handlers.vision.TargetingSystem;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.controlSchemes.defaults.FiringControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.LauncherControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Basic Tele for the OLD ROBOT. DO NOT USE.")
public class OldTeleOp extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);

        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);

        LauncherHandler launcherHandler = new LauncherHandler(0.97, 1.05);
        components.add(launcherHandler);
        telemetryEnabled.add(launcherHandler);
        launcherHandler.setEnabled(true);

        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);

        ArtifactSystem artifactSystem = new ArtifactSystem();

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);
        telemetryEnabled.add(slotTracker);

        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.01);
        components.add(ballColorInterpreter);

        WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER wheelHandler = new WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER();
        components.add(wheelHandler);
        telemetryEnabled.add(wheelHandler);


        WheelDriver wheelDriver = new WheelDriver(0, 1, 5, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(wheelDriver);

        WheelControlScheme wheelControlScheme = new WheelControlScheme();

        LauncherDriver launcherDriver = new LauncherDriver();
        components.add(launcherDriver);

        LauncherControlScheme launcherControlScheme = new LauncherControlScheme();

        MicrowaveDriver microwaveDriver = new MicrowaveDriver();
        components.add(microwaveDriver);

        MicrowaveControlScheme microwaveControlScheme = new MicrowaveControlScheme();


        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        ballColorInterpreter.link(colorReader);
        artifactSystem.link(microwaveScoopHandler, slotTracker);

        wheelControlScheme.link(gamePadHandler);
        launcherControlScheme.link(gamePadHandler);
        microwaveControlScheme.link(gamePadHandler);

        wheelDriver.link(gamePadHandler, wheelHandler, wheelControlScheme);
        launcherDriver.link(gamePadHandler, launcherHandler, launcherControlScheme);
        microwaveDriver.link(gamePadHandler, microwaveScoopHandler, microwaveControlScheme);
    }
}
