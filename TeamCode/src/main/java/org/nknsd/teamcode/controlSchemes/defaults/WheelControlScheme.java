package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class WheelControlScheme extends NKNControlScheme {
    private boolean delaySpeedChangesUp = false, delaySpeedChangesDown = false, delaySpecFancyDeposit = false;

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> gearUp() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.RIGHT_BUMPER.detect(gamePadHandler.getGamePad1());

                if (!delaySpeedChangesUp && button) {
                    delaySpeedChangesUp = true;
                    return true;
                } else if (!button) {
                    delaySpeedChangesUp = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> gearDown() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.LEFT_BUMPER.detect(gamePadHandler.getGamePad1());

                if (!delaySpeedChangesDown && button) {
                    delaySpeedChangesDown = true;
                    return true;
                } else if (!button) {
                    delaySpeedChangesDown = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> specFancyDeposit() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.X.detect(gamePadHandler.getGamePad1());

                if (!delaySpecFancyDeposit && button) {
                    delaySpecFancyDeposit = true;
                    return true;
                } else if (!button) {
                    delaySpecFancyDeposit = false;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> resetAngle() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.BACK.detect(gamePadHandler.getGamePad1());
            }
        };
    }

    public Callable<Boolean> initDisableAutoFix() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return GamePadHandler.GamepadButtons.Y.detect(gamePadHandler.getGamePad1());
            }
        };
    }
}