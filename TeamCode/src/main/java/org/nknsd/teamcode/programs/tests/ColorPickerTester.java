package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.Generic2PControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Color Picker Tester", group="Tests") @Disabled
// @Disabled
public class ColorPickerTester extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);

        ColorPicker colorPicker = new ColorPicker();
        components.add(colorPicker);

        Generic2PControlScheme controller = new Generic2PControlScheme();
        controller.link(gamePadHandler);
        colorPicker.link(gamePadHandler, controller);

        telemetryEnabled.add(colorPicker);
    }
}
