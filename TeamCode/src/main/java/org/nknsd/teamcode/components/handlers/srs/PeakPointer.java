package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.vision.BasketLocator;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;

import java.util.Objects;

public class PeakPointer implements NKNComponent {
    private final PeakFinder peakFinder;
    private final AutoPositioner positioner;
    private final AbsolutePosition absPos;
    private final SRSHubHandler srsHubHandler;

    final private double MAX_XOFFSET = 0.1;
    final private double WAIT_TIME_MS = 50;

    final private PidController pid =  new PidController(0.5, .4, 0.08, .2, true, 0.1, 0.2);

//    private final SRSDataAverager srsDataAverager;

    private boolean enable;
    private double lastRunTime;
    private Double offset;
    private Short dist;

    public void enableTargeting(boolean enable) {
        this.enable = enable;
//        positioner.enableAutoPositioning(false, false, enable); // for when we merge
        positioner.enableAutoPositioning(enable);
        positioner.setTargetH(0, RobotVersion.INSTANCE.pidControllerH);
        positioner.setTargetX(0, RobotVersion.INSTANCE.pidControllerX);
        positioner.setTargetY(0, RobotVersion.INSTANCE.pidControllerY);
    }

    public PeakPointer(PeakFinder peakFinder, SRSHubHandler srsHubHandler, AbsolutePosition absPos, AutoPositioner positioner /*, SRSDataAverager srsDataAverager*/) {
        this.peakFinder = peakFinder;
        this.absPos = absPos;
        this.srsHubHandler = srsHubHandler;
        this.positioner = positioner;
//        this.srsDataAverager = srsDataAverager;
    }

    public Double getOffset() {
        IntPoint currentPeak = peakFinder.getPeak(srsHubHandler.getNormalizedDists());
        if (currentPeak == null) {
            dist = null;
            return null;
        }
        dist = srsHubHandler.getDistances()[currentPeak.getX()][currentPeak.getY()];
        return -(AngleCalculator.calculateHeadingOffset(currentPeak.getX(), dist));
    }

    public Short getDist(){
        return dist;
    }

    public boolean targetAcquired() {
        if (offset == null) {
            return false;
        }
        return Math.abs(offset) < MAX_XOFFSET;
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
        if (runtime.milliseconds() - lastRunTime > WAIT_TIME_MS) {
            offset = getOffset();
            RobotLog.v("offset " + offset);
            if (enable && offset != null) {
                positioner.setTargetH((absPos.getPosition().h + offset), pid);
            }
            lastRunTime = runtime.milliseconds();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("enable", enable);
        if (enable) {
            if (offset != null) {
                telemetry.addData("offset", offset);
            } else {
                telemetry.addLine("offset null");
            }
            telemetry.addData("targeted?", targetAcquired());
        }
    }
}