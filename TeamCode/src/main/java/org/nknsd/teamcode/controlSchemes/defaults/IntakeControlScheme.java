package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class IntakeControlScheme extends NKNControlScheme {
    private boolean intakeDelay = false, scanSlotsDelay = false;

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> startIntake(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad2());
                if (!intakeDelay && button) {
                    intakeDelay = true;
                    return true;
                } else if (!button) {
                    intakeDelay = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> stopIntake(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad2());

            }
        };
    }

    public Callable<Boolean> scanSlots(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.BACK.detect(gamePadHandler.getGamePad2());
                if (!scanSlotsDelay && button) {
                    scanSlotsDelay = true;
                    return true;
                } else if (!button) {
                    scanSlotsDelay = false;
                }

                return false;
            }
        };
    }

}