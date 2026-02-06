package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoLaunchColorsState extends StateMachine.State {
    private final FiringSystem firingSystem;
    private final BallColor[] colors;
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;
    private boolean launched = false;
    private int timesLaunched = 0;

    public AutoLaunchColorsState(FiringSystem firingSystem, BallColor[] colors, String[] toStopOnEnd, String[] toStartOnEnd) {
        this.firingSystem = firingSystem;
        this.colors = colors;
        this.toStopOnEnd = toStopOnEnd;
        this.toStartOnEnd = toStartOnEnd;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(launched && firingSystem.isReady()){
            if(timesLaunched >= colors.length) {
                StateMachine.INSTANCE.stopAnonymous(this);
            } else {
                launched = false;
            }
        }

        if(firingSystem.isReady() && !launched){
            launched = true;
            timesLaunched++;
            switch(colors[timesLaunched]){
                case GREEN: firingSystem.fireGreen();
                break;

                case PURPLE: firingSystem.firePurple();
                break;

                default: break;
            }
        }
    }

    @Override
    protected void started() {

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
