package org.nknsd.teamcode.components.utility;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.ServoHandler;
import org.nknsd.teamcode.components.sensors.ColourSensor;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.states.MockColorSensor;

public class ColourSensorInterpreter implements NKNComponent {

//    private ColourSensor colourSensor = new ColourSensor("ColorSensor");
    private int currentSample;
    private final int maxSamples = 20;
    private BallColor[] ballColorSamples = new BallColor[maxSamples];
    private boolean currentlySampling = false;

    //xCount is used AFTER ballColorSamples have been completed to compare the values.
    private int greenCount;
    private int purpleCount;
    private int nothingCount;
    private int max;

    //timeNext and timeDelay to make sure the ball color happens evenly.
    private final double timeDelay = 5;
    private double timeNext;

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
        return "ColourSensorInterpreter";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        BallColor color;
        if(currentlySampling) {
            if (currentSample < maxSamples) {
                color = detectBallColor(currentSample);
                if (currentSample == 0) {
                    if (color != BallColor.NOTHING) {
                        timeNext = runtime.milliseconds();
                    }
                }
                if (runtime.milliseconds() >= timeNext) {
                    ballColorSamples[currentSample] = color;
                    currentSample++;
                    timeNext = timeDelay + timeNext;
                }
            } else {
                currentlySampling = false;
            }
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("DetectedColor ", getColorGuess());
        telemetry.addData("isTesting", currentlySampling);
    }

    public enum BallColor {
        GREEN,
        PURPLE,
        NOTHING,
        UNSURE
    }

    //light thresh sets the minimum light for all values, diff thresh sets the minimum difference to properly detect a color.
    private final int lightThresh = 20;
    private final int diffThresh = 50;

    /**
     * LightThresh and DiffThresh should be changed as necessary to properly tune the ability to detect the color
     * @param currentTest the current test that the ColourSensor is on
     * @return a BallColor result used as a single sample to get a final BallColor
     */
    private BallColor detectBallColor(int currentTest){
        int blue = colourSensor.colorBlue();
        int red = colourSensor.colorRed();
        int green = colourSensor.colorGreen();
        int purple = (blue + red)/2;
        if(lightThresh > green || lightThresh > red || lightThresh > blue){
            return BallColor.NOTHING;
        } else if (Math.abs(purple - green) > diffThresh){
            return BallColor.NOTHING;
        } else if (purple > green) {
            return BallColor.PURPLE;
        } else {
            return BallColor.GREEN;
        }
    }
    //a value from 0 to 1 that sets the confidence threshold for the ball color tests.
    double confidenceThreshold = 0.7;

    /**
     * If there are multiple max values that show up in the array it has the preference order is as follows: NOTHING -> GREEN-> PURPLE
     * Also uses a confidence threshold to avoid the possibility of random noise within ball color detecting
     * @return BallColor value that appears most in the ballColorSamples array
     */
    public BallColor getColorGuess() {
        nothingCount = greenCount = purpleCount = 0;
        if(isReady()) {
            for (BallColor color : ballColorSamples) {
                switch (color) {
                    case NOTHING:
                        nothingCount++;
                        break;

                    case GREEN:
                        greenCount++;
                        break;

                    case PURPLE:
                        purpleCount++;
                        break;

                }
            }
        }

        max = Math.max(nothingCount,Math.max(greenCount, purpleCount));
        if((max / (double) maxSamples) <= confidenceThreshold) {
            return BallColor.UNSURE;
        }
        if (max == nothingCount){
            return BallColor.NOTHING;
        }else if(max == greenCount){
            return BallColor.GREEN;
        }else{
            return BallColor.PURPLE;
        }
    }

    /**
     * Sets the ColourSensorInterpreter up so it can start sampling colors to interpret
     */
    public void startSampling(){
        currentSample = 0;
        greenCount = 0;
        purpleCount = 0;
        nothingCount = 0;
        currentlySampling = true;
    }

    /**
     *
     * @return boolean of if it is done with all previous tests.
     */
    public boolean isReady(){
        return(currentSample == maxSamples);
    }
//    public void link(ColourSensor colourSensor){
//        this.colourSensor = colourSensor;
//    }






//    Mock Testing Code - comment out when done!!!!
    final MockColorSensor colourSensor;
    public ColourSensorInterpreter(MockColorSensor mockColorSensor) {
        this.colourSensor = mockColorSensor;
    }

}
