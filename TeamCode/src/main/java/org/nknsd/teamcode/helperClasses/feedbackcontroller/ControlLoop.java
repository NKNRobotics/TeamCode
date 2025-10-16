package org.nknsd.teamcode.helperClasses.feedbackcontroller;

public interface ControlLoop {

    double findOutput(double error, double errorDelta, double vel, double interval) ;


}
