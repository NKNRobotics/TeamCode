package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.FlowHandler;
import org.nknsd.teamcode.components.handlers.HappyDancer;
import org.nknsd.teamcode.components.handlers.MotorHandler;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgramTrue;

import java.util.List;

@TeleOp(name = "Happy Dance", group="Tests")
public class HappyDanceTester  extends NKNProgramTrue {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        MotorHandler motorHandler = new MotorHandler();
        FlowHandler flowHandler = new FlowHandler();
        HappyDancer happyDancer = new HappyDancer(motorHandler, flowHandler);
        components.add(motorHandler);
        telemetryEnabled.add(motorHandler);
        components.add(happyDancer);
        telemetryEnabled.add(happyDancer);
        components.add(flowHandler);
        telemetryEnabled.add(flowHandler);
    }

}
