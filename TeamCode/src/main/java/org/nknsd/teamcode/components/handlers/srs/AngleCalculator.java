package org.nknsd.teamcode.components.handlers.srs;

public class AngleCalculator {
    // Represents the distance between the sensor and the center of the robot in cm. I think it's cm. Whatever the sensor measures distance in.
    private static final double sensorOffset = 0;
    private static final double rightAngle = 3.14159 / 2;
    private static final double angleBetweenDataPoints = (0.785398 / 7);
    private static final double halfOfFov = 0.3926991;

    public static double calculateHeadingOffset(int ballXPos, int ballDist) {
        double angleFromPlane = rightAngle + angleBetweenDataPoints * ballXPos - halfOfFov;
        double distFromOriginToBall = (sensorOffset * sensorOffset) + (ballDist * ballDist)
                - (2 * sensorOffset * ballDist * Math.cos(angleFromPlane));
        double headingOffset = rightAngle - Math.asin(
                Math.sin(angleFromPlane) * (ballDist / distFromOriginToBall)
        );

        return headingOffset;
    }
}
