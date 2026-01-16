package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.IntPoint;

import java.util.Arrays;

public class PeakFinder {
    private Telemetry telemetry;
    private static final int PEAK_THRESHOLD = 50;
    private IntPoint bestPos;
    private int bestVal;
    private static final IntPoint[] CENTRAL_SPIRAL = new IntPoint[]{
            new IntPoint(-1, 0),
            new IntPoint(-1, -1),
            new IntPoint(0, -1),
            new IntPoint(1, -1),
            new IntPoint(1, 0),
            new IntPoint(1, 1),
            new IntPoint(0, 1),
            new IntPoint(-1, 1)
    };
    //* D i l l o n* Wanted to be in the code
    private static final IntPoint[] OUTER_SPIRAL = new IntPoint[]{
            new IntPoint(-2, 0),
            new IntPoint(-2, 1),
            new IntPoint(-2, 2),
            new IntPoint(-1, 2),
            new IntPoint(0, 2),
            new IntPoint(1, 2),
            new IntPoint(2, 2),
            new IntPoint(2, 1),
            new IntPoint(2, 0),
            new IntPoint(2, -1),
            new IntPoint(2, -2),
            new IntPoint(1, -2),
            new IntPoint(0, -2),
            new IntPoint(-1, -2),
            new IntPoint(-2, -2),
            new IntPoint(-2, -1)
    };

    public IntPoint getPeak(short[][] data){
        bestVal = -100;
        bestPos = new IntPoint(-10,-10);
        for(int i = 0; i < 8; i++){
            if (data[i][4] > bestVal){
                bestVal = data[i][4];
                bestPos = new IntPoint(i, 4);
            }
        }
        if(bestVal >= PEAK_THRESHOLD) {
            RobotLog.v(Arrays.toString(data), bestPos);
            return calculatePeak(data, bestPos);
        }
        return new IntPoint(-10,-10);
    }
    private IntPoint calculatePeak(short[][] data, IntPoint currentPeak){
        for(IntPoint offset : CENTRAL_SPIRAL){
            IntPoint checkPoint = offset.addPointToPoint(currentPeak);
            if(safelyGetValueOfArrayAtPoint(checkPoint, data) > bestVal){
                bestPos = checkPoint;
                bestVal = safelyGetValueOfArrayAtPoint(checkPoint, data);
                calculatePeak(data, bestPos);
                break;
            }
        }
        for(IntPoint offset : OUTER_SPIRAL){
            IntPoint checkPoint = offset.addPointToPoint(currentPeak);
            if(safelyGetValueOfArrayAtPoint(checkPoint, data) > bestVal){
                bestPos = checkPoint;
                bestVal = safelyGetValueOfArrayAtPoint(checkPoint, data);
                calculatePeak(data, bestPos);
            }
        }
        return bestPos;
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








    public IntPoint altPeakFind(short[][] data) {
        int highestVal = PEAK_THRESHOLD;
        bestPos = new IntPoint(-10, -10);

        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (highestVal < data[x][y]) {
                    highestVal = data[x][y];
                    bestPos = new IntPoint(x, y);
                }
            }
            if (highestVal != PEAK_THRESHOLD && y > bestPos.getY()) {
                // we went a row without finding a new record, so we can stop searching
                break;
            }
        }

        return bestPos;
    }
}
