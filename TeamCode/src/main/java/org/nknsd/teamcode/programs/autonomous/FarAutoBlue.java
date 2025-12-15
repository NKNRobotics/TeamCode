package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.utility.PositionTransform;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.programs.parts.FarAutoPart;
import org.nknsd.teamcode.programs.parts.Setup;

import java.util.List;

@Autonomous(name = "Far Auto Blue")
public class FarAutoBlue extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        RobotVersion.setIsAutonomous(true);
        RobotVersion.setRobotAlliance(ID.BLUE);

        Setup setup = new Setup();
        setup.createComponents(components,telemetryEnabled);

        PositionTransform transform = new PositionTransform(0,0,0,1,1,1);
        FarAutoPart farAutoPart = new FarAutoPart(transform, setup);
        farAutoPart.createComponents(components,telemetryEnabled);



    }
}
