package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;

import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "ColorSensorTest", group = "Tests")
public class ColourSensorTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        telemetryEnabled.add(colorReader);

        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10,0.01);
        components.add(ballColorInterpreter);
        telemetryEnabled.add(ballColorInterpreter);

        ballColorInterpreter.link(colorReader);
    }
}
