package org.nknsd.teamcode.components.utility;

import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class RobotVersion {

//    comment out the unwanted version, top is new robot and bottom is old robot
//    as a note, all interpolater values are currently the same
    public final static RobotVersion INSTANCE= new RobotVersion(.038,60,.5,1,
        new Interpolater(new double[][]{{16,1350}, {32,1400}, {48,1400}, {64,1500}, {80,1600}, {96, 1700}, {112,1800}, {132, 1900}}),
        new Interpolater(new double[][]{{16,1}, {32,0.75}, {48,0.65}, {64,0.5}, {80,0.4}, {96,0.3}, {112,0.2}, {132,0.2}}),
        new Interpolater(new double[][]{{0.242,14}, {0.190,24}, {0.152,26}, {0.126,48}, {0.106,60}, {0.092,72}, {0.081,84}, {0.074,96}, {0.067,108}, {0.061,120}, {0.056,132}, {0.052,144}}),
        new PidController(0.8,.5,0.4,0.5,true,0,0));

//  public final static RobotVersion INSTANCE= new RobotVersion(0,35,0.5,1,
//      new Interpolater(new double[][]{{16,1400}, {32,1450}, {48,1500}, {64,1550}, {80,1650}, {96, 1850}, {112,2000}, {132, 2200}}),
//      new Interpolater(new double[][]{{16,1}, {32,0.75}, {48,0.65}, {64,0.5}, {80,0.4}, {96,0.3}, {112,0.2}, {132,0.1}}),
//      new Interpolater(new double[][]{{14,0.240}, {24,0.190}, {26,0.152}, {48,0.126}, {60,0.106}, {72,0.092}, {84,0.081}, {96,0.074}, {108,0.067}, {120,0.061}, {132,0.056}, {14496,0.052}}));


    public final double microwaveOffset;
    public final double distSensorThreshold;

    public final double scoopRestPos;
    public final double scoopLaunchPos;

    public final Interpolater launchSpeedInterpolater;
    public final Interpolater launchAngleInterpolater;
    public final Interpolater aprilDistanceInterpolater;

    public final PidController aprilTargetingPid;

    private RobotVersion(double microwaveOffset, double distSensorThreshold, double scoopRestPos, double scoopLaunchPos, Interpolater launchSpeedInterpolater, Interpolater launchAngleInterpolater, Interpolater aprilHeightInterpolater, PidController aprilTargetingPid){
        this.microwaveOffset = microwaveOffset;
        this.distSensorThreshold = distSensorThreshold;
        this.scoopRestPos = scoopRestPos;
        this.scoopLaunchPos = scoopLaunchPos;
        this.launchSpeedInterpolater = launchSpeedInterpolater;
        this.launchAngleInterpolater = launchAngleInterpolater;
        this.aprilDistanceInterpolater = aprilHeightInterpolater;
        this.aprilTargetingPid = aprilTargetingPid;
    }


}
