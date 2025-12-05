package org.nknsd.teamcode.components.handlers.artifact;


import org.nknsd.teamcode.components.handlers.artifact.states.IntakeBallState;
import org.nknsd.teamcode.components.handlers.artifact.states.IntakeStartState;
import org.nknsd.teamcode.components.handlers.artifact.states.LaunchAllStartState;
import org.nknsd.teamcode.components.handlers.artifact.states.LaunchBCStartState;
import org.nknsd.teamcode.components.handlers.artifact.states.ScanStartState;
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
    public void setScanState(StateMachine.State scanState) {this.scanState = scanState; }

    private StateMachine.State intakeState;
    private StateMachine.State launchState;
    private StateMachine.State scanState;

    /**
     * checks if any artifact states are currently running
     * @return boolean showing if none of the artifact states are running
     */
    public boolean isReady(){
        if(intakeState != null && intakeState.isRunning()){
            return false;
        }
        if(launchState != null && launchState.isRunning()){
            return false;
        }
        if(scanState != null && scanState.isRunning()){
            return false;
        }
        return true;
    }

    /**
     * gets the contents of the microwave
     * @return BallColor array of the BallColor in each of the three slots
     */
    public BallColor[] getContents(){
        BallColor[] colors = new BallColor[3];
        for (int i = 0; i < 3; i++) {
            colors[i] = slotTracker.getSlotColor(i);
        }
        return colors;
    }

    /**
     * Scans all three slots to check the color
     */
    public boolean scanAll(){
        if(isReady()) {
            StateMachine.INSTANCE.startAnonymous(new ScanStartState(this, microwaveScoopHandler, slotTracker));
            return true;
        } else {
            return false;
        }
    }

    /**
     * launches a ball of a given BallColor that is in a slot
     * @param color The BallColor that is supposed to be launched
     * @return if the specified BallColor was found or not
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
     * Launches all three slots of the microwave without care for if a slot is empty
     */
    public boolean launchAll(){
        if(isReady()) {
                StateMachine.INSTANCE.startAnonymous(new LaunchAllStartState(microwaveScoopHandler, slotTracker, this));
            return true;
        }
        return false;
    }

    /**
     * Intakes balls until it has a BallColor for each slot
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