package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.VisionHandler;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgramTrue;

import java.util.List;

@TeleOp(name = "ObjectSensing Tester", group="Tests")
public class ObjectSensorTester extends NKNProgramTrue {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        VisionHandler visionHandler = new VisionHandler();
        components.add(visionHandler);
        telemetryEnabled.add(visionHandler);
    }
}
