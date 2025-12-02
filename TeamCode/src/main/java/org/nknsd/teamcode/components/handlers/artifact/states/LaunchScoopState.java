package org.nknsd.teamcode.components.handlers.artifact.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.utility.StateMachine;


public class LaunchScoopState extends StateMachine.State{
    private final MicrowaveScoopHandler microwaveScoopHandler;
    private final SlotTracker slotTracker;
    private final ArtifactSystem artifactSystem;
    private final MicrowavePositions slot;
    public LaunchScoopState(MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker, ArtifactSystem artifactSystem, MicrowavePositions slot){
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.slotTracker = slotTracker;
        this.artifactSystem = artifactSystem;
        this.slot = slot;
    }
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (microwaveScoopHandler.isDone()){
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        artifactSystem.setLaunchState(this);
        microwaveScoopHandler.doScoopLaunch();
        slotTracker.clearSlot(slot.ordinal() - 3);
    }

    @Override
    protected void stopped() {

    }
}