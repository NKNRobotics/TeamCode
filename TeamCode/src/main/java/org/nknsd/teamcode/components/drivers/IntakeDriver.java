package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.IntakeHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class IntakeDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private IntakeHandler intakeHandler;
    private MicrowaveControlScheme controlScheme;

    Runnable startIntakeSpin = new Runnable(){
        @Override
        public void run() {
            intakeHandler.toggleIntake(true);
            intakeHandler.setDontDisableFlag();
        }
    };
    Runnable stopIntakeSpin = new Runnable() {
        @Override
        public void run() {
            intakeHandler.toggleIntake(false);
            intakeHandler.releaseDontDisableFlag();
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
        gamePadHandler.addListener(controlScheme.startIntake(), startIntakeSpin, "startIntakeSpin");
        gamePadHandler.addListener(controlScheme.stopIntake(), stopIntakeSpin, "stopIntakeSpin");
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
    public void link(GamePadHandler gamePadHandler, IntakeHandler intakeHandler, MicrowaveControlScheme controlScheme) {
        this.gamePadHandler = gamePadHandler;
        this.intakeHandler = intakeHandler;
        this.controlScheme = controlScheme;
    }
}
