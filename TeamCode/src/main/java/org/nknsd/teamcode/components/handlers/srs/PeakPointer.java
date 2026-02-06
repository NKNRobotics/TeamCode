package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;

import java.util.Objects;

public class PeakPointer implements NKNComponent {
    private final PeakFinder peakFinder;
    private final AutoPositioner positioner;
    private final AbsolutePosition absPos;
    private final SRSHubHandler srsHubHandler;
//    private final SRSDataAverager srsDataAverager;

    private boolean enable;

    public void enableTargeting(boolean enable){
        this.enable = enable;
    }
    public PeakPointer(PeakFinder peakFinder, SRSHubHandler srsHubHandler, AbsolutePosition absPos, AutoPositioner positioner /*, SRSDataAverager srsDataAverager*/) {
        this.peakFinder = peakFinder;
        this.absPos = absPos;
        this.srsHubHandler = srsHubHandler;
        this.positioner = positioner;
//        this.srsDataAverager = srsDataAverager;
    }

    public double getOffset(){
        IntPoint currentPeak = peakFinder.getPeak(srsHubHandler.getNormalizedDists());
        return AngleCalculator.calculateHeadingOffset(currentPeak.getX(), srsHubHandler.getDistances()[currentPeak.getX()][currentPeak.getY()]);
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        return false;
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

    }

    public void link(){

    }
}