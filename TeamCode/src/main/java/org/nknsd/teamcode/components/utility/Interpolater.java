package org.nknsd.teamcode.components.utility;

import com.qualcomm.robotcore.util.RobotLog;

public class Interpolater {

    final public double[][] xyPoints;

    public Interpolater(double[][] xyPoints) {
        this.xyPoints = xyPoints;
    }

    public double getValue(double x) {
        int i = 1;
        boolean increasing = xyPoints[1][0] > xyPoints[0][0];
        for (; i < xyPoints.length - 1; i++) {
            if (increasing ? xyPoints[i][0] >= x : xyPoints[i][0] <= x) {
                break;
            }
        }

        double x1 = xyPoints[i][0];
        double y1 = xyPoints[i][1];
        double x2 = xyPoints[i - 1][0];
        double y2 = xyPoints[i - 1][1];

        double slope = (y2 - y1)/(x2 - x1);
//        RobotLog.v("x: " + x + " x1, x2: " + x1 + ", " + x2 + " y1, y2: " + y1 + ", " + y2 + " slope: " + slope + " y: " + ((x - x1) * slope + y1));
        return (x - x1) * slope + y1;
    }

}
