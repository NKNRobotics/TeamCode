package org.nknsd.teamcode.components.motormixers;

import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class AutoPositioner {
    private final PidController pidControllerX;
    private final PidController pidControllerY;
    private final PidController pidControllerH;
    private final PowerInputMixer powerInputMixer;
    private final AbsolutePosition absolutePosition;

    public AutoPositioner(PidController pidControllerX, PidController pidControllerY, PidController pidControllerH, PowerInputMixer powerInputMixer, AbsolutePosition absolutePosition) {
        this.pidControllerX = pidControllerX;
        this.pidControllerY = pidControllerY;
        this.pidControllerH = pidControllerH;
        this.powerInputMixer = powerInputMixer;
        this.absolutePosition = absolutePosition;
    }

    public void setTargetX(double speed){

    }
    public void setTargetY(double speed){

    }
    public void setTargetH(double speed){

    }
}
