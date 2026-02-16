package org.nknsd.teamcode.programs.tests.thisYear.peakFinding;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.autoStates.AutoSlotCheck;
import org.nknsd.teamcode.components.handlers.srs.PeakFinder;
import org.nknsd.teamcode.components.handlers.srs.PeakPointer;
import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.programs.parts.Setup;

import java.util.List;

@TeleOp(name = "SRS PeakTargetingTest", group = "Tests")
public class PeakTargetTester extends NKNProgram {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        components.add(StateMachine.INSTANCE);

        Setup setup = new Setup();
        setup.changeEnableSettings(false,false);
        setup.createComponents(components,telemetryEnabled);

        StateMachine.INSTANCE.addState("scan slots", new AutoSlotCheck(setup.getArtifactSystem(), new String[]{}, new String[]{"intake 1"}));
        StateMachine.INSTANCE.addState("intake 1", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 0, RobotVersion.INSTANCE.pidControllerH, RobotVersion.INSTANCE.ballEatingPidXY , setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(), new String[]{}, new String[]{"intake 2"}));
        StateMachine.INSTANCE.addState("intake 2", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 0, RobotVersion.INSTANCE.pidControllerH, RobotVersion.INSTANCE.ballEatingPidXY , setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(), new String[]{}, new String[]{"intake 3"}));
        StateMachine.INSTANCE.addState("intake 3", new SRSIntakeState(setup.getPeakPointer(), setup.getAutoPositioner(), setup.getAbsolutePosition(), true, 0, RobotVersion.INSTANCE.pidControllerH, RobotVersion.INSTANCE.ballEatingPidXY , setup.getMicrowaveScoopHandler(), setup.getSlotTracker(), setup.getArtifactSystem(), new String[]{}, new String[]{}));

        StateMachine.INSTANCE.startState("scan slots");
    }
}

