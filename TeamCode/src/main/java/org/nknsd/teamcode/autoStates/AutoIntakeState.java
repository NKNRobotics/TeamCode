package org.nknsd.teamcode.autoStates;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.utility.StateMachine;

public class AutoIntakeState extends StateMachine.State {
    private final ArtifactSystem artifactSystem;

    public AutoIntakeState(ArtifactSystem artifactSystem) {
        this.artifactSystem = artifactSystem;
    }

    @Override
    protected void run(ElapsedTime runtime, Telemetry telemetry) {
        if(artifactSystem.isReady()){
            StateMachine.INSTANCE.stopAnonymous(this);
        }
    }

    @Override
    protected void started() {
        artifactSystem.intakeUntilFull();
    }

    @Override
    protected void stopped() {
    }
}
