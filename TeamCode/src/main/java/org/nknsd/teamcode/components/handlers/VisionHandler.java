package org.nknsd.teamcode.components.handlers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class VisionHandler implements NKNComponent {

    Limelight3A limelight;

    public SparkFunOTOS.Pose2D findObjectPos() {
        LLResult result = limelight.getLatestResult();
        SparkFunOTOS.Pose2D objectPos = new SparkFunOTOS.Pose2D(0, 0, 0);
        if (limelight.getLatestResult().isValid()) {
            objectPos.x = result.getTx(); // How far left or right the target is (degrees)
            objectPos.y = result.getTy(); // How far up or down the target is (degrees)
            objectPos.h = result.getTa(); // using h to hold the area of the object

        }
        return objectPos;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!
        limelight.pipelineSwitch(0); // Switch to pipeline number 0
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
        return null;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        SparkFunOTOS.Pose2D objectPos = findObjectPos();
        telemetry.addData("object x", objectPos.x);
        telemetry.addData("object y", objectPos.y);
        telemetry.addData("object size", objectPos.h);
    }
}
