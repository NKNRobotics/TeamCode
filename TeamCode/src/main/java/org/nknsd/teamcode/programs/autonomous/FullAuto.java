package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.handlers.FlowAverager;
import org.nknsd.teamcode.components.handlers.IntakeHandler;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.handlers.ScoopHandler;
import org.nknsd.teamcode.components.handlers.SlotTracker;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;
import org.nknsd.teamcode.states.DriveToPosState;
import org.nknsd.teamcode.states.FindAllColors;
import org.nknsd.teamcode.states.RobotPosWithin;
import org.nknsd.teamcode.states.ShootState;
import org.nknsd.teamcode.states.SwitchMicrowaveSlotState;
import org.nknsd.teamcode.states.ToggleLaunchingWheelState;

import java.util.List;

@Autonomous(name="Normal Auto")
public class FullAuto extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);

        LauncherHandler launcherHandler = new LauncherHandler();
        components.add(launcherHandler);

        MicrowaveHandler microwaveHandler = new MicrowaveHandler();
        components.add(microwaveHandler);

        IntakeHandler intakeHandler = new IntakeHandler();
        components.add(intakeHandler);

        ScoopHandler scoopHandler = new ScoopHandler();
        components.add(scoopHandler);

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);

        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.5);
        components.add(ballColorInterpreter);

        // SENSORS
        FlowSensor rightFlowSensor = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "RODOS");
        components.add(rightFlowSensor);
        FlowSensor leftFlowSensor = new FlowSensor(new SparkFunOTOS.Pose2D(0, 0, 0), "LODOS");
        components.add(leftFlowSensor);

        FlowAverager flowAverager = new FlowAverager(rightFlowSensor,leftFlowSensor);
        components.add(flowAverager);



        // LINKING
        microwaveHandler.link(intakeHandler);
        microwaveHandler.link(scoopHandler);
        microwaveHandler.link(slotTracker);
        scoopHandler.link(microwaveHandler);
        slotTracker.link(microwaveHandler, ballColorInterpreter);


        // AUTONOMOUS
        StateCore stateCore = new StateCore();
        components.add(stateCore);
        telemetryEnabled.add(stateCore);


        PidController xpController = new PidController(0.05, .75, 0.1, .25, true, 0.03, 0.3);
        PidController ypController = new PidController(0.06, .75, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.3, .5, 0.1, .25, true, 0.4, 0.5);
        MotorDriver motorDriver = new MotorDriver(flowAverager,wheelHandler, xpController, ypController, hpController);
        components.add(motorDriver);


        // STATE
        FindAllColors findAllColors = new FindAllColors(microwaveHandler, new String[]{"bringPurpleToFire"});
        stateCore.addState("findAllColors", findAllColors);

        SwitchMicrowaveSlotState switchToPurple = new SwitchMicrowaveSlotState(microwaveHandler, BallColor.PURPLE);
        stateCore.addState("bringPurpleToFire", switchToPurple);

        SwitchMicrowaveSlotState switchToGreen = new SwitchMicrowaveSlotState(microwaveHandler, BallColor.GREEN);
        stateCore.addState("bringGreenToFire", switchToGreen);

        DriveToPosState driveToPosState = new DriveToPosState(new SparkFunOTOS.Pose2D(0,48,0), motorDriver);
        stateCore.addState("driveToPos1", driveToPosState);

        RobotPosWithin robotPosWithin = new RobotPosWithin(motorDriver, 0.0872665, .1, 1, 1, new String[]{"driveToPos1"}, new String[]{});
        stateCore.addState("withinPos1", robotPosWithin);

        ToggleLaunchingWheelState startLaunchWheel = new ToggleLaunchingWheelState(launcherHandler, 1500, 5, new String[]{"shoot"});
        stateCore.addState("startLaunchWheel", startLaunchWheel);

        SwitchMicrowaveSlotState aa = new SwitchMicrowaveSlotState(microwaveHandler, MicrowaveHandler.MicrowaveState.FIRE0);
        stateCore.addState("fire0", aa);

        ShootState shoot = new ShootState(scoopHandler);
        stateCore.addState("shoot", shoot);

//        stateCore.startState("findAllColors");
        stateCore.startState("fire0");
        stateCore.startState("startLaunchWheel");
    }
}
