package org.nknsd.teamcode.drivers;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.components.handlers.SpecimenClawHandler;
import org.nknsd.teamcode.components.handlers.SpecimenExtensionHandler;
import org.nknsd.teamcode.components.handlers.SpecimenRotationHandler;
import org.nknsd.teamcode.components.utility.GamePadHandler;

public class SpecimenDriver implements NKNComponent {
    GamePadHandler gamePadHandler;
    SpecimenExtensionHandler specimenExtensionHandler;
    SpecimenRotationHandler specimenRotationHandler;
    SpecimenClawHandler specimenClawHandler;

    GamePadHandler.GamepadButtons rotateForwardButton = GamePadHandler.GamepadButtons.DPAD_UP;
    GamePadHandler.GamepadButtons rotateBackwardButton = GamePadHandler.GamepadButtons.DPAD_DOWN;
    GamePadHandler.GamepadButtons extendButton = GamePadHandler.GamepadButtons.RIGHT_BUMPER;
    GamePadHandler.GamepadButtons retractButton = GamePadHandler.GamepadButtons.LEFT_BUMPER;
    GamePadHandler.GamepadButtons grabButton = GamePadHandler.GamepadButtons.A;
    GamePadHandler.GamepadButtons releaseButton = GamePadHandler.GamepadButtons.B;

    Runnable specimenExtend = new Runnable() {
        @Override
        public void run() {
            if (specimenRotationHandler.targetPosition() != SpecimenRotationHandler.SpecimenRotationPositions.FORWARD) {
                boolean done = false; // Repeat until we either hit the end of the array or we reach a valid extension position
                int index = specimenExtensionHandler.targetPosition().ordinal();
                while (!done) {
                    index++;

                    if (index >= SpecimenExtensionHandler.SpecimenExtensionPositions.values().length) {
                        return;
                    }

                    done = specimenExtensionHandler.gotoPosition(SpecimenExtensionHandler.SpecimenExtensionPositions.values()[index]);
                }
            }
        }
    };
    Runnable specimenRetract = new Runnable() {
        @Override
        public void run() {
            boolean done = false; // Repeat until we either hit the end of the array or we reach a valid rotation position
            int index = specimenExtensionHandler.targetPosition().ordinal();
            while (!done) {
                index --;

                if (index < 0) {return;}

                done = specimenExtensionHandler.gotoPosition(SpecimenExtensionHandler.SpecimenExtensionPositions.values()[index]);
            }
        }
    };
    Runnable specimenForward = new Runnable() {
        @Override
        public void run() {
            boolean done = false; // Repeat until we either hit the end of the array or we reach a valid extension position
            int index = specimenRotationHandler.targetPosition().ordinal();
            while (!done) {
                index++;

                if (index >= SpecimenRotationHandler.SpecimenRotationPositions.values().length) {
                    return;
                }

                done = specimenRotationHandler.goToPosition(SpecimenRotationHandler.SpecimenRotationPositions.values()[index]);
            }
        }
    };
    Runnable specimenBackward = new Runnable() {
        @Override
        public void run() {
            boolean done = false; // Repeat until we either hit the end of the array or we reach a valid extension position
            int index = specimenRotationHandler.targetPosition().ordinal();
            while (!done) {
                index --;

                if (index < 0) {return;}

                done = specimenRotationHandler.goToPosition(SpecimenRotationHandler.SpecimenRotationPositions.values()[index]);
            }
        }
    };
    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {
        gamePadHandler.addListener(extendButton, 2, "specimenExtend", true, specimenExtend);
        gamePadHandler.addListener(retractButton, 2, "specimenRetract", true, specimenRetract);
        gamePadHandler.addListener(rotateForwardButton,2,"specimenForward",true,specimenForward);
        gamePadHandler.addListener(rotateBackwardButton,2,"specimenBackward",true,specimenBackward);
    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "SpecimenDriver";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        if (grabButton.detect(gamePadHandler.getGamePad2()) && (specimenRotationHandler.targetPosition() == SpecimenRotationHandler.SpecimenRotationPositions.FORWARD)) {
            specimenClawHandler.setClawPosition(SpecimenClawHandler.ClawPositions.GRIP);
        } else if (releaseButton.detect(gamePadHandler.getGamePad2())) {
            specimenClawHandler.setClawPosition(SpecimenClawHandler.ClawPositions.RELEASE);
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("SpecimenExtTarget", specimenExtensionHandler.targetPosition());
        telemetry.addData("SpecimenRotTarget", specimenRotationHandler.targetPosition());
    }
    public void link (SpecimenExtensionHandler specimenExtensionHandler, SpecimenRotationHandler specimenRotationHandler, SpecimenClawHandler specimenClawHandler, GamePadHandler gamepadHandler){
        this.specimenExtensionHandler = specimenExtensionHandler;
        this.specimenRotationHandler = specimenRotationHandler;
        this.specimenClawHandler = specimenClawHandler;
        this.gamePadHandler = gamepadHandler;
    }
}