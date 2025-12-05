package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.IntakeControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class IntakeDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private IntakeControlScheme controlScheme;
    private ArtifactSystem artifactSystem;

    Runnable startIntake = new Runnable() {
        @Override
        public void run() {
            artifactSystem.intakeUntilFull();
        }
    };
    Runnable stopIntake = new Runnable() {
        @Override
        public void run() {artifactSystem.stopIntake();}
    };

    Runnable scanSlots = new Runnable() {
        @Override
        public void run() {
          artifactSystem.scanAll();
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
        gamePadHandler.addListener(controlScheme.startIntake(), startIntake, "startIntakeSpin");
        gamePadHandler.addListener(controlScheme.stopIntake(), stopIntake, "stopIntakeSpin");
        gamePadHandler.addListener(controlScheme.scanSlots(), scanSlots, "scanSlots");
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

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(GamePadHandler gamePadHandler, ArtifactSystem artifactSystem, IntakeControlScheme controlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.controlScheme = controlScheme;
        this.artifactSystem = artifactSystem;
    }
}
