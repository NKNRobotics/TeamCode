package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;
@TeleOp(name = "AprilTagTester", group="Tests")
public class AprilTester extends NKNProgram {
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        AprilTagSensor aprilTagSensor = new AprilTagSensor();
        components.add(aprilTagSensor);
        telemetryEnabled.add(aprilTagSensor);
    }
}
