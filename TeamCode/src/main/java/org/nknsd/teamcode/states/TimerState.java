package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateCore;

public class TimerState extends StateCore.State {
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private final String[] startOnStart;
    double timerMS;

    public TimerState(double timerMS,String[] startOnStart, String[] toStartOnEnd, String[] toStopOnEnd ) {
        this.timerMS = timerMS;
        this.startOnStart = startOnStart;
        this.toStartOnEnd = toStartOnEnd;
        this.toStopOnEnd = toStopOnEnd;
    }

    public TimerState(double timerMS,String[] toStartOnEnd, String[] toStopOnEnd ) {
        this.timerMS = timerMS;
        this.startOnStart = new String[0];
        this.toStartOnEnd = toStartOnEnd;
        this.toStopOnEnd = toStopOnEnd;
    }


    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (runtime.milliseconds() > (startTime + timerMS)) {
            stateCore.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        for (String stateName : this.startOnStart) {
            RobotLog.v(String.format("Starting: '%s' ", stateName));
            stateCore.startState(stateName);
        }
    }

    @Override
    protected void stopped() {
        for (String stateName : this.toStopOnEnd) {
            stateCore.stopState(stateName);
        }
        for (String stateName : this.toStartOnEnd) {
            stateCore.startState(stateName);
        }
    }
}
