package org.nknsd.teamcode.components.handlers.srs;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.IntPoint;

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

    public IntPoint GetPeak(short[][] data){
        bestVal = PEAK_THRESHOLD;
        bestPos = new IntPoint(-10,-10);
        for(int i = 0; i < 8; i++){
            if (data[i][7] > bestVal){
                bestVal = data[i][7];
                bestPos = new IntPoint(i, 7);
            }
        }
        return CalculatePeak(data, bestPos);
    }
    private IntPoint CalculatePeak(short[][] data, IntPoint currentPeak){
        for(IntPoint currentPoint : CENTRAL_SPIRAL){
            IntPoint checkPoint = currentPoint.addPointToPoint(currentPoint);
            if(data[currentPoint.getX()][currentPoint.getY()] > bestVal){
                bestPos = currentPoint;
                bestVal = data[currentPoint.getX()][currentPoint.getY()];
                CalculatePeak(data, bestPos);
                break;
            }
        }
        for(IntPoint currentPoint : OUTER_SPIRAL){
            currentPoint.addPointToPoint(bestPos);
            if(data[currentPoint.getX()][currentPoint.getY()] > bestVal){
                bestPos = currentPoint;
                bestVal = data[currentPoint.getX()][currentPoint.getY()];
                CalculatePeak(data, bestPos);
            }
        }
        return bestPos;
    }
}
