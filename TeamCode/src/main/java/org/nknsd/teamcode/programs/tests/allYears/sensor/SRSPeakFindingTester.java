package org.nknsd.teamcode.programs.tests.allYears.sensor;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.gamepad.AdvancedTelemetry;
import org.nknsd.teamcode.components.handlers.srs.PeakFindingKernel;
import org.nknsd.teamcode.components.handlers.srs.SRSHubHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "SRSPeakTest", group = "Tests")
public class SRSPeakFindingTester extends NKNProgram {

    SRSHubHandler srsHub = new SRSHubHandler();
    PeakFindingKernel kernel = new PeakFindingKernel();

    private class SRSPeakTestState extends StateMachine.State{
        private final PeakFindingKernel kernel;
        private final SRSHubHandler srsHub;
        private double lastRun = 0;
        private SRSPeakTestState(PeakFindingKernel kernel, SRSHubHandler srsHub) {
            this.kernel = kernel;
            this.srsHub = srsHub;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if(runtime.milliseconds() >= lastRun + 500){
                kernel.findPeaks(srsHub.getNormalizedDists());
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
        StateMachine.INSTANCE.startAnonymous(new SRSPeakTestState(kernel, srsHub));

        components.add(srsHub);
        telemetryEnabled.add(srsHub);
        kernel.linkTelemetry(telemetry);
    }
}
