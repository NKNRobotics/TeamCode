package org.nknsd.teamcode.components.sensors;

import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class IMUSensor implements NKNComponent {
    private AdafruitBNO055IMU imu;
    private HardwareMap hardwareMap;

//

    private final RevHubOrientationOnRobot orientationOnRobot;

    public IMUSensor(RevHubOrientationOnRobot orientationOnRobot) {
        this.orientationOnRobot = orientationOnRobot;
    }

    private void initIMU(){
        imu = hardwareMap.get(AdafruitBNO055IMU.class, "imu");
        imu.initialize(new AdafruitBNO055IMU.Parameters());
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        this.hardwareMap = hardwareMap;
        initIMU();
        if (imu == null)
            return false;

        /* The next two lines define Hub orientation.
         * The Default Orientation (shown) is when a hub is mounted horizontally with the printed logo pointing UP and the USB port pointing FORWARD.
         *
         * To Do:  EDIT these two lines to match YOUR mounting configuration.
         */


        // Now initialize the IMU with this mounting orientation
        // Note: if you choose two conflicting directions, this initialization will cause a code exception.
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
        return "IMUComponent";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    public double getYaw() {
//        return -imu.getRobotYawPitchRollAngles().getYaw();
//        return imu.get
        return imu.getGravity().xAccel;
    }
    public double getPitch() {
//        return imu.getRobotYawPitchRollAngles().getPitch();
        return imu.getGravity().yAccel;
    }
    public double getRoll() {
//        return imu.getRobotYawPitchRollAngles().getRoll();
        return imu.getGravity().zAccel;
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
//        telemetry.addData("Yaw", -imu.getRobotYawPitchRollAngles().getYaw());
//        telemetry.addData("Pitch", imu.getRobotYawPitchRollAngles().getPitch());
//        telemetry.addData("Roll", imu.getRobotYawPitchRollAngles().getRoll());

    }

    public void resetIMU() {
    }
}