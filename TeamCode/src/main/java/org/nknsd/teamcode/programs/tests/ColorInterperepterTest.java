package org.nknsd.teamcode.programs.tests;

import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

public class ColorInterperepterTest extends NKNProgram {




    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        telemetryEnabled.add(colorReader);

        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(20,2);
        components.add(ballColorInterpreter);
        telemetryEnabled.add(ballColorInterpreter);

        ballColorInterpreter.link(colorReader);
    }
}