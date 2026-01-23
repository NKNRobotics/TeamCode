package org.nknsd.teamcode.components.handlers.srs;

import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;

import java.util.Objects;

public class PeakPointer {
    private final PeakFinder peakFinder;
    private final SRSHubHandler srsHubHandler;
    private final PowerInputMixer inputMixer;
    private final AbsolutePosition absPos;

    public boolean targetPeaks(){
        IntPoint ballLocation = peakFinder.altPeakFind(srsHubHandler.getNormalizedDists());
        if(!ballLocation.equals(new IntPoint(-10, -10))) {
            StateMachine.INSTANCE.startAnonymous(new PeakTargetState(ballLocation, inputMixer, absPos));
            return true;
        }
        return false;
    }
    public void stopTargetPeaks(){

    }

    public PeakPointer(PeakFinder peakFinder, SRSHubHandler srsHubHandler, PowerInputMixer inputMixer, AbsolutePosition absPos) {
        this.peakFinder = peakFinder;
        this.srsHubHandler = srsHubHandler;
        this.inputMixer = inputMixer;
        this.absPos = absPos;
    }
}