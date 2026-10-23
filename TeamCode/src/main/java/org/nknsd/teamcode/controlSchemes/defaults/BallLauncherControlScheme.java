package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class BallLauncherControlScheme extends NKNControlScheme {

    public String getName() { return "Default"; }

    boolean raiseLiftDelay = false;
    boolean lowerLiftDelay = false;

    public Callable<Boolean> raiseLift(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad2());
                if(!raiseLiftDelay && button){
                    raiseLiftDelay = true;
                    return true;
                } else if (!button){
                    raiseLiftDelay = false;
                }

                return false;
            }
        };

    }
    public Callable<Boolean> lowerLift(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad2());
                if(!lowerLiftDelay && button){
                    lowerLiftDelay = true;
                    return true;
                } else if (!button){
                    lowerLiftDelay = false;
                }

                return false;
            }
        };

    }
}
