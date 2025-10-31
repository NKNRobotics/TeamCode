package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.FlowAverager;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Move To Position", group = "Tests")
public class MoveToPosTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        FlowSensor flowSensor1 = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "LODOS");
        components.add(flowSensor2);
        FlowAverager flowAverager = new FlowAverager(flowSensor1, flowSensor2);
        components.add(flowAverager);
        WheelHandler motorHandler = new WheelHandler();
//        SimplePController pControllerX = pControllerY = new SimplePController(0.1,.75);
//        SimplePController pControllerH = new SimplePController(0.5,.75);
        PidController pControllerX = new PidController(0.2, .3, 0.1, .2, true, 0.01, 0.2);
        PidController pControllerY = new PidController(0.2, .3, 0.1, .2, true, 0.01, 0.2);
        PidController pControllerH = new PidController(0.6, .5, 0.1, .25, true, 0.2, 0.3);
        MotorDriver motorDriver = new MotorDriver(flowAverager, motorHandler, pControllerX, pControllerY, pControllerH);
        components.add(motorHandler);
        telemetryEnabled.add(motorHandler);

        components.add(flowAverager);
        telemetryEnabled.add(flowAverager);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        motorDriver.setTarget(new SparkFunOTOS.Pose2D(10, 0, 0));
    }
}
