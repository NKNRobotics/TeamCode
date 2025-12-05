package org.nknsd.teamcode.programs.tests.thisYear.lift;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "manual lift test", group = "Tests") @Disabled
public class ManualLiftTest extends NKNProgram {

    public class LiftTheServos extends StateMachine.State{

        CRServo BLlift;
        CRServo BRlift;
        CRServo FLlift;

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(runtime.milliseconds()-startTimeMS > 500) {
                BLlift.setPower(0.35);
            }
        }

        @Override
        protected void started() {
            BLlift = hardwareMap.crservo.get("BLlift");
            BRlift = hardwareMap.crservo.get("BRlift");
            FLlift = hardwareMap.crservo.get("FLlift");

            BRlift.setDirection(DcMotorSimple.Direction.REVERSE);
            FLlift.setDirection(DcMotorSimple.Direction.REVERSE);
            BRlift.setPower(1);
            FLlift.setPower(0.35);
        }

        @Override
        protected void stopped() {

        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        components.add(StateMachine.INSTANCE);
        StateMachine.INSTANCE.startAnonymous(new LiftTheServos());
    }
}
