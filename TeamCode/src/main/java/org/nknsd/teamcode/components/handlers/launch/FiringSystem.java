package org.nknsd.teamcode.components.handlers.launch;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.handlers.vision.TargetingSystem;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class FiringSystem implements NKNComponent {

    private final int WHEELSPEED_CONFIDENCE = 4;

     private LaunchSystem launchSystem;
     private TargetingSystem targetingSystem;
     private ArtifactSystem artifactSystem;

    private double lastTime;
    private boolean autoLocked;
    private ID color;


    public void setTargetColor(ID targetColor) {
        color = targetColor;
        targetingSystem.setTargetingColor(targetColor);
    }

    public void lockTarget(boolean enable) {
        autoLocked = enable;
        targetingSystem.enableAutoTargeting(enable);
    }

    public boolean isReady() {
        return launchSystem.isReady() && launchSystem.confidence > WHEELSPEED_CONFIDENCE && artifactSystem.isReady() && targetingSystem.targetAcquired();
    }

    public void fireGreen() {
        if (!autoLocked) {
            artifactSystem.launchColor(BallColor.GREEN);
            return;
        }

        if (isReady()) {
            artifactSystem.launchColor(BallColor.GREEN);
        }
    }

    public void firePurple() {
        if (!autoLocked) {
            artifactSystem.launchColor(BallColor.PURPLE);
            return;
        }

        if (isReady()) {
            artifactSystem.launchColor(BallColor.PURPLE);
        }
    }

    public void fireAll() {
        if (!autoLocked) {
            artifactSystem.launchAll();
            return;
        }

        if (isReady()) {
            artifactSystem.launchAll();
        }
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
        if (runtime.milliseconds() - lastTime > RobotVersion.INSTANCE.visionLoopIntervalMS) {
            lastTime = runtime.milliseconds();
            launchSystem.setDistance(targetingSystem.getDistance());
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        if(color != null){
        telemetry.addData("alliance color", color.name());}
    }

    public void link(LaunchSystem launchSystem, TargetingSystem targetingSystem, ArtifactSystem artifactSystem) {
        this.launchSystem = launchSystem;
        this.targetingSystem = targetingSystem;
        this.artifactSystem = artifactSystem;
    }
}
