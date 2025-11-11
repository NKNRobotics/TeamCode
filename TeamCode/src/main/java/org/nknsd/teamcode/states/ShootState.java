package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.ScoopHandler;
import org.nknsd.teamcode.components.utility.StateCore;

public class ShootState extends StateCore.State {
    ScoopHandler scoopHandler;

    public ShootState(ScoopHandler scoopHandler) {
        this.scoopHandler = scoopHandler;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    protected void started(){
        scoopHandler.triggerScoopToLaunch();
        stateCore.stopAnonymous(this);
    }

    @Override
    protected void stopped() {

    }
}
