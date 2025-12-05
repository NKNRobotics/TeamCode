package org.nknsd.teamcode.programs.tests.artifactSystem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.MockSlotTracker;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.artifact.states.IntakeBallState;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;

import org.nknsd.teamcode.components.handlers.launch.LauncherHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;
@TeleOp(name = "ArtifactLaunchTest", group = "Tests")
public class ArtifactLaunchingTester extends NKNProgram {

    ArtifactSystem artifactSystem;
    ColorReader colorReader;
    LauncherHandler launcherHandler;
    private MicrowaveScoopHandler microwaveScoopHandler;
    private MockSlotTracker slotTracker;
    private BallColorInterpreter ballColorInterpreter;


//    private class LaunchGreenTestState extends StateMachine.State {
//
//        @Override
//        protected void run(ElapsedTime runtime, Telemetry telemetry) {
//            if(artifactSystem.isReady() && runtime.milliseconds() > startTimeMS + 1000){
//                StateMachine.INSTANCE.stopAnonymous(this);
//            }
//        }
//
//        @Override
//        protected void started() {
//            launcherHandler.setTargetTps(1200);
//            launcherHandler.setEnabled(true);
//            boolean worked = artifactSystem.launchColor(BallColor.GREEN);
//        }
//
//        @Override
//        protected void stopped() {
//            StateMachine.INSTANCE.startAnonymous(new LaunchAllTestState());
//        }
//    }
    private class LaunchAllTestState extends StateMachine.State{

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(artifactSystem.isReady() && runtime.milliseconds() > startTimeMS + 1000){
                StateMachine.INSTANCE.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
            launcherHandler.setTargetTps(1200);
            launcherHandler.setEnabled(true);
            artifactSystem.launchAll();
        }

        @Override
        protected void stopped() {

        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        artifactSystem = new ArtifactSystem();

        microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);
        BallColor[] balls = new BallColor[] {BallColor.GREEN, BallColor.PURPLE, BallColor.NOTHING};
        slotTracker = new MockSlotTracker(balls);
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
        artifactSystem.link(microwaveScoopHandler, slotTracker);
        slotTracker.link(microwaveScoopHandler, ballColorInterpreter);
        ballColorInterpreter.link(colorReader);

        StateMachine.INSTANCE.startAnonymous(new LaunchAllTestState());
    }
}