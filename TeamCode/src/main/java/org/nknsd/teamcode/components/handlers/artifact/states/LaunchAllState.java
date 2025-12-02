package org.nknsd.teamcode.components.handlers.artifact.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.utility.StateMachine;

public class LaunchAllState extends StateMachine.State {
    private final ArtifactSystem artifactSystem;
    private final SlotTracker slotTracker;
    private final MicrowaveScoopHandler microwaveScoopHandler;
    private final int timesRan;

    public LaunchAllState(ArtifactSystem artifactSystem, SlotTracker slotTracker, MicrowaveScoopHandler microwaveScoopHandler, int timesRan) {
        this.artifactSystem = artifactSystem;
        this.slotTracker = slotTracker;
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.timesRan = timesRan;
    }

    boolean endNow = false;
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(microwaveScoopHandler.isDone()){
            if(!endNow){
                microwaveScoopHandler.doScoopLaunch();
                endNow = true;
            } else {
                if(timesRan < 2) {
                    StateMachine.INSTANCE.startAnonymous(new LaunchAllState(artifactSystem, slotTracker, microwaveScoopHandler, timesRan + 1));
                }
                StateMachine.INSTANCE.stopAnonymous(this);
            }
        }
    }

    @Override
    protected void started() {
        if(timesRan >= 0 && timesRan <= 2) {
            microwaveScoopHandler.setMicrowavePosition(MicrowavePositions.values()[timesRan + 3]);
        }
        artifactSystem.setLaunchState(this);
    }

    @Override
    protected void stopped() {

    }
}
