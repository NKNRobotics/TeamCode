package org.nknsd.teamcode.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.controlSchemes.abstracts.WheelControlScheme;
import org.nknsd.teamcode.components.handlers.RackNPinion;


public class TestDriver implements NKNComponent {

private double moveSpeedMultiplier;
private double power;
private final double speedStepAmount;
private final GamePadHandler.GamepadSticks forwardStick;
private GamePadHandler gamePadHandler;
private WheelControlScheme controlScheme;
private RackNPinion rackNPinion;
    public TestDriver(double speedStepAmount, GamePadHandler.GamepadSticks forwardStick) {
        this.forwardStick = forwardStick;
        this.speedStepAmount = speedStepAmount;
    }
    Runnable speedUp = new Runnable() {
        @Override
        public void run() {
                moveSpeedMultiplier = moveSpeedMultiplier + speedStepAmount;
                if(moveSpeedMultiplier > 1){
                    moveSpeedMultiplier = 1;
                }
        }
    };

    Runnable speedDown = new Runnable() {
        @Override
        public void run() {
                moveSpeedMultiplier = moveSpeedMultiplier - speedStepAmount;
            if(moveSpeedMultiplier < 0){
                moveSpeedMultiplier = 0;
            }
        }
    };


private Gamepad gamepad;
    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        moveSpeedMultiplier = 0;
        this.gamepad = gamepad1;

        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {
        gamePadHandler.addListener(controlScheme.gearDown(), speedDown, "Speed Down");
        gamePadHandler.addListener(controlScheme.gearUp(), speedUp, "Speed Up");
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "TestDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        power = moveSpeedMultiplier * forwardStick.getValue(gamepad);
        rackNPinion.setExtensionPower(power);
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("Speed", moveSpeedMultiplier);
    }

    public void link(GamePadHandler gamePadHandler, RackNPinion rackNPinion) {
        this.gamePadHandler = gamePadHandler;
        this.rackNPinion = rackNPinion;
    }
}
