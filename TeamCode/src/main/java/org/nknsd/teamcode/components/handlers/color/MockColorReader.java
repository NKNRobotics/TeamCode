package org.nknsd.teamcode.components.handlers.color;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MockColorReader extends ColorReader {

    private final String sensorName;

    private final int[][][] mockHLData = new int[][][]{
            {{0, 5}, {1, 6}, {2, 7}},
            {{2, 9}, {1, 6}, {2, 7}}};

    private int testNumber = 0;

    private int readCounter = 0;

    public MockColorReader(String sensorName) {
        super(sensorName);
        this.sensorName = sensorName;

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
        return "org.nknsd.teamcode.components.handlers.color.MockColorReader:" + sensorName;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Mock Data Hue", mockHLData[readCounter][0]);
        telemetry.addData("Mock Data Lightness", mockHLData[readCounter][1]);
    }

    public void enableLED() {
    }

    public void disableLED() {
    }

    public int[] getRGB() {
        int[] rgb = new int[3];
        rgb[0] = 500;
        rgb[1] = 100;
        rgb[2] = 600;
        return rgb;
    }

    public int[] getHueLight() {
        int[] hueLight = mockHLData[testNumber][readCounter];
        readCounter += 1;
        if (readCounter >= mockHLData.length) {
            readCounter = 0;
        }
        return hueLight;
    }

    public void setTestNumber(int testNumber){
        this.testNumber =  testNumber;
    }
}
