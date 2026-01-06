package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.DoublePoint;
import org.nknsd.teamcode.frameworks.NKNComponent;

import java.util.HashMap;

public class SRSHubHandler implements NKNComponent {
    private SRSHub hub;
    private short[][] distArray;
    private short[][] distMeans = new short[8][8];

    private double previousSampleTime = 0;
    private final double sampleDelay = 100;

    private short[][] getDistances() {
        hub.update();
        SRSHub.VL53L5CX distData = hub.getI2CDevice(1, SRSHub.VL53L5CX.class);

        distArray = new short[8][8];

        for (int i = 0; i < 64; i++) {
            distArray[7 - i / 8][i % 8] = distData.distances[i];
        }
        return distArray;
    }
    private short[][] getNewMean(short[][] currentDists) {
        for(int i = 0; i < 64; i++){
            int x = 7 - i / 8;
            int y = i % 8;
             distMeans[x][y] = (short) ((distMeans[x][y] + currentDists[x][y]) / 2);
        }
        return distMeans;
    }
    public short[][] getNormalizedDists(){
        getDistances();
        short[][] normalDists = new short[8][8];
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                normalDists[x][y] = (short) (distArray[x][y] - distMeans[x][y]);
            }
        }
        return normalDists;
    }

    public DoublePoint ballLocation(){
        DoublePoint thePlaceOfBallResting = new DoublePoint(10,10);
        short[][] normalDists;
        normalDists = getNormalizedDists();
        double highestPoint = -30;

        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                //greater than because the sensor reads negative values as high
                if (highestPoint > normalDists[x][y]){
                    highestPoint = normalDists[x][y];

                    thePlaceOfBallResting.setX(x - 3.5);
                    thePlaceOfBallResting.setY(y - 3.5);
                }
            }
        }
        return thePlaceOfBallResting;
    }
    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        // All ports default to NONE, buses default to empty
        SRSHub.Config config = new SRSHub.Config();
        config.addI2CDevice(1, new SRSHub.VL53L5CX(SRSHub.VL53L5CX.Resolution.GRID_8x8));

        hub = hardwareMap.get(
                SRSHub.class,
                "srsHub"
        );
        hub.init(config);
        return true;
    }

    private int timesSampled = 0;
    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {
        telemetry.addLine("Waiting for SRSHub");
        while (!hub.ready()) ;
        telemetry.addLine("SRSHub Ready!");
        if(runtime.milliseconds() >= previousSampleTime + sampleDelay){
            previousSampleTime = runtime.milliseconds();
            getNewMean(getDistances());
            timesSampled++;
        }
        if (timesSampled > 20){
            telemetry.addLine("Normal Values Found");
        }
    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "SRSHub";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
//        if (distArray != null) {
//            for (int y = 0; y < 8; y++) {
//                StringBuilder sb = new StringBuilder();
//                for (int x = 0; x < 8; x++) {
//                    sb.append(distArray[x][y]);
//                    sb.append(", ");
//                }
//                telemetry.addData("row: " + y, sb.toString());
//            }
//        }
//        if (distMeans != null) {
//            for (int y = 0; y < 8; y++) {
//                StringBuilder sb = new StringBuilder();
//                for (int x = 0; x < 8; x++) {
//                    sb.append(distMeans[x][y]);
//                    sb.append(", ");
//                }
//                telemetry.addData("row: " + y, sb.toString());
//            }
//        }
        if (getNormalizedDists() != null) {

            short[][] printVals;
            printVals = getNormalizedDists();
            for (int y = 0; y < 8; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < 8; x++) {
                    sb.append(printVals[x][y]);
                    sb.append(", ");
                }
                telemetry.addData("row: " + y, sb.toString());
            }
        }
        DoublePoint ballSpot = ballLocation();
        if (ballSpot != null){
            telemetry.addData("x", ballSpot.getX());
            telemetry.addData("y", ballSpot.getY());
        }
    }
}
