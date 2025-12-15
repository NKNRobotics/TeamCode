package org.nknsd.teamcode.programs.tests.thisYear.firing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.nknsd.teamcode.components.drivers.LauncherDriver;
import org.nknsd.teamcode.components.handlers.WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER;
import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.components.handlers.launch.LauncherHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.controlSchemes.defaults.LauncherControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "Launcher Test", group = "Tests")
public class FTCVelLauncherTest extends NKNProgram {

    class spin extends StateMachine.State {
        private double targetTps = 1000;
        DcMotorEx wMotor;
        private double lastRunMS;

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (runtime.milliseconds() - lastRunMS < 1000)
                return;
            lastRunMS = runtime.milliseconds();
            telemetry.addData("velocity", wMotor.getVelocity());
        }

        @Override
        protected void started() {
            wMotor = (DcMotorEx) hardwareMap.dcMotor.get("LM");

            wMotor.setVelocityPIDFCoefficients(100,4,2,0);
            wMotor.setVelocity(targetTps);
        }

        @Override
        protected void stopped() {

        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        components.add(StateMachine.INSTANCE);
        StateMachine.INSTANCE.startAnonymous(new spin());
    }
}
