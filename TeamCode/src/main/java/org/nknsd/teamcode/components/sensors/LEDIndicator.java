package org.nknsd.teamcode.components.sensors;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class LEDIndicator implements NKNComponent {

    private final String redName;
    private final String greenName;
    private DigitalChannel redLED;
    private DigitalChannel greenLED;

    private boolean redIsOn,greenIsOn;

    public LEDIndicator (String redHardwareName, String greenHardwareName) {
        this.redName = redHardwareName;
        this.greenName = greenHardwareName;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        redLED = hardwareMap.get(DigitalChannel.class, redName);
        greenLED = hardwareMap.get(DigitalChannel.class, greenName);

        redLED.setMode(DigitalChannel.Mode.OUTPUT);
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);

        redLED.setState(true);
        greenLED.setState(true);

        redIsOn = false;
        greenIsOn = false;

        return true;
    }

    public void setRedLED (boolean state) {
        if (redIsOn != state) {
            redLED.setState(!state);
            redIsOn = state;
            RobotLog.v("Red set to : " + state);
        }
    }
    public void setGreenLED (boolean state) {
        if (greenIsOn != state) {
            greenLED.setState(!state);
            greenIsOn = state;
            RobotLog.v("Green set to : " + state);
        }
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
        return "LED Indicator";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {

    }

    public boolean isRedOn(){
        return redIsOn;
    }
    public boolean isGreenOn(){
        return greenIsOn;
    }
}
