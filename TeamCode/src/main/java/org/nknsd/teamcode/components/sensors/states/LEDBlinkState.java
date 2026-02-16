package org.nknsd.teamcode.components.sensors.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.LEDIndicator;
import org.nknsd.teamcode.components.utility.StateMachine;

public class LEDBlinkState extends StateMachine.State {

    private final int blinkTimeMS;
    private final LEDIndicator led;

    private final boolean red,green;

    public LEDBlinkState(int blinkTimeMS, LEDIndicator led , boolean red, boolean green) {
        this.blinkTimeMS = blinkTimeMS;
        this.led = led;
        this.red = red;
        this.green = green;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (startTimeMS + blinkTimeMS < runtime.milliseconds()) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        if (green) {
            led.setGreenLED(true);
        }
        if (red){
            led.setRedLED(true);
        }
    }

    @Override
    protected void stopped() {
        if (green) {
            led.setGreenLED(false);
        }
        if (red){
            led.setRedLED(false);
        }
    }
}
