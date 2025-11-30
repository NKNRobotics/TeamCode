package org.nknsd.teamcode.components.utility.feedbackcontroller;

public interface ControlLoop {

    double findOutput(double error, double errorDelta, double vel, double interval) ;


}
