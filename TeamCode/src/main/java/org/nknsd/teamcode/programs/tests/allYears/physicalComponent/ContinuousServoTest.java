package org.nknsd.teamcode.programs.tests.allYears.physicalComponent;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.MicrowavePositions;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "ServoTest", group = "Tests")
public class ContinuousServoTest extends NKNProgram {

    MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();

    public class RunServo extends StateMachine.State {

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (runtime.milliseconds() < 3000) {
                microwaveScoopHandler.microwaveServo.setPosition(1);
                telemetry.addLine("micro 1");
            }
            if (runtime.milliseconds() < 6000 && runtime.milliseconds() > 3000) {
                microwaveScoopHandler.microwaveServo.setPosition(0);
                telemetry.addLine("micro 0");

            }
            if (runtime.milliseconds() < 9000 && runtime.milliseconds() > 6000) {
                microwaveScoopHandler.spinner.setPower(1);
                telemetry.addLine("intake 1");

            }
            if (runtime.milliseconds() < 12000 && runtime.milliseconds() > 9000) {
                microwaveScoopHandler.spinner.setPower(0);
                telemetry.addLine("intake 0");

            }
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
        components.add(StateMachine.INSTANCE);
        components.add(microwaveScoopHandler);

        StateMachine.INSTANCE.startAnonymous(new RunServo());

    }
}
