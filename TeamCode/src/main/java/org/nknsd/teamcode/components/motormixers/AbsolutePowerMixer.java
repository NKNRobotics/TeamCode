package org.nknsd.teamcode.components.motormixers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class AbsolutePowerMixer implements NKNComponent {

    MecanumMotorMixer mecanumMotorMixer;
    FlowSensor flowSensor;

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

//        TODO: MATH

        mecanumMotorMixer.setPowers(output);
    }

    public void link(MecanumMotorMixer mecanumMotorMixer, FlowSensor flowSensor){
        this.mecanumMotorMixer = mecanumMotorMixer;
        this.flowSensor = flowSensor;
    }
}
