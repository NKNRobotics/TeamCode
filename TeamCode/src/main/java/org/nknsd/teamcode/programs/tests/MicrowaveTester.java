package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.drivers.MicrowaveDriver;
import org.nknsd.teamcode.components.drivers.WheelDriver;
import org.nknsd.teamcode.components.handlers.MicrowaveHandler;
import org.nknsd.teamcode.components.handlers.WheelHandler;
import org.nknsd.teamcode.components.sensors.ColourSensor;
import org.nknsd.teamcode.components.utility.GamePadHandler;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.controlSchemes.defaults.MicrowaveControlScheme;
import org.nknsd.teamcode.controlSchemes.defaults.WheelControlScheme;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "MicrowaveTester", group = "Tests")
public class MicrowaveTester extends NKNProgram {

    class Microwave extends StateCore.State {
        double switchState = time + 2000;

        final MicrowaveHandler microwaveHandler;

        public Microwave(MicrowaveHandler microwaveHandler){
            this.microwaveHandler = microwaveHandler;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

            while(runtime.milliseconds() < switchState) {
                microwaveHandler.intakeOne();
            }
            switchState = runtime.milliseconds() + 2000;
            while(runtime.milliseconds() < switchState) {
                microwaveHandler.intakeTwo();
            }
            switchState = runtime.milliseconds() + 2000;
            while(runtime.milliseconds() < switchState) {
                microwaveHandler.intakeThree();
            }
            switchState = runtime.milliseconds() + 2000;
            while(runtime.milliseconds() < switchState) {
                microwaveHandler.fireOne();
            }
            switchState = runtime.milliseconds() + 2000;
            while(runtime.milliseconds() < switchState) {
                microwaveHandler.fireTwo();
            }
            switchState = runtime.milliseconds() + 2000;
            while(runtime.milliseconds() <  switchState) {
                microwaveHandler.fireThree();
            }
            switchState = runtime.milliseconds() + 2000;
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


        MicrowaveHandler microwaveHandler = new MicrowaveHandler("Spin");
        components.add(microwaveHandler);

        MicrowaveDriver microwaveDriver = new MicrowaveDriver();
        components.add(microwaveHandler);

        StateCore stateCore = new StateCore();
        components.add(stateCore);

        stateCore.addState("microwave", new Microwave(microwaveHandler));
        stateCore.startState("microwave");
    }
}