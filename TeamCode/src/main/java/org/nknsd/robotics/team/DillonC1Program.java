package org.nknsd.robotics.team;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.nknsd.robotics.framework.NKNComponent;
import org.nknsd.robotics.framework.NKNProgram;
import org.nknsd.robotics.team.components.GamepadComponent;
import org.nknsd.robotics.team.components.MotorsComponent;

import java.util.List;

public class DillonC1Program extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components) {
        GamepadComponent gamepadManager = new GamepadComponent();
        MotorsComponent motorManager = new MotorsComponent();
        components.add(gamepadManager);
        components.add(motorManager);
        gamepadManager.setMotor(motorManager);
    }
}
