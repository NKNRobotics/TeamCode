package org.nknsd.teamcode.controlSchemes.defaults;

import android.telecom.Call;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class FiringControlScheme extends NKNControlScheme {
    private boolean fireGreenDelay = false, firePurpleDelay = false, fireAllDelay = false;

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> lockTarget(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.RIGHT_TRIGGER.detect(gamePadHandler.getGamePad2());
            }
        };
    }

    public Callable<Boolean> unlockTarget(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !GamePadHandler.GamepadButtons.RIGHT_TRIGGER.detect(gamePadHandler.getGamePad2());
            }
        };
    }

    public Callable<Boolean> fireGreen(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.LEFT_BUMPER.detect(gamePadHandler.getGamePad2());
                if (!fireGreenDelay && button) {
                    fireGreenDelay = true;
                    return true;
                } else if (!button) {
                    fireGreenDelay = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> firePurple(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.RIGHT_BUMPER.detect(gamePadHandler.getGamePad2());
                if (!firePurpleDelay && button) {
                    firePurpleDelay = true;
                    return true;
                } else if (!button) {
                    firePurpleDelay = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> fireAll(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.LEFT_TRIGGER.detect(gamePadHandler.getGamePad2());
                if (!fireAllDelay && button) {
                    fireAllDelay = true;
                    return true;
                } else if (!button) {
                    fireAllDelay = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> setBlue(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad2());
            }
        };
    }

    public Callable<Boolean> setRed(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad2());
            }
        };
    }

//    public Callable<Boolean> gearUp() {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                boolean button = GamePadHandler.GamepadButtons.RIGHT_BUMPER.detect(gamePadHandler.getGamePad1());
//
//                if (!delaySpeedChangesUp && button) {
//                    delaySpeedChangesUp = true;
//                    return true;
//                } else if (!button) {
//                    delaySpeedChangesUp = false;
//                }
//
//                return false;
//            }
//        };
//    }
//
//    public Callable<Boolean> gearDown() {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                boolean button = GamePadHandler.GamepadButtons.LEFT_BUMPER.detect(gamePadHandler.getGamePad1());
//
//                if (!delaySpeedChangesDown && button) {
//                    delaySpeedChangesDown = true;
//                    return true;
//                } else if (!button) {
//                    delaySpeedChangesDown = false;
//                }
//
//                return false;
//            }
//        };
//    }
//
//    public Callable<Boolean> specFancyDeposit() {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                boolean button = GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad1());
//
//                if (!delaySpecFancyDeposit && button) {
//                    delaySpecFancyDeposit = true;
//                    return true;
//                } else if (!button) {
//                    delaySpecFancyDeposit = false;
//                }
//
//                return false;
//            }
//        };
//    }
//
//    public Callable<Boolean> resetAngle() {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return GamePadHandler.GamepadButtons.BACK.detect(gamePadHandler.getGamePad1());
//            }
//        };
//    }
//
//    public Callable<Boolean> initDisableAutoFix() {
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return GamePadHandler.GamepadButtons.Y.detect(gamePadHandler.getGamePad1());
//            }
//        };
//    }
}