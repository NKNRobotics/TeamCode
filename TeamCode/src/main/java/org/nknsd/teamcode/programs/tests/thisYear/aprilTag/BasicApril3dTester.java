package org.nknsd.teamcode.programs.tests.thisYear.aprilTag;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;

@TeleOp(name = "Limelight 3A 3D Telemetry", group = "Sensor")
public class BasicApril3dTester extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);

        // Switch to your AprilTag pipeline (e.g., pipeline index 7)
        limelight.pipelineSwitch(0);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        limelight.start();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                // Get the robot's 3D pose relative to the Field Coordinate System

                for (LLResultTypes.FiducialResult sighting : result.getFiducialResults()) {
                    Pose3D target = sighting.getTargetPoseCameraSpace();
                    if (target != null) {
                        // Extract exact X, Y, and Z translations in inches (or DistanceUnit.METER)
                        double x = target.getPosition().toUnit(DistanceUnit.INCH).x;
                        double y = target.getPosition().toUnit(DistanceUnit.INCH).y;
                        double z = target.getPosition().toUnit(DistanceUnit.INCH).z;

                        // Extract Euler rotation angles in degrees
                        double roll =target.getOrientation().getRoll(AngleUnit.DEGREES);
                        double pitch =target.getOrientation().getRoll(AngleUnit.DEGREES);
                        double yaw =target.getOrientation().getRoll(AngleUnit.DEGREES);

                        // Output clean, structured coordinate data
                        telemetry.addLine("--- Robot Field Pose ---");
                        telemetry.addData("X (Inches)", "%.2f", x);
                        telemetry.addData("Y (Inches)", "%.2f", y);
                        telemetry.addData("Z (Inches)", "%.2f", z);

                        telemetry.addLine("--- Robot Rotation ---");
                        telemetry.addData("Roll  (Deg)", "%.2f", roll);
                        telemetry.addData("Pitch (Deg)", "%.2f", pitch);
                        telemetry.addData("Yaw   (Deg)", "%.2f", yaw);
                    } else {
                        telemetry.addData("3D Data", "No botpose calculated.");
                    }
                }
            } else {
                telemetry.addData("Target Status", "No valid AprilTags visible.");
            }

            telemetry.update();
        }

        limelight.stop();
    }
}
