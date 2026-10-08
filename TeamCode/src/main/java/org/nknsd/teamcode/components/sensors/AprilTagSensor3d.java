package org.nknsd.teamcode.components.sensors;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class AprilTagSensor3d implements NKNComponent {


    Limelight3A limelight;
    private double lastReadTime;

    private AprilTagPosition tag30;
    private AprilTagPosition tag31;
    private AprilTagPosition tag32;

    private AprilTagPosition tag33;

    private AprilTagPosition tag34;

    private AprilTagPosition tag35;

    private AprilTagPosition tag36;

    private AprilTagPosition tag37;

    private AprilTagPosition tag38;

    private AprilTagPosition tag39;

    private AprilTagPosition tag40;

    private AprilTagPosition tag41;

    private AprilTagPosition tag42;

    private AprilTagPosition tag43;

    private AprilTagPosition tag44;

    private AprilTagPosition tag45;


    @NonNull
    @Override
    public String toString() {
        return "";
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
        return "AprilTag3dSensor";
    }


    public AprilTagPosition getTag30() {
        return tag30;
    }

    public AprilTagPosition getTag31() {
        return tag31;
    }

    public AprilTagPosition getTag32() {
        return tag32;
    }

    public AprilTagPosition getTag33() {
        return tag33;
    }

    public AprilTagPosition getTag34() {
        return tag34;
    }

    public AprilTagPosition getTag35() {
        return tag35;
    }

    public AprilTagPosition getTag36() {
        return tag36;
    }

    public AprilTagPosition getTag37() {
        return tag37;
    }
    public AprilTagPosition getTag38() {
        return tag38;
    }
    public AprilTagPosition getTag39() {
        return tag39;
    }
    public AprilTagPosition getTag40() {
        return tag40;
    }
    public AprilTagPosition getTag41() {
        return tag41;
    }
    public AprilTagPosition getTag42() {
        return tag42;
    }
    public AprilTagPosition getTag43() {
        return tag43;
    }
    public AprilTagPosition getTag44() {
        return tag44;
    }
    public AprilTagPosition getTag45() {
        return tag45;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

        if (runtime.milliseconds() < lastReadTime + 50) {
            return;
        }
        lastReadTime = runtime.milliseconds();
        LLResult sightings = limelight.getLatestResult();
        if (sightings == null) {
            throw new NullPointerException("No Limelight Data");
        }
        tag30 = null;
        tag31 = null;
        tag32 = null;
        tag33 = null;
        tag34 = null;
        tag35 = null;
        tag36 = null;
        tag38 = null;
        tag39 = null;
        tag40 = null;
        tag41 = null;
        tag42 = null;
        tag43 = null;
        tag44 = null;
        tag45 = null;


        for (LLResultTypes.FiducialResult sighting : sightings.getFiducialResults()) {
            if (sighting.getFiducialId() == 30) {
                tag30= new AprilTagPosition();
                tag30.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag30.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag30.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag30.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag30.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag30.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);

            } else if (sighting.getFiducialId() == 31) {
                tag31= new AprilTagPosition();
                tag31.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag31.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag31.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag31.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag31.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag31.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 32) {
                tag32= new AprilTagPosition();
                tag32.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag32.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag32.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag32.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag32.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag32.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 33) {
                tag33= new AprilTagPosition();
                tag33.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag33.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag33.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag33.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag33.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag33.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 34) {
                tag34= new AprilTagPosition();
                tag34.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag34.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag34.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag34.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag34.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag34.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 35) {
                tag35= new AprilTagPosition();
                tag35.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag35.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag35.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag35.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag35.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag35.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 36) {
                tag36= new AprilTagPosition();
                tag36.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag36.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag36.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag36.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag36.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag36.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 37) {
                tag37= new AprilTagPosition();
                tag37.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag37.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag37.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag37.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag37.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag37.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 38) {
                tag38= new AprilTagPosition();
                tag38.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag38.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag38.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag38.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag38.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag38.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 39) {
                tag39= new AprilTagPosition();
                tag39.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag39.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag39.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag39.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag39.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag39.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 40) {
                tag40= new AprilTagPosition();
                tag40.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag40.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag40.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag40.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag40.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag40.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 41) {
                tag41= new AprilTagPosition();
                tag41.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag41.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag41.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag41.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag41.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag41.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 42) {
                tag42= new AprilTagPosition();
                tag42.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag42.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag42.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag42.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag42.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag42.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 43) {
                tag43= new AprilTagPosition();
                tag43.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag43.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag43.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag43.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag43.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag43.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 44) {
                tag44= new AprilTagPosition();
                tag44.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag44.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag44.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag44.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag44.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag44.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 45) {
                tag45= new AprilTagPosition();
                tag45.tag_X = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag45.tag_Y = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag45.tag_Z = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag45.tag_r = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag45.tag_p = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag45.tag_y = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
        }

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
//        telemetry.addData("blue target", visionResultBlue);

        if (tag30 == null) {
            telemetry.addData("tag30", "not found");
        } else {
            telemetry.addData("Tag30 x", tag30.tag_X);

            telemetry.addData("Tag30 y", tag30.tag_y);

            telemetry.addData("Tag30 z", tag30.tag_Z);

            telemetry.addData("Tag30 Y", tag30.tag_Y);

            telemetry.addData("Tag30 P", tag30.tag_p);

            telemetry.addData("Tag30 R", tag30.tag_r);
        }
        if (tag31 == null) {
            telemetry.addData("tag31", "not found");
        } else {
            telemetry.addData("Tag31 x", tag31.tag_X);

            telemetry.addData("Tag31 y", tag31.tag_y);

            telemetry.addData("Tag31 z", tag31.tag_Z);

            telemetry.addData("Tag31 Y", tag31.tag_Y);

            telemetry.addData("Tag31 P", tag31.tag_p);

            telemetry.addData("Tag31 R", tag31.tag_r);
        }
        if (tag32 == null) {
            telemetry.addData("tag32", "not found");
        } else {
            telemetry.addData("Tag32 x", tag32.tag_X);

            telemetry.addData("Tag32 y", tag32.tag_y);

            telemetry.addData("Tag32 z", tag32.tag_Z);

            telemetry.addData("Tag32 Y", tag32.tag_Y);

            telemetry.addData("Tag32 P", tag32.tag_p);

            telemetry.addData("Tag32 R", tag32.tag_r);
        }
        if (tag33 == null) {
            telemetry.addData("tag33", "not found");
        } else {
            telemetry.addData("Tag33 x", tag33.tag_X);

            telemetry.addData("Tag33 y", tag33.tag_y);

            telemetry.addData("Tag33 z", tag33.tag_Z);

            telemetry.addData("Tag33 Y", tag33.tag_Y);

            telemetry.addData("Tag33 P", tag33.tag_p);

            telemetry.addData("Tag33 R", tag33.tag_r);
        }
        if (tag34 == null) {
            telemetry.addData("tag34", "not found");
        } else {
            telemetry.addData("Tag34 x", tag34.tag_X);

            telemetry.addData("Tag34 y", tag34.tag_y);

            telemetry.addData("Tag34 z", tag34.tag_Z);

            telemetry.addData("Tag34 Y", tag34.tag_Y);

            telemetry.addData("Tag34 P", tag34.tag_p);

            telemetry.addData("Tag34 R", tag34.tag_r);
        }
        if (tag35 == null) {
            telemetry.addData("tag35", "not found");
        } else {
            telemetry.addData("Tag35 x", tag35.tag_X);

            telemetry.addData("Tag35 y", tag35.tag_y);

            telemetry.addData("Tag35 z", tag35.tag_Z);

            telemetry.addData("Tag35 Y", tag35.tag_Y);

            telemetry.addData("Tag35 P", tag35.tag_p);

            telemetry.addData("Tag35 R", tag35.tag_r);
        }
        if (tag36 == null) {
            telemetry.addData("tag36", "not found");
        } else {
            telemetry.addData("Tag36 x", tag36.tag_X);

            telemetry.addData("Tag36 y", tag36.tag_y);

            telemetry.addData("Tag36 z", tag36.tag_Z);

            telemetry.addData("Tag36 Y", tag36.tag_Y);

            telemetry.addData("Tag36 P", tag36.tag_p);

            telemetry.addData("Tag36 R", tag36.tag_r);
        }
        if (tag37 == null) {
            telemetry.addData("tag37", "not found");
        } else {
            telemetry.addData("Tag37 x", tag37.tag_X);

            telemetry.addData("Tag37 y", tag37.tag_y);

            telemetry.addData("Tag37 z", tag37.tag_Z);

            telemetry.addData("Tag37 Y", tag37.tag_Y);

            telemetry.addData("Tag37 P", tag37.tag_p);

            telemetry.addData("Tag37 R", tag37.tag_r);
        }
        if (tag38 == null) {
            telemetry.addData("tag38", "not found");
        } else {
            telemetry.addData("Tag38 x", tag38.tag_X);

            telemetry.addData("Tag38 y", tag38.tag_y);

            telemetry.addData("Tag38 z", tag38.tag_Z);

            telemetry.addData("Tag38 Y", tag38.tag_Y);

            telemetry.addData("Tag38 P", tag38.tag_p);

            telemetry.addData("Tag38 R", tag38.tag_r);
        }
        if (tag39 == null) {
            telemetry.addData("tag39", "not found");
        } else {
            telemetry.addData("Tag39 x", tag39.tag_X);

            telemetry.addData("Tag39 y", tag39.tag_y);

            telemetry.addData("Tag39 z", tag39.tag_Z);

            telemetry.addData("Tag39 Y", tag39.tag_Y);

            telemetry.addData("Tag39 P", tag39.tag_p);

            telemetry.addData("Tag39 R", tag39.tag_r);
        }
        if (tag40 == null) {
            telemetry.addData("tag40", "not found");
        } else {
            telemetry.addData("Tag40 x", tag40.tag_X);

            telemetry.addData("Tag40 y", tag40.tag_y);

            telemetry.addData("Tag40 z", tag40.tag_Z);

            telemetry.addData("Tag40 Y", tag40.tag_Y);

            telemetry.addData("Tag40 P", tag40.tag_p);

            telemetry.addData("Tag40 R", tag40.tag_r);
        }
        if (tag41 == null) {
            telemetry.addData("tag41", "not found");
        } else {
            telemetry.addData("Tag41 x", tag41.tag_X);

            telemetry.addData("Tag41 y", tag41.tag_y);

            telemetry.addData("Tag41 z", tag41.tag_Z);

            telemetry.addData("Tag41 Y", tag41.tag_Y);

            telemetry.addData("Tag41 P", tag41.tag_p);

            telemetry.addData("Tag41 R", tag41.tag_r);
        }
        if (tag42 == null) {
            telemetry.addData("tag42", "not found");
        } else {
            telemetry.addData("Tag42 x", tag42.tag_X);

            telemetry.addData("Tag42 y", tag42.tag_y);

            telemetry.addData("Tag42 z", tag42.tag_Z);

            telemetry.addData("Tag42 Y", tag42.tag_Y);

            telemetry.addData("Tag42 P", tag42.tag_p);

            telemetry.addData("Tag42 R", tag42.tag_r);
        }
        if (tag43 == null) {
            telemetry.addData("tag43", "not found");
        } else {
            telemetry.addData("Tag43 x", tag43.tag_X);

            telemetry.addData("Tag43 y", tag43.tag_y);

            telemetry.addData("Tag43 z", tag43.tag_Z);

            telemetry.addData("Tag43 Y", tag43.tag_Y);

            telemetry.addData("Tag43 P", tag43.tag_p);

            telemetry.addData("Tag43 R", tag43.tag_r);
        }
        if (tag44 == null) {
            telemetry.addData("tag44", "not found");
        } else {
            telemetry.addData("Tag44 x", tag44.tag_X);

            telemetry.addData("Tag44 y", tag44.tag_y);

            telemetry.addData("Tag44 z", tag44.tag_Z);

            telemetry.addData("Tag44 Y", tag44.tag_Y);

            telemetry.addData("Tag44 P", tag44.tag_p);

            telemetry.addData("Tag44 R", tag44.tag_r);
        }
        if (tag45 == null) {
            telemetry.addData("tag45", "not found");
        } else {
            telemetry.addData("Tag45 x", tag45.tag_X);

            telemetry.addData("Tag45 y", tag45.tag_y);

            telemetry.addData("Tag45 z", tag45.tag_Z);

            telemetry.addData("Tag45 Y", tag45.tag_Y);

            telemetry.addData("Tag45 P", tag45.tag_p);

            telemetry.addData("Tag45 R", tag45.tag_r);
        }

    }
}


