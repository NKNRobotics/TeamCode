package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.nknsd.teamcode.autoStates.AutoIntakeAllState;
import org.nknsd.teamcode.autoStates.AutoIntakeFromLoadingZoneState;
import org.nknsd.teamcode.autoStates.AutoLaunchAllState;
import org.nknsd.teamcode.autoStates.AutoMoveToPosState;
import org.nknsd.teamcode.autoStates.AutoReadPatternState;
import org.nknsd.teamcode.autoStates.AutoSlotCheck;
import org.nknsd.teamcode.autoStates.AutoTargetState;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.handlers.launch.LaunchSystem;
import org.nknsd.teamcode.components.handlers.launch.LauncherHandler;
import org.nknsd.teamcode.components.handlers.launch.TrajectoryHandler;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.vision.BasketLocator;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.handlers.vision.TargetingSystem;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@Autonomous(name = "Far Auto Red")
public class FarAutoRed extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        RobotVersion.setIsAutonomous(true);
        RobotVersion.setRobotAlliance(ID.RED);

//        statemachine
        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);


//        firing
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
//        telemetryEnabled.add(firingSystem);


//        microwave and artifact system
        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.01);
        components.add(ballColorInterpreter);

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);
        telemetryEnabled.add(slotTracker);

        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);

        ArtifactSystem artifactSystem = new ArtifactSystem();


//        driving
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

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);

        AutoPositioner autoPositioner = new AutoPositioner();
        components.add(autoPositioner);


//        apriltag tracking
        AprilTagSensor aprilTagSensor = new AprilTagSensor();
        components.add(aprilTagSensor);
        telemetryEnabled.add(aprilTagSensor);

        BasketLocator basketLocator = new BasketLocator(RobotVersion.INSTANCE.aprilDistanceInterpolater);
        components.add(basketLocator);
//        telemetryEnabled.add(basketLocator);

        TargetingSystem targetingSystem = new TargetingSystem(RobotVersion.INSTANCE.aprilTargetingPid);
        components.add(targetingSystem);
        telemetryEnabled.add(targetingSystem);


//        all links
        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        targetingSystem.link(basketLocator, powerInputMixer, absolutePosition);
        basketLocator.link(aprilTagSensor);
        powerInputMixer.link(absolutePowerMixer, mecanumMotorMixer);
        absolutePowerMixer.link(mecanumMotorMixer,absolutePosition);
        ballColorInterpreter.link(colorReader);
        launchSystem.link(trajectoryHandler, launcherHandler);
        firingSystem.link(launchSystem, targetingSystem, artifactSystem);
        artifactSystem.link(microwaveScoopHandler, slotTracker, launchSystem);
        autoPositioner.link(powerInputMixer, absolutePosition);



//        auto states
        StateMachine.INSTANCE.addState("start", new AutoMoveToPosState(autoPositioner, absolutePosition,true,0,7.6,-0.09, 0,0,0,0,RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("check slots", new AutoSlotCheck(artifactSystem, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("read pattern", new AutoReadPatternState(aprilTagSensor, firingSystem, new String[]{"start"}, new String[]{"rotate to fire pos"}));
        StateMachine.INSTANCE.addState("rotate to fire pos", new AutoMoveToPosState(autoPositioner, absolutePosition,true,0,8, 0.35, 1,1,0.05,1,RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{}, new String[]{"target"}));
        StateMachine.INSTANCE.addState("target", new AutoTargetState(firingSystem,true, new String[]{}, new String[]{"launch pattern", "target while firing"}));
        StateMachine.INSTANCE.addState("target while firing", new AutoTargetState(firingSystem,true, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("launch pattern", new AutoLaunchAllState(firingSystem, new String[]{"target while firing"}, new String[]{"move to loading zone"}));

        StateMachine.INSTANCE.addState("move to loading zone", new AutoMoveToPosState(autoPositioner, absolutePosition,true,35,8,-Math.PI/2, 0.2,0.2,0.2,1,RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{}, new String[]{"intake", "loading zone procedure"}));
        StateMachine.INSTANCE.addState("intake", new AutoIntakeAllState(artifactSystem,new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState( "loading zone procedure",new AutoIntakeFromLoadingZoneState(autoPositioner,7,1, ID.RED,RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{"intake"}, new String[]{"return to fire pos"}));

        StateMachine.INSTANCE.addState("return to fire pos", new AutoMoveToPosState(autoPositioner, absolutePosition,true,0,7.2, 0.35, 1,1,0.05,1,RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{}, new String[]{"target #2"}));
        StateMachine.INSTANCE.addState("target #2", new AutoTargetState(firingSystem,true, new String[]{}, new String[]{"launch pattern #2", "target while firing #2"}));
        StateMachine.INSTANCE.addState("target while firing #2", new AutoTargetState(firingSystem,true, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("launch pattern #2", new AutoLaunchAllState(firingSystem, new String[]{"target while firing #2"}, new String[]{}));


        StateMachine.INSTANCE.startState("start");
        StateMachine.INSTANCE.startState("read pattern");
        StateMachine.INSTANCE.startState("check slots");
    }
}
