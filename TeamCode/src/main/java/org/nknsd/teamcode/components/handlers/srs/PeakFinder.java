package org.nknsd.teamcode.components.handlers.srs;

import org.nknsd.teamcode.components.utility.SensorGridPoint;

public class PeakFinder {

    private int[][] searchOrder = new int[][]{ // what peak gets preference
            {3,0}, {4,0}, {3,1}, {4,1}, {3,2}, {4,2},
            {2,0}, {5,0}, {2,1}, {5,1}, {2,2}, {5,2},
            {1,0}, {6,0}, {1,1}, {6,1}, {1,2}, {6,2},
            {0,0}, {7,0}, {0,1}, {7,1}, {0,2}, {7,2},
            {3,3}, {4,3}, {3,4}, {4,4}, {3,5}, {4,5},
            {2,3}, {5,3}, {2,4}, {5,4}, {2,5}, {5,5},
            {1,3}, {6,4}, {1,4}, {6,4}, {1,5}, {6,5},
            {0,3}, {7,3}, {0,4}, {7,4}, {0,5}, {7,5},
            {3,6}, {4,6}, {3,7}, {4,7},
            {2,6}, {5,6}, {2,7}, {5,7},
            {1,6}, {6,6}, {1,7}, {6,7},
            {0,6}, {7,6}, {0,7}, {7,7},
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

    public SensorGridPoint findPeak(short[][] normalizedDists) {
        for (int[] point : searchOrder) {
            if (normalizedDists[point[0]][point[1]] > 0 && normalizedDists[point[0]][point[1]] < 50) {
                return new SensorGridPoint(3.5 - point[0], 7 - point[1]);
            }
        }
        return null;
    }


}

