package org.nknsd.teamcode.components.handlers;

import org.nknsd.teamcode.components.utility.RobotVersion;

public enum MicrowavePositions {


    LOAD0(0.22, 0.95),
    LOAD1(0.99, 3.04),
    LOAD2(0.61, 2.05),
    FIRE0(0.8, 2.58),
    FIRE1(0.42, 1.52),
    FIRE2(0.03, 0.41);



    public final double ROBOT_OFFSET = RobotVersion.INSTANCE.microwaveOffset;
    public final double microPosition;
    public final double powerPosition;

    MicrowavePositions(double microPositions, double powerPosition) {
        this.microPosition = microPositions + ROBOT_OFFSET;
        this.powerPosition = powerPosition;
    }
}
