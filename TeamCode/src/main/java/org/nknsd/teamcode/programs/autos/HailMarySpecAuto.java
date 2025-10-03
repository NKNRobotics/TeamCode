//package org.nknsd.teamcode.programs.autos;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//
//import org.nknsd.teamcode.autoSteps.AutoStepAbsoluteControl;
//import org.nknsd.teamcode.autoSteps.AutoStepChangeMaxSpeed;
//import org.nknsd.teamcode.autoSteps.AutoStepExtendJointedArm;
//import org.nknsd.teamcode.autoSteps.AutoStepJointedArmGripper;
//import org.nknsd.teamcode.autoSteps.AutoStepMove;
//import org.nknsd.teamcode.autoSteps.AutoStepMoveBackwardWithSensor;
//import org.nknsd.teamcode.autoSteps.AutoStepMoveForwardWithSensorSmart;
//import org.nknsd.teamcode.autoSteps.magentaSteps.AutoStepMoveNRotate;
//import org.nknsd.teamcode.autoSteps.AutoStepMoveSidewaysWithSensor;
//import org.nknsd.teamcode.autoSteps.AutoStepRelativeMove;
//import org.nknsd.teamcode.autoSteps.AutoStepSleep;
//import org.nknsd.teamcode.components.handlers.JointedArmHandler;
//import org.nknsd.teamcode.components.handlers.ShaiHuludHandler;
//import org.nknsd.teamcode.components.handlers.TheBowlHandler;
//import org.nknsd.teamcode.components.handlers.WheelHandler;
//import org.nknsd.teamcode.components.sensors.DistHubSensor;
//import org.nknsd.teamcode.components.sensors.DistSensor;
//import org.nknsd.teamcode.components.sensors.FlowSensor;
//import org.nknsd.teamcode.components.sensors.IMUSensor;
//import org.nknsd.teamcode.components.sensors.TouchSens;
//import org.nknsd.teamcode.components.sensors.hummelvision.LilyVisionHandler;
//import org.nknsd.teamcode.components.utility.AutoHeart;
//import org.nknsd.teamcode.components.utility.ColorPicker;
//import org.nknsd.teamcode.components.utility.GamePadHandler;
//import org.nknsd.teamcode.controlSchemes.reals.KarstenGeneric2PController;
//import org.nknsd.teamcode.frameworks.NKNAutoStep;
//import org.nknsd.teamcode.frameworks.NKNComponent;
//import org.nknsd.teamcode.frameworks.NKNProgramTrue;
//import org.nknsd.teamcode.helperClasses.AutoSkeleton;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//@Autonomous(name = "Hail Mary: Specimen") @Disabled
//public class HailMarySpecAuto extends NKNProgramTrue {
//    @Override
//    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
//        // Step List
//        List<NKNAutoStep> stepList = new LinkedList<NKNAutoStep>();
//
//
//        // Core mover
//        AutoSkeleton autoSkeleton = new AutoSkeleton(0.7, 0.3, 0.3, 1.5);
//
//        AutoHeart autoHeart = new AutoHeart(stepList);
//        components.add(autoHeart);
//        telemetryEnabled.add(autoHeart);
//
//
//        // Color Section
//        GamePadHandler gamePadHandler = new GamePadHandler();
//        components.add(gamePadHandler);
//
//        ColorPicker colorPicker = new ColorPicker();
//        components.add(colorPicker);
//
//        KarstenGeneric2PController controller = new KarstenGeneric2PController();
//
//        // Sensors
//        FlowSensor flowSensor = new FlowSensor();
//        components.add(flowSensor);
//        telemetryEnabled.add(flowSensor);
//
//        IMUSensor imuSensor = new IMUSensor();
//        components.add(imuSensor);
//        //telemetryEnabled.add(imuComponent);
//
//        LilyVisionHandler lilyVisionHandler = new LilyVisionHandler();
//        components.add(lilyVisionHandler);
//        //telemetryEnabled.add(lilyVisionHandler);
//
//        TouchSens touchSens = new TouchSens("jaLimit");
//        components.add(touchSens);
//        //telemetryEnabled.add(touchSens);
//
//        // Dist Sensors
//        DistSensor backwardSensor = new DistSensor("sensorBackDist");
//        components.add(backwardSensor);
//        DistSensor leftSensor = new DistSensor("sensorLeftDist");
//        components.add(leftSensor);
//        DistSensor rightSensor = new DistSensor("sensorRightDist");
//        components.add(rightSensor);
//        DistHubSensor allSensor = new DistHubSensor();
//        components.add(allSensor);
//        telemetryEnabled.add(allSensor);
//
//
//        // Wheel Handler
//        WheelHandler wheelHandler = new WheelHandler();
//        components.add(wheelHandler);
//
//
//        // Arm Stuff
//        ShaiHuludHandler shaiHuludHandler = new ShaiHuludHandler();
//        components.add(shaiHuludHandler);
//
//        JointedArmHandler jointedArmHandler = new JointedArmHandler();
//        components.add(jointedArmHandler);
//
//        TheBowlHandler theBowlHandler = new TheBowlHandler();
//        components.add(theBowlHandler);
//
//
//        // Linking
//        // Sensors
//        HashMap<DistHubSensor.SensorNames, DistSensor> sensors = new HashMap<>();
//        sensors.put(DistHubSensor.SensorNames.BACK, backwardSensor);
//        sensors.put(DistHubSensor.SensorNames.LEFT, leftSensor);
//        sensors.put(DistHubSensor.SensorNames.RIGHT, rightSensor);
//        allSensor.link(sensors);
//
//        lilyVisionHandler.link(colorPicker);
//
//        colorPicker.link(gamePadHandler, controller);
//        controller.link(gamePadHandler);
//
//        // Handlers
//        shaiHuludHandler.link(lilyVisionHandler, colorPicker);
//        jointedArmHandler.link(touchSens);
//
//        // Autoskeleton
//        autoSkeleton.link(wheelHandler, flowSensor, imuSensor);
//        autoSkeleton.shaiHuludLink(shaiHuludHandler, jointedArmHandler, theBowlHandler);
//        autoSkeleton.distSensorLink(allSensor);
//        autoSkeleton.setOffset(new double[]{0.0, 0.0}, 0);
//        assembleList(stepList, autoHeart, autoSkeleton);
//    }
//
//    private void assembleList(List<NKNAutoStep> stepList, AutoHeart autoHeart, AutoSkeleton autoSkeleton) {
//        // Declare steps
//        AutoStepSleep sleep = new AutoStepSleep(300);
//
//        AutoStepAbsoluteControl moveToBar = new AutoStepAbsoluteControl(-0.4332, 1.35, 0);
//        AutoStepMoveBackwardWithSensor approachBar = new AutoStepMoveBackwardWithSensor(3.5, .1, 0.5);
//        AutoStepRelativeMove clipApproach = new AutoStepRelativeMove(0,-0.24,100);
//        AutoStepAbsoluteControl moveToB2 = new AutoStepAbsoluteControl(1.3, 1, 0);
//        AutoStepMove moveUp = new AutoStepMove(0, 1);
//        AutoStepAbsoluteControl moveTo1stSample = new AutoStepAbsoluteControl(1.6, 2.4861, 0);
//        AutoStepMove depositSample = new AutoStepMove(0, -1.801);
//        AutoStepAbsoluteControl moveTo2ndSample = new AutoStepAbsoluteControl(2.15, 2.4861, 0);
//        //AutoStepAbsoluteControl moveTo3rdSample = new AutoStepAbsoluteControl(2.4, 2.4861, 0);
//        AutoStepAbsoluteControl prepareFor1stPickup = new AutoStepAbsoluteControl(1.6008, 0.5233, 0);
//        AutoStepMoveForwardWithSensorSmart approachPickup = new AutoStepMoveForwardWithSensorSmart(17, 0.2, .4);
//        AutoStepMoveSidewaysWithSensor alignSpecimen = new AutoStepMoveSidewaysWithSensor(17, 0.2, 0.4, -0.3,400);
//        AutoStepMoveNRotate rotateToEnd = new AutoStepMoveNRotate(0, -.5, -90);
//        AutoStepMove slightEndAdjust = new AutoStepMove(-.2, 0);
//
//        AutoStepJointedArmGripper grip = new AutoStepJointedArmGripper(true);
//        AutoStepJointedArmGripper release = new AutoStepJointedArmGripper(false);
//
//        AutoStepExtendJointedArm rest = new AutoStepExtendJointedArm(JointedArmHandler.Positions.REST);
//        AutoStepExtendJointedArm bird = new AutoStepExtendJointedArm(JointedArmHandler.Positions.EARLY_BIRD);
//        AutoStepExtendJointedArm worm = new AutoStepExtendJointedArm(JointedArmHandler.Positions.WORM_SEARCH);
//        AutoStepExtendJointedArm nest = new AutoStepExtendJointedArm(JointedArmHandler.Positions.NEST);
//        AutoStepExtendJointedArm feed = new AutoStepExtendJointedArm(JointedArmHandler.Positions.FEED);
//
//        AutoStepChangeMaxSpeed slowSpeed = new AutoStepChangeMaxSpeed(0.5);
//        AutoStepChangeMaxSpeed normalSpeed = new AutoStepChangeMaxSpeed(0.7);
//
//
//        // Create path
//        // Approach bar and align arm
//        stepList.add(grip);
//        stepList.add(moveToBar);
//
//        // Deposit 1st specimen
//        stepList.add(nest);
//        stepList.add(approachBar);
//        stepList.add(grip);
//        stepList.add(feed);
//        stepList.add(sleep);
//        stepList.add(sleep);
//        stepList.add(clipApproach);
//        stepList.add(sleep);
//        stepList.add(release);
//
//        // Transition
//        stepList.add(rest);
//
//        // Push 1st blue
//        stepList.add(normalSpeed);
//        stepList.add(moveToB2);
//        stepList.add(moveUp);
//        stepList.add(moveTo1stSample);
//        stepList.add(depositSample);
//
//        // Push 2nd blue
//        stepList.add(moveTo1stSample);
//        stepList.add(moveTo2ndSample);
//        stepList.add(depositSample);
//
//        // Grab 2nd specimen
//        stepList.add(prepareFor1stPickup);
//        stepList.add(slowSpeed);
//        stepList.add(approachPickup);
//        stepList.add(bird);
//        stepList.add(worm);
//        //temporary sleep added below by karsten to observe potential issues
//        stepList.add(sleep);
//        stepList.add(sleep);
//        stepList.add(grip);
//        stepList.add(sleep);
//        stepList.add(sleep);
//
//        // Deposit 2nd specimen
//        stepList.add(rest);
//        stepList.add(sleep);
//        stepList.add(normalSpeed);
//        stepList.add(moveToBar);
//        stepList.add(slightEndAdjust);
//        //actual deposit
//        stepList.add(nest);
//        stepList.add(approachBar);
//
//        stepList.add(feed);
//        stepList.add(sleep);
//        stepList.add(sleep);
//        stepList.add(clipApproach);
//        stepList.add(sleep);
//        stepList.add(release);
//
//        stepList.add(rotateToEnd);
//        stepList.add(rest);
//        stepList.add(sleep);
//
//
//
//        autoHeart.linkSteps(stepList, autoSkeleton);
//    }
//}
