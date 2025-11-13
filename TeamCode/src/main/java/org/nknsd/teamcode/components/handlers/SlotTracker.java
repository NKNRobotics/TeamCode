package org.nknsd.teamcode.components.handlers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class SlotTracker implements NKNComponent {

    MicrowaveHandler microwaveHandler;
    BallColorInterpreter colorInterpreter;

    BallColor[] slotColors = new BallColor[]{BallColor.UNSURE, BallColor.UNSURE, BallColor.UNSURE};


    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {
        findSlotColors();
    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        findSlotColors();
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("slot 0 color", slotColors[0]);
        telemetry.addData("slot 1 color", slotColors[1]);
        telemetry.addData("slot 2 color", slotColors[2]);
    }

    public void link(MicrowaveHandler microwaveHandler, BallColorInterpreter colorInterpreter){
        this.microwaveHandler = microwaveHandler;
        this.colorInterpreter = colorInterpreter;
    }

    public void findSlotColors(){
        MicrowaveHandler.MicrowaveState slotState = microwaveHandler.getMicrowaveState();
        if(slotState == MicrowaveHandler.MicrowaveState.LOAD0){
            slotColors[0] = colorInterpreter.getColorGuess();
        } else if(slotState == MicrowaveHandler.MicrowaveState.LOAD1){
            slotColors[1] = colorInterpreter.getColorGuess();
        } else if(slotState == MicrowaveHandler.MicrowaveState.LOAD2){
            slotColors[2] = colorInterpreter.getColorGuess();
        }
    }
    public void clearSlotColors(){
        for (int i = 0; i < 3; i++) {
            slotColors[i] = BallColor.UNSURE;
        }
    }

    public BallColor getSlotColor(int slotnumber){
        return slotColors[slotnumber];
    }

}
