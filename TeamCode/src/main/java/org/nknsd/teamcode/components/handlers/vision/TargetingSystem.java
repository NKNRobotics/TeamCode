package org.nknsd.teamcode.components.handlers.vision;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class TargetingSystem implements NKNComponent {

    final private double MAX_XOFFSET = 0.07;
    final private double MAX_ANGLE_VEL = 0.1;
    final private double MIN_MOVE_VEL = 0.05;
    final private double SKEW_MULTIPLIER = 0;

    private BasketLocator basketLocator;
    PowerInputMixer powerInputMixer;

    ID targetingColor;

    double lastRunTime;
    double lastOffset;

    double vel;

    boolean targetEnabled = false;

    final private PidController pidController;
    private double power;
    private AbsolutePosition absolutePosition;
    private double distance;

    public TargetingSystem(PidController pidController) {
        this.pidController = pidController;
    }


    public boolean targetAcquired() {
        boolean ready;
        if (Math.abs(absolutePosition.getVelocity().x) > MIN_MOVE_VEL && Math.abs(absolutePosition.getVelocity().y) > MIN_MOVE_VEL) {
            ready = false;
//            RobotLog.v("targeting ready " + ready);
            return ready;
        }
        ready = Math.abs(lastOffset) < MAX_XOFFSET;
//        RobotLog.v("targeting ready " + ready);
        return ready;
    }

    public void enableAutoTargeting(boolean enable) {
        if (targetEnabled && !enable) {
            lastOffset = .5;
            vel = 0;
        }
        targetEnabled = enable;
        powerInputMixer.setAutoEnabled(new boolean[]{false, false, enable});
    }

    public double getDistance() {
        return distance;
    }

    public void setTargetingColor(ID color) {
        if (color == ID.RED || color == ID.BLUE) {
            targetingColor = color;
        }
    }

    public boolean targetVisible() {
        return basketLocator.getOffset(targetingColor).distance != -1;
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
        if (basketLocator.getOffset(targetingColor).distance == -1 && targetEnabled && !RobotVersion.isAutonomous()) {
            powerInputMixer.setAutoEnabled(new boolean[]{false, false, false});
            targetEnabled = false;
        }
        if (runtime.milliseconds() - lastRunTime > RobotVersion.INSTANCE.visionLoopIntervalMS) {
            distance = basketLocator.getOffset(targetingColor).distance;
            if (targetEnabled && distance != -1) {
                double[] targetingPowers = new double[]{0, 0, 0};
                BasketLocator.BasketOffset basketData = basketLocator.getOffset(targetingColor);
                double currentOffset = basketData.xOffset - 0.5;
                currentOffset += basketData.skew * SKEW_MULTIPLIER;
                vel = (currentOffset - lastOffset) / 50;
                targetingPowers[2] = (pidController.findOutput(currentOffset, 0, vel, runtime.milliseconds() - lastRunTime));
//            RobotLog.v("xOffset" + shiftedOffset);
                lastOffset = currentOffset;
                power = targetingPowers[2];
//                RobotLog.v("targeting powers x: " + targetingPowers[0] + ", y: " + targetingPowers[1] + ", h: " + targetingPowers[2]);
                powerInputMixer.setAutoPowers(targetingPowers);

            } else if(targetEnabled){
                powerInputMixer.setAutoPowers(new double[]{0,0,0}) ;
            }
            lastRunTime = runtime.milliseconds();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("xOffset", lastOffset);
        telemetry.addData("turning power", power);
    }

    public void link(BasketLocator basketLocator, PowerInputMixer powerInputMixer, AbsolutePosition absolutePosition) {
        this.basketLocator = basketLocator;
        this.powerInputMixer = powerInputMixer;
        this.absolutePosition = absolutePosition;
    }
}