package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.nknsd.teamcode.autoStates.AutoSimpleLaunchState;
import org.nknsd.teamcode.autoStates.AutoTimedForwardState;
import org.nknsd.teamcode.components.handlers.intakeTurret.ConveyorHandler;
import org.nknsd.teamcode.components.handlers.intakeTurret.IntakeHandler;
import org.nknsd.teamcode.components.handlers.launch.BallSystem;
import org.nknsd.teamcode.components.handlers.launch.LaunchHandler;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@Autonomous(name = "Meet One Auto")
public class MeetOneAuto extends NKNProgram {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        //stateMachine
        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);
        //wheels
        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);
        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);
        powerInputMixer.link(absolutePowerMixer, mecanumMotorMixer);

        //intake
        IntakeHandler intakeHandler = new IntakeHandler();
        components.add(intakeHandler);
        telemetryEnabled.add(intakeHandler);

        //launcher stuff
        LaunchHandler launchHandler = new LaunchHandler(0.9, 1.1);
        components.add(launchHandler);
        ConveyorHandler conveyorHandler = new ConveyorHandler();
        components.add(conveyorHandler);
        BallSystem ballSystem = new BallSystem();
        ballSystem.link(conveyorHandler, intakeHandler, launchHandler);

        StateMachine.INSTANCE.startAnonymous(new AutoTimedForwardState(powerInputMixer, 500,
                new AutoSimpleLaunchState(ballSystem,
                        new AutoTimedForwardState(powerInputMixer, 500))));
    }
}