package org.nknsd.teamcode.components.sensors;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class AprilTagSensor implements NKNComponent {

    Limelight3A limelight;

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        if (limelight == null) {
            throw new NullPointerException("No Limelight Camera Detected");
        }
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start(); // This tells Limelight to start looking!distSensor = hardwareMap.get(Rev2mDistanceSensor.class,"distanceSensor");
        return limelight.pipelineSwitch(0);
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
    int size; // How many tags it sees

    int idNum;

    public Patterns getPattern() {
        return pattern;
    }

    public enum Patterns {
        PGP,
        PPG,
        GPP,
        NONE
    }

    Patterns pattern = Patterns.NONE;

    public static class VisionResult {
        public final double tx, ty, ta;

        public VisionResult(double tx, double ty, double ta) {
            this.tx = tx;
            this.ty = ty;
            this.ta = ta;
        }
    }

    public VisionResult getAprilPos() {
        return new VisionResult(tx, ty, ta);
    }

    public boolean doesSee() {
        return size > 0;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

        LLResult result = limelight.getLatestResult();
        size = result.getFiducialResults().size();

        if (size > 0) {
            idNum = result.getFiducialResults().get(0).getFiducialId();
            tx = result.getTx();
            ty = result.getTy();
            ta = result.getTa();
            switch (idNum) {
                case 23:
                    pattern = Patterns.PPG;
                    break;
                case 22:
                    pattern = Patterns.PGP;
                    break;
                case 21:
                    pattern = Patterns.GPP;
                    break;
            }
        } else {
//            resets pattern the moment it stops seeing the tag; this is for testing purposes
            pattern = Patterns.NONE;
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        if (size == 0) {
            telemetry.addLine("Not seen");
        } else {
            telemetry.addData("tag id", idNum);
        }
        telemetry.addData("pattern", pattern);
    }
}
