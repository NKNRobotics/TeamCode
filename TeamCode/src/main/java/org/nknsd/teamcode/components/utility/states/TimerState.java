package org.nknsd.teamcode.components.utility.states;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateMachine;

public class TimerState extends StateMachine.State {
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
        if (runtime.milliseconds() > (startTimeMS + timerMS)) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    final protected void started() {
        internalStarted();
        for (String stateName : this.startOnStart) {
            RobotLog.v(String.format("Starting: '%s' ", stateName));
            StateMachine.INSTANCE.startState(stateName);
        }
    }

    protected void internalStarted(){}

    @Override
    final protected void stopped() {
        internalStopped();
        for (String stateName : this.toStopOnEnd) {
            StateMachine.INSTANCE.stopState(stateName);
        }
        for (String stateName : this.toStartOnEnd) {
            StateMachine.INSTANCE.startState(stateName);
        }
    }

    protected void internalStopped(){

    }
}