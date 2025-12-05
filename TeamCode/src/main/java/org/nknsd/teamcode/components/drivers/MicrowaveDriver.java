package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class MicrowaveDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private MicrowaveScoopHandler microwaveScoopHandler;
    private MicrowaveControlScheme controlScheme;

    Runnable loadBall = new Runnable() {
        @Override
        public void run() {
//            microwaveScoopHandler.prepLoad();
        }
    };

//    Runnable firePurple = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.prepFirePurple();
//        }
//    };
//
//    Runnable fireGreen = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.prepFireGreen();
//        }
//    };
//    Runnable fireOne = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.fireOne();
//        }
//    };
//    Runnable fireTwo = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.fireTwo();
//        }
//    };
//    Runnable fireThree = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.fireThree();
//        }
//    };
//    Runnable intakeOne = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.intakeOne();
//        }
//    };
//    Runnable intakeTwo = new Runnable() {
//        @Override
//        public void run() {
//            microwaveScoopHandler.intakeTwo();
//        }
//    };
//    Runnable intakeThree = new Runnable() {
//        @Override
//        public void run() {microwaveScoopHandler.intakeThree();}
//    };

    boolean controlSchemeMicrowaveState = false;
    Runnable swapStateTelemetry = new Runnable() {
        @Override
        public void run() {
            controlSchemeMicrowaveState =!controlSchemeMicrowaveState;
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
//
//        gamePadHandler.addListener(controlScheme.load1(), intakeOne, "intakeOne");
//        gamePadHandler.addListener(controlScheme.load2(), intakeTwo, "intakeTwo");
//        gamePadHandler.addListener(controlScheme.load3(), intakeThree, "intakeThree");
//        gamePadHandler.addListener(controlScheme.fire1(), fireOne, "fireOne");
//        gamePadHandler.addListener(controlScheme.fire2(), fireTwo, "fireTwo");
//        gamePadHandler.addListener(controlScheme.fire3(), fireThree, "fireThree");

//        gamePadHandler.addListener(controlScheme.swapState(), swapStateTelemetry, "Swap Microwave Control State");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "MicrowaveScoopHandler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Microwave Control State", controlSchemeMicrowaveState);
    }

    public void link(GamePadHandler gamePadHandler, MicrowaveScoopHandler microwaveScoopHandler, MicrowaveControlScheme controlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.controlScheme = controlScheme;
    }
}
