package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.srs.PeakPointer;
import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.controlSchemes.defaults.IntakeControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.SRSControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class SRSDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private SRSControlScheme srsControlScheme;
    private StateMachine stateMachine;
    private PeakPointer peakPointer;
    private AbsolutePosition absolutePosition;
    private AutoPositioner autoPositioner;

    Runnable lockTarget = new Runnable() {
        @Override
        public void run() {
            stateMachine.startState("srsIntake");
        }
    };
    Runnable unlockTarget = new Runnable() {
        @Override
        public void run() {
            stateMachine.stopState("srsIntake");
        }
    };


    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        stateMachine.addState("srsIntake", new SRSIntakeState(peakPointer, absolutePosition, autoPositioner, false));

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

    public void link(GamePadHandler gamePadHandler, SRSControlScheme srsControlScheme, StateMachine stateMachine,  PeakPointer peakPointer, AbsolutePosition absolutePosition, AutoPositioner autoPositioner) {
        this.gamePadHandler = gamePadHandler;
        this.srsControlScheme = srsControlScheme;
        this.stateMachine = stateMachine;
        this.peakPointer = peakPointer;
        this.absolutePosition = absolutePosition;
        this.autoPositioner = autoPositioner;
    }
}