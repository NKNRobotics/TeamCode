package org.nknsd.teamcode.components.handlers.odometry;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.nknsd.teamcode.components.sensors.AprilTagSensor3d;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class CameraFlowFuser implements NKNComponent {


    private final static int SAMPLE_TIME_MS = 25;

    private final SparkFunOTOS.Pose2D Pos = new SparkFunOTOS.Pose2D(0, 0, 0);


    FlowSensor flowSensor;
    AprilTagSensor3d aprilSensor;


    public CameraFlowFuser(FlowSensor flowSensor, AprilTagSensor3d aprilSensor) {
        this.flowSensor = flowSensor;
        this.aprilSensor = aprilSensor;
    }


    //  averages both sensors
    public SparkFunOTOS.Pose2D getPosition() {
        return Pos;
    }

    public double[] getDoublePosition(){
        double[] positions = new double[3];
        positions[0] = Pos.x;
        positions[1] = Pos.y;
        positions[2] = Pos.h;
        return positions;


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
