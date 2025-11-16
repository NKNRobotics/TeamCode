package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.AbsolutePosition;
//import org.nknsd.teamcode.components.sensors.FlowHandler;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.handlers.RailHandler;
import org.nknsd.teamcode.components.handlers.ServoHandler;
import org.nknsd.teamcode.components.handlers.VisionHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.sensors.WufSpotSensor;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.WufGrabState;
import org.nknsd.teamcode.states.WufHunter;
import org.nknsd.teamcode.states.WufReachState;
import org.nknsd.teamcode.states.WufRetractState;
import org.nknsd.teamcode.states.WufSpinner;

import java.util.List;

@TeleOp(name = "Wuf Killer Tester", group = "Tests")
public class WufKillerTester extends NKNProgram {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        VisionHandler visionHandler = new VisionHandler(1);

        FlowSensor flowSensor1 = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1, flowSensor2);
        components.add(absolutePosition);

        WheelHandler motorHandler = new WheelHandler();
        StateCore stateCore = new StateCore();
        PidController xpController = new PidController(0.08, .5, 0.1, .25, true, 0.03, 0.3);
        PidController ypController = new PidController(0.08, .5, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.4, .5, 0.1, .2, true, 0.1, 0.5);
        MotorDriver motorDriver = new MotorDriver(absolutePosition, motorHandler, xpController, ypController, hpController);
        WufSpotSensor wufSpotSensor = new WufSpotSensor(visionHandler, motorDriver, absolutePosition);

        RailHandler railHandler = new RailHandler();
        components.add(railHandler);
        telemetryEnabled.add(railHandler);
        ServoHandler servoHandler = new ServoHandler("rServo","lServo");
        components.add(servoHandler);
        telemetryEnabled.add(servoHandler);

        components.add(visionHandler);
        telemetryEnabled.add(visionHandler);
        components.add(motorHandler);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        components.add(stateCore);
        telemetryEnabled.add(stateCore);
        components.add(wufSpotSensor);
        telemetryEnabled.add(wufSpotSensor);

        motorDriver.setTarget(new SparkFunOTOS.Pose2D(0,0,0));

        WufHunter wufHunter = new WufHunter(wufSpotSensor, absolutePosition, motorDriver, 35);
        stateCore.addState(WufHunter.STATE_NAME, wufHunter);
        stateCore.addState(WufSpinner.STATE_NAME, new WufSpinner(wufSpotSensor, motorDriver, absolutePosition));

        stateCore.startState(WufSpinner.STATE_NAME);

        stateCore.addState(WufReachState.STATE_NAME, new WufReachState(railHandler, servoHandler, visionHandler, 0.7,650, WufGrabState.STATE_NAME, WufRetractState.STATE_NAME));
        stateCore.addState(WufGrabState.STATE_NAME, new WufGrabState(servoHandler));
        stateCore.addState(WufRetractState.STATE_NAME, new WufRetractState(railHandler,servoHandler,0.7));
//
//        stateMachine.startState(WufReachState.STATE_NAME);
    }
}
