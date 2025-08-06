package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.FlowHandler;
import org.nknsd.teamcode.components.handlers.MotorDriver;
import org.nknsd.teamcode.components.handlers.MotorHandler;
import org.nknsd.teamcode.components.handlers.PidController;
import org.nknsd.teamcode.components.handlers.SimplePController;
import org.nknsd.teamcode.components.handlers.statemachine.StateMachine;
import org.nknsd.teamcode.components.handlers.statemachine.states.DriveToPosState;
import org.nknsd.teamcode.components.handlers.statemachine.states.RobotPosWithin;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgramTrue;

import java.util.List;

@TeleOp(name = "State Movement Test", group = "Tests")
public class StateMovementTest extends NKNProgramTrue {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateMachine stateMachine = new StateMachine();
        FlowHandler flowHandler = new FlowHandler();
        MotorHandler motorHandler = new MotorHandler();

        PidController xpController = new PidController(0.05, .75, 0.1, .25, true, 0.04, 0.3);
        PidController ypController = new PidController(0.06     , .75, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.5, .75, 0.1, .25, true, 0.3, 0.5);

        MotorDriver motorDriver = new MotorDriver(flowHandler, motorHandler, xpController, ypController, hpController);
        components.add(stateMachine);
        telemetryEnabled.add(stateMachine);
        components.add(flowHandler);
        telemetryEnabled.add(flowHandler);
        components.add(motorHandler);
        telemetryEnabled.add(motorHandler);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        SparkFunOTOS.Pose2D target1 = new SparkFunOTOS.Pose2D(20, 20, Math.PI);
        SparkFunOTOS.Pose2D target2 = new SparkFunOTOS.Pose2D(0, 0, 0);
        DriveToPosState driveToPosState1 = new DriveToPosState(target1, motorDriver);
        stateMachine.addState("driver1", driveToPosState1);
        DriveToPosState driveToPosState2 = new DriveToPosState(target2, motorDriver);
        stateMachine.addState("driver2", driveToPosState2);
        RobotPosWithin robotPosWithin1 = new RobotPosWithin(target1, motorDriver, 1, 100, 1, 100, 100, new String[]{"driver2", "isWithin2"}, new String[]{"driver1"});
        stateMachine.addState("isWithin1", robotPosWithin1);
        RobotPosWithin robotPosWithin2 = new RobotPosWithin(target2, motorDriver, 1, 100, 1, 100, 100, new String[]{"driver1", "isWithin1"}, new String[]{"driver2"});
        stateMachine.addState("isWithin2", robotPosWithin2);
        stateMachine.startState("driver1");
        stateMachine.startState("isWithin1");
    }
}
