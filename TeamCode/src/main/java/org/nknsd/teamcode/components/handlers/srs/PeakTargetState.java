package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;

public class PeakTargetState extends StateMachine.State {
    private final IntPoint ballLocation;
    private final PowerInputMixer powerInputMixer;

    public static boolean killIntakeTargeting = false;

    public PeakTargetState(IntPoint ballLocation, PowerInputMixer powerInputMixer) {
        this.ballLocation = ballLocation;
        this.powerInputMixer = powerInputMixer;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (!killIntakeTargeting) {
            powerInputMixer.setAutoPowers(new double[]{});
        }
    }

    @Override
    protected void started() {
        powerInputMixer.setAutoEnabled(new boolean[]{true, true, true});
        powerInputMixer.setDirectPower(true);
    }

    @Override
    protected void stopped() {
        powerInputMixer.setAutoEnabled(new boolean[]{false, false, false});
        powerInputMixer.setDirectPower(false);
    }
}
