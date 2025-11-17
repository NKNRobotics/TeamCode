package org.nknsd.teamcode.components.motormixers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.AbsolutePosition;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class AbsolutePowerMixer implements NKNComponent {

    MecanumMotorMixer mecanumMotorMixer;
    AbsolutePosition absolutePosition;

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

    public void setPowers(double[] input){
        double[] output = new double[3];
        double theta = absolutePosition.getPosition().h;

        output[0] = Math.sin(theta)*input[1] + Math.cos(theta)*input[0];
        output[1] = Math.cos(theta)*input[1] - Math.sin(theta)*input[0];
        output[2] = input[2] - theta;

        mecanumMotorMixer.setPowers(output);
    }

    public void link(MecanumMotorMixer mecanumMotorMixer, AbsolutePosition absolutePosition){
        this.mecanumMotorMixer = mecanumMotorMixer;
        this.absolutePosition = absolutePosition;
    }
}
