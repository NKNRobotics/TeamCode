package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.launch.FiringSystem;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoTargetState extends StateMachine.State {
    private final FiringSystem firingSystem;

    public AutoTargetState(FiringSystem firingSystem) {
        this.firingSystem = firingSystem;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(firingSystem.isReady()){
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        firingSystem.lockTarget(true);
    }

    @Override
    protected void stopped() {

    }
}
