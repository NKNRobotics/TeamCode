package org.nknsd.teamcode.controlSchemes.reals;

import org.nknsd.teamcode.controlSchemes.abstracts.WheelControlScheme;

import java.util.concurrent.Callable;

public class DefaultWheelController extends WheelControlScheme {
    @Override
    public String getName() {
        return "Colly";
    }

    @Override
    public Callable<Boolean> gearUp() {
        return null;
    }

    @Override
    public Callable<Boolean> gearDown() {
        return null;
    }

    @Override
    public Callable<Boolean> resetAngle() {
        return null;
    }
}
