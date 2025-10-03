//package org.nknsd.teamcode.programs.teleops;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.nknsd.teamcode.components.handlers.ExtensionHandler;
//import org.nknsd.teamcode.components.handlers.IntakeSpinnerHandler;
//import org.nknsd.teamcode.components.handlers.RotationHandler;
//import org.nknsd.teamcode.components.handlers.SpecimenClawHandler;
//import org.nknsd.teamcode.components.handlers.SpecimenExtensionHandler;
//import org.nknsd.teamcode.components.handlers.SpecimenRotationHandler;
//import org.nknsd.teamcode.components.handlers.WheelHandler;
//import org.nknsd.teamcode.components.sensors.DistSensor;
//import org.nknsd.teamcode.components.sensors.FlowSensor;
//import org.nknsd.teamcode.components.sensors.IMUSensor;
//import org.nknsd.teamcode.components.sensors.PotentiometerSensor;
//import org.nknsd.teamcode.components.utility.GamePadHandler;
//import org.nknsd.teamcode.controlSchemes.reals.CollyWheelController;
//import org.nknsd.teamcode.controlSchemes.reals.KarstenEACController;
//import org.nknsd.teamcode.controlSchemes.reals.KarstenSpecimenController;
//import org.nknsd.teamcode.drivers.AdvancedWheelDriver;
//import org.nknsd.teamcode.drivers.EACDriver;
//import org.nknsd.teamcode.drivers.SpecimenDriver;
//import org.nknsd.teamcode.drivers.SpecimenFancyDepositDriver;
//import org.nknsd.teamcode.frameworks.NKNComponent;
//import org.nknsd.teamcode.frameworks.NKNProgram;
//import org.nknsd.teamcode.helperClasses.AutoSkeleton;
//
//import java.util.List;
//
//@TeleOp(name = "Karsten & Colly OpMode")
//public class KarstenMovementNKNProgram extends NKNProgram {
//    @Override
//    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
//        // Misc
//        GamePadHandler gamePadHandler = new GamePadHandler();
//        components.add(gamePadHandler);
//        //telemetryEnabled.add(gamePadHandler);
//
//        WheelHandler wheelHandler = new WheelHandler();
//        components.add(wheelHandler);
//
//
//        // Sensor
//        PotentiometerSensor potentiometerSensor = new PotentiometerSensor();
//        components.add(potentiometerSensor);
//        //telemetryEnabled.add(potentiometerHandler);
//
//        IMUSensor imuSensor = new IMUSensor();
//        components.add(imuSensor);
//
//        DistSensor sensorForDist = new DistSensor("sensorForDist");
//        components.add(sensorForDist);
//        telemetryEnabled.add(sensorForDist);
//        DistSensor sensorBackDist = new DistSensor("sensorBackDist");
//        components.add(sensorBackDist);
//        telemetryEnabled.add(sensorBackDist);
//
//
//        // Arm
//        RotationHandler rotationHandler = new RotationHandler ();
//        components.add(rotationHandler);
//        //telemetryEnabled.add(rotationHandler);
//
//        ExtensionHandler extensionHandler = new ExtensionHandler();
//        components.add(extensionHandler);
//        //telemetryEnabled.add(extensionHandler);
//
//        IntakeSpinnerHandler intakeSpinnerHandler = new IntakeSpinnerHandler();
//        components.add(intakeSpinnerHandler);
//
//
//        // Specimen Handler
//        SpecimenRotationHandler specimenRotationHandler = new SpecimenRotationHandler();
//        components.add(specimenRotationHandler);
//        //telemetryEnabled.add(specimenRotationHandler);
//
//        SpecimenExtensionHandler specimenExtensionHandler = new SpecimenExtensionHandler();
//        components.add(specimenExtensionHandler);
//        //telemetryEnabled.add(specimenExtensionHandler);
//
//        SpecimenClawHandler specimenClawHandler = new SpecimenClawHandler();
//        components.add(specimenClawHandler);
//
//
//        // Driver
//        AdvancedWheelDriver wheelDriver = new AdvancedWheelDriver(0, 1, 5, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
//        components.add(wheelDriver);
//        telemetryEnabled.add(wheelDriver);
//
//        EACDriver eacDriver = new EACDriver();
//        components.add(eacDriver);
//        telemetryEnabled.add(eacDriver);
//
//        SpecimenDriver specimenDriver = new SpecimenDriver();
//        components.add(specimenDriver);
//        telemetryEnabled.add(specimenDriver);
//
//
//        // Controllers
//        CollyWheelController wheelController = new CollyWheelController();
//        KarstenEACController eacController = new KarstenEACController();
//        KarstenSpecimenController specimenController = new KarstenSpecimenController();
//
//
//        // Fancy Depositing my boi
//        SpecimenFancyDepositDriver specimenFancyDepositDriver = new SpecimenFancyDepositDriver();
//        components.add(specimenFancyDepositDriver);
//        telemetryEnabled.add(specimenFancyDepositDriver);
//        AutoSkeleton autoSkeleton = new AutoSkeleton(0.75, 0.2, 1.4);
//        autoSkeleton.setOffset(new double[]{0, 0}, 180);
//
//
//
//
//        // Link the components to each other
//        wheelDriver.link(gamePadHandler, wheelHandler, imuSensor, wheelController);
//        rotationHandler.link(potentiometerSensor, extensionHandler);
//        extensionHandler.link(rotationHandler);
//        specimenClawHandler.link(specimenRotationHandler);
//        specimenExtensionHandler.link(specimenClawHandler, specimenRotationHandler);
//
//        specimenFancyDepositDriver.link(specimenExtensionHandler, gamePadHandler, wheelController, wheelHandler, autoSkeleton);
//        autoSkeleton.link(wheelHandler, rotationHandler, extensionHandler, intakeSpinnerHandler, new FlowSensor(), imuSensor);
//        autoSkeleton.distSensorLink(sensorForDist, sensorBackDist);
//        autoSkeleton.specimenLink(specimenExtensionHandler, specimenRotationHandler, specimenClawHandler);
//
//        eacDriver.link(gamePadHandler, rotationHandler, extensionHandler, intakeSpinnerHandler, eacController);
//        specimenDriver.link(specimenExtensionHandler, specimenRotationHandler, specimenClawHandler, gamePadHandler, specimenController);
//        wheelController.link(gamePadHandler);
//        eacController.link(gamePadHandler);
//        eacController.linkExtensionHandler(extensionHandler);
//        specimenController.link(gamePadHandler);
//        specimenController.linkSchemes(eacController);
//    }
//}
