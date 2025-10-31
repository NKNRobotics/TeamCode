package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateCore;

public class MockColorSensor extends StateCore.State {

    final int blue;
    final int red;
    final int green;

    public MockColorSensor(int blue, int red, int green) {
        this.blue = blue;
        this.red = red;
        this.green = green;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
//        blue += 1;
//        if(blue == 1000){
//            blue = 0;
//        }
//        red += 1;
//        if(red == 1000){
//            red = 0;
//        }
//        green += 1;
//        if(green == 1000){
//            green = 0;
//        }
    }

    @Override
    protected void started() {

    }

    @Override
    protected void stopped() {

    }

    public int colorBlue(){
        return blue;
    }
    public int colorRed(){
        return red;
    }
    public int colorGreen(){
        return green;
    }


}
