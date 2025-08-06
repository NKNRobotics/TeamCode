package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.FlowHandler;
import org.nknsd.teamcode.components.handlers.MotorDriver;
import org.nknsd.teamcode.components.handlers.MotorHandler;
import org.nknsd.teamcode.components.handlers.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgramTrue;

import java.util.List;

@TeleOp(name = "Move To Position", group="Tests")
public class MoveToPosTest extends NKNProgramTrue {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        FlowHandler flowHandler = new FlowHandler();
        MotorHandler motorHandler = new MotorHandler();
//        SimplePController pControllerX = pControllerY = new SimplePController(0.1,.75);
//        SimplePController pControllerH = new SimplePController(0.5,.75);
        PidController pControllerX = new PidController(0.1,.75,0.1,.5,true, 0.02,0.2);
        PidController pControllerY = new PidController(0.1,.75,0.1,.5,true,0.02,0.2);
        PidController pControllerH = new PidController(0.5,.75,0.1,.5,true,0.05,0.2);
        MotorDriver motorDriver = new MotorDriver(flowHandler,motorHandler,pControllerX,pControllerY,pControllerH);
        components.add(motorHandler);
        telemetryEnabled.add(motorHandler);

        components.add(flowHandler);
        telemetryEnabled.add(flowHandler);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        motorDriver.setTarget(new SparkFunOTOS.Pose2D(0,0,0));
    }
}
