package org.nknsd.teamcode.programs.tests.allYears.autonomousTests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Absolute Movement Test", group="Tests") @Disabled
public class MovementTestProgram extends NKNProgram {

    class SetMotorPower extends StateMachine.State{

        final PowerInputMixer mixer;

        public SetMotorPower(PowerInputMixer mixer){
            this.mixer = mixer;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
//            mixer.setAutoPowers(new double[]{0.3,0.3,0.3});
//            mixer.setManualPowers(new double[]{-0.3,-0.3,0});
        }

        @Override
        protected void started() {
            mixer.setAutoEnabled(new boolean[]{true, false, false});
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

        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);
        telemetryEnabled.add(mecanumMotorMixer);

        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        absolutePowerMixer.link(mecanumMotorMixer,absolutePosition);

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);
        powerInputMixer.link(absolutePowerMixer);


        components.add(StateMachine.INSTANCE);
        StateMachine.INSTANCE.addState("setty", new SetMotorPower(powerInputMixer));
        StateMachine.INSTANCE.startState("setty");
    }
}
