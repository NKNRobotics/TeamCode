package org.nknsd.teamcode.components.sensors;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.frameworks.NKNComponent;

import java.util.List;

public class AprilTagSensor implements NKNComponent {

    final private double XPIXELS = 960, YPIXELS = 720;

    Limelight3A limelight;

    double lastReadTime = 0;
    private VisionResult visionResultBlue = new VisionResult(0, 0, 0, 0, 0, ID.NONE);
    private VisionResult visionResultRed = new VisionResult(0, 0, 0, 0, 0, ID.NONE);
    private VisionResult visionResultPattern = new VisionResult(0, 0, 0, 0, 0, ID.NONE);


    public static class VisionResult {

        public final double centerX, centerY;
        public final double height, width;
        public final double skew;
        public final ID id;

        public VisionResult() {
            centerX = 0;
            centerY = 0;
            height = 0;
            width = 0;
            skew = 0;
            id = ID.NONE;
        }

        public VisionResult(double centerX, double centerY, double height, double width, double skew, ID id) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.height = height;
            this.width = width;
            this.skew = skew;
            this.id = id;
        }

        @Override
        public String toString() {
            return "VisionResult{" +
                    "centerX=" + String.format("%.3f", centerX) +
                    ", centerY=" + String.format("%.3f", centerY) +
                    ", height=" + String.format("%.3f", height) +
                    ", width=" + String.format("%.3f", width) +
                    ", skew=" + String.format("%.3f", skew) +
                    ", id=" + id.name() +
                    '}';
        }
    }


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

    public VisionResult getVisionResultBlue() {
        return visionResultBlue;
    }

    public VisionResult getVisionResultRed() {
        return visionResultRed;
    }

    public VisionResult getVisionResultPattern() {
        return visionResultPattern;
    }

    private VisionResult createVisionResult(LLResultTypes.FiducialResult sighting) {
        int idNum = sighting.getFiducialId();
        ID id;
        switch (idNum) {
            case 23:
                id = ID.PPG;
                break;
            case 22:
                id = ID.PGP;
                break;
            case 21:
                id = ID.GPP;
                break;
            case 20:
                id = ID.BLUE;
                break;
            case 24:
                id = ID.RED;
                break;
            default:
                id = ID.NONE;
                return new VisionResult();
        }

        double[][] corners = new double[4][2];


        List<List<Double>> rawCorners = sighting.getTargetCorners();

        for (int i = 0; i < 4; i++) {
            corners[i][0] = rawCorners.get(i).get(0) / XPIXELS;
            corners[i][1] = rawCorners.get(i).get(1) / YPIXELS;
        }

        double centerX = 0, centerY = 0;
        double top, bottom, left, right;


        for (double[] corner : corners) {
            centerX += corner[0];
            centerY += corner[1];
        }
        centerX /= 4;
        centerY /= 4;

        top = corners[2][0] - corners[3][0];
        bottom = corners[1][0] - corners[0][0];
        left = corners[0][1] - corners[3][1];
        right = corners[1][1] - corners[2][1];

        double width = (top + bottom) / 2;
        double height = (left + right) / 2;

        double shortening = (corners[3][1] - corners[2][1])/height;

        return new VisionResult(centerX, centerY, height, width, shortening, id);
    }


    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

        if (runtime.milliseconds() < lastReadTime + 50) {
            return;
        }
        lastReadTime = runtime.milliseconds();
        LLResult sightings = limelight.getLatestResult();

        visionResultRed = visionResultBlue = visionResultPattern = new VisionResult();

        for (LLResultTypes.FiducialResult sighting : sightings.getFiducialResults()) {
            VisionResult result = createVisionResult(sighting);
            if (result.id == ID.NONE) {
                return;
            } else if (result.id == ID.BLUE) {
                visionResultBlue = result;
            } else if (result.id == ID.RED) {
                visionResultRed = result;
            } else {
                visionResultPattern = result;
            }
        }

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
//        telemetry.addData("blue target", visionResultBlue);
        telemetry.addData("april height", visionResultBlue.height);
    }
}
