package org.nknsd.teamcode.components.motormixers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class PowerInputMixer implements NKNComponent {

    AbsolutePowerMixer absolutePowerMixer;

    double[] powers = new double[3];
    boolean[] autoEnabled = new boolean[3];
    boolean directPower = false;

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

    public void setAutoPowers(double[] autoPowers, boolean directPower) {
        if (autoEnabled[0]) {
            powers[0] = autoPowers[0];
        }
        if (autoEnabled[1]) {
            powers[1] = autoPowers[1];
        }
        if (autoEnabled[2]) {
            powers[2] = autoPowers[2];
        }
        if (directPower) {
            absolutePowerMixer.setDirectPowers(powers);
        } else {
            absolutePowerMixer.setPowers(powers);
        }
    }

    public void setManualPowers(double[] manualPowers, boolean directPower) {
        if (!autoEnabled[0]) {
            powers[0] = manualPowers[0];
        }
        if (!autoEnabled[1]) {
            powers[1] = manualPowers[1];
        }
        if (!autoEnabled[2]) {
            powers[2] = manualPowers[2];
        }
        absolutePowerMixer.setPowers(powers);
    }

    public void setAutoEnabled(boolean[] autoEnable) {
        this.autoEnabled = autoEnable;
    }

    public void link(AbsolutePowerMixer absolutePowerMixer) {
        this.absolutePowerMixer = absolutePowerMixer;
    }
}
