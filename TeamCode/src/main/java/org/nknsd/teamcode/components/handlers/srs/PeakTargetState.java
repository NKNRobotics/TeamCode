package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;

public class PeakTargetState extends StateMachine.State {
    private final IntPoint ballLocation;
    private final PowerInputMixer powerInputMixer;
    private final AbsolutePosition absPos;
    private final double maxError;
    private final PidController pidController;
    private double[] currentPos = new double[3];
    private double[] targetPos = new double[3];

    public static boolean killIntakeTargeting = false;

    public PeakTargetState(IntPoint ballLocation, PowerInputMixer powerInputMixer, AbsolutePosition absPos, double maxError, PidController pidController) {
        this.ballLocation = ballLocation;
        this.powerInputMixer = powerInputMixer;
        this.absPos = absPos;
        this.maxError = maxError;
        this.pidController = pidController;
    }
    public PeakTargetState(IntPoint ballLocation, PowerInputMixer powerInputMixer, AbsolutePosition absPos){
        this.ballLocation = ballLocation;
        this.powerInputMixer = powerInputMixer;
        this.absPos = absPos;
        this.maxError = 5;
        PidController pidController = new PidController(0.6, .5, 0.1, .25, true, 0.2, 0.3);
        this.pidController = pidController;
    }

    public double errorDelta = 0;
    public double prevError = 0;
    public double lastRuntime;


    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (!killIntakeTargeting) {
            currentPos = absPos.getDoublePosition();
            double error = targetPos[2] - currentPos[2];
            errorDelta = error - prevError;
            double headingChange = pidController.findOutput(error, errorDelta
                    ,absPos.getVelocity().h ,runtime.milliseconds() - lastRuntime);

            if((currentPos[2] >= targetPos[2] - error || currentPos[2] <= currentPos[2] + error) && absPos.getVelocity().h < 0.05){
                StateMachine.INSTANCE.stopAnonymous(this);
            }

            powerInputMixer.setManualPowers(new double[]{0, 0, headingChange});

            lastRuntime = runtime.milliseconds();
            prevError = error;
        } else {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        currentPos = absPos.getDoublePosition();
        targetPos[2] = ballLocation.getX() * 6 + currentPos[2];
        powerInputMixer.setAutoEnabled(new boolean[]{false, false, false});
    }

    @Override
    protected void stopped() {
        powerInputMixer.setManualPowers(new double[]{});
//        powerInputMixer.setDirectPower(false);
    }
}
