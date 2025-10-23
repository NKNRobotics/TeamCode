package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class LauncherControlScheme extends NKNControlScheme {
    private DelaySpinStates delaySpinChange = DelaySpinStates.NO_DELAY;

    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> spinUp() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.Y.detect(gamePadHandler.getGamePad2());

                if (button && delaySpinChange == DelaySpinStates.NO_DELAY) {
                    delaySpinChange = DelaySpinStates.UP_DELAY;
                    return true;
                } else if (!button && delaySpinChange == DelaySpinStates.UP_DELAY) {
                    delaySpinChange = DelaySpinStates.NO_DELAY;
                }

                return false;
            }
        };
    }

    public Callable<Boolean> spinDown() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.B.detect(gamePadHandler.getGamePad2());

                if (button && delaySpinChange == DelaySpinStates.NO_DELAY) {
                    delaySpinChange = DelaySpinStates.DOWN_DELAY;
                    return true;
                } else if (!button && delaySpinChange == DelaySpinStates.DOWN_DELAY) {
                    delaySpinChange = DelaySpinStates.NO_DELAY;
                }

                return false;
            }
        };
    }

    private boolean delayLaunchButtonChecks = false;
    private boolean lastLaunchStateWasLaunch = false;

    public Callable<Boolean> launchBall() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (lastLaunchStateWasLaunch) {
                    return false; // early quit this check if the last state was launching, since we want to transfer control to its twin
                }

                boolean button = GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad2());

                if (button && !delayLaunchButtonChecks) {
                    delayLaunchButtonChecks = true;
                    lastLaunchStateWasLaunch = true;
                    return true;

                } else if (!button) {
                    delayLaunchButtonChecks = false;

                }

                return false;
            }
        };
    }

    public Callable<Boolean> resetScoop() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (!lastLaunchStateWasLaunch) {
                    return false; // early quit this check if the last state was resetting, since we want to transfer control to its twin
                }

                boolean button = GamePadHandler.GamepadButtons.A.detect(gamePadHandler.getGamePad2());

                if (button && !delayLaunchButtonChecks) {
                    delayLaunchButtonChecks = true;
                    lastLaunchStateWasLaunch = false;
                    return true;
                } else if (!button) {
                    delayLaunchButtonChecks = false;
                }

                return false;
            }
        };
    }

    private enum DelaySpinStates {
        NO_DELAY,
        UP_DELAY,
        DOWN_DELAY
    }
}
