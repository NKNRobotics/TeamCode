package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateMachine;

public abstract class TransitionState extends StateMachine.State {
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private final String[] startOnStart;

    private final double msDelay;

    private double lastTime;

    public TransitionState(String[] startOnStart, String[] toStartOnEnd, String[] toStopOnEnd, double msCheckDelay) {
        this.startOnStart = startOnStart;
        this.toStartOnEnd = toStartOnEnd;
        this.toStopOnEnd = toStopOnEnd;
        this.msDelay = msCheckDelay;
    }

    public TransitionState(String[] toStartOnEnd, String[] toStopOnEnd, double msCheckDelay) {
        this.msDelay = msCheckDelay;
        this.startOnStart = new String[0];
        this.toStartOnEnd = toStartOnEnd;
        this.toStopOnEnd = toStopOnEnd;
    }

    abstract protected boolean doTransition();


    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        double now = runtime.milliseconds();
        if (now > (lastTime + msDelay)) {
            if (doTransition()) {
                StateMachine.INSTANCE.stopAnonymous(this);
            }
            lastTime = now;
        }
    }

    @Override
    protected void started() {
        for (String stateName : this.startOnStart) {
            RobotLog.v(String.format("Starting: '%s' ", stateName));
            StateMachine.INSTANCE.startState(stateName);
        }
        lastTime = startTimeMS;
    }

    @Override
    protected void stopped() {
        for (String stateName : this.toStopOnEnd) {
            StateMachine.INSTANCE.stopState(stateName);
        }
        for (String stateName : this.toStartOnEnd) {
            StateMachine.INSTANCE.startState(stateName);
        }
    }
}
