package org.nknsd.teamcode.components.handlers.launch.States;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.intakeTurret.IntakeHandler;
import org.nknsd.teamcode.components.utility.StateMachine;

public class StartIntakeState extends StateMachine.State {

    private final IntakeHandler intakeHandler;
    private final ConveyorHandler conveyorHandler;

    public StartIntakeState(IntakeHandler intakeHandler, ConveyorHandler conveyorHandler) {
        this.intakeHandler = intakeHandler;
        this.conveyorHandler = conveyorHandler;
    }



    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    protected void started() {

    }

    @Override
    protected void stopped() {

    }
}
