package org.nknsd.robotics.team;

import org.nknsd.robotics.framework.NKNComponent;
import org.nknsd.robotics.framework.NKNProgram;
import org.nknsd.robotics.team.components.ArmRotator;
import org.nknsd.robotics.team.components.GamePadHandler;
import org.nknsd.robotics.team.components.PotentiometerHandler;
import org.nknsd.robotics.team.components.WheelDriver;
import org.nknsd.robotics.team.components.WheelHandler;
import org.nknsd.robotics.team.components.testfiles.RotatorMonkey;

import java.util.List;

public class RotatorMonkeyProgram extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        PotentiometerHandler potentiometerHandler = new PotentiometerHandler("armPot");
        components.add(potentiometerHandler);
        telemetryEnabled.add(potentiometerHandler);

        ArmRotator armRotator = new ArmRotator("motorArmRotate", 0.05, 0.9);
        armRotator.link(potentiometerHandler);
        components.add(armRotator);
        telemetryEnabled.add(armRotator);

        RotatorMonkey rotatorMonkey = new RotatorMonkey();
        rotatorMonkey.link(armRotator);
        components.add(rotatorMonkey);
        telemetryEnabled.add(rotatorMonkey);
    }
}
