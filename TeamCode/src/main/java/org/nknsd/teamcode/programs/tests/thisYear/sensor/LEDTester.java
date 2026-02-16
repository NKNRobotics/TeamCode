
package org.nknsd.teamcode.programs.tests.thisYear.sensor;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.sensors.LEDIndicator;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;


@TeleOp(name = "Led Tester", group = "Tests")
public class LEDTester extends NKNProgram {

    class LEDState extends StateMachine.State {


        LEDIndicator ledIndicatorleft;
        LEDIndicator ledIndicatorright;

        public LEDState(LEDIndicator ledIndicator,LEDIndicator ledIndicator2) {

            this.ledIndicatorleft = ledIndicatorleft;
            this.ledIndicatorright = ledIndicatorright;

        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {

            ledIndicatorleft.setGreenLED((int)(runtime.seconds()) %4 == 0);
            ledIndicatorleft.setRedLED((int)(runtime.seconds()) %4 == 1);


            ledIndicatorright.setGreenLED((int)(runtime.seconds()) %4 == 2);
            ledIndicatorright.setRedLED((int)(runtime.seconds()) %4 == 3);

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
        components.add(StateMachine.INSTANCE);
        telemetryEnabled.add(StateMachine.INSTANCE);

        LEDIndicator indicatorleft = new LEDIndicator("redledleft","greenledleft");
        LEDIndicator indicatorright = new LEDIndicator("redledright","greenledright");
        components.add(indicatorleft);
        telemetryEnabled.add(indicatorleft);
        components.add(indicatorright);
        telemetryEnabled.add(indicatorright);


//        StateMachine.State timer1 = new TimerState(5000,new String[]{"A"},new String[]{ "state 2"}, new String[]{"A"} );
//        StateMachine.State timer2 = new TimerState(5000,new String[]{"B"},new String[]{ "state 3"}, new String[]{"B"} );
//        StateMachine.State timer3 = new TimerState(5000,new String[]{"C"},new String[]{ "state 4"}, new String[]{"C"} );
//        StateMachine.State timer4 = new TimerState(5000,new String[]{"D"},new String[]{ "state 1"}, new String[]{"D"} );


        StateMachine.INSTANCE.addState("A", new LEDState(indicatorleft, indicatorright));

//        StateMachine.INSTANCE.addState("state 1", timer1);
//        StateMachine.INSTANCE.addState("state 2", timer2);
//        StateMachine.INSTANCE.addState("state 3", timer3);
//        StateMachine.INSTANCE.addState("state 4", timer4);
        StateMachine.INSTANCE.startState("A");
    }
}
