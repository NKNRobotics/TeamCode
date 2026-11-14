package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.components.handlers.launch.BallSystem;
import org.nknsd.teamcode.controlSchemes.defaults.LaunchControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LaunchDriver implements NKNComponent {

    private GamePadHandler gamePadHandler;
    private LaunchControlScheme launchControlScheme;
    private BallSystem ballSystem;

    Runnable startLaunch = new Runnable(){

        @Override
        public void run() {
            ballSystem.polyLaunch();
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
        gamePadHandler.addListener(launchControlScheme.startLaunch(), startLaunch, "startLaunch");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "LaunchDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }
    public void link(GamePadHandler gamePadHandler, LaunchControlScheme launchControlScheme, BallSystem ballSystem){
        this.gamePadHandler = gamePadHandler;
        this.launchControlScheme = launchControlScheme;
        this.ballSystem = ballSystem;
    }
}
