package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.utility.ColourSensorInterpreter;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.MockColorSensor;

import java.util.List;

@TeleOp(name = "ColourSensorInterpreterTest", group = "Tests")
public class ColourSensorInterpreterTest extends NKNProgram {

    class SampleTimer extends StateCore.State {

        final ColourSensorInterpreter colourSensorInterpreter;

        SampleTimer(ColourSensorInterpreter colourSensorInterpreter) {
            this.colourSensorInterpreter = colourSensorInterpreter;
        }

        private double nextSampleTime = 0;
        private double sampleLength = 2000;

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (runtime.milliseconds() > nextSampleTime) {
                colourSensorInterpreter.startSampling();
                nextSampleTime = runtime.milliseconds() + sampleLength;
            }
        }

        @Override
        protected void started() {

        }

        @Override
        protected void stopped() {

        }
    }


    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateCore stateCore = new StateCore();

        MockColorSensor mockColorSensor = new MockColorSensor(600, 600, 100);
        stateCore.addState("mockColorSensor", mockColorSensor);

        ColourSensorInterpreter colourSensorInterpreter = new ColourSensorInterpreter(mockColorSensor);
        components.add(colourSensorInterpreter);
        telemetryEnabled.add(colourSensorInterpreter);

        stateCore.addState("testytimer", new SampleTimer(colourSensorInterpreter));

        stateCore.startState("mockColorSensor");
        stateCore.startState("testytimer");
    }
}
