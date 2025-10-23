package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.LauncherControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LauncherDriver implements NKNComponent {
    private double launcherTargetTPS = 3000;

    private GamePadHandler gamePadHandler;
    private LauncherHandler launcherHandler;
    private LauncherControlScheme controlScheme;

    Runnable spinUp = new Runnable() {
        @Override
        public void run() {
            launcherHandler.setEnabled(true);
            launcherHandler.setTargetTps(launcherTargetTPS);
        }
    };

    Runnable spinDown = new Runnable() {
        @Override
        public void run() {
            launcherHandler.setEnabled(false);
        }
    };

    Runnable launchBall = new Runnable() {
        @Override
        public void run() {
//            if (launcherHandler.getCurrentTps() < launcherTargetTPS) {
//                return;
//            }

            launcherHandler.setScoopToLaunch(true);
        }
    };

    Runnable resetScoop = new Runnable() {
        @Override
        public void run() {
            launcherHandler.setScoopToLaunch(false);
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
        gamePadHandler.addListener(controlScheme.spinUp(), spinUp, "Start spinning up");
        gamePadHandler.addListener(controlScheme.spinDown(), spinDown, "Release spin, start down");
        gamePadHandler.addListener(controlScheme.launchBall(), launchBall, "Trigger a launch via the scoop");
        gamePadHandler.addListener(controlScheme.resetScoop(), resetScoop, "Return the scoop to the down position");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "LauncherDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(GamePadHandler gamePadHandler, LauncherHandler launcherHandler, LauncherControlScheme controlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.launcherHandler = launcherHandler;
        this.controlScheme = controlScheme;
    }

    public void adjustTargetSpeed(Telemetry telemetry, double newTps) {
        telemetry.addData("WARNING", "THIS CODE IS JUST FOR TESTING. IT HAS SERIOUS DRIVER PERMISSION OVERRIDES.");
        launcherTargetTPS = newTps;
    }
}
