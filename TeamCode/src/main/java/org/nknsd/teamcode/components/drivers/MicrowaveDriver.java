package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private MicrowaveHandler microwaveHandler;
    private MicrowaveControlScheme controlScheme;

    Runnable loadBall = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.prepLoad();
        }
    };

    Runnable firePurple = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.prepFirePurple();
        }
    };

    Runnable fireGreen = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.prepFireGreen();
        }
    };
    Runnable fireOne = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.fireOne();
        }
    };
    Runnable fireTwo = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.fireTwo();
        }
    };
    Runnable fireThree = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.fireThree();
        }
    };
    Runnable intakeOne = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.intakeOne();
        }
    };
    Runnable intakeTwo = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.intakeOne();
        }
    };
    Runnable intakeThree = new Runnable() {
        @Override
        public void run() {
            microwaveHandler.intakeOne();
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
//        gamePadHandler.addListener(controlScheme.loadBall(), loadBall, "Bring to loading pos");
//        gamePadHandler.addListener(controlScheme.firePurple(), firePurple, "Bring to firing pos (purple)");
//        gamePadHandler.addListener(controlScheme.fireGreen(), fireGreen, "Bring to firing pos (green)");

        gamePadHandler.addListener(controlScheme.load1(), intakeOne, "intakeOne");
        gamePadHandler.addListener(controlScheme.load2(), intakeTwo, "intakeTwo");
        gamePadHandler.addListener(controlScheme.load3(), intakeThree, "intakeThree");
        gamePadHandler.addListener(controlScheme.fire1(), fireOne, "fireOne");
        gamePadHandler.addListener(controlScheme.fire2(), fireTwo, "fireTwo");
        gamePadHandler.addListener(controlScheme.fire3(), fireThree, "fireThree");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "MicrowaveHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public void link(GamePadHandler gamePadHandler, MicrowaveHandler microwaveHandler, MicrowaveControlScheme controlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.microwaveHandler = microwaveHandler;
        this.controlScheme = controlScheme;
    }
}
