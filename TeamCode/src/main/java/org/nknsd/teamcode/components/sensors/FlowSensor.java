package org.nknsd.teamcode.components.sensors;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class FlowSensor implements NKNComponent {


    private SparkFunOTOS odometry;
    final SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, -Math.PI / 2);
    final String sensorName;


    public FlowSensor(String sensorName) {
        this.sensorName = sensorName;

    }

    private void configureSensor() {
        odometry.setLinearUnit(DistanceUnit.INCH);
        odometry.setAngularUnit(AngleUnit.RADIANS);
        odometry.setOffset(offset);
        odometry.setLinearScalar(1.0);
        odometry.setAngularScalar(1.0);
        odometry.calibrateImu();
        odometry.resetTracking();
    }

    public SparkFunOTOS.Pose2D getPosition() {
        if (odometry != null)
            return odometry.getPosition();
        else
            return new SparkFunOTOS.Pose2D();
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        odometry = hardwareMap.get(SparkFunOTOS.class, sensorName);
        if (odometry == null) {
            telemetry.addLine("CANT FIND ODOMETRY SENSOR:" + sensorName);
        } else {
            configureSensor();
        }
        odometry.resetTracking();
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
        return "Flow Handler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData(sensorName + " pos", printPose2D(getPosition()));
    }

    static public String printPose2D(SparkFunOTOS.Pose2D pos) {
        return "x: " + String.format("%.2f", pos.x) + " y: " + String.format("%.2f", pos.y) + " h: " + String.format("%.2f", pos.h);
    }
}
