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

    private boolean moveToFirePositions = false;
    private boolean delaySwappingPositions = false;
    public Callable<Boolean> swapState() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                boolean button = GamePadHandler.GamepadButtons.BACK.detect(gamePadHandler.getGamePad2());
                if(!delaySwappingPositions){
                    delaySwappingPositions = true;
                    if(button && moveToFirePositions){
                        moveToFirePositions = false;
                    } else {
                        moveToFirePositions = true;
                    }
                } else if (!button) {
                    delaySwappingPositions = false;
                }
                return null;
            }
        };
    }

    public Callable<Boolean> fire1() {
        if(moveToFirePositions) {
            return () -> GamePadHandler.GamepadButtons.DPAD_LEFT.detect(gamePadHandler.getGamePad2());
        } else return () -> false;
    }

    public Callable<Boolean> fire2() {
        if(moveToFirePositions) {
            return () -> GamePadHandler.GamepadButtons.DPAD_DOWN.detect(gamePadHandler.getGamePad2());
        } else return  () -> false;
    }

    public Callable<Boolean> fire3() {
        if(moveToFirePositions) {
            return () -> GamePadHandler.GamepadButtons.DPAD_RIGHT.detect(gamePadHandler.getGamePad2());
        } else return () -> false;
    }

    public Callable<Boolean> load1() {
        if(!moveToFirePositions) {
            return () -> GamePadHandler.GamepadButtons.DPAD_LEFT.detect(gamePadHandler.getGamePad2());
        } else return () -> false;
    }

    public Callable<Boolean> load2() {
        if(!moveToFirePositions) {
            return () -> GamePadHandler.GamepadButtons.DPAD_DOWN.detect(gamePadHandler.getGamePad2());
        } else return () -> false;
    }

    public Callable<Boolean> load3() {
        if(!moveToFirePositions) {
            return () -> GamePadHandler.GamepadButtons.DPAD_RIGHT.detect(gamePadHandler.getGamePad2());
        } else return () -> false;
    }
    private boolean intakeIsSpinning = false;
    private boolean delayIntakeButtonCheck = false;
    public Callable<Boolean> startIntake() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if(intakeIsSpinning){
                    return false;
                }
                boolean button = GamePadHandler.GamepadButtons.RIGHT_BUMPER.detect(gamePadHandler.getGamePad2());

                if (button && !delayIntakeButtonCheck) {
                    delayIntakeButtonCheck = true;
                    intakeIsSpinning = true;
                    return true;
                }
                else if (!button) {
                    delayIntakeButtonCheck = false;
                }

                return false;
            }
        };
    }
    public Callable<Boolean> stopIntake() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if(!intakeIsSpinning){
                    return false;
                }
                boolean button = GamePadHandler.GamepadButtons.RIGHT_BUMPER.detect(gamePadHandler.getGamePad2());

                if (button && !delayIntakeButtonCheck) {
                    delayIntakeButtonCheck = true;
                    intakeIsSpinning = false;
                    return true;
                }
                else if (!button) {
                    delayIntakeButtonCheck = false;
                }

                return false;
            }
        };
    }
}
