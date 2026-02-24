//package org.nknsd.teamcode.programs.parts;
//
//import org.nknsd.teamcode.autoStates.AutoIntakeAllState;
//import org.nknsd.teamcode.autoStates.AutoLaunchAllState;
//import org.nknsd.teamcode.autoStates.AutoMoveToPosState;
//import org.nknsd.teamcode.autoStates.AutoReadPatternState;
//import org.nknsd.teamcode.autoStates.AutoSlotCheck;
//import org.nknsd.teamcode.autoStates.AutoTargetState;
//import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
//import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
//import org.nknsd.teamcode.components.handlers.launch.LaunchSystem;
//import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
//import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
//import org.nknsd.teamcode.components.handlers.vision.ID;
//import org.nknsd.teamcode.components.motormixers.AutoPositioner;
//import org.nknsd.teamcode.components.sensors.AprilTagSensor;
//import org.nknsd.teamcode.components.utility.PositionTransform;
//import org.nknsd.teamcode.components.utility.RobotVersion;
//import org.nknsd.teamcode.components.utility.StateMachine;
//import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
//import org.nknsd.teamcode.frameworks.NKNComponent;
//import org.nknsd.teamcode.frameworks.ProgramPart;
//import org.nknsd.teamcode.states.TimerState;
//
//import java.util.List;
//
//public class CloseAutoPart extends ProgramPart {
//    final private PositionTransform transform;
//
//    private final Setup setup;
//
//    public CloseAutoPart(PositionTransform positionTransform, Setup setup) {
//        this.transform = positionTransform;
//        this.setup = setup;
//    }
//
//    @Override
//    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
//        //        auto states
//        final AutoPositioner autoPositioner = setup.getAutoPositioner();
//        final AbsolutePosition absolutePosition = setup.getAbsolutePosition();
//        final AprilTagSensor aprilTagSensor = setup.getAprilTagSensor();
////        final FiringSystem firingSystem = setup.getFiringSystem();
//        final ArtifactSystem artifactSystem = setup.getArtifactSystem();
//
//        PidController[] pidControllers = new PidController[]{
//                RobotVersion.INSTANCE.pidControllerX,
//                RobotVersion.INSTANCE.pidControllerY,
//                RobotVersion.INSTANCE.pidControllerH,
//                new PidController(0.15, 0.15, 0.1, 0.1, true, 0.1, 0.17),
//                new PidController(0.15, 0.15, 0.1, 0.1, true, 0.1, 0.17),
//                new PidController(0.5, 0.5, 0.5, 0.5, true, 0.2, 0.2)};
//
//
//        //        auto states
//        StateMachine.INSTANCE.addState("start",
//                new AutoMoveToPosState(autoPositioner, absolutePosition, true, transform.adjustPos(0, -20, Math.PI / 2), 1, 1, 0.1, 2,
//                        RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("read pattern", new AutoReadPatternState(aprilTagSensor, firingSystem, new String[]{"start"}, new String[]{"move to fire pos"}));
//
//        StateMachine.INSTANCE.addState("move to fire pos",
//                new AutoMoveToPosState(autoPositioner, absolutePosition, true, transform.adjustPos(0, -30, 0), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
//                        new String[]{}, new String[]{/*"timeToTarget #3", "target #3"*/ "target while firing", "launch pattern"}));
////        StateMachine.INSTANCE.addState("timeToTarget #3", new TimerState(1000, new String[]{"launch pattern #3", "target while firing #3"}, new String[]{"target #3"}));
////        StateMachine.INSTANCE.addState("target #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("target while firing", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("launch pattern", new AutoLaunchAllState(firingSystem, new String[]{"target while firing"}, new String[]{"move to spike"}));
//
//        StateMachine.INSTANCE.addState("move to spike",
//                new AutoMoveToPosState(autoPositioner, absolutePosition, true, transform.adjustPos(-20, -43, 2.37), 1, 1, 0.1, 2, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
//                        new String[]{}, new String[]{"eat from spike 1"}));
//        StateMachine.INSTANCE.addState("eat from spike 1", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 3000, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
//                new String[]{}, new String[]{"eat from spike 2"}));
//        StateMachine.INSTANCE.addState("eat from spike 2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 1500, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
//                new String[]{}, new String[]{"eat from spike 3"}));
//        StateMachine.INSTANCE.addState("eat from spike 3", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 1500, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
//                new String[]{}, new String[]{"return to launch"}));
//
//        StateMachine.INSTANCE.addState("return to launch",
//                new AutoMoveToPosState(autoPositioner, absolutePosition, true, transform.adjustPos(-13, -45, 0), 1, 1, 0.1, 2, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
//                        new String[]{}, new String[]{/*"timeToTarget #3", "target #3"*/ "target while firing #2", "launch pattern #2"}));
////        StateMachine.INSTANCE.addState("timeToTarget #3", new TimerState(1000, new String[]{"launch pattern #3", "target while firing #3"}, new String[]{"target #3"}));
////        StateMachine.INSTANCE.addState("target #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("target while firing #2", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("launch pattern #2", new AutoLaunchAllState(firingSystem, new String[]{"target while firing #2"}, new String[]{"move to spike #2"}));
//
//        StateMachine.INSTANCE.addState("move to spike #2",
//                new AutoMoveToPosState(autoPositioner, absolutePosition, true, transform.adjustPos(-36, -58, 2.37), 1, 1, 0.1, 2, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
//                        new String[]{}, new String[]{"eat from spike 1 #2"}));
//        StateMachine.INSTANCE.addState("eat from spike 1 #2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 3000, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
//                new String[]{}, new String[]{"eat from spike 2 #2"}));
//        StateMachine.INSTANCE.addState("eat from spike 2 #2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 1500, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
//                new String[]{}, new String[]{"eat from spike 3 #2"}));
//        StateMachine.INSTANCE.addState("eat from spike 3 #2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 1500, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
//                new String[]{}, new String[]{"return to launch #2"}));
//
//        StateMachine.INSTANCE.addState("return to launch #2",
//                new AutoMoveToPosState(autoPositioner, absolutePosition, true, transform.adjustPos(-10, -57, 0), 1, 1, 0.1, 2, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
//                        new String[]{}, new String[]{/*"timeToTarget #3", "target #3"*/ "target while firing #3", "launch pattern #3"}));
////        StateMachine.INSTANCE.addState("timeToTarget #3", new TimerState(1000, new String[]{"launch pattern #3", "target while firing #3"}, new String[]{"target #3"}));
////        StateMachine.INSTANCE.addState("target #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("target while firing #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
//        StateMachine.INSTANCE.addState("launch pattern #3", new AutoLaunchAllState(firingSystem, new String[]{"target while firing #3"}, new String[]{}));
//
//        StateMachine.INSTANCE.startState("start");
//        StateMachine.INSTANCE.startState("read pattern");
//    }
//}
