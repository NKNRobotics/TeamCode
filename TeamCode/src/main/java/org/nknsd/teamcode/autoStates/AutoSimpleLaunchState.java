package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.launch.BallSystem;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoSimpleLaunchState extends StateMachine.State {
    private final StateMachine.State nextState;
    private final BallSystem ballSystem;
    private int launchNum = 0;

    public AutoSimpleLaunchState(BallSystem ballSystem, StateMachine.State nextState) {
        this.nextState = nextState;
        this.ballSystem = ballSystem;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (launchNum >= 4) {
            StateMachine.INSTANCE.stopAnonymous(this);
        } else if (!StateMachine.INSTANCE.isStateRunning("MonoLaunchState")) {
            ballSystem.monoLaunch();
            launchNum++;
        }
    }

    @Override
    protected void started() {
    }

    @Override
    protected void stopped() {
        StateMachine.INSTANCE.startAnonymous(nextState);
    }
}
