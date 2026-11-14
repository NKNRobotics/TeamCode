package org.nknsd.teamcode.components.handlers.launch;

import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.intakeTurret.IntakeHandler;
import org.nknsd.teamcode.components.handlers.launch.States.MonoLaunchState;
import org.nknsd.teamcode.components.utility.StateMachine;

public class BallSystem {

    private ConveyorHandler conveyorHandler;
    private IntakeHandler intakeHandler;
    private LaunchHandler launchHandler;

    private final int confidence = 5;

    public void monoLaunch(){
        if(!intakeHandler.intakeRunning() && !StateMachine.INSTANCE.isStateRunning("MonoLaunchState")) {
            StateMachine.INSTANCE.startAnonymous(new MonoLaunchState(conveyorHandler, launchHandler));
        }
    }
    public void polyLaunch(){
    }

    public void launchDistance(double dist){

    }
    public void intakeToLaunch(){

    }
    public void intake(){
//        StateMachine.INSTANCE.startAnonymous(new StartIntakeState(intakeHandler, conveyorHandler));
    }

    public void link(ConveyorHandler conveyorHandler, IntakeHandler intakeHandler, LaunchHandler launchHandler){
        this.conveyorHandler = conveyorHandler;
        this.intakeHandler = intakeHandler;
        this.launchHandler = launchHandler;
    }
}
