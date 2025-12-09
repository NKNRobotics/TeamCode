package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoLaunchColorsState extends StateMachine.State {
    private final FiringSystem firingSystem;
    private final BallColor[] colors;
    private boolean launched = false;
    private int timesLaunched = 0;

    public AutoLaunchColorsState(FiringSystem firingSystem, BallColor[] colors) {
        this.firingSystem = firingSystem;
        this.colors = colors;
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

    }
}
