package org.nknsd.teamcode.components.utility;

import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class RobotVersion {


//    comment out the unwanted version, top is new robot and bottom is old robot

    public final static RobotVersion INSTANCE = new RobotVersion(50, .038, false, 60, .5, 1,
//            new Interpolater(new double[][]{{16, 1290}, {24, 1330}, {36, 1370}, {48, 1450}, {60, 1510}, {72, 1600}, {84, 1650}, {96, 1660}, {108, 1760}, {130, 1820}}),
            new Interpolater(new double[][]{{16, 1600}, {24, 1700}, {36, 1800}, {48, 1900}, {60, 2000}, {72, 2150}, {84, 2250}, {96, 2350}, {108, 2350}, {130, 2350}}),
            new Interpolater(new double[][]{{16, 1}, {24, 0.9}, {36, 0.7}, {48, 0.45}, {60, 0.35}, {72, 0.3}, {84, 0.2}, {96, 0}, {108, 0}, {130, 0}}),
            new Interpolater(new double[][]{{0.246, 16}, {0.2, 24}, {0.16, 36}, {0.132, 48}, {0.11, 60}, {0.096, 72}, {0.085, 84}, {0.077, 96}, {0.068, 108}, {0.064, 120}, {0.058, 132}, {0.054, 144}, {0.05, 156}}),
            new PidController(1.2, 0.62, 1.2, 0.5, true, 0, 0),
            new PidController(0.3, .3, 0.17, .12, true, 0.02, 0.2),
            new PidController(0.3, .3, 0.17, .12, true, 0.02, 0.2),
            new PidController(0.68, .53, 0.17, .27, true, 0.2, 0.3));

//    public final static RobotVersion INSTANCE= new RobotVersion(50,0,true,35,.5,1,
//            new Interpolater(new double[][]{{16, 1330}, {24, 1370}, {36, 1410}, {48, 1490}, {60, 1550}, {72, 1600}, {84, 1660}, {96, 1700}, {108, 1870}, {130, 1950}}),
//            new Interpolater(new double[][]{{16, 1}, {24, 0.9}, {36, .8}, {48, .55}, {60, 0.6}, {72, 0.55}, {84, 0.2}, {96, 0}, {108, 0.0}, {130, 0}}),
//            new Interpolater(new double[][]{{0.246, 16}, {0.2, 24}, {0.16, 36}, {0.132, 48}, {0.11, 60}, {0.096, 72}, {0.085, 84}, {0.077, 96}, {0.068, 108}, {0.064, 120}, {0.058, 132}, {0.054, 144}, {0.05, 156}}),
//            new PidController(1.2, 0.62, 1, 0.4, true, 0, 0),
//            new PidController(0.2, .26, 0.17, .12, true, 0.02, 0.2),
//            new PidController(0.2, .26, 0.17, .12, true, 0.02, 0.2),
//            new PidController(0.68, .53, 0.17, .27, true, 0.2, 0.3));


    private static boolean autoMode = false;

    public static void setIsAutonomous(boolean isAutonomous) {
        RobotVersion.autoMode = isAutonomous;
    }


    public static boolean isAutonomous() {
        return autoMode;
    }

    private static ID robotAlliance = ID.NONE;

    public static void setRobotAlliance(ID alliance) {
        RobotVersion.robotAlliance = alliance;
    }

    public static ID getRobotAlliance() {
        return robotAlliance;
    }


    public final double microwaveOffset;
    public final boolean oldVoltagePositions;
    public final double distSensorThreshold;

    public final double scoopRestPos;
    public final double scoopLaunchPos;

    public final double visionLoopIntervalMS;

    public final Interpolater launchSpeedInterpolater;
    public final Interpolater launchAngleInterpolater;
    public final Interpolater aprilDistanceInterpolater;
    public final PidController aprilTargetingPid;
    public final PidController pidControllerX;
    public final PidController pidControllerY;
    public final PidController pidControllerH;


    private RobotVersion(double visionLoopIntervalMS, double microwaveOffset, boolean oldVoltagePositions, double distSensorThreshold, double scoopRestPos, double scoopLaunchPos, Interpolater launchSpeedInterpolater, Interpolater launchAngleInterpolater, Interpolater aprilHeightInterpolater, PidController aprilTargetingPid, PidController pidControllerX, PidController pidControllerY, PidController pidControllerH) {
        this.visionLoopIntervalMS = visionLoopIntervalMS;
        this.microwaveOffset = microwaveOffset;
        this.oldVoltagePositions = oldVoltagePositions;
        this.distSensorThreshold = distSensorThreshold;
        this.scoopRestPos = scoopRestPos;
        this.scoopLaunchPos = scoopLaunchPos;
        this.launchSpeedInterpolater = launchSpeedInterpolater;
        this.launchAngleInterpolater = launchAngleInterpolater;
        this.aprilDistanceInterpolater = aprilHeightInterpolater;
        this.aprilTargetingPid = aprilTargetingPid;
        this.pidControllerX = pidControllerX;
        this.pidControllerY = pidControllerY;
        this.pidControllerH = pidControllerH;
    }


}
