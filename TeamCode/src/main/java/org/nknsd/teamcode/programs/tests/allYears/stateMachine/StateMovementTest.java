package org.nknsd.teamcode.programs.tests.allYears.stateMachine;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.handlers.WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.components.utility.states.RobotPosWithin;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "State Movement Test", group = "Tests") @Disabled
public class StateMovementTest extends NKNProgram {

    public static class DriveToPosState extends StateMachine.State {

        private final MotorDriver motorDriver;
        private SparkFunOTOS.Pose2D target;

        public DriveToPosState(SparkFunOTOS.Pose2D target, MotorDriver motorDriver) {
            this.target = target;
            this.motorDriver = motorDriver;
        }

        public void setTarget(SparkFunOTOS.Pose2D target) {
            this.target = target;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        protected void started() {
            motorDriver.setTarget(target);
        }

        @Override
        protected void stopped() {

        }
    }
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {

        FlowSensor flowSensor1 = new FlowSensor("RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor("LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1, flowSensor2);
        components.add(absolutePosition);

        WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER motorHandler = new WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER();

        PidController xpController = new PidController(0.05, .75, 0.1, .25, true, 0.03, 0.3);
        PidController ypController = new PidController(0.06     , .75, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.3, .5, 0.1, .25, true, 0.4, 0.5);

        MotorDriver motorDriver = new MotorDriver(absolutePosition, motorHandler, xpController, ypController, hpController);

        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);
        components.add(motorHandler);
        telemetryEnabled.add(motorHandler);
        components.add(motorDriver);
        telemetryEnabled.add(motorDriver);
        SparkFunOTOS.Pose2D target1 = new SparkFunOTOS.Pose2D(20, 0, Math.PI);
        SparkFunOTOS.Pose2D target2 = new SparkFunOTOS.Pose2D(20, 50, 0);
        SparkFunOTOS.Pose2D target3 = new SparkFunOTOS.Pose2D(0, 50, Math.PI);
        SparkFunOTOS.Pose2D target4 = new SparkFunOTOS.Pose2D(0, 0, 0);
        DriveToPosState driveToPosState1 = new DriveToPosState(target1, motorDriver);
        StateMachine.INSTANCE.addState("driver1", driveToPosState1);
        DriveToPosState driveToPosState2 = new DriveToPosState(target2, motorDriver);
        StateMachine.INSTANCE.addState("driver2", driveToPosState2);
        DriveToPosState driveToPosState3 = new DriveToPosState(target3, motorDriver);
        StateMachine.INSTANCE.addState("driver3", driveToPosState3);
        DriveToPosState driveToPosState4 = new DriveToPosState(target4, motorDriver);
        StateMachine.INSTANCE.addState("driver4", driveToPosState4);
        RobotPosWithin robotPosWithin1 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver2", "isWithin2"}, new String[]{"driver1"});
        StateMachine.INSTANCE.addState("isWithin1", robotPosWithin1);
        RobotPosWithin robotPosWithin2 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver3", "isWithin3"}, new String[]{"driver2"});
        StateMachine.INSTANCE.addState("isWithin2", robotPosWithin2);
        RobotPosWithin robotPosWithin3 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver4", "isWithin4"}, new String[]{"driver3"});
        StateMachine.INSTANCE.addState("isWithin3", robotPosWithin3);
        RobotPosWithin robotPosWithin4 = new RobotPosWithin( motorDriver, .1, .1, 1, 1, new String[]{"driver1", "isWithin1"}, new String[]{"driver4"});
        StateMachine.INSTANCE.addState("isWithin4", robotPosWithin4);

        StateMachine.INSTANCE.startState("driver1");
        StateMachine.INSTANCE.startState("isWithin1");
    }
}
