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
                tag30.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag30.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag30.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag30.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag30.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag30.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);

            } else if (sighting.getFiducialId() == 31) {
                tag31= new AprilTagPosition();
                tag31.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag31.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag31.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag31.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag31.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag31.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 32) {
                tag32= new AprilTagPosition();
                tag32.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag32.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag32.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag32.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag32.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag32.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 33) {
                tag33= new AprilTagPosition();
                tag33.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag33.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag33.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag33.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag33.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag33.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 34) {
                tag34= new AprilTagPosition();
                tag34.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag34.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag34.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag34.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag34.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag34.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 35) {
                tag35= new AprilTagPosition();
                tag35.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag35.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag35.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag35.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag35.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag35.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 36) {
                tag36= new AprilTagPosition();
                tag36.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;
                ;
                tag36.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;
                ;
                tag36.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;
                ;
                tag36.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag36.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag36.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            } else if (sighting.getFiducialId() == 37) {
                tag37= new AprilTagPosition();
                tag37.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag37.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag37.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag37.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag37.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag37.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 38) {
                tag38= new AprilTagPosition();
                tag38.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag38.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag38.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag38.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag38.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag38.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 39) {
                tag39= new AprilTagPosition();
                tag39.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag39.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag39.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag39.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag39.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag39.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 40) {
                tag40= new AprilTagPosition();
                tag40.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag40.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag40.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag40.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag40.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag40.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 41) {
                tag41= new AprilTagPosition();
                tag41.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag41.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag41.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag41.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag41.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag41.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 42) {
                tag42= new AprilTagPosition();
                tag42.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag42.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag42.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag42.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag42.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag42.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 43) {
                tag43= new AprilTagPosition();
                tag43.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag43.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag43.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag43.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag43.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag43.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 44) {
                tag44= new AprilTagPosition();
                tag44.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag44.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag44.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag44.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag44.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag44.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
            else if (sighting.getFiducialId() == 45) {
                tag45= new AprilTagPosition();
                tag45.getTagX = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).x;

                tag45.getTagY = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).y;

                tag45.getTagz = sighting.getTargetPoseCameraSpace().getPosition().toUnit(DistanceUnit.INCH).z;

                tag45.getTagr = sighting.getTargetPoseCameraSpace().getOrientation().getRoll(AngleUnit.DEGREES);
                tag45.getTagp = sighting.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.DEGREES);
                tag45.getTagy = sighting.getTargetPoseCameraSpace().getOrientation().getYaw(AngleUnit.DEGREES);
            }
        }

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
//        telemetry.addData("blue target", visionResultBlue);

        if (tag30 == null) {
            telemetry.addData("tag30", "not found");
        } else {
            telemetry.addData("Tag30 x", tag30.getTagX);

            telemetry.addData("Tag30 y", tag30.getTagy);

            telemetry.addData("Tag30 z", tag30.getTagz);

            telemetry.addData("Tag30 Y", tag30.getTagY);

            telemetry.addData("Tag30 P", tag30.getTagp);

            telemetry.addData("Tag30 R", tag30.getTagr);
        }
        if (tag31 == null) {
            telemetry.addData("tag31", "not found");
        } else {
            telemetry.addData("Tag31 x", tag31.getTagX);

            telemetry.addData("Tag31 y", tag31.getTagy);

            telemetry.addData("Tag31 z", tag31.getTagz);

            telemetry.addData("Tag31 Y", tag31.getTagY);

            telemetry.addData("Tag31 P", tag31.getTagp);

            telemetry.addData("Tag31 R", tag31.getTagr);
        }
        if (tag32 == null) {
            telemetry.addData("tag32", "not found");
        } else {
            telemetry.addData("Tag32 x", tag32.getTagX);

            telemetry.addData("Tag32 y", tag32.getTagy);

            telemetry.addData("Tag32 z", tag32.getTagz);

            telemetry.addData("Tag32 Y", tag32.getTagY);

            telemetry.addData("Tag32 P", tag32.getTagp);

            telemetry.addData("Tag32 R", tag32.getTagr);
        }
        if (tag33 == null) {
            telemetry.addData("tag33", "not found");
        } else {
            telemetry.addData("Tag33 x", tag33.getTagX);

            telemetry.addData("Tag33 y", tag33.getTagy);

            telemetry.addData("Tag33 z", tag33.getTagz);

            telemetry.addData("Tag33 Y", tag33.getTagY);

            telemetry.addData("Tag33 P", tag33.getTagp);

            telemetry.addData("Tag33 R", tag33.getTagr);
        }
        if (tag34 == null) {
            telemetry.addData("tag34", "not found");
        } else {
            telemetry.addData("Tag34 x", tag34.getTagX);

            telemetry.addData("Tag34 y", tag34.getTagy);

            telemetry.addData("Tag34 z", tag34.getTagz);

            telemetry.addData("Tag34 Y", tag34.getTagY);

            telemetry.addData("Tag34 P", tag34.getTagp);

            telemetry.addData("Tag34 R", tag34.getTagr);
        }
        if (tag35 == null) {
            telemetry.addData("tag35", "not found");
        } else {
            telemetry.addData("Tag35 x", tag35.getTagX);

            telemetry.addData("Tag35 y", tag35.getTagy);

            telemetry.addData("Tag35 z", tag35.getTagz);

            telemetry.addData("Tag35 Y", tag35.getTagY);

            telemetry.addData("Tag35 P", tag35.getTagp);

            telemetry.addData("Tag35 R", tag35.getTagr);
        }
        if (tag36 == null) {
            telemetry.addData("tag36", "not found");
        } else {
            telemetry.addData("Tag36 x", tag36.getTagX);

            telemetry.addData("Tag36 y", tag36.getTagy);

            telemetry.addData("Tag36 z", tag36.getTagz);

            telemetry.addData("Tag36 Y", tag36.getTagY);

            telemetry.addData("Tag36 P", tag36.getTagp);

            telemetry.addData("Tag36 R", tag36.getTagr);
        }
        if (tag37 == null) {
            telemetry.addData("tag37", "not found");
        } else {
            telemetry.addData("Tag37 x", tag37.getTagX);

            telemetry.addData("Tag37 y", tag37.getTagy);

            telemetry.addData("Tag37 z", tag37.getTagz);

            telemetry.addData("Tag37 Y", tag37.getTagY);

            telemetry.addData("Tag37 P", tag37.getTagp);

            telemetry.addData("Tag37 R", tag37.getTagr);
        }
        if (tag38 == null) {
            telemetry.addData("tag38", "not found");
        } else {
            telemetry.addData("Tag38 x", tag38.getTagX);

            telemetry.addData("Tag38 y", tag38.getTagy);

            telemetry.addData("Tag38 z", tag38.getTagz);

            telemetry.addData("Tag38 Y", tag38.getTagY);

            telemetry.addData("Tag38 P", tag38.getTagp);

            telemetry.addData("Tag38 R", tag38.getTagr);
        }
        if (tag39 == null) {
            telemetry.addData("tag39", "not found");
        } else {
            telemetry.addData("Tag39 x", tag39.getTagX);

            telemetry.addData("Tag39 y", tag39.getTagy);

            telemetry.addData("Tag39 z", tag39.getTagz);

            telemetry.addData("Tag39 Y", tag39.getTagY);

            telemetry.addData("Tag39 P", tag39.getTagp);

            telemetry.addData("Tag39 R", tag39.getTagr);
        }
        if (tag40 == null) {
            telemetry.addData("tag40", "not found");
        } else {
            telemetry.addData("Tag40 x", tag40.getTagX);

            telemetry.addData("Tag40 y", tag40.getTagy);

            telemetry.addData("Tag40 z", tag40.getTagz);

            telemetry.addData("Tag40 Y", tag40.getTagY);

            telemetry.addData("Tag40 P", tag40.getTagp);

            telemetry.addData("Tag40 R", tag40.getTagr);
        }
        if (tag41 == null) {
            telemetry.addData("tag41", "not found");
        } else {
            telemetry.addData("Tag41 x", tag41.getTagX);

            telemetry.addData("Tag41 y", tag41.getTagy);

            telemetry.addData("Tag41 z", tag41.getTagz);

            telemetry.addData("Tag41 Y", tag41.getTagY);

            telemetry.addData("Tag41 P", tag41.getTagp);

            telemetry.addData("Tag41 R", tag41.getTagr);
        }
        if (tag42 == null) {
            telemetry.addData("tag42", "not found");
        } else {
            telemetry.addData("Tag42 x", tag42.getTagX);

            telemetry.addData("Tag42 y", tag42.getTagy);

            telemetry.addData("Tag42 z", tag42.getTagz);

            telemetry.addData("Tag42 Y", tag42.getTagY);

            telemetry.addData("Tag42 P", tag42.getTagp);

            telemetry.addData("Tag42 R", tag42.getTagr);
        }
        if (tag43 == null) {
            telemetry.addData("tag43", "not found");
        } else {
            telemetry.addData("Tag43 x", tag43.getTagX);

            telemetry.addData("Tag43 y", tag43.getTagy);

            telemetry.addData("Tag43 z", tag43.getTagz);

            telemetry.addData("Tag43 Y", tag43.getTagY);

            telemetry.addData("Tag43 P", tag43.getTagp);

            telemetry.addData("Tag43 R", tag43.getTagr);
        }
        if (tag44 == null) {
            telemetry.addData("tag44", "not found");
        } else {
            telemetry.addData("Tag44 x", tag44.getTagX);

            telemetry.addData("Tag44 y", tag44.getTagy);

            telemetry.addData("Tag44 z", tag44.getTagz);

            telemetry.addData("Tag44 Y", tag44.getTagY);

            telemetry.addData("Tag44 P", tag44.getTagp);

            telemetry.addData("Tag44 R", tag44.getTagr);
        }
        if (tag45 == null) {
            telemetry.addData("tag45", "not found");
        } else {
            telemetry.addData("Tag45 x", tag45.getTagX);

            telemetry.addData("Tag45 y", tag45.getTagy);

            telemetry.addData("Tag45 z", tag45.getTagz);

            telemetry.addData("Tag45 Y", tag45.getTagY);

            telemetry.addData("Tag45 P", tag45.getTagp);

            telemetry.addData("Tag45 R", tag45.getTagr);
        }

    }
}


