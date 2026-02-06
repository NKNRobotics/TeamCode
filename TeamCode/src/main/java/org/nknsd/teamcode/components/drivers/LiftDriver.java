package org.nknsd.teamcode.components.drivers;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.BalancedLiftHandler;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.LiftControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LiftDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private BalancedLiftHandler balancedLiftHandler;
    private LiftControlScheme liftControlScheme;

    Runnable startLift = new Runnable() {
        @Override
        public void run() {
            balancedLiftHandler.startLift();
        }
    };

    Runnable stopLift = new Runnable() {
        @Override
        public void run() {
            balancedLiftHandler.stopLift();
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
//        gamePadHandler.addListener(liftControlScheme.startLift(), startLift, "startLift");
//        gamePadHandler.addListener(liftControlScheme.stopLift(), stopLift, "stopLift");
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

    public void link(GamePadHandler gamePadHandler, BalancedLiftHandler balancedLiftHandler, LiftControlScheme liftControlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.liftControlScheme = liftControlScheme;
        this.balancedLiftHandler = balancedLiftHandler;
    }
}