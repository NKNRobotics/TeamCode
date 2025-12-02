package org.nknsd.teamcode.components.handlers.artifact;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;

public class MockSlotTracker extends SlotTracker {


    BallColor[] slotColors;

    /**
     *
     * @param slotColors use an array of 3 colors otherwise it will not work
     */
    public MockSlotTracker(BallColor[] slotColors){
        this.slotColors = slotColors;
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

    public void link(MicrowaveScoopHandler microwaveScoopHandler, BallColorInterpreter colorInterpreter){
    }

    private void findSlotColors(){
    }

    public BallColor getSlotColor(int slotnumber){
        return slotColors[slotnumber];
    }

    public void clearSlot(int slotNumber){
        slotColors[slotNumber] = BallColor.NOTHING;
    }
}
