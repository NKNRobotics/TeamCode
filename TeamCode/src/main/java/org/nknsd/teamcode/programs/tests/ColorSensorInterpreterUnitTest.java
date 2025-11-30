package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.MockColorReader;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "ColorSensorInterpreterUnitTest", group = "Tests")
public class ColorSensorInterpreterUnitTest extends NKNProgram {

    BallColorInterpreter ballColorInterpreter;

    class testSwitch extends StateCore.State {

        final MockColorReader mockColorReader;
        final double timerTime;
        final int testNum;
        final String nextTest;
        final BallColor expectedResult;

        testSwitch(MockColorReader mockColorReader, double timerTime, int testNum, String nextTest, BallColor expectedResult) {
            this.mockColorReader = mockColorReader;
            this.timerTime = timerTime;
            this.testNum = testNum;
            this.nextTest = nextTest;
            this.expectedResult = expectedResult;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            if (runtime.milliseconds() > startTimeMs + timerTime) {
                stateCore.stopAnonymous(this);
            }
        }

        @Override
        protected void started() {
            mockColorReader.setTestNumber(testNum);
        }

        @Override
        protected void stopped() {
            stateCore.startState(nextTest);
            BallColor result = ballColorInterpreter.getColorGuess();
            String message = "color test " + testNum + " saw " + result.name() + " expected " + expectedResult.name();
            RobotLog.v(message);
            if (result != expectedResult) {
                throw new NullPointerException(message);
            }
        }
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        StateCore stateCore = new StateCore();
        components.add(stateCore);
        telemetryEnabled.add(stateCore);

        MockColorReader mockColorReader = new MockColorReader("Wuf");
        components.add(mockColorReader);
        telemetryEnabled.add(mockColorReader);

        ballColorInterpreter = new BallColorInterpreter(20, 5);
        components.add(ballColorInterpreter);
        telemetryEnabled.add(ballColorInterpreter);

        ballColorInterpreter.link(mockColorReader);

        stateCore.addState("test0", new testSwitch(mockColorReader, 5000, 0, "test1", BallColor.PURPLE));
        stateCore.addState("test1", new testSwitch(mockColorReader, 5000, 1, "test0", BallColor.UNSURE));
    }
}
