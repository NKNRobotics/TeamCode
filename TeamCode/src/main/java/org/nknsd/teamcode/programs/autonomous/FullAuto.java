package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.nknsd.teamcode.components.drivers.MotorDriver;
import org.nknsd.teamcode.components.handlers.FlowAverager;
import org.nknsd.teamcode.components.handlers.IntakeHandler;
import org.nknsd.teamcode.components.handlers.LauncherHandler;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.handlers.ScoopHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.helperClasses.feedbackcontroller.PidController;
import org.nknsd.teamcode.states.DriveToPosState;

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
        scoopHandler.link(microwaveHandler);


        // AUTONOMOUS
        StateCore stateCore = new StateCore();
        components.add(stateCore);


        PidController xpController = new PidController(0.05, .75, 0.1, .25, true, 0.03, 0.3);
        PidController ypController = new PidController(0.06, .75, 0.1, .25, true, 0.03, 0.3);
        PidController hpController = new PidController(0.3, .5, 0.1, .25, true, 0.4, 0.5);
        MotorDriver motorDriver = new MotorDriver(flowAverager,wheelHandler, xpController, ypController, hpController);
        components.add(motorDriver);
        // STATE
        DriveToPosState driveToPosState = new DriveToPosState(new SparkFunOTOS.Pose2D(0,48,0), motorDriver);
        stateCore.addState("driveToPos1", driveToPosState);

        stateCore.startState("driveToPos1");
    }
}
