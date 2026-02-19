package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.RobotLog;

import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.SensorGridPoint;

import java.util.LinkedList;
import java.util.List;

public class PeakFinder {

    private int[][] searchOrder = new int[][]{ // what peak gets preference
            {3, 0}, {4, 0}, {3, 1}, {4, 1}, {3, 2}, {4, 2},
            {2, 0}, {5, 0}, {2, 1}, {5, 1}, {2, 2}, {5, 2},
            {1, 0}, {6, 0}, {1, 1}, {6, 1}, {1, 2}, {6, 2},
            {0, 0}, {7, 0}, {0, 1}, {7, 1}, {0, 2}, {7, 2},
            {3, 3}, {4, 3}, {3, 4}, {4, 4}, {3, 5}, {4, 5},
            {2, 3}, {5, 3}, {2, 4}, {5, 4}, {2, 5}, {5, 5},
            {1, 3}, {6, 4}, {1, 4}, {6, 4}, {1, 5}, {6, 5},
            {0, 3}, {7, 3}, {0, 4}, {7, 4}, {0, 5}, {7, 5},
            {3, 6}, {4, 6}, {3, 7}, {4, 7},
            {2, 6}, {5, 6}, {2, 7}, {5, 7},
            {1, 6}, {6, 6}, {1, 7}, {6, 7},
            {0, 6}, {7, 6}, {0, 7}, {7, 7},
    };

    private static int[][] GENERATE_SEARCH_ORDER() {
        int[][] order = new int[64][2];
        for (int i = 0; i < 64; i++) {
            int x, y;
            if (i % 2 == 0) {
                x = 3 - (i % 8) / 2;
            } else {
                x = 3 + ((i % 8) + 1) / 2;
            }
            y = 7 - (i / 8);
            order[i][0] = x;
            order[i][1] = y;
//            RobotLog.v("SO[" + i + "] : " + x + " , " + y);
        }
        return order;
    }

    private List<int[]> findPointsList(short[][] array, int[] firstPoint) {
        List<int[]> pointsList = new LinkedList<>();
        pointsList.add(firstPoint);

        for (int i = 1; i < 3; i++) {
            if (pointsList.size() >= 3) {
                return pointsList;
            }
            int[] point = new int[]{firstPoint[0] + i, firstPoint[1]};
            double pointValue = getSafeValue(array, point);

            if (pointValue > 0) {
                pointsList.add(point);
            } else {
                break;
            }

        }
        for (int i = 1; i < 3; i++) {
            if (pointsList.size() >= 3) {
                return pointsList;
            }
            int[] point = new int[]{firstPoint[0] - i, firstPoint[1]};
            double pointValue = getSafeValue(array, point);

            if (pointValue > 0) {
                pointsList.add(point);
            } else {
                break;
            }
        }

        return pointsList;
    }

    private double getSafeValue(short[][] array, int[] point) {
        if (point[0] > 7 || point[0] < 0 || point[1] > 7 || point[1] < 0) {
            return 0;
        } else {
            return array[point[0]][point[1]];
        }
    }

    private SensorGridPoint averagePoints(List<int[]> pointsList, short[][] array) {
        double xSum = 0;
        double ySum = 0;
        double totalWeight = 0;

        for (int i = 0; i < pointsList.size(); i++) {
            int x = pointsList.get(i)[0];
            int y = pointsList.get(i)[1];
            double weight = array[x][y];
            xSum += x * weight;
            ySum += y * weight;
            totalWeight += weight;
        }
        xSum /= totalWeight;
        ySum /= totalWeight;
        return new SensorGridPoint(xSum, ySum);
    }

    public SensorGridPoint findPeak(short[][] normalizedDists) {
        int[] firstPoint = null;
        for (int[] point : searchOrder) {
            if (normalizedDists[point[0]][point[1]] > 0 && normalizedDists[point[0]][point[1]] < 50) {
                firstPoint = point;
//                return new SensorGridPoint(3.5 - point[0], 7 - point[1]);
            }
        }
        if (firstPoint == null) {
            return null;
        }
        List<int[]> pointList = findPointsList(normalizedDists, firstPoint);
        RobotLog.v("point list size = " + pointList.size());
        for (int[] point : pointList) {
            RobotLog.v("\t x: " + point[0] + ", y: " + point[1] + ", z: " + normalizedDists[point[0]][point[1]]);
        }
        SensorGridPoint avPoint = averagePoints(pointList, normalizedDists);
        RobotLog.v("av point: " + avPoint);
        return new SensorGridPoint(3.5 - avPoint.getX(), 7 - avPoint.getY());
    }
}

