package org.nknsd.teamcode.components.motormixers;

import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class AutoPositioner {
    private final PidController pidControllerX;
    private final PidController pidControllerY;
    private final PidController pidControllerH;
    private final PowerInputMixer powerInputMixer;

    public AutoPositioner(PidController pidControllerX, PidController pidControllerY, PidController pidControllerH, PowerInputMixer powerInputMixer) {
        this.pidControllerX = pidControllerX;
        this.pidControllerY = pidControllerY;
        this.pidControllerH = pidControllerH;
        this.powerInputMixer = powerInputMixer;
    }

    public void setTargetX(double speed){

    }
    public void setTargetY(double speed){

    }
    public void setTargetH(double speed){

    }
}
