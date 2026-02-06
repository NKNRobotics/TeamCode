package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.utility.PositionTransform;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.programs.parts.CloseAutoPart;
import org.nknsd.teamcode.programs.parts.Setup;

import java.util.List;

@Autonomous(name = "Close Auto Red")
public class CloseAutoRed extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        RobotVersion.setIsAutonomous(true);
        RobotVersion.setRobotAlliance(ID.RED);

        Setup setup = new Setup();
        setup.createComponents(components, telemetryEnabled);

        PositionTransform transform = new PositionTransform(0,0,0,-1,1,-1);
        CloseAutoPart closeAutoPart = new CloseAutoPart(transform, setup);
        closeAutoPart.createComponents(components,telemetryEnabled);

    }
}
