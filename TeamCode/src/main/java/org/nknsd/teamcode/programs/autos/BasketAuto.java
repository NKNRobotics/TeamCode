package org.nknsd.teamcode.programs.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.nknsd.teamcode.autoSteps.AutoStepExtendArmSynced;
import org.nknsd.teamcode.autoSteps.AutoStepExtendArmWithFlowAdjusting;
import org.nknsd.teamcode.autoSteps.AutoStepMoveNRotate;
import org.nknsd.teamcode.frameworks.NKNAutoStep;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.autoSteps.AutoStepAbsoluteControl;
import org.nknsd.teamcode.autoSteps.AutoStepChangeMaxSpeed;
import org.nknsd.teamcode.autoSteps.AutoStepMove;
import org.nknsd.teamcode.autoSteps.AutoStepRelativeMove;
import org.nknsd.teamcode.autoSteps.AutoStepRotateArm;
import org.nknsd.teamcode.autoSteps.AutoStepServo;
import org.nknsd.teamcode.autoSteps.AutoStepSleep;
import org.nknsd.teamcode.components.handlers.ExtensionHandler;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.components.handlers.IntakeSpinnerHandler;
import org.nknsd.teamcode.components.sensors.PotentiometerSensor;
import org.nknsd.teamcode.components.handlers.RotationHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.utility.AutoHeart;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.helperClasses.AutoSkeleton;

import java.util.LinkedList;
import java.util.List;

@Autonomous(name = "Score Samples in Basket (GOOD)")
public class BasketAuto extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        // Step List
        List<NKNAutoStep> stepList = new LinkedList<NKNAutoStep>();


        // Core mover
        AutoSkeleton autoSkeleton = new AutoSkeleton(0.75, 0.2, 1.4);

        AutoHeart autoHeart = new AutoHeart(stepList);
        components.add(autoHeart);
        telemetryEnabled.add(autoHeart);


        // Sensors
        FlowSensor flowSensor = new FlowSensor();
        components.add(flowSensor);
        //telemetryEnabled.add(flowSensor);

        IMUSensor imuSensor = new IMUSensor();
        components.add(imuSensor);
        //telemetryEnabled.add(imuComponent);

        PotentiometerSensor potentiometerSensor = new PotentiometerSensor();
        components.add(potentiometerSensor);


        // Wheel Handler
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);


        // Arm Stuff
        RotationHandler rotationHandler = new RotationHandler ();
        components.add(rotationHandler);

        ExtensionHandler extensionHandler = new ExtensionHandler();
        components.add(extensionHandler);

        IntakeSpinnerHandler intakeSpinnerHandler = new IntakeSpinnerHandler();
        components.add(intakeSpinnerHandler);


        // Linking
        rotationHandler.link(potentiometerSensor, extensionHandler);
        extensionHandler.link(rotationHandler);

        autoSkeleton.link(wheelHandler, rotationHandler, extensionHandler, intakeSpinnerHandler, flowSensor, imuSensor);
        autoSkeleton.setOffset(new double[]{0.0, 1.02}, 0);
        assembleList(stepList, autoHeart, autoSkeleton);
    }

    private void assembleList(List<NKNAutoStep> stepList, AutoHeart autoHeart, AutoSkeleton autoSkeleton) {
        // Declare steps
        AutoStepSleep sleep = new AutoStepSleep(375);
        AutoStepSleep armSleep = new AutoStepSleep(500);

        AutoStepMove moveSlightForward = new AutoStepMove(0, 0.2);
        AutoStepAbsoluteControl orientToBasket = new AutoStepAbsoluteControl(-0.88, 0.32, -135);

        AutoStepRelativeMove backAwayFromBasket = new AutoStepRelativeMove(0, -.45, 200);
        AutoStepRelativeMove backMOREAwayFromBasket = new AutoStepRelativeMove(0, -.45, 250);
        AutoStepRelativeMove slightlyBackAway = new AutoStepRelativeMove(0, -.4, 190);

        AutoStepAbsoluteControl pickUpFirstYellow = new AutoStepAbsoluteControl(0.4376, 1.3268, -68.8);
        AutoStepRelativeMove moveToPickup = new AutoStepRelativeMove(0, 0.3, 400);
        AutoStepMove dodgeWall = new AutoStepMove(0, -0.5);

        AutoStepAbsoluteControl pickUpSecondYellow = new AutoStepAbsoluteControl(0.0242, 1.7214, -90);
        AutoStepAbsoluteControl alignToPark = new AutoStepAbsoluteControl(-0.05, 2.2, 90);
        AutoStepMove driveInToPark = new AutoStepMove(0.58, 0);

        AutoStepMoveNRotate turnToEnd = new AutoStepMoveNRotate(0, 0, 90);

        AutoStepRotateArm rotateToHigh = new AutoStepRotateArm(RotationHandler.RotationPositions.HIGH);
        AutoStepRotateArm rotateToPickup = new AutoStepRotateArm(RotationHandler.RotationPositions.PICKUP);
        AutoStepRotateArm rotateToRest = new AutoStepRotateArm(RotationHandler.RotationPositions.RESTING);
        AutoStepRotateArm rotateToPrepickup = new AutoStepRotateArm(RotationHandler.RotationPositions.PREPICKUP);
        AutoStepRotateArm rotateToSpecial = new AutoStepRotateArm(RotationHandler.RotationPositions.SPECIMEN);

        AutoStepExtendArmWithFlowAdjusting extendToHigh = new AutoStepExtendArmWithFlowAdjusting(ExtensionHandler.ExtensionPositions.HIGH_BASKET, -135, -0.88, 0.32);
        AutoStepExtendArmSynced retract = new AutoStepExtendArmSynced(ExtensionHandler.ExtensionPositions.RESTING);

        AutoStepServo releaseBlock = new AutoStepServo(IntakeSpinnerHandler.HandStates.RELEASE, 1200);
        AutoStepServo gripBlock = new AutoStepServo(IntakeSpinnerHandler.HandStates.GRIP, 400);
        AutoStepServo neutralServo = new AutoStepServo(IntakeSpinnerHandler.HandStates.REST, 0);

        AutoStepChangeMaxSpeed reallySlowSpeed = new AutoStepChangeMaxSpeed(0.2);
        AutoStepChangeMaxSpeed slowSpeed = new AutoStepChangeMaxSpeed(0.5);
        AutoStepChangeMaxSpeed normalSpeed = new AutoStepChangeMaxSpeed(0.75);



        // Put away first block
        stepList.add(moveSlightForward);
        stepList.add(rotateToHigh); // rotate early and often
        stepList.add(orientToBasket);
        stepList.add(reallySlowSpeed);
        stepList.add(extendToHigh);
        stepList.add(normalSpeed);
        stepList.add(releaseBlock);
        stepList.add(sleep);
        stepList.add(rotateToSpecial);
        stepList.add(sleep);
        stepList.add(backAwayFromBasket);
        stepList.add(sleep);
        stepList.add(retract);

        // Get second block
        stepList.add(slowSpeed);
        stepList.add(pickUpFirstYellow);
        stepList.add(neutralServo);
        stepList.add(rotateToPickup);
        stepList.add(armSleep);
        stepList.add(moveToPickup);
        stepList.add(sleep);
        stepList.add(gripBlock);
        stepList.add(moveToPickup);
        stepList.add(sleep);

        // Place second block
        stepList.add(normalSpeed);
        stepList.add(rotateToHigh);
        stepList.add(orientToBasket);
        stepList.add(reallySlowSpeed);
        stepList.add(extendToHigh);
        stepList.add(normalSpeed);
        stepList.add(releaseBlock);
        stepList.add(sleep);
        stepList.add(rotateToSpecial);
        stepList.add(sleep);
        stepList.add(backMOREAwayFromBasket);
        stepList.add(retract);

        // Get third block
        stepList.add(slowSpeed);
        stepList.add(pickUpSecondYellow);
        stepList.add(gripBlock);
        stepList.add(rotateToPickup);
        stepList.add(armSleep);
        stepList.add(moveToPickup);
        stepList.add(sleep);

        // Place third block
        stepList.add(normalSpeed);
        stepList.add(rotateToHigh);
        stepList.add(orientToBasket);
        stepList.add(slightlyBackAway);
        stepList.add(reallySlowSpeed);
        stepList.add(extendToHigh);
        stepList.add(normalSpeed);
        stepList.add(releaseBlock);
        stepList.add(sleep);
        stepList.add(rotateToSpecial);
        stepList.add(sleep);
        stepList.add(backAwayFromBasket);
        stepList.add(retract);
        stepList.add(turnToEnd);
        stepList.add(rotateToRest);
        stepList.add(sleep);

        autoHeart.linkSteps(stepList, autoSkeleton);
    }
}
