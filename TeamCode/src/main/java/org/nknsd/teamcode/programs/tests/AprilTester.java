package org.nknsd.teamcode.programs.tests;

import org.nknsd.teamcode.components.sensors.AprilTag;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

public class AprilTester extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        AprilTag aprilTag = new AprilTag();
        components.add(aprilTag);
        telemetryEnabled.add(aprilTag);
    }
}
