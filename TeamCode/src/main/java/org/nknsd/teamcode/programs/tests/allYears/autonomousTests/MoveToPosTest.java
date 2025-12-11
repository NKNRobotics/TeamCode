package org.nknsd.teamcode.programs.tests.allYears.autonomousTests;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.autoStates.AutoMoveToPosState;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;


import java.util.List;

@TeleOp(name = "Move To Position", group = "Tests")

public class MoveToPosTest extends NKNProgram {


    class DriveToPosState extends StateMachine.State {

        final AutoPositioner autoPositioner;
        final SparkFunOTOS.Pose2D targetPos;

        DriveToPosState(AutoPositioner autoPositioner, SparkFunOTOS.Pose2D targetPos) {
            this.autoPositioner = autoPositioner;
            this.targetPos = targetPos;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        protected void started() {
            autoPositioner.setTargetX(targetPos.x);
            autoPositioner.setTargetY(targetPos.y);
            autoPositioner.setTargetH(targetPos.h);
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
        telemetryEnabled.add(absolutePosition);

        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);
        telemetryEnabled.add(mecanumMotorMixer);

        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        absolutePowerMixer.link(mecanumMotorMixer, absolutePosition);

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);

        absolutePowerMixer.link(mecanumMotorMixer, absolutePosition);
        powerInputMixer.link(absolutePowerMixer);

        AutoPositioner autoPositioner = new AutoPositioner(RobotVersion.INSTANCE.pControllerX, RobotVersion.INSTANCE.pControllerY, RobotVersion.INSTANCE.pControllerH);

        components.add(autoPositioner);
        autoPositioner.link(powerInputMixer, absolutePosition);

        components.add(StateMachine.INSTANCE);
        StateMachine.INSTANCE.addState("start", new AutoMoveToPosState(autoPositioner, absolutePosition,0,10,-0.09, 0,0,0,0, new String[]{}, new String[]{}));
        StateMachine.INSTANCE.startState("start");
    }
}
