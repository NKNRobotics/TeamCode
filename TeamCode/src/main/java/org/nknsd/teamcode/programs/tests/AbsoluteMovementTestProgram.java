package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.nknsd.teamcode.components.handlers.AbsolutePosition;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.drivers.AdvancedWheelDriver;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Advanced Movement Test", group="Tests") @Disabled
public class AbsoluteMovementTestProgram extends NKNProgram {
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        // Gamepad Handler
        GamePadHandler gamePadHandler = new GamePadHandler();
        components.add(gamePadHandler);
        //telemetryEnabled.add(gamePadHandler);

        // Wheel Handler
        WheelHandler wheelHandler = new WheelHandler();
        components.add(wheelHandler);
        //telemetryEnabled.add(wheelHandler);

        // Flow Sensory Handler
        FlowSensor flowSensor1 = new FlowSensor(new SparkFunOTOS.Pose2D(0,0,0), "RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor(new SparkFunOTOS.Pose2D(0,0,0), "LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1,flowSensor2);
        components.add(absolutePosition);
        //telemetryEnabled.add(flowSensorHandler);

        // IMU Handler
        IMUSensor imuSensor = new IMUSensor();
        components.add(imuSensor);
        //telemetryEnabled.add(imuComponent);

        WheelControlScheme wheelController = new WheelControlScheme();
        wheelController.link(gamePadHandler);

        // Wheel Driver
        AdvancedWheelDriver wheelDriver = new AdvancedWheelDriver(0, 1, 5, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_Y, GamePadHandler.GamepadSticks.LEFT_JOYSTICK_X, GamePadHandler.GamepadSticks.RIGHT_JOYSTICK_X);
        components.add(wheelDriver);
        //telemetryEnabled.add(wheelDriver);
        wheelDriver.link(gamePadHandler, wheelHandler, imuSensor, wheelController);
    }
}
