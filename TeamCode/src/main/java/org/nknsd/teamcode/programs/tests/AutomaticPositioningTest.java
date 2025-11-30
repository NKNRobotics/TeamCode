package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.BasketLocator;
import org.nknsd.teamcode.components.handlers.ID;
import org.nknsd.teamcode.components.handlers.LaunchSystem;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.SlotTracker;
import org.nknsd.teamcode.components.handlers.TargetingSystem;
import org.nknsd.teamcode.components.handlers.TrajectoryHandler;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "automatic positioning tester", group = "Tests")
public class AutomaticPositioningTest extends NKNProgram {

    TargetingSystem targetingSystem = new TargetingSystem(RobotVersion.INSTANCE.aprilTargetingPid);

    class IntakeState extends StateCore.State {

        private final MicrowavePositions slot;
        private final MicrowaveScoopHandler microwaveScoopHandler;
        private final SlotTracker slotTracker;
        private final LaunchSystem launchSystem;
        private final int slotNum;

        IntakeState(int slotNum, MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker, LaunchSystem launchSystem) {
            this.launchSystem = launchSystem;
            switch (slotNum) {
                case 1:
                    slot = MicrowavePositions.LOAD1;
                    break;
                case 2:
                    slot = MicrowavePositions.LOAD2;
                    break;
                default:
                    slot = MicrowavePositions.LOAD0;
            }
            this.microwaveScoopHandler = microwaveScoopHandler;
            this.slotTracker = slotTracker;
            this.slotNum = slotNum;
        }


        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (microwaveScoopHandler.isDone()) {
                if (slotTracker.getSlotColor(slotNum) == BallColor.GREEN || slotTracker.getSlotColor(slotNum) == BallColor.PURPLE) {
                    stateCore.stopAnonymous(this);
                }
            }
            if (targetingSystem.getDistance() != -1) {
                launchSystem.setDistance(targetingSystem.getDistance());
            }
        }

        @Override
        protected void started() {
            microwaveScoopHandler.setMicrowavePosition(slot);
            microwaveScoopHandler.toggleIntake(true);
        }

        @Override
        protected void stopped() {
            if (slotNum != 2) {
                stateCore.startAnonymous(new IntakeState(slotNum + 1, microwaveScoopHandler, slotTracker, launchSystem));
            } else {
                stateCore.startAnonymous(new LaunchState(0, microwaveScoopHandler, launchSystem, slotTracker));
            }

        }
    }

    class LaunchState extends StateCore.State {

        private boolean hasLaunched = false;

        private final MicrowavePositions slot;
        private final MicrowaveScoopHandler microwaveScoopHandler;
        private final LaunchSystem launchSystem;
        private final SlotTracker slotTracker;
        private final int slotNum;

        LaunchState(int slotNum, MicrowaveScoopHandler microwaveScoopHandler, LaunchSystem launchSystem, SlotTracker slotTracker) {
            this.launchSystem = launchSystem;
            this.slotTracker = slotTracker;
            switch (slotNum) {
                case 1:
                    slot = MicrowavePositions.FIRE1;
                    break;
                case 2:
                    slot = MicrowavePositions.FIRE2;
                    break;
                default:
                    slot = MicrowavePositions.FIRE0;
            }
            this.microwaveScoopHandler = microwaveScoopHandler;
            this.slotNum = slotNum;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (targetingSystem.getDistance() != -1) {
                launchSystem.setDistance(targetingSystem.getDistance());
            }
            if (!hasLaunched && microwaveScoopHandler.isDone() && launchSystem.isReady() && targetingSystem.targetAcquired()) {
                microwaveScoopHandler.doScoopLaunch();
                hasLaunched = true;
            }
            if (hasLaunched && microwaveScoopHandler.isDone()) {
                slotTracker.clearSlot(slotNum);
                targetingSystem.enableAutoTargeting(false);
                stateCore.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
            microwaveScoopHandler.toggleIntake(false);
            targetingSystem.enableAutoTargeting(true);
            microwaveScoopHandler.setMicrowavePosition(slot);
        }

        @Override
        protected void stopped() {
            if (slotNum != 2) {
                stateCore.startAnonymous(new LaunchState(slotNum + 1, microwaveScoopHandler, launchSystem, slotTracker));
            } else {
                stateCore.startAnonymous(new IntakeState(0, microwaveScoopHandler, slotTracker, launchSystem));
            }
        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateCore stateCore = new StateCore();
        components.add(stateCore);
        telemetryEnabled.add(stateCore);


        TrajectoryHandler trajectoryHandler = new TrajectoryHandler();
        components.add(trajectoryHandler);
        telemetryEnabled.add(trajectoryHandler);

        LauncherHandler launcherHandler = new LauncherHandler(0.97, 1.05);
        components.add(launcherHandler);
        telemetryEnabled.add(launcherHandler);
        launcherHandler.setEnabled(true);

        LaunchSystem launchSystem = new LaunchSystem(RobotVersion.INSTANCE.launchSpeedInterpolater, RobotVersion.INSTANCE.launchAngleInterpolater, 2, 16, 132);


        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);
        telemetryEnabled.add(slotTracker);

        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.01);
        components.add(ballColorInterpreter);


        FlowSensor flowSensor1 = new FlowSensor( "RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor( "LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1,flowSensor2);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);

        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);
        telemetryEnabled.add(mecanumMotorMixer);

        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        absolutePowerMixer.link(mecanumMotorMixer,absolutePosition);

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);


        AprilTagSensor aprilTagSensor = new AprilTagSensor();
        components.add(aprilTagSensor);
        telemetryEnabled.add(aprilTagSensor);

        BasketLocator basketLocator = new BasketLocator(RobotVersion.INSTANCE.aprilDistanceInterpolater);
        components.add(basketLocator);
        telemetryEnabled.add(basketLocator);

        components.add(targetingSystem);
        telemetryEnabled.add(targetingSystem);
        targetingSystem.setTargetingColor(ID.BLUE);


        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        targetingSystem.link(basketLocator, powerInputMixer);
        basketLocator.link(aprilTagSensor);
        powerInputMixer.link(absolutePowerMixer);
        ballColorInterpreter.link(colorReader);
        microwaveScoopHandler.link(stateCore);
        launchSystem.link(trajectoryHandler, launcherHandler);


        stateCore.startAnonymous(new IntakeState(0, microwaveScoopHandler, slotTracker, launchSystem));
    }
}
