package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.R;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.controlSchemes.defaults.FiringControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class FiringDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private FiringSystem firingSystem;
    private FiringControlScheme firingControlScheme;

    Runnable lockTarget = new Runnable() {
        @Override
        public void run() {
            firingSystem.lockTarget(true);
        }
    };
    Runnable unlockTarget = new Runnable() {
        @Override
        public void run() {
            firingSystem.lockTarget(false);
        }
    };
    Runnable fireGreen = new Runnable() {
        @Override
        public void run() {
            firingSystem.fireGreen();
        }
    };
    Runnable firePurple = new Runnable() {
        @Override
        public void run() {
            firingSystem.firePurple();
        }
    };
    Runnable fireAll = new Runnable() {
        @Override
        public void run() {
            firingSystem.fireAll();
        }
    };
    Runnable setAllianceBlue = new Runnable() {
        @Override
        public void run() {
            firingSystem.setTargetColor(ID.BLUE);
        }
    };
    Runnable setAllianceRed = new Runnable() {
        @Override
        public void run() {
            firingSystem.setTargetColor(ID.RED);
        }
    };


    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        gamePadHandler.addListener(firingControlScheme.setBlue(), setAllianceBlue, "Set Alliance Blue");
        gamePadHandler.addListener(firingControlScheme.setRed(), setAllianceRed, "Set Alliance Red");
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {
        gamePadHandler.addListener(firingControlScheme.fireGreen(), fireGreen, "Fire Green");
        gamePadHandler.addListener(firingControlScheme.firePurple(), firePurple, "Fire Purple");
        gamePadHandler.addListener(firingControlScheme.fireAll(), fireAll, "Fire All");
        gamePadHandler.addListener(firingControlScheme.lockTarget(), lockTarget, "Lock Auto Targeting Mode");
        gamePadHandler.addListener(firingControlScheme.unlockTarget(), unlockTarget, "Unlock Auto Targeting Mode");

        gamePadHandler.removeListener("Set Alliance Blue");
        gamePadHandler.removeListener("Set Alliance Red");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "FiringDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(GamePadHandler gamePadHandler, FiringSystem firingSystem, FiringControlScheme firingControlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.firingSystem = firingSystem;
        this.firingControlScheme = firingControlScheme;
    }
}