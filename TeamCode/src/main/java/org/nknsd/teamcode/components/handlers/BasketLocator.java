package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.utility.Interpolater;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class BasketLocator implements NKNComponent {

    private final Interpolater distanceInterpolater;

    private AprilTagSensor aprilTagSensor;

    public BasketLocator(Interpolater distanceInterpolater) {
        this.distanceInterpolater = distanceInterpolater;
    }


//    this little class can hold basket offset data
    public static class BasketOffset {

        public final double xOffset;
        public final double skew;
        public final double distance;

        public BasketOffset(double xOffset, double skew, double distance) {
            this.xOffset = xOffset;
            this.skew = skew;
            this.distance = distance;
        }

        public BasketOffset() {
            xOffset = 0;
            skew = 0;
            distance = -1;
        }
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
        telemetry.addData("blue distance", getOffset(ID.BLUE).distance);
        telemetry.addData("red distance", getOffset(ID.RED).distance);
    }

    public BasketOffset getOffset(ID color) {
        AprilTagSensor.VisionResult result;
        if (color == ID.BLUE) {
            result = aprilTagSensor.getVisionResultBlue();
        } else if (color == ID.RED) {
            result = aprilTagSensor.getVisionResultRed();
        } else {
//            if someone asks for a color that is not blue or red, return not seen
            return new BasketOffset(0, 0, -1);
        }

        if (result.id == ID.NONE) {
            return new BasketOffset();
        }
        return new BasketOffset(result.centerX, result.skew, distanceInterpolater.getValue(result.height));
    }

    public void link(AprilTagSensor aprilTagSensor) {
        this.aprilTagSensor = aprilTagSensor;
    }
}
