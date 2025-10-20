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
    private int currentSample;
    private int maxSamples = 15;
    //xCount is used AFTER ballColorSamples have been completed to compare the values.
    private BallColor[] ballColorSamples = new BallColor[maxSamples];

    private int greenCount;
    private int purpleCount;
    private int nothingCount;
    private int max;

    //timeNext and timeDelay to make sure the ball color happens evenly.
    private final double timeDelay = 5;
    private double timeNext;
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
    private double timeTemporary = 0;
    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        BallColor color = BallColor.NOTHING; //setting it to nothing ensures that if it
        if(currentSample < maxSamples) {
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
            if (runtime.milliseconds() >= timeTemporary + 10000) {
                startSampling();
                timeTemporary = runtime.milliseconds();
            }
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {telemetry.addData(sensorName + " hue", sensor.argb());

        telemetry.addData(sensorName + " light", sensor.alpha());
        telemetry.addData(sensorName + " blueness", sensor.blue());
        telemetry.addData(sensorName + " redness", sensor.red());
        telemetry.addData(sensorName + " greenness", sensor.green());
        telemetry.addData(sensorName + " detectedColor", getColorGuess());
        telemetry.addData(sensorName + " 50% sure", isSure(50));
        telemetry.addData(sensorName + " 70% sure", isSure(70));
        telemetry.addData(sensorName + " 90% sure", isSure(90));
        //temporaryTestingTelemetry
        telemetry.addData(sensorName + "nothingCount", nothingCount);
        telemetry.addData(sensorName + "greenCount", greenCount);
        telemetry.addData(sensorName + "purpleCount", purpleCount);
        telemetry.addData(sensorName + "currentSample", currentSample);


        telemetry.update();
    }
    public void enableLED() {sensor.enableLed(true);}
    public void disableLED() {sensor.enableLed(false);}

    public int colorHue() {return sensor.argb();};
    public int colorLight() {return sensor.alpha();};
    public int colorBlue() {return sensor.blue();};
    public int colorRed() {return sensor.red();}
    public int colorGreen() {return sensor.green();}
    public enum BallColor {
        GREEN,
        PURPLE,
        NOTHING
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
        int blue = sensor.blue();
        int red = sensor.red();
        int green = sensor.green();
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

    /**
     * If there are multiple max values that show up in the array it has the preference order is as follows: NOTHING -> GREEN-> PURPLE
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
        if (max == nothingCount){
            return BallColor.NOTHING;
        }else if(max == greenCount){
            return BallColor.GREEN;
        }else{
            return BallColor.PURPLE;
        }
    }

    /**
     * Sets the ColourSensor up so it can start sampling colors
     */
    public void startSampling(){
        currentSample = 0;
        greenCount = 0;
        purpleCount = 0;
        nothingCount = 0;
    }

    /**
     * compares # of tests to highest color amount and decides if it is sure based on a given confidencePercent
     * @param confidencePercent should be a value from 0 - 100 based on how confident you need to be.
     * @return boolean of if detected value is equal to or higher than confidencePercent
     */
   public boolean isSure(int confidencePercent){
       return (((max / (double) maxSamples ) * 100) >= confidencePercent);
   }

    /**
     *
     * @return boolean of if it is done with all previous tests.
     */
   public boolean isReady(){
       return(currentSample == maxSamples);
   }
}
