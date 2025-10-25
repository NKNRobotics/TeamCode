package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "lift Tester", group = "Tests")
public class LiftTester extends NKNProgram {

    class Extend extends StateCore.State{

        CRServo servo;

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            servo.setPower(-1);
        }

        @Override
        protected void started() {
            servo =  hardwareMap.get(CRServo.class,"rail");

        }

        @Override
        protected void stopped() {

        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateCore stateMachine = new StateCore();
        components.add(stateMachine);
        telemetryEnabled.add(stateMachine);
        stateMachine.addState("spin", new Extend());
        stateMachine.startState("spin");
    }
}
