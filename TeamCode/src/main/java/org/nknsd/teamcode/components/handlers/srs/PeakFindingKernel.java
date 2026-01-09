package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.DoublePoint;
import org.nknsd.teamcode.components.utility.IntPoint;

import java.util.ArrayList;

public class PeakFindingKernel {
    private Telemetry telemetry;
    private static final int PEAK_THRESHOLD = 50;
    private static final IntPoint[] DIRECTION_SPIRAL = new IntPoint[]{
            new IntPoint(-1, 0),
            new IntPoint(-1, -1),
            new IntPoint(0, -1),
            new IntPoint(1, -1),
            new IntPoint(1, 0),
            new IntPoint(1, 1),
            new IntPoint(0, 1),
            new IntPoint(-1, 1)
    };

    public DoublePoint[] findPeaks(short[][] data) {
        return findPeaks(data, false);
    }

    public DoublePoint[] findPeaks(short[][] data, boolean enableTelemetry) {
        ArrayList<DoublePoint> peaks = new ArrayList<>();

        for (int x = 0; x < data.length; x++) {

            // This loops over the points in a row. The inner logic handles determining whether the point is a peak or not.
            loopOverPoints:for (int y = 0; y < data[x].length; y++) {
                int currentPointValue = Math.abs(data[x][y]);

                DoublePoint offset = new DoublePoint(0, 0);

                for (int i = 0; i < 8; i++) {
                    int adjacentPointValue = Math.abs(safelyGetValueOfArrayAtPoint(DIRECTION_SPIRAL[i].addPairToPoint(x, y), data));

                    if (adjacentPointValue == 0) {
                        telemetry.addData("Zero at", DIRECTION_SPIRAL[i].addPairToPoint(x, y).toString());
                    }

                    // if this value is low, discard it
                    if (currentPointValue <= PEAK_THRESHOLD){
                        continue loopOverPoints;
                    }
                    // We know this isn't a peak if an adjacent point is greater or equal
                    if (adjacentPointValue >= currentPointValue) {
                        // If it's not equal, for sure that this point is not a peak
                        if (adjacentPointValue > currentPointValue) {
                            continue loopOverPoints;
                        }

                        // If we have two equal points, we need adjacency rules to pick which one to use
                        if (i > 3) {
                            continue loopOverPoints;
                        }
                    }


                    // if the adjacent position is in a diagonal direction the weighting of the change to the offset must reflect that
                    if (Math.abs(DIRECTION_SPIRAL[i].getX()) + Math.abs(DIRECTION_SPIRAL[i].getY()) == 2) {
                        offset = offset.addPointToPoint(DIRECTION_SPIRAL[i].castToDoublePoint().multiplyByScalar(adjacentPointValue * 1.41421));
                    } else {
                        offset = offset.addPointToPoint(DIRECTION_SPIRAL[i].castToDoublePoint().multiplyByScalar(adjacentPointValue));
                    }
                }

                // if we reach here, we know we have a peak
                // step 1: normalize the summed offsets
                offset = offset.multiplyByScalar(1.0 / currentPointValue);

                // step 2: convert the offset to a POSITION
                DoublePoint peakPoint = offset.addPairToPoint(x - 3.5, y - 3.5);
                peaks.add(peakPoint);

                if (enableTelemetry) {
    //                addTelemetry("Peak strength: " + currentPointValue);
                    addTelemetry("Peak int pos. x:" + x + ", y: " + y);
    //                addTelemetry("Peak double pos. x:" + peakPoint.getX() + ", y: " + peakPoint.getY());
    //                addTelemetry("Peak offset amount. x offset:" + offset.getX() + ", y: " + offset.getY());
    //                if (offset.getX() >= 1 || offset.getY() >= 1) {
    //                    addTelemetry("OMG THE DATA IS WILD THE OFFSET IS OVER 1 THAT'S BAD GET KARSTEN");
    //                }
                }
            }
        }

        return peaks.toArray(new DoublePoint[0]);
    }

    private int safelyGetValueOfArrayAtPoint(IntPoint point, short[][] data) {
        int y = point.getY(), x = point.getX();

        if (y < 0 || y >= data.length) {
            return 0;
        }
        if (x < 0 || x >= data[point.getY()].length) {
            return 0;
        }

        return data[y][x];
    }

    public void linkTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    private void addTelemetry(String data) {
        if (telemetry != null) {
            telemetry.addData("", data);
        } else {
            RobotLog.v(data);
        }
    }
}
