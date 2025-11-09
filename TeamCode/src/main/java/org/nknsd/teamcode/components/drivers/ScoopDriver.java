package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.ScoopHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.LauncherControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class ScoopDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private ScoopHandler scoopHandler;
    private LauncherControlScheme controlScheme;

    Runnable launchBall = new Runnable() {
        @Override
        public void run() {
            scoopHandler.triggerScoopToLaunch();
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
        gamePadHandler.addListener(controlScheme.launchBall(), launchBall, "Trigger a launch via the scoop");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "ScoopDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(GamePadHandler gamePadHandler, ScoopHandler scoopHandler, LauncherControlScheme controlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.scoopHandler = scoopHandler;
        this.controlScheme = controlScheme;
    }
}
