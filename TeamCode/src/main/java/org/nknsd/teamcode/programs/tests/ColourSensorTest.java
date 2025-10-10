package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.sensors.ColourSensor;

import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "ColorSensorTest", group = "Tests")
public class ColourSensorTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        ColourSensor colorSensor = new ColourSensor("colorSensor0");
        components.add(colorSensor);
        telemetryEnabled.add(colorSensor);
    }
}
