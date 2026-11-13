package org.nknsd.teamcode.components.handlers.launch;

import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.intakeTurret.IntakeHandler;
import org.nknsd.teamcode.components.handlers.launch.States.StartIntakeState;
import org.nknsd.teamcode.components.handlers.launch.States.StartLongLaunchState;
import org.nknsd.teamcode.components.utility.StateMachine;

public class BallSystem {

    private ConveyorHandler conveyorHandler;
    private IntakeHandler intakeHandler;
    private LaunchHandler launchHandler;

    private final int confidence = 5;

    public void longLaunch(){
        if(!intakeHandler.intakeRunning() && StateMachine.INSTANCE.isStateRunning("StartLongLaunchState")) {
            StateMachine.INSTANCE.startAnonymous(new StartLongLaunchState(conveyorHandler, launchHandler));
        }
    }
    public void shortLaunch(){
    }

    public void launchDistance(double dist){

    }
    public void intakeToLaunch(){

    }
    public void intake(){
//        StateMachine.INSTANCE.startAnonymous(new StartIntakeState(intakeHandler, conveyorHandler));
    }

    public void link(ConveyorHandler conveyorHandler, IntakeHandler intakeHandler, LaunchHandler launchHandler){
        conveyorHandler = this.conveyorHandler;
        intakeHandler = this.intakeHandler;
        launchHandler = this.launchHandler;
    }
}
