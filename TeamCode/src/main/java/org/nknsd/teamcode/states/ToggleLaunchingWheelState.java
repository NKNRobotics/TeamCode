package org.nknsd.teamcode.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.ScoopHandler;
import org.nknsd.teamcode.components.utility.StateCore;

public class ToggleLaunchingWheelState extends StateCore.State {
    LauncherHandler launcherHandler;
    double tps, tpsError;
    String[] toStartOnEnd, toStopOnEnd;

    /**
     * @param tps The target TPS for the LauncherHandler's wheel to spin to.
     */
    public ToggleLaunchingWheelState(LauncherHandler launcherHandler, double tps) {
        this.launcherHandler = launcherHandler;
        this.tps = tps;
        this.tpsError = 1000;
        this.toStartOnEnd = new String[]{};
        this.toStopOnEnd = new String[]{};
    }

    public ToggleLaunchingWheelState(LauncherHandler launcherHandler, double tps, double tpsError, String[] toStartOnEnd) {
        this.launcherHandler = launcherHandler;
        this.tps = tps;
        this.tpsError = tpsError;
        this.toStartOnEnd = toStartOnEnd;
        this.toStopOnEnd = new String[]{};
    }

    public ToggleLaunchingWheelState(LauncherHandler launcherHandler, double tps, double tpsError, String[] toStartOnEnd, String[] toStopOnEnd) {
        this.launcherHandler = launcherHandler;
        this.tps = tps;
        this.tpsError = tpsError;
        this.toStartOnEnd = toStartOnEnd;
        this.toStopOnEnd = toStopOnEnd;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (Math.abs(launcherHandler.getCurrentTps() - tps) < tpsError) {
            stateCore.stopAnonymous(this);
        }
    }

    @Override
    protected void started(){
        launcherHandler.setTargetTps(tps);
        launcherHandler.setEnabled(tps != 0);
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
