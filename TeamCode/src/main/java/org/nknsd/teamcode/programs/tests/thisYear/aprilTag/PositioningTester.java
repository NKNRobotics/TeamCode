package org.nknsd.teamcode.programs.tests.thisYear.aprilTag;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.odometry.CameraFlowFuser;
import org.nknsd.teamcode.components.sensors.AprilTagSensor3d;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "PositioningTester", group="Tests")
public class PositioningTester extends NKNProgram {
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        AprilTagSensor3d aprilTagSensor = new AprilTagSensor3d();
        FlowSensor flowSensor = new FlowSensor("flowSens", 1);
        CameraFlowFuser flowFuser = new CameraFlowFuser(flowSensor, aprilTagSensor);
        components.add(aprilTagSensor);
        components.add(flowSensor);

        components.add(flowFuser);
        telemetryEnabled.add(flowFuser);

    }
}

