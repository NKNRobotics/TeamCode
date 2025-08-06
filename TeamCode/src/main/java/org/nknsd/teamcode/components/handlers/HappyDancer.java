package org.nknsd.teamcode.components.handlers;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class HappyDancer implements NKNComponent {

    // We set
    double targetX;
    double targetY;
    double targetH;

    double startTime;
    double currentTime;

    MotorHandler motorHandler;
    FlowHandler flowHandler;

    public HappyDancer(MotorHandler motorHandler, FlowHandler flowHandler) {
        this.motorHandler = motorHandler;
        this.flowHandler = flowHandler;
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
        startTime = runtime.milliseconds();
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
        currentTime = runtime.milliseconds();

        SparkFunOTOS.Pose2D pos = flowHandler.getPosition();
        telemetry.addData("x: ", pos.x);
        telemetry.addData("Y: ", pos.y);
        telemetry.addData("H: ", pos.h);

//        if (currentTime - startTime <= 2000) {
//            motorHandler.setPowers(0.5, 0, 0);
//        } else if (currentTime - startTime <= 4000) {
//            motorHandler.setPowers(0.5, 0, 0);
//        } else if (currentTime - startTime <= 6000) {
//            motorHandler.setPowers(-.5, 0, 0);
//        } else if (currentTime - startTime <= 8000) {
//            motorHandler.setPowers(0, -0.5, 0);
//        } else if (currentTime - startTime <= 10000) {
//            motorHandler.setPowers(0, 0, .5);
//        } else if (currentTime - startTime <= 12000) {
//            motorHandler.setPowers(0, 0, -0.5);
//        } else if (currentTime - startTime <= 15000) {
//            motorHandler.setPowers(0, 0, 0);



        if (currentTime - startTime <= 1000) {
            motorHandler.setPowers(-.3, 0, 0);
        } else if (currentTime - startTime <= 4000) {
            motorHandler.setPowers(0, .3, 0);
        } else if (currentTime - startTime <= 6000) {
            motorHandler.setPowers(-.2, -.2, 0);
        } else if (currentTime - startTime <= 6900) {
            motorHandler.setPowers(0, 0, .25);
        } else if (currentTime - startTime <= 8200) {
            motorHandler.setPowers(.35, -.25, 0);
        } else if (currentTime - startTime <= 10500) {
            motorHandler.setPowers(0, -.25, .042);
        } else if (currentTime - startTime <= 12750) {
            motorHandler.setPowers(0, -.3, 0);
        } else if (currentTime - startTime <= 13000) {
            motorHandler.setPowers(.4, -.20, 0);
        } else {
            motorHandler.setPowers(0, 0, 0);
//            startTime = runtime.milliseconds();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }
}
