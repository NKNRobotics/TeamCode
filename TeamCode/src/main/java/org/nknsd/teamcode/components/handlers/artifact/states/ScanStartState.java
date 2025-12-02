package org.nknsd.teamcode.components.handlers.artifact.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.utility.StateMachine;

public class ScanStartState extends StateMachine.State {
    private final ArtifactSystem artifactSystem;
    private final MicrowaveScoopHandler microwaveScoopHandler;
    private final SlotTracker slotTracker;

    public ScanStartState(ArtifactSystem artifactSystem, MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker){
        this.artifactSystem = artifactSystem;
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.slotTracker = slotTracker;
    }
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(microwaveScoopHandler.isDone()){
            StateMachine.INSTANCE.startAnonymous(new ScanState(artifactSystem, microwaveScoopHandler, slotTracker, 0));
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        artifactSystem.setScanState(this);
    }

    @Override
    protected void stopped() {

    }
}
