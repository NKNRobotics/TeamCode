package org.nknsd.teamcode.components.handlers.artifact;


import org.nknsd.teamcode.components.handlers.artifact.states.IntakeBallState;
import org.nknsd.teamcode.components.handlers.artifact.states.IntakeStartState;
import org.nknsd.teamcode.components.handlers.artifact.states.LaunchBCStartState;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.utility.StateMachine;


public class ArtifactSystem {
    private MicrowaveScoopHandler microwaveScoopHandler;
    private SlotTracker slotTracker;

    public void setIntakeState(StateMachine.State intakeState) {
        this.intakeState = intakeState;
    }
    public void setLaunchState(StateMachine.State launchState) {
        this.launchState = launchState;
    }

    private StateMachine.State intakeState;
    private StateMachine.State launchState;

    /**
     *
     * @return boolean showing if none of the artifact states are running
     */
    public boolean isReady(){
        if(intakeState != null && intakeState.isRunning()){
            return false;
        }
        if(launchState != null && launchState.isRunning()){
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public BallColor[] getContents(){
        BallColor[] colors = new BallColor[3];
        for (int i = 0; i < 3; i++) {
            colors[i] = slotTracker.getSlotColor(i);
        }
        return colors;
    }

    /**
     *
     */
    public boolean scanAll(){
        if(isReady()) {
//            stateMachine.startState(ScanStart.STATE_NAME);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param color
     * @return
     */
    public boolean launchColor(BallColor color){
        if(isReady()) {
            for (int i = 0; i < 3; i++) {
                if (slotTracker.getSlotColor(i) == color) {
                    MicrowavePositions microwavePos = MicrowavePositions.values()[i + 3];
                    StateMachine.INSTANCE.startAnonymous(new LaunchBCStartState(microwaveScoopHandler, slotTracker, this,microwavePos));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     */
    public boolean launchAll(){
        if(isReady()) {
//            stateMachine.startState(LaunchStart.STATE_NAME);
            return true;
        }
        return false;
    }

    /**
     *
     */
    public boolean intakeUntilFull(){
        if(isReady()) {
            IntakeBallState.killIntake = false;
            StateMachine.INSTANCE.startAnonymous(new IntakeStartState(microwaveScoopHandler, slotTracker, this));
            return true;
        }
        return false;
    }
    public boolean stopIntake(){
        if(!isReady()) {
            IntakeBallState.killIntake = true;
            return true;
        }
        return false;
    }


    public void link(MicrowaveScoopHandler microwaveScoopHandler, SlotTracker slotTracker){
        this.microwaveScoopHandler = microwaveScoopHandler;
        this.slotTracker = slotTracker;
    }
}