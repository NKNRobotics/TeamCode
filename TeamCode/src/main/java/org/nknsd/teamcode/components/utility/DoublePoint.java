package org.nknsd.teamcode.components.utility;

public class DoublePoint {
    double x;
    double y;
    public DoublePoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void setXY(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}
