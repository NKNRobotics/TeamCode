package org.nknsd.teamcode.programs.tests.thisYear.aprilTag;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.vision.BasketLocator;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.sensors.AprilTagSensor3d;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "AprilTagTester3d", group="Tests")
public class AprilTester3d extends NKNProgram {
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        AprilTagSensor3d aprilTagSensor = new AprilTagSensor3d();
        components.add(aprilTagSensor);
        telemetryEnabled.add(aprilTagSensor);

    }
}
