package org.nknsd.teamcode.components.handlers.launch.States;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.launch.LaunchHandler;
import org.nknsd.teamcode.components.utility.StateMachine;

public class MonoLaunchState extends StateMachine.State {

    private final ConveyorHandler conveyorHandler;
    private final LaunchHandler launchHandler;
    private final int backwardsTime = 500;
    private final int stopTime = 5000;


    public MonoLaunchState(ConveyorHandler conveyorHandler, LaunchHandler launchHandler) {
        this.conveyorHandler = conveyorHandler;
        this.launchHandler = launchHandler;
    }


    boolean startLaunch = true;

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

        if (runtime.milliseconds() - startTimeMS > backwardsTime && startLaunch) {
            launchHandler.setEnabled(true);
            launchHandler.setTargetTicks(1000);
            conveyorHandler.startUp();
            startLaunch = false;
        }

        if (runtime.milliseconds() - startTimeMS > stopTime) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        conveyorHandler.startDown();
        name = "MonoLaunchState";
    }

    @Override
    protected void stopped() {
        launchHandler.setTargetTicks(-500);
//        launchHandler.setEnabled(false);
//        conveyorHandler.stopTransfer();
    }
}
