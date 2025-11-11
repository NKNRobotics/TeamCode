package org.nknsd.teamcode.components.handlers;

public enum MicrowaveState {
    LOAD0(0.22),
    LOAD1(0.61),
    LOAD2(0.99),
    FIRE0(0.8),
    FIRE1(0.03),
    FIRE2(0.42);
    public final double microPosition;

    MicrowaveState(double microPosition) {
        this.microPosition = microPosition;
    }
}
