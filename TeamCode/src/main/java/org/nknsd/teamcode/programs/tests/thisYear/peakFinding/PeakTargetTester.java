package org.nknsd.teamcode.programs.tests.thisYear.peakFinding;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.srs.PeakFinder;
import org.nknsd.teamcode.components.handlers.srs.PeakTargetState;
import org.nknsd.teamcode.components.handlers.srs.SRSHub;
import org.nknsd.teamcode.components.handlers.srs.SRSHubHandler;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name="Peak Targeting Test", group="Tests")
public class PeakTargetTester extends NKNProgram {
    public class PeakTargetTestState extends StateMachine.State{

        private final PowerInputMixer powerInputMixer;
        private final AbsolutePosition absPos;
        private final SRSHubHandler srsHubHandler;
        private final PeakFinder peakFinder;

        public PeakTargetTestState(PowerInputMixer powerInputMixer, AbsolutePosition absPos, SRSHubHandler srsHubHandler, PeakFinder peakFinder) {
            this.powerInputMixer = powerInputMixer;
            this.absPos = absPos;
            this.srsHubHandler = srsHubHandler;
            this.peakFinder = peakFinder;
        }

        //        private final PidController pidController;

        public double lastRunTime = 0;
        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(lastRunTime < runtime.milliseconds() - 10000){
                if(!PeakTargetState.killIntakeTargeting){
                    PeakTargetState.killIntakeTargeting = true;
                } else {
                    PeakTargetState.killIntakeTargeting = false;
                    IntPoint point = peakFinder.getPeak(srsHubHandler.getNormalizedDists());
                    StateMachine.INSTANCE.startAnonymous(new PeakTargetState(point, powerInputMixer, absPos));
                    lastRunTime = runtime.milliseconds();
                }
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

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);
        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);

        FlowSensor flowSensor1 = new FlowSensor("RODOS");
        FlowSensor flowSensor2 = new FlowSensor("LODOS");
        components.add(flowSensor1);
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1, flowSensor2);
        components.add(absolutePosition);

        SRSHubHandler srsHubHandler = new SRSHubHandler();
        components.add(srsHubHandler);

        PeakFinder peakFinder = new PeakFinder();

        powerInputMixer.link(absolutePowerMixer, mecanumMotorMixer);
        absolutePowerMixer.link(mecanumMotorMixer, absolutePosition);

        telemetryEnabled.add(powerInputMixer);
        telemetryEnabled.add(srsHubHandler);
        telemetryEnabled.add(absolutePosition);

        StateMachine.INSTANCE.startAnonymous(new PeakTargetTestState(powerInputMixer, absolutePosition, srsHubHandler, peakFinder));
    }
}
