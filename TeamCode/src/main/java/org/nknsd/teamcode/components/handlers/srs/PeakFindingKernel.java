package org.nknsd.teamcode.components.handlers.srs;

import android.graphics.Path;

import org.nknsd.teamcode.components.handlers.gamepad.AdvancedTelemetry;
import org.nknsd.teamcode.components.utility.DoublePoint;
import org.nknsd.teamcode.components.utility.IntPoint;

import java.util.ArrayList;

public class PeakFindingKernel {
    private AdvancedTelemetry telemetry;
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
        ArrayList<DoublePoint> peaks = new ArrayList<>();

        for (int y = 0; y < data.length; y++) {

            ded:for (int x = 0; x < data[y].length; x++) {
                int currentPointValue = Math.abs(data[x][y]);
                DoublePoint offset = new DoublePoint(0, 0);

                for (int i = 0; i < 8; i++) {
                    int adjacentPointValue = Math.abs(safelyGetValueOfArrayAtPoint(DIRECTION_SPIRAL[i].addPairToPoint(x, y), data));

                    // We know this isn't a peak if an adjacent point is greater
                    if (adjacentPointValue > currentPointValue) {
                        continue ded;
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

                addTelemetry("Peak int pos. x:" + x + ", y: " + y);
                addTelemetry("Peak double pos. x:" + peakPoint.getX() + ", y: " + peakPoint.getY());
                addTelemetry("Peak offset amount. x offset:" + offset.getX() + ", y: " + offset.getY());
                if (offset.getX() >= 1 || offset.getY() >= 1) {
                    addTelemetry("OMG THE DATA IS WILD THE OFFSET IS OVER 1 THAT'S BAD GET KARSTEN");
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

    public void linkTelemetry(AdvancedTelemetry telemetry) {
        this.telemetry = telemetry;
    }

    private void addTelemetry(String data) {
        if (telemetry != null) {
            telemetry.addSingleData(data);
        }
    }
}
