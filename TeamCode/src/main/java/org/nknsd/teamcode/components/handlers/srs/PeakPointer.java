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
    private AutoPositioner positioner;
    private final AbsolutePosition absPos;
    private final SRSHubHandler srsHubHandler;

    public boolean targetPeaks(){
        IntPoint currentPeak = new IntPoint(peakFinder.getPeak(srsHubHandler.getNormalizedDists()).getX(), peakFinder.getPeak(srsHubHandler.getNormalizedDists()).getY());
        if(peakFinder.getPeak(srsHubHandler.getNormalizedDists()).getX() != 10){
            positioner.enableAutoPositioning(true);
//            positioner.setTargetH(AngleCalculator.calculateHeadingOffset(currentPeak.getX(), srsHubHandler.getDistances()[currentPeak.getX()][currentPeak.getY()]), new PidController());
        }
        return false;
    }
    public void stopTargetPeaks(){

    }

    public PeakPointer(PeakFinder peakFinder, SRSHubHandler srsHubHandler, AbsolutePosition absPos) {
        this.peakFinder = peakFinder;
        this.absPos = absPos;
        this.srsHubHandler = srsHubHandler;
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

    public void link(AutoPositioner positioner){
        this.positioner = positioner;
    }
}