package org.nknsd.teamcode.components.handlers;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class FlowAverager implements NKNComponent {

    private SparkFunOTOS.Pose2D pos1 = new SparkFunOTOS.Pose2D(0, 0, 0);
    private SparkFunOTOS.Pose2D pos2 = new SparkFunOTOS.Pose2D(0, 0, 0);
    private SparkFunOTOS.Pose2D avPos = new SparkFunOTOS.Pose2D(0, 0, 0);

    final FlowSensor sensor1;
    final FlowSensor sensor2;

    public FlowAverager(FlowSensor sensor1, FlowSensor sensor2) {
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
    }


    //  averages both sensors
    public SparkFunOTOS.Pose2D getAvPos() {
        pos1 = sensor1.getPosition();
        pos2 = sensor2.getPosition();
        avPos = new SparkFunOTOS.Pose2D();

        double hSign = -1;
        if (((Math.abs(pos1.h) + Math.abs(pos2.h)) / 2 > Math.PI / 2) || ((Math.abs(pos1.h) + Math.abs(pos2.h)) / 2 < -Math.PI / 2)) {
            if (Math.abs(pos1.h) < Math.abs(pos2.h)) {
                if (pos1.h > 0) {
                    hSign = 1;
                }
            } else {
                if (pos2.h > 0) {
                    hSign = 1;
                }
            }
            avPos.h = ((Math.abs(pos1.h) + Math.abs(pos2.h)) / 2) * hSign;
        } else {
            avPos.h = (pos1.h + pos2.h) / 2;
        }

//      TODO: Test heading, may be negative
        avPos.h = -avPos.h;
        avPos.x = (pos1.x + pos2.x) / 2;
        avPos.y = (pos1.y + pos2.y) / 2;

        return avPos;
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
        return "Flow Handler";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("pos", printPose2D(getAvPos()));
    }

    static public String printPose2D(SparkFunOTOS.Pose2D pos) {
        return "x: " + String.format("%.2f", pos.x) + " y: " + String.format("%.2f", pos.y) + " h: " + String.format("%.2f", pos.h);
    }
}
