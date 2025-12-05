package org.nknsd.teamcode.programs.tests.allYears.autonomousTests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Move To Position", group = "Tests") @Disabled
public class MoveToPosTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        FlowSensor flowSensor1 = new FlowSensor("RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor("LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1, flowSensor2);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);

        PidController pControllerX = new PidController(0.2, .3, 0.1, .2, true, 0.01, 0.2);
        PidController pControllerY = new PidController(0.2, .3, 0.1, .2, true, 0.01, 0.2);
        PidController pControllerH = new PidController(0.6, .5, 0.1, .25, true, 0.2, 0.3);

    }
}
