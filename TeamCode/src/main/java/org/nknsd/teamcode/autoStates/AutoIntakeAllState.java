package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoIntakeAllState extends StateMachine.State {
    private final ArtifactSystem artifactSystem;
    private final String[] toStopOnEnd;
    private final String[] toStartOnEnd;

    public AutoIntakeAllState(ArtifactSystem artifactSystem, String[] toStopOnEnd, String[] toStartOnEnd) {
        this.artifactSystem = artifactSystem;
        this.toStopOnEnd = toStopOnEnd;
        this.toStartOnEnd = toStartOnEnd;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if (artifactSystem.isReady()) {
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        artifactSystem.intakeUntilFull();

    }

    @Override
    protected void stopped() {
        artifactSystem.stopIntake();
        for (String stateName : this.toStopOnEnd) {
            StateMachine.INSTANCE.stopState(stateName);
        }
        for (String stateName : this.toStartOnEnd) {
            StateMachine.INSTANCE.startState(stateName);
        }
    }
}
