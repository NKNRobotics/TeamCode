package org.nknsd.teamcode.programs.tests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.components.sensors.IMUSensor;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.drivers.AdvancedWheelDriver;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Absolute Movement Test", group="Tests")
public class AbsoluteMovementTestProgram extends NKNProgram {

    MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();

    class SetMotorPower extends StateCore.State{

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            mecanumMotorMixer.setPowers(new double[]{0,0.3,0});
        }

        @Override
        protected void started() {

        }

        @Override
        protected void stopped() {

        }
    }



    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        FlowSensor flowSensor1 = new FlowSensor( "RODOS");
        components.add(flowSensor1);
        FlowSensor flowSensor2 = new FlowSensor( "LODOS");
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1,flowSensor2);
        components.add(absolutePosition);
        telemetryEnabled.add(absolutePosition);

        components.add(mecanumMotorMixer);
        telemetryEnabled.add(mecanumMotorMixer);

        StateCore stateCore = new StateCore();
        components.add(stateCore);
        stateCore.addState("setty", new SetMotorPower());
        stateCore.startState("setty");
    }
}
