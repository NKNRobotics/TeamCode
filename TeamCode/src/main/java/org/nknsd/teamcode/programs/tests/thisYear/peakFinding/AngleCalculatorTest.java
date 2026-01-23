package org.nknsd.teamcode.programs.tests.thisYear.peakFinding;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.gamepad.AdvancedTelemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.srs.AngleCalculator;
import org.nknsd.teamcode.components.handlers.srs.PeakFinder;
import org.nknsd.teamcode.components.handlers.srs.PeakTargetState;
import org.nknsd.teamcode.components.handlers.srs.SRSHubHandler;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name="Angle Calculator Test", group="Tests")
public class AngleCalculatorTest extends NKNProgram {
    private class AngleTestState extends StateMachine.State {
        private final SRSHubHandler srsHubHandler;
        private final PeakFinder peakFinder;
        private final AdvancedTelemetry advancedTelemetry;

        public AngleTestState(SRSHubHandler srsHubHandler, PeakFinder peakFinder, AdvancedTelemetry advancedTelemetry) {
            this.srsHubHandler = srsHubHandler;
            this.peakFinder = peakFinder;
            this.advancedTelemetry = advancedTelemetry;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            short[][] data = srsHubHandler.getNormalizedDists();
            IntPoint ballPoint = peakFinder.altPeakFind(data);

            int ballXPos = ballPoint.getX();
            int ballDist = srsHubHandler.getDistances()[ballXPos][ballPoint.getY()];
            advancedTelemetry.modifyData("Offset Angle", AngleCalculator.calculateHeadingOffset(ballXPos, ballDist));
        }

        @Override
        protected void started() {

        }

        @Override
        protected void stopped() {

        }
    }


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
        SRSHubHandler srsHubHandler = new SRSHubHandler();
        components.add(srsHubHandler);

        AdvancedTelemetry advancedTelemetry = new AdvancedTelemetry(telemetry);
        components.add(advancedTelemetry);
        telemetryEnabled.add(advancedTelemetry);

        PeakFinder peakFinder = new PeakFinder();

        telemetryEnabled.add(srsHubHandler);

        components.add(StateMachine.INSTANCE);
        StateMachine.INSTANCE.startAnonymous(new AngleTestState(srsHubHandler, peakFinder, advancedTelemetry));
    }
}
