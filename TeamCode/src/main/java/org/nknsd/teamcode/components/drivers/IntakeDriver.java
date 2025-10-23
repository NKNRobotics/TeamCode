package org.nknsd.teamcode.components.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class IntakeDriver implements NKNComponent {
    private GamePadHandler gamePadHandler;
    private IntakeHandler intakeHandler;
    private MicrowaveControlScheme microwaveControlScheme;

    Runnable toggleIntake = new Runnable(){
        @Override
        public void run() {
            intakeHandler.toggleIntake();
        }
    }
    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
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
        return "";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }
}
