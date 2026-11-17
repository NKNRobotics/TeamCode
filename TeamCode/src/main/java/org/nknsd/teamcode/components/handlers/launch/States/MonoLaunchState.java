package org.nknsd.teamcode.components.handlers.launch.States;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.intakeTurret.IntakeHandler;
import org.nknsd.teamcode.components.handlers.launch.LaunchHandler;
import org.nknsd.teamcode.components.utility.StateMachine;

public class MonoLaunchState extends StateMachine.State {

    private final ConveyorHandler conveyorHandler;
    private final LaunchHandler launchHandler;
    private final IntakeHandler intakeHandler;
    private final int backwardsTime = 300;
    private final int waitTime = 300;
    private final int stopTime = 5000;


    public MonoLaunchState(ConveyorHandler conveyorHandler, LaunchHandler launchHandler, IntakeHandler intakeHandler) {
        this.conveyorHandler = conveyorHandler;
        this.launchHandler = launchHandler;
        this.intakeHandler = intakeHandler;
    }


    boolean startLaunch = true;

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

        if (runtime.milliseconds() - startTimeMS > backwardsTime && startLaunch) {
            conveyorHandler.startUp(.5);
        }

        if(runtime.milliseconds() - startTimeMS > waitTime && startLaunch){
            launchHandler.setEnabled(true);
            startLaunch = false;
        }

        if (runtime.milliseconds() - startTimeMS > stopTime) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        conveyorHandler.startDown(.5);
        launchHandler.setTargetTicks(1100);
        intakeHandler.startIntake(true);
        name = "MonoLaunchState";
    }

    @Override
    protected void stopped() {
        launchHandler.setTargetTicks(-500);
        conveyorHandler.startUp(1);
        intakeHandler.startIntake(false);
//        launchHandler.setEnabled(false);
//        conveyorHandler.stopTransfer();
    }
}
