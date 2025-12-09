package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoLaunchAllState extends StateMachine.State {
    private final FiringSystem firingSystem;
    private boolean launched = false;

    public AutoLaunchAllState(FiringSystem firingSystem) {
        this.firingSystem = firingSystem;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(launched && firingSystem.isReady()){
                StateMachine.INSTANCE.stopAnonymous(this);
        }

        if(firingSystem.isReady() && !launched){
            launched = true;
        }
    }

    @Override
    protected void started() {

    }

    @Override
    protected void stopped() {

    }
}

