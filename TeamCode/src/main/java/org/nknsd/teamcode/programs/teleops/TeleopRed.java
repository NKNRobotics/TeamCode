package org.nknsd.teamcode.programs.teleops;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.drivers.FiringDriver;
import org.nknsd.teamcode.components.drivers.IntakeDriver;
import org.nknsd.teamcode.components.drivers.LiftDriver;
import org.nknsd.teamcode.components.drivers.MixedInputWheelDriver;
import org.nknsd.teamcode.components.handlers.BalancedLiftHandler;
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
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.controlSchemes.defaults.FiringControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.IntakeControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.LiftControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Basic Tele Op (USE ME)")
public class TeleopRed extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        RobotVersion.setRobotAlliance(ID.RED);


        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);


        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);


        TrajectoryHandler trajectoryHandler = new TrajectoryHandler();
        components.add(trajectoryHandler);
        telemetryEnabled.add(trajectoryHandler);

        LauncherHandler launcherHandler = new LauncherHandler(0.97, 1.05);
        components.add(launcherHandler);
        telemetryEnabled.add(launcherHandler);
        launcherHandler.setEnabled(true);

        LaunchSystem launchSystem = new LaunchSystem(RobotVersion.INSTANCE.launchSpeedInterpolater, RobotVersion.INSTANCE.launchAngleInterpolater, 2, 16, 132);

        FiringSystem firingSystem = new FiringSystem();
        components.add(firingSystem);
        telemetryEnabled.add(firingSystem);


        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);

        ArtifactSystem artifactSystem = new ArtifactSystem();

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);
        telemetryEnabled.add(slotTracker);

        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        telemetryEnabled.add(colorReader);
        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.01);
        components.add(ballColorInterpreter);


        FlowSensor flowSensor1 = new FlowSensor("RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor("LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1, flowSensor2);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);

        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);
        telemetryEnabled.add(mecanumMotorMixer);

        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        absolutePowerMixer.link(mecanumMotorMixer, absolutePosition);

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);


        IMUSensor imuSensor = new IMUSensor(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.RIGHT, RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        components.add(imuSensor);

        BalancedLiftHandler balancedLiftHandler = new BalancedLiftHandler();
        components.add(balancedLiftHandler);


        AprilTagSensor aprilTagSensor = new AprilTagSensor();
        components.add(aprilTagSensor);
        telemetryEnabled.add(aprilTagSensor);

        BasketLocator basketLocator = new BasketLocator(RobotVersion.INSTANCE.aprilDistanceInterpolater);
        components.add(basketLocator);
        telemetryEnabled.add(basketLocator);

        TargetingSystem targetingSystem = new TargetingSystem(RobotVersion.INSTANCE.aprilTargetingPid);
        components.add(targetingSystem);
        telemetryEnabled.add(targetingSystem);
        targetingSystem.setTargetingColor(RobotVersion.getRobotAlliance());


        FiringDriver firingDriver = new FiringDriver();
        components.add(firingDriver);

        FiringControlScheme firingControlScheme = new FiringControlScheme();

        MixedInputWheelDriver mixedInputWheelDriver = new MixedInputWheelDriver(0, 1, 5, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(mixedInputWheelDriver);

        WheelControlScheme wheelControlScheme = new WheelControlScheme();

        IntakeDriver intakeDriver = new IntakeDriver();
        components.add(intakeDriver);

        IntakeControlScheme intakeControlScheme = new IntakeControlScheme();

        LiftDriver liftDriver = new LiftDriver();
        components.add(liftDriver);

        LiftControlScheme liftControlScheme = new LiftControlScheme();


        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        targetingSystem.link(basketLocator, powerInputMixer, absolutePosition);
        basketLocator.link(aprilTagSensor);
        powerInputMixer.link(absolutePowerMixer, mecanumMotorMixer);
        ballColorInterpreter.link(colorReader);
        launchSystem.link(trajectoryHandler, launcherHandler);
        firingSystem.link(launchSystem, targetingSystem, artifactSystem);
        artifactSystem.link(microwaveScoopHandler, slotTracker, launchSystem);
        firingDriver.link(gamePadHandler, firingSystem, firingControlScheme);
        firingControlScheme.link(gamePadHandler);
        mixedInputWheelDriver.link(gamePadHandler, powerInputMixer, wheelControlScheme);
        wheelControlScheme.link(gamePadHandler);
        intakeDriver.link(gamePadHandler, artifactSystem, intakeControlScheme);
        intakeControlScheme.link(gamePadHandler);
        liftDriver.link(gamePadHandler, balancedLiftHandler, liftControlScheme);
        liftControlScheme.link(gamePadHandler);
        balancedLiftHandler.link(imuSensor);
    }
}