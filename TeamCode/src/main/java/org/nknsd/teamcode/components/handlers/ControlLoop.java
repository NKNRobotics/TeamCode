package org.nknsd.teamcode.components.handlers;

public interface ControlLoop {

    double findOutput(double error, double errorDelta, double vel, double interval) ;


}
