package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class MicrowaveControlScheme extends NKNControlScheme {
    private boolean delayLoad;

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> loadBall() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() {
                boolean button = GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad2());

                if (!delayLoad && button) {
                    delayLoad = true;
                    return true;
                } else if (!button) {
                    delayLoad = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> firePurple() {
        return () -> GamePadHandler.GamepadButtons.DPAD_LEFT.detect(gamePadHandler.getGamePad2());
    }

    public Callable<Boolean> fireGreen() {
        return () -> GamePadHandler.GamepadButtons.DPAD_RIGHT.detect(gamePadHandler.getGamePad2());
    }
}
