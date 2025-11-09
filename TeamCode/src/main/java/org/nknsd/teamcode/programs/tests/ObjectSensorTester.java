package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.FlowAverager;
//import org.nknsd.teamcode.components.sensors.FlowHandler;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.handlers.VisionHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.sensors.WufSpotSensor;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.RobotPosWithin;
import org.nknsd.teamcode.states.WufHunter;

import java.util.List;

@TeleOp(name = "ObjectSensing Tester", group = "Tests")
public class ObjectSensorTester extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        VisionHandler visionHandler = new VisionHandler(1);

        FlowSensor flowSensor1 = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "LODOS");
        components.add(flowSensor2);
        FlowAverager flowAverager = new FlowAverager(flowSensor1, flowSensor2);
        components.add(flowAverager);

        WheelHandler motorHandler = new WheelHandler();
        StateCore stateCore = new StateCore();
        PidController xpController = new PidController(0.08, .5, 0.1, .25, true, 0.03, 0.3);
        PidController ypController = new PidController(0.08     , .5, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.4, .5, 0.1, .2, true, 0.1, 0.5);
        MotorDriver motorDriver = new MotorDriver(flowAverager, motorHandler, xpController, ypController, hpController);
        WufSpotSensor wufSpotSensor = new WufSpotSensor(visionHandler, motorDriver,flowAverager);

        components.add(visionHandler);
        telemetryEnabled.add(visionHandler);
        components.add(motorHandler);
        components.add(flowAverager);
        telemetryEnabled.add(flowAverager);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        components.add(stateCore);
        telemetryEnabled.add(stateCore);
        components.add(wufSpotSensor);
        telemetryEnabled.add(wufSpotSensor);

        WufHunter wufHunter = new WufHunter(wufSpotSensor,flowAverager,motorDriver,35);
        stateCore.addState(WufHunter.STATE_NAME, wufHunter);
        stateCore.addState("visionWithin",new RobotPosWithin(motorDriver,1,1,1,1, new String[]{"distancing"}, new String[]{WufHunter.STATE_NAME}));


//        RobotPosWithin robotPosWithin = new RobotPosWithin(motorDriver, 3, 100, 0.1, 100, 100, new String[]{WufHunter.STATE_NAME}, new String[]{WufDriver.STATE_NAME});
//        stateMachine.addState(WufDriver.STATE_NAME + "_WITHIN", robotPosWithin);
//        WufDriver wufDriver = new WufDriver(wufSpotter, motorDriver,flowAverager,20);
//        stateMachine.addState(WufDriver.STATE_NAME, wufDriver);

        stateCore.startState(WufHunter.STATE_NAME);

    }
}
