package org.nknsd.teamcode.programs.parts;

import org.firstinspires.ftc.teamcode.R;
import org.nknsd.teamcode.autoStates.AutoIntakeAllState;
import org.nknsd.teamcode.autoStates.AutoIntakeFromLoadingZoneState;
import org.nknsd.teamcode.autoStates.AutoLaunchAllState;
import org.nknsd.teamcode.autoStates.AutoMoveToPosState;
import org.nknsd.teamcode.autoStates.AutoReadPatternState;
import org.nknsd.teamcode.autoStates.AutoSlotCheck;
import org.nknsd.teamcode.autoStates.AutoTargetState;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.utility.PositionTransform;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.ProgramPart;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;

public class FarAutoPart extends ProgramPart {


    final private PositionTransform transform;

    private final Setup setup;
    private final boolean shortened;

    public FarAutoPart(PositionTransform positionTransform, Setup setup, boolean shortened) {
        this.transform = positionTransform;
        this.setup = setup;
        this.shortened = shortened;
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        setup.changeEnableSettings(false, true);
        //        auto states
        final AutoPositioner autoPositioner = setup.getAutoPositioner();
        final AbsolutePosition absolutePosition = setup.getAbsolutePosition();
        final AprilTagSensor aprilTagSensor = setup.getAprilTagSensor();
        final FiringSystem firingSystem = setup.getFiringSystem();
        final ArtifactSystem artifactSystem = setup.getArtifactSystem();

        StateMachine.INSTANCE.addState("scan slots", new AutoSlotCheck(setup.getArtifactSystem(), new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("start", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(0, 7.6, 0.09), 0, 0, 0, 0, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("read pattern", new AutoReadPatternState(aprilTagSensor, firingSystem, new String[]{"start"}, new String[]{"rotate to fire pos"}));
        StateMachine.INSTANCE.addState("rotate to fire pos", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(0, 8, -0.35), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{/*"timeToTarget #3", "target #3"*/ "target while firing", "launch pattern"}));
//        StateMachine.INSTANCE.addState("timeToTarget #3", new TimerState(1000, new String[]{"launch pattern #3", "target while firing #3"}, new String[]{"target #3"}));
//        StateMachine.INSTANCE.addState("target #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("target while firing", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
        if (!shortened) {
            StateMachine.INSTANCE.addState("launch pattern", new AutoLaunchAllState(firingSystem, new String[]{"target while firing"}, new String[]{"move to intake pos"}));
        } else {            StateMachine.INSTANCE.addState("launch pattern", new AutoLaunchAllState(firingSystem, new String[]{"target while firing"}, new String[]{"move to loading zone"}));
        }

        StateMachine.INSTANCE.addState("move to intake pos", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(0, 20, 0), 1, 1, 1, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{"rotate to intake pos"}));
        StateMachine.INSTANCE.addState("rotate to intake pos", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(-11, 25, Math.PI / 2), 1, 1, 0.1, 1, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{"eat from spike 1"}));
        StateMachine.INSTANCE.addState("eat from spike 1", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 3000, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
                new String[]{}, new String[]{"eat from spike 2"}));
        StateMachine.INSTANCE.addState("eat from spike 2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 1500, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
                new String[]{}, new String[]{"eat from spike 3"}));
        StateMachine.INSTANCE.addState("eat from spike 3", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 1500, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
                new String[]{}, new String[]{"return to launch"}));

        StateMachine.INSTANCE.addState("return to launch", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(0, 8, -0.35), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{/*"timeToTarget #3", "target #3"*/ "target while firing #2", "launch pattern #2"}));
//        StateMachine.INSTANCE.addState("timeToTarget #3", new TimerState(1000, new String[]{"launch pattern #3", "target while firing #3"}, new String[]{"target #3"}));
//        StateMachine.INSTANCE.addState("target #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("target while firing #2", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("launch pattern #2", new AutoLaunchAllState(firingSystem, new String[]{"target while firing #2"}, new String[]{"move to loading zone"}));

        StateMachine.INSTANCE.addState("move to loading zone", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(-39, 12, 1.19), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{"eat from spike 1 #2"}));
        StateMachine.INSTANCE.addState("eat from spike 1 #2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 3000, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
                new String[]{}, new String[]{"back up 1"}));
        StateMachine.INSTANCE.addState("back up 1", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(-37, 9, 1.19), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{"eat from spike 2 #2"}));
        StateMachine.INSTANCE.addState("eat from spike 2 #2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 2000, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
                new String[]{}, new String[]{"back up 2"}));
        StateMachine.INSTANCE.addState("back up 2", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(-37, 8, 1), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{"eat from spike 3 #2"}));
        StateMachine.INSTANCE.addState("eat from spike 3 #2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 2000, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(),
                new String[]{}, new String[]{"return to launch #2"}));

        StateMachine.INSTANCE.addState("return to launch #2", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(0, 8, -0.35), 2, 2, 0.3, 4, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH,
                new String[]{}, new String[]{/*"timeToTarget #3", "target #3"*/ "target while firing #3", "launch pattern #3"}));
//        StateMachine.INSTANCE.addState("timeToTarget #3", new TimerState(1000, new String[]{"launch pattern #3", "target while firing #3"}, new String[]{"target #3"}));
//        StateMachine.INSTANCE.addState("target #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("target while firing #3", new AutoTargetState(firingSystem, false, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.addState("launch pattern #3", new AutoLaunchAllState(firingSystem, new String[]{"target while firing #3"}, new String[]{"move"}));

        StateMachine.INSTANCE.addState("move", new AutoMoveToPosState(autoPositioner, absolutePosition, true,
                transform.adjustPos(-5, 20, 0), 0, 0, 0, 0, RobotVersion.INSTANCE.pidControllerX, RobotVersion.INSTANCE.pidControllerY, RobotVersion.INSTANCE.pidControllerH, new String[]{}, new String[]{}));


        StateMachine.INSTANCE.startState("start");
        StateMachine.INSTANCE.startState("read pattern");
        StateMachine.INSTANCE.startState("scan slots");

//        StateMachine.INSTANCE.startState("move to intake pos");
    }
}
