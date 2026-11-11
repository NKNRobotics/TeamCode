package org.nknsd.teamcode.programs.tests.thisYear;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.intakeTurret.TurretHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "TurretTest", group = "Tests")
public class TurretTest extends NKNProgram {

    static class MoveToAngleState extends StateMachine.State {

        final TurretHandler turretHandler;
        final double angle;

        MoveToAngleState(TurretHandler turretHandler, double angle) {
            this.turretHandler = turretHandler;
            this.angle = angle;

        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        protected void started() {
            turretHandler.setAngle(angle);
        }

        @Override
        protected void stopped() {

        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        TurretHandler turretHandler = new TurretHandler();
        components.add(turretHandler);
        telemetryEnabled.add(turretHandler);

        components.add(StateMachine.INSTANCE);

        StateMachine.INSTANCE.startAnonymous(new MoveToAngleState(turretHandler, 90));
    }
}