package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class LaunchControlScheme extends NKNControlScheme {
    private boolean launchDelay = false;
    public Callable<Boolean> startLaunch(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.Y.detect(gamePadHandler.getGamePad2());
                if(!launchDelay) {
                    launchDelay = true;
                    return button;
                } else if (!button && launchDelay){
                    launchDelay = false;
                }
                return false;
            }
        };
    }
//    public Callable<Boolean> startConveyor(){
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return GamePadHandler.GamepadButtons.DPAD_UP.detect(gamePadHandler.getGamePad2());
//            }
//        };
//    }
//    public Callable<Boolean> reverseConveyor(){
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return GamePadHandler.GamepadButtons.DPAD_DOWN.detect(gamePadHandler.getGamePad2());
//            }
//        };
//    }
//    public Callable<Boolean> stopConveyor(){
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return GamePadHandler.GamepadButtons.DPAD_LEFT.detect(gamePadHandler.getGamePad2());
//            }
//        };
//    }

}
