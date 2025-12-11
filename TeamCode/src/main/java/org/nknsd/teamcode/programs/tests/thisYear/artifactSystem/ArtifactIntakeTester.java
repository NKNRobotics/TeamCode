package org.nknsd.teamcode.programs.tests.thisYear.artifactSystem;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.artifact.states.IntakeBallState;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;

import org.nknsd.teamcode.components.handlers.launch.LauncherHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;
@TeleOp(name = "ArtifactIntakeTest", group = "Tests") @Disabled
public class ArtifactIntakeTester extends NKNProgram {

    ArtifactSystem artifactSystem;
    ColorReader colorReader;
    LauncherHandler launcherHandler;
    private MicrowaveScoopHandler microwaveScoopHandler;
    private SlotTracker slotTracker;
    private BallColorInterpreter ballColorInterpreter;


    private class IntakeStopTestState extends StateMachine.State {

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(runtime.milliseconds() > startTimeMS + 6000){
//                RobotLog.v("killState" + IntakeBallState.killIntake);
                StateMachine.INSTANCE.stopAnonymous(this);
            }
            if(runtime.milliseconds() > startTimeMS + 5000){
                artifactSystem.stopIntake();
            }

        }

        @Override
        protected void started() {
            artifactSystem.intakeUntilFull();
        }

        @Override
        protected void stopped() {
            artifactSystem.intakeUntilFull();
        }
    }

    private class IntakeTestState extends StateMachine.State {

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
//            RobotLog.v("intakeTestState" + artifactSystem.isReady());
            if(artifactSystem.isReady()){
                StateMachine.INSTANCE.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
//            RobotLog.v("killState" + IntakeBallState.killIntake);
            StateMachine.INSTANCE.startAnonymous(new IntakeTestState());
        }

        @Override
        protected void stopped() {
//                stateCore.startAnonymous(new LaunchGreenTestState());
        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        artifactSystem = new ArtifactSystem();

        microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);

        slotTracker = new SlotTracker();
        components.add(slotTracker);

        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);

        ballColorInterpreter = new BallColorInterpreter(10, 0.01);
        components.add(ballColorInterpreter);
        colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        telemetryEnabled.add(colorReader);
        telemetryEnabled.add(slotTracker);
        telemetryEnabled.add(microwaveScoopHandler);
        telemetryEnabled.add(ballColorInterpreter);

        launcherHandler = new LauncherHandler(.97,1.03);
        components.add(launcherHandler);

        //linking
//        artifactSystem.link(microwaveScoopHandler, slotTracker);
        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        ballColorInterpreter.link(colorReader);

        StateMachine.INSTANCE.startAnonymous(new IntakeStopTestState());
    }
}