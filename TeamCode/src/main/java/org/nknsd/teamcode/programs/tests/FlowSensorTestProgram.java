package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Flow Sensor Test", group="Tests")
public class FlowSensorTestProgram extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        FlowSensor flowSensor1 = new FlowSensor("RODOS");
        components.add(flowSensor1);
        telemetryEnabled.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor("LODOS");
        components.add(flowSensor2);
        telemetryEnabled.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1,flowSensor2);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);
    }
}
