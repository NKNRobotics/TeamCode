package org.nknsd.teamcode.components.handlers.srs;

import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.StateMachine;

import java.util.Objects;

public class PeakPointer {
    private final PeakFinder peakFinder;
    private final SRSHubHandler srsHubHandler;
    private final PowerInputMixer inputMixer;

    public boolean targetPeaks(){
        IntPoint ballLocation = peakFinder.altPeakFind(srsHubHandler.getNormalizedDists());
        if(Objects.equals(ballLocation, new IntPoint(-10, -10))) {
            StateMachine.INSTANCE.startAnonymous(new PeakTargetState(ballLocation, inputMixer));
            return true;
        }
        return false;
    }
    public void stopTargetPeaks(){

    }

    public PeakPointer(PeakFinder peakFinder, SRSHubHandler srsHubHandler, PowerInputMixer inputMixer) {
        this.peakFinder = peakFinder;
        this.srsHubHandler = srsHubHandler;
        this.inputMixer = inputMixer;
    }
}
