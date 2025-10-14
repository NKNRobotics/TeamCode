package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.FlowHandler;
import org.nknsd.teamcode.components.handlers.MotorDriver;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.DriveToPosState;
import org.nknsd.teamcode.states.RobotPosWithin;

import java.util.List;

@TeleOp(name = "State Movement Test", group = "Tests")
public class StateMovementTest extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateCore stateCore = new StateCore();
        FlowHandler flowHandler = new FlowHandler();
        WheelHandler motorHandler = new WheelHandler();

        PidController xpController = new PidController(0.05, .75, 0.1, .25, true, 0.03, 0.3);
        PidController ypController = new PidController(0.06     , .75, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.3, .5, 0.1, .25, true, 0.4, 0.5);

        MotorDriver motorDriver = new MotorDriver(flowHandler, motorHandler, xpController, ypController, hpController);

        components.add(stateCore);
        telemetryEnabled.add(stateCore);
        components.add(flowHandler);
        telemetryEnabled.add(flowHandler);
        components.add(motorHandler);
        telemetryEnabled.add(motorHandler);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        SparkFunOTOS.Pose2D target1 = new SparkFunOTOS.Pose2D(20, 0, Math.PI);
        SparkFunOTOS.Pose2D target2 = new SparkFunOTOS.Pose2D(20, 50, 0);
        SparkFunOTOS.Pose2D target3 = new SparkFunOTOS.Pose2D(0, 50, Math.PI);
        SparkFunOTOS.Pose2D target4 = new SparkFunOTOS.Pose2D(0, 0, 0);
        DriveToPosState driveToPosState1 = new DriveToPosState(target1, motorDriver);
        stateCore.addState("driver1", driveToPosState1);
        DriveToPosState driveToPosState2 = new DriveToPosState(target2, motorDriver);
        stateCore.addState("driver2", driveToPosState2);
        DriveToPosState driveToPosState3 = new DriveToPosState(target3, motorDriver);
        stateCore.addState("driver3", driveToPosState3);
        DriveToPosState driveToPosState4 = new DriveToPosState(target4, motorDriver);
        stateCore.addState("driver4", driveToPosState4);
        RobotPosWithin robotPosWithin1 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver2", "isWithin2"}, new String[]{"driver1"});
        stateCore.addState("isWithin1", robotPosWithin1);
        RobotPosWithin robotPosWithin2 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver3", "isWithin3"}, new String[]{"driver2"});
        stateCore.addState("isWithin2", robotPosWithin2);
        RobotPosWithin robotPosWithin3 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver4", "isWithin4"}, new String[]{"driver3"});
        stateCore.addState("isWithin3", robotPosWithin3);
        RobotPosWithin robotPosWithin4 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver1", "isWithin1"}, new String[]{"driver4"});
        stateCore.addState("isWithin4", robotPosWithin4);

        stateCore.startState("driver1");
        stateCore.startState("isWithin1");
    }
}
