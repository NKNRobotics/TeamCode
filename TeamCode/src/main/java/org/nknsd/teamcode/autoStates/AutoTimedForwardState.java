package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoTimedForwardState extends StateMachine.State {
    private final PowerInputMixer powerInputMixer;
    private final StateMachine.State nextState;
    private final int time;

    public AutoTimedForwardState(PowerInputMixer powerInputMixer, int time, StateMachine.State nextState) {
        this.powerInputMixer = powerInputMixer;
        this.nextState = nextState;
        this.time = time;
    }

    public AutoTimedForwardState(PowerInputMixer powerInputMixer, int time) {
        this.powerInputMixer = powerInputMixer;
        this.nextState = null;
        this.time = time;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (runtime.milliseconds() > time + startTimeMS) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        powerInputMixer.setManualPowers(new double[]{-0.5, 0, 0});
    }

    @Override
    protected void stopped() {
        powerInputMixer.setManualPowers(new double[]{0, 0, 0});
        if (nextState != null) {
            StateMachine.INSTANCE.startAnonymous(nextState);
        }
    }
}
