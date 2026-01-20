package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.autoStates.AutoMoveToPosState;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;

public class PeakTargetState extends StateMachine.State {
    private final IntPoint ballLocation;
    private final PowerInputMixer powerInputMixer;
    private final AbsolutePosition absPos;
    private final double[] startPos = new double[3];

    public static boolean killIntakeTargeting = false;

    public PeakTargetState(IntPoint ballLocation, PowerInputMixer powerInputMixer, AbsolutePosition absPos) {
        this.ballLocation = ballLocation;
        this.powerInputMixer = powerInputMixer;
        this.absPos = absPos;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (!killIntakeTargeting) {
            powerInputMixer.setManualPowers(new double[]{});
        } else {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        powerInputMixer.setAutoEnabled(new boolean[]{false, false, false});
//        powerInputMixer.setDirectPower(true);
    }

    @Override
    protected void stopped() {
        powerInputMixer.setManualPowers(new double[]{});
//        powerInputMixer.setDirectPower(false);
    }
}
