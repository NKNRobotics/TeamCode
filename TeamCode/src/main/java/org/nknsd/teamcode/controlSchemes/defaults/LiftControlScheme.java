package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.handlers.gamepad.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class LiftControlScheme extends NKNControlScheme {

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> startLift(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad1());
            }
        };
    }

    public Callable<Boolean> stopLift(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad1());
            }
        };
    }

}
