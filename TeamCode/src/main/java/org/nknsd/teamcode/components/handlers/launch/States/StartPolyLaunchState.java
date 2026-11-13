package org.nknsd.teamcode.components.handlers.launch.States;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.launch.LaunchHandler;
import org.nknsd.teamcode.components.utility.StateMachine;

public class StartPolyLaunchState extends StateMachine.State {

    private final ConveyorHandler conveyorHandler;
    private final LaunchHandler launchHandler;
    private final int backwardsTime = 200;
    private final int stopTime = 5000;

    public StartPolyLaunchState(ConveyorHandler conveyorHandler, LaunchHandler launchHandler) {
        this.conveyorHandler = conveyorHandler;
        this.launchHandler = launchHandler;
    }


    boolean startLaunch = true;
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

        if(startTimeMS - runtime.milliseconds() > backwardsTime && startLaunch){
            launchHandler.setEnabled(true);
            conveyorHandler.startUp();
            startLaunch = false;
        }

        if(startTimeMS - runtime.milliseconds() > stopTime){
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        conveyorHandler.startDown();
    }

    @Override
    protected void stopped() {
        launchHandler.setEnabled(false);
//        conveyorHandler.stopTransfer();
    }
}
