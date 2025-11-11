package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler.MicrowaveState;

public class SwitchMicrowaveSlotState extends StateCore.State {
    MicrowaveHandler microwaveHandler;
    MicrowaveState microwaveState;
    BallColor color;

    public SwitchMicrowaveSlotState(MicrowaveHandler microwaveHandler, MicrowaveState microwaveState) {
        this.microwaveHandler = microwaveHandler;
        this.microwaveState = microwaveState;
    }

    public SwitchMicrowaveSlotState(MicrowaveHandler microwaveHandler, BallColor color) {
        this.microwaveHandler = microwaveHandler;
        this.color = color;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    protected void started() {
        if (microwaveState != null) {
            microwaveHandler.setMicrowaveState(microwaveState);
        } else {
            switch (color) {
                case GREEN:
                    microwaveHandler.fireGreen();
                    break;
                case PURPLE:
                    microwaveHandler.firePurple();
                    break;
                default:
                    // IDK
                    RobotLog.w("I AM GLAD YOU SEE THIS, I DON'T KNOW HOW IT HAPPENED, BUT SwitchMicrowaveSlotState has been created such that it is switching to neither purple nor green.");
            }
        }

        stateCore.stopAnonymous(this);
    }

    @Override
    protected void stopped() {

    }
}
