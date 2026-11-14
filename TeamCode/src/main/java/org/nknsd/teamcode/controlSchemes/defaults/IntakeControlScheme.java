package org.nknsd.teamcode.controlSchemes.defaults;

import android.provider.ContactsContract;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class IntakeControlScheme extends NKNControlScheme {

    boolean startIntakeDelay = false;
    boolean reverseIntakeDelay = false;
    boolean reversed = false;

    @Override
    public String getName() {
        return "Default";
    }


    boolean intakeBeenStarted = false;
    public Callable<Boolean> startIntake(){
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad1());
                if(button) {
                    intakeBeenStarted = true;
                }
                return button;
            }
        };
    }

    public Callable<Boolean> reverseIntake(){
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad1());
                if(button && !reversed){
                    reversed = true;
                    return true;
                } else if(!button && reversed){
                    reversed = false;
                    return true;
                }
                return false;
            }
        };
    }

    public Callable<Boolean> stopIntake(){
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                if(intakeBeenStarted && GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad1())) {
                    intakeBeenStarted = false;
                    return true;
                } else {
                    return false;
                }
            }
        };
    }
    public Callable<Boolean> startIntakeToggle(){
        return new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.Y.detect(gamePadHandler.getGamePad1());
                if(button && !startIntakeDelay){
                    startIntakeDelay = true;
                    return true;
                } else if (!button){
                    startIntakeDelay = false;
                }
                return false;
            }
        };
    }


    public Callable<Boolean> reverseIntakeToggle(){
        return new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad1());
                if(button && !reverseIntakeDelay){
                    reverseIntakeDelay = true;
                    return true;
                } else if (!button){
                    reverseIntakeDelay = false;
                }
                return false;
            }
        };
    }
//    public Callable<Boolean> scanSlots(){
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                boolean button = GamePadHandler.GamepadButtons.BACK.detect(gamePadHandler.getGamePad1());
//                if (!scanSlotsDelay && button) {
//                    scanSlotsDelay = true;
//                    return true;
//                } else if (!button) {
//                    scanSlotsDelay = false;
//                }
//
//                return false;
//            }
//        };
//    }

}