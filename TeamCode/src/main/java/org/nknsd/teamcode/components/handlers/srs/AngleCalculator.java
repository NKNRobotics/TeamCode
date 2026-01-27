package org.nknsd.teamcode.components.handlers.srs;

public class AngleCalculator {
    // Represents the distance between the sensor and the center of the robot in cm. I think it's cm. Whatever the sensor measures distance in.
    private static final double sensorOffset = 0;

    public static double calculateHeadingOffset(int ballXPos, int ballDist) {
        double angleFromPlane = 90 + (45.0 / 7) * ballXPos - 22.5;
        double distFromOriginToBall = (sensorOffset * sensorOffset) + (ballDist * ballDist)
                - (2 * sensorOffset * ballDist * Math.cos(angleFromPlane));
        double headingOffset = 90 - Math.asin(
                Math.sin(angleFromPlane) * (sensorOffset / distFromOriginToBall)
        );

        return headingOffset;
    }
}
