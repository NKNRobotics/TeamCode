package org.nknsd.teamcode.controlSchemes.defaults;

import android.provider.ContactsContract;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class IntakeControlScheme extends NKNControlScheme {

    boolean startIntakeDelay = false;
    boolean reverseIntakeDelay = false;

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> startIntake(){
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad2());
            }
        };
    }

    public Callable<Boolean> reverseIntake(){
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad2());
            }
        };
    }

    public Callable<Boolean> stopIntake(){
        return new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return !(GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad2()) || GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad2()));
            }
        };
    }
    public Callable<Boolean> startIntakeToggle(){
        return new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.Y.detect(gamePadHandler.getGamePad2());
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
                boolean button = GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad2());
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
//                boolean button = GamePadHandler.GamepadButtons.BACK.detect(gamePadHandler.getGamePad2());
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