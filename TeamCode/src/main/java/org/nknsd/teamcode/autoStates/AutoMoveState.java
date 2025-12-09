package org.nknsd.teamcode.autoStates;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoMoveState extends StateMachine.State {
    final private AutoPositioner autoPositioner;
    final private AbsolutePosition absolutePosition;
    final private double xTarget;
    final private double yTarget;
    final private double hTarget;
    final private double errorXMargin;
    final private double errorYMargin;
    final private double errorHMargin;
    final private double speedError;

    /**
     *
     * @param xTarget sets the absolute X target
     * @param yTarget sets the absolute Y target
     * @param hTarget sets the heading target
     * @param errorXMargin sets the x error margin based on (SOMETHING IDK)
     * @param errorYMargin sets the y error margin based on (SOMETHING IDK)
     * @param errorHMargin sets the heading error margin based on (SOMETHING IDK)
     * @param speedError sets the speed error margin
     */
    public AutoMoveState(AutoPositioner autoPositioner, AbsolutePosition absolutePosition, double xTarget, double yTarget, double hTarget, double errorXMargin, double errorYMargin, double errorHMargin, double speedError){
        this.autoPositioner = autoPositioner;
        this.absolutePosition = absolutePosition;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
        this.hTarget = hTarget;
        this.errorXMargin = errorXMargin;
        this.errorYMargin = errorYMargin;
        this.errorHMargin = errorHMargin;
        this.speedError = speedError;
    }
    private boolean isWithin(double currentError, double allowedError) {
        return (Math.abs(currentError) < allowedError);
    }
    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {


        boolean angleCheck = false;
        boolean speedCheck = false;
        boolean xyCheck = false;

        if (isWithin(xTarget - absolutePosition.getPosition().x, errorXMargin) && isWithin(yTarget - absolutePosition.getPosition().y, errorXMargin)) {
            xyCheck = true;
        }
        if(isWithin(hTarget - absolutePosition.getPosition().h, errorHMargin)){
            angleCheck = true;
        }

        if (isWithin(absolutePosition.getVelocity().x, speedError) && isWithin(absolutePosition.getVelocity().y, speedError)) {
            speedCheck = true;
        }

//        logger.info("DC:" + distCheck + " SC:" + speedCheck);

        if (angleCheck && speedCheck && xyCheck) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        autoPositioner.setTargetX(xTarget);
        autoPositioner.setTargetY(yTarget);
        autoPositioner.setTargetH(hTarget);
    }

    @Override
    protected void stopped() {

    }
}
