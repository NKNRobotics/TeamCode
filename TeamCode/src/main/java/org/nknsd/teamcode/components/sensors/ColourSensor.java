package org.nknsd.teamcode.components.sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class ColourSensor implements NKNComponent {

    private final String sensorName;
    private ColorSensor sensor;
    public ColourSensor(String sensorName){this.sensorName = sensorName;}

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
        return "ColorSensor:" + sensorName;
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData(sensorName + " hue", sensor.argb());
//        telemetry.addData(sensorName + "light", sensor.alpha());
        telemetry.addData(sensorName + " blueness", sensor.blue());
        telemetry.addData(sensorName + " redness", sensor.red());
        telemetry.addData(sensorName + " greenness", sensor.green());
        telemetry.update();
    }
    public void enableLED() {sensor.enableLed(true);}
    public void disableLED() {sensor.enableLed(false);}

    public int colorHue() {return sensor.argb();};
    public int colorLight() {return sensor.alpha();};
    public int colorBlue() {return sensor.blue();};
    public int colorRed() {return sensor.red();}
    public int colorGreen() {return sensor.green();}
    enum BallColor {
        GREEN,
        PURPLE,
        NOTHING
    }
    //Thresholds for the ball color, available here for easy access
    final int greenThreshold = 400;
    final int purpleThreshold = 400;

    /**
     * thresholds for the colors are set in ColourSensor
     * @return BallColor ; can be PURPLE, GREEN, or NOTHING
     */
    public BallColor getBallColor (){
        if (colorGreen() >= greenThreshold) {
            return BallColor.GREEN;
        } else if (((colorRed() + colorBlue()) / 2) >= purpleThreshold){
            return BallColor.PURPLE;
        } else {
            return BallColor.NOTHING;
        }
    }
}
