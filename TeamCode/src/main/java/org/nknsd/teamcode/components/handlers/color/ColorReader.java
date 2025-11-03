package org.nknsd.teamcode.components.handlers.color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class ColorReader implements NKNComponent {

    private final String sensorName;
    private ColorSensor sensor;

    public ColorReader(String sensorName){this.sensorName = sensorName;}
    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        sensor = hardwareMap.get(ColorSensor.class, sensorName);
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
        return "ColorReader:" + sensorName;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        int[] rgb = getRGB();
        int[] hueLight = getHueLight();
        telemetry.addData(sensorName + " hue", hueLight[0]);
        telemetry.addData(sensorName + " light",hueLight[1]);
        telemetry.addData(sensorName + " redness",rgb[0]);
        telemetry.addData(sensorName + " greenness", rgb[1]);
        telemetry.addData(sensorName + " blueness", rgb[2]);
        telemetry.update();
    }
    public void enableLED() {sensor.enableLed(true);}
    public void disableLED() {sensor.enableLed(false);}

    public int[] getRGB(){
        int[] rgb = new int[3];
        rgb[0] = sensor.red();
        rgb[1] = sensor.green();
        rgb[2] = sensor.blue();
        RobotLog.v("ColorReader: R="+rgb[0]+" G="+rgb[1]+" B="+rgb[1]);
        return rgb;
    }

    public int[] getHueLight(){
        int[] hueLight = new int[2];
        hueLight[0] = sensor.argb();
        hueLight[1] = sensor.alpha();
        RobotLog.v("ColorReader: H="+hueLight[0]+" L="+hueLight[1]);
        return hueLight;
    }
}
