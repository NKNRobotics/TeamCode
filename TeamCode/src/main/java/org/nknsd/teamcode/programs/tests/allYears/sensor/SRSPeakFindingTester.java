package org.nknsd.teamcode.programs.tests.allYears.sensor;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.gamepad.AdvancedTelemetry;
import org.nknsd.teamcode.components.handlers.srs.PeakFindingKernel;
import org.nknsd.teamcode.components.handlers.srs.SRSHub;
import org.nknsd.teamcode.components.handlers.srs.SRSHubHandler;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "SRSPeakTest", group = "Tests")
public class SRSPeakFindingTester extends NKNProgram {

    SRSHubHandler srsHub = new SRSHubHandler();
    PeakFindingKernel kernel = new PeakFindingKernel();

    private class SRSPeakTestState implements NKNComponent {
        private final PeakFindingKernel kernel;
        private final SRSHubHandler srsHub;
        public SRSPeakTestState(PeakFindingKernel kernel, SRSHubHandler srsHub) {
            this.kernel = kernel;
            this.srsHub = srsHub;
        }

        @Override
        public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
            return true;
        }

        @Override
        public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        public void start(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        public void stop(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public void loop(ElapsedTime runtime, Telemetry telemetry) {

        }

        @Override
        public void doTelemetry(Telemetry telemetry) {
            telemetry.addData("Readings are currently fake", "");

            short[][] printVals;
            printVals = fakeData;
            for (int y = 0; y < 8; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < 8; x++) {
                    sb.append(normalizeIntCharacterLength(printVals[x][y], 3));
                    sb.append(", ");
                }
                telemetry.addData("row: " + y, sb.toString());
            }

            kernel.findPeaks(fakeData, true);
        }

        private String normalizeIntCharacterLength(int number, int amountOfCharacters) {
            StringBuilder out = new StringBuilder(String.valueOf(number));

            while (out.length() < amountOfCharacters) {
                out.insert(0, " ");
            }

            return out.toString();
        }

        private short[][] fakeData = new short[][]{
                new short[]{-100, -100, 1, 1, 1, 1, 1, 1},
                new short[]{-100, -100, 1, 1, 1, 1, 1, 1},
                new short[]{1, 1, 1, 1, 1, 1, 1, 1},
                new short[]{1, 1, 1, 1, 1, 1, 1, 1},
                new short[]{1, 1, 1, 1, 1, 1, 1, 1},
                new short[]{1, 1, 1, 1, 1, 1, 1, 1},
                new short[]{1, 1, 1, 1, 1, 1, 1, 1},
                new short[]{1, 1, 1, 1, 1, 1, 1, 1},
        };
    }
    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
//        components.add(StateMachine.INSTANCE);
//        StateMachine.INSTANCE.startAnonymous();

        components.add(srsHub);
//        telemetryEnabled.add(srsHub);
        kernel.linkTelemetry(telemetry);

        SRSPeakTestState testComponent = new SRSPeakTestState(kernel, srsHub);
        components.add(testComponent);
        telemetryEnabled.add(testComponent);
    }
}
