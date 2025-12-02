package org.nknsd.teamcode.components.handlers.artifact.states;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.color.BallColor;

import org.nknsd.teamcode.components.utility.StateMachine;

public class IntakeBallState extends StateMachine.State{
    public static boolean killIntake = false;
    private final MicrowaveScoopHandler microwaveScoopHandler;
    private final SlotTracker slotTracker;
    private final ArtifactSystem artifactSystem;
    private final int targetSlot;
    public IntakeBallState(MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker, ArtifactSystem artifactSystem, int targetSlot){
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.slotTracker = slotTracker;
        this.targetSlot = targetSlot;
        this.artifactSystem = artifactSystem;
    }
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(killIntake){
            StateMachine.INSTANCE.stopAnonymous(this);
            killIntake = false;
            return;
        }
        if(microwaveScoopHandler.isDone()){
            microwaveScoopHandler.toggleIntake(true);
            if(slotTracker.getSlotColor(targetSlot) == BallColor.PURPLE || slotTracker.getSlotColor(targetSlot) == BallColor.GREEN) {
                if(targetSlot < 2) {
                    StateMachine.INSTANCE.startAnonymous(new IntakeBallState(microwaveScoopHandler, slotTracker, artifactSystem, targetSlot + 1));
                }
                microwaveScoopHandler.toggleIntake(false);
                StateMachine.INSTANCE.stopAnonymous(this);
            }
        }
    }

    @Override
    protected void started() {
        artifactSystem.setIntakeState(this);
        microwaveScoopHandler.setMicrowavePosition(MicrowavePositions.values()[targetSlot]);
    }

    @Override
    protected void stopped() {
        if(killIntake){
            killIntake = false;
            return;
        }
    }
}