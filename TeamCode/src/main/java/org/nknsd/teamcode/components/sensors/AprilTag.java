package org.nknsd.teamcode.components.sensors;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

public class AprilTag implements NKNComponent {

    Limelight3A limelight;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!
        limelight.pipelineSwitch(2); // Switch to aprilTag pipeline
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

    double tx; // How far left or right the target is (degrees)
    double ty; // How far up or down the target is (degrees)
    double ta; // How big the target looks (0%-100% of the image)

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
             tx = result.getTx();
             ty = result.getTy();
             ta = result.getTa();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Target X", tx);
        telemetry.addData("Target Y", ty);
        telemetry.addData("Target Area", ta);
    }
}
