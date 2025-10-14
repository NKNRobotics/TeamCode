package org.nknsd.teamcode.controlSchemes.defaults;

import org.nknsd.teamcode.frameworks.NKNControlScheme;

import java.util.concurrent.Callable;

public class Generic2PControlScheme extends NKNControlScheme {
    @Override
    public String getName() {
        return "Default";
    }

    public Callable<Boolean> switchColor() {
        return () -> false;
    }

    public Callable<Boolean> switchColorInit() {
        return switchColor();
    }
}

