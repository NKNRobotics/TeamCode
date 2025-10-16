package org.nknsd.teamcode.programs.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.ServoHandler;
import org.nknsd.teamcode.components.utility.StateCore;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;
import org.nknsd.teamcode.states.TimerState;

import java.util.List;

@TeleOp(name = "Servo Tester", group = "Tests")
public class ServoTester extends NKNProgram {

    class Move extends StateCore.State{

        ServoHandler servoHandler;
        final double rPos;
        final double lPos;

        public Move(ServoHandler servoHandler, double rPos, double lPos){
            this.servoHandler = servoHandler;
            this.rPos = rPos;
            this.lPos = lPos;
        }

        @Override
        protected void run(ElapsedTime runtime, Telemetry telemetry) {
            servoHandler.setLeftPos(lPos);
            servoHandler.setRightPos(rPos);
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
        ServoHandler servoHandler = new ServoHandler("rServo","lServo");
        components.add(servoHandler);
        telemetryEnabled.add(servoHandler);

        StateCore stateCore = new StateCore();
        components.add(stateCore);
        telemetryEnabled.add(stateCore);

        stateCore.addState("uc",new Move(servoHandler,0.65,0.35));
        stateCore.addState("dc",new Move(servoHandler,0.75,0.45));
        stateCore.addState("hc", new Move(servoHandler,0.3,0));
        stateCore.addState("uo",new Move(servoHandler,0.4,0.6));
        stateCore.addState("do",new Move(servoHandler,0.5,0.7));
        stateCore.addState("ho", new Move(servoHandler,0,0.2));

        stateCore.addState("TimerUO",new TimerState(3000,new String[]{"do","TimerDO"}, new String[]{"uo"}));
        stateCore.addState("TimerDO",new TimerState(3000,new String[]{"dc","TimerDC"}, new String[]{"do"}));
        stateCore.addState("TimerDC",new TimerState(3000,new String[]{"uc","TimerUC"}, new String[]{"dc"}));
        stateCore.addState("TimerUC",new TimerState(3000,new String[]{"hc","TimerHC"}, new String[]{"uc"}));
        stateCore.addState("TimerHC",new TimerState(3000,new String[]{"ho","TimerHO"}, new String[]{"hc"}));
        stateCore.addState("TimerHO",new TimerState(3000,new String[]{"uo","TimerUO"}, new String[]{"ho"}));

        stateCore.startState("uo");
        stateCore.startState("TimerUO");
    }
}
