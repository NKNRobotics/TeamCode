package org.nknsd.teamcode.programs.tests.thisYear.peakFinding;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.autoStates.AutoMoveToPosState;
import org.nknsd.teamcode.autoStates.AutoSlotCheck;
import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.programs.parts.GamepadPart;
import org.nknsd.teamcode.programs.parts.Setup;

import java.util.List;

@TeleOp(name = "SRS Eating Test", group = "Tests")
public class SrsEatingTest extends NKNProgram {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        RobotVersion.setIsAutonomous(true);

        Setup setup = new Setup();
        setup.createComponents(components, telemetryEnabled);

        GamepadPart gamepadPart = new GamepadPart(setup);
        gamepadPart.createComponents(components, telemetryEnabled);

        StateMachine.INSTANCE.addState("scan slots", new AutoSlotCheck(setup.getArtifactSystem(), new String[]{}, new String[]{"intake 1"}));
        StateMachine.INSTANCE.addState("intake 1", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), false, 0, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(), new String[]{}, new String[]{"intake 2"}));
        StateMachine.INSTANCE.addState("intake 2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), false, 0, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(), new String[]{}, new String[]{"intake 3"}));
        StateMachine.INSTANCE.addState("intake 3", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), false, 0, RobotVersion.INSTANCE.ballEatingPidH, RobotVersion.INSTANCE.ballEatingPidXY, setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(), new String[]{}, new String[]{}));

        StateMachine.INSTANCE.startState("scan slots");
    }
}

