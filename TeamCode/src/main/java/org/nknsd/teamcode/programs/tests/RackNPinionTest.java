package org.nknsd.teamcode.programs.tests;

import org.nknsd.teamcode.components.handlers.RackNPinion;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.drivers.TestDriver;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

public class RackNPinionTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);

        RackNPinion rackNPinion = new RackNPinion();
        components.add(rackNPinion);

        TestDriver testDriver = new TestDriver(0.1, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y);
        components.add(testDriver);

        testDriver.link(gamePadHandler, rackNPinion);
    }
}
