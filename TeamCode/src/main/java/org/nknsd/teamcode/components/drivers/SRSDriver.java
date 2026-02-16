package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.srs.PeakPointer;
import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.controlSchemes.defaults.SRSControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class SRSDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private SRSControlScheme srsControlScheme;
    private StateMachine stateMachine;
    private PeakPointer peakPointer;
    private MicrowaveScoopHandler microwaveScoopHandler;
    private SlotTracker slotTracker;
    private ArtifactSystem artifactSystem;
    private AutoPositioner autoPositioner;
    private AbsolutePosition absolutePosition;
    private boolean running = false;

    private SRSIntakeState intake;
    Runnable lockTarget = new Runnable() {
        @Override
        public void run() {
            if (!running) {
                running = true;
                RobotLog.v("Turning on srs");
                intake = new SRSIntakeState(peakPointer, autoPositioner, absolutePosition, false, 0, RobotVersion.INSTANCE.pidControllerH, RobotVersion.INSTANCE.ballEatingPidXY, /*microwaveScoopHandler, slotTracker, artifactSystem, */ new String[]{}, new String[]{});
                StateMachine.INSTANCE.startAnonymous(intake);
                artifactSystem.intakeUntilFull();
                //                autoPositioner.enableAutoPositioning(false, false, true);
            }
        }
    };

    Runnable unlockTarget = new Runnable() {
        @Override
        public void run() {
            if (running) {
                running = false;
                RobotLog.v("Turning off srs");
                StateMachine.INSTANCE.stopAnonymous(intake);
//                autoPositioner.enableAutoPositioning(false, false, false);
            }
        }
    };


    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "IntakeDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        gamePadHandler.addListener(srsControlScheme.lockTarget(), lockTarget, "Enable Ball Targeting Mode");
        gamePadHandler.addListener(srsControlScheme.unlockTarget(), unlockTarget, "Disable Ball Targeting Mode");
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(GamePadHandler gamePadHandler, SRSControlScheme srsControlScheme, StateMachine stateMachine, MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker, ArtifactSystem artifactSystem, PeakPointer peakPointer, AutoPositioner autoPositioner, AbsolutePosition absolutePosition) {
        this.gamePadHandler = gamePadHandler;
        this.srsControlScheme = srsControlScheme;
        this.stateMachine = stateMachine;
        this.peakPointer = peakPointer;
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.slotTracker = slotTracker;
        this.artifactSystem = artifactSystem;
        this.absolutePosition = absolutePosition;
        this.autoPositioner = autoPositioner;
    }
}