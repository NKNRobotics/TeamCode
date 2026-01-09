package org.nknsd.teamcode.components.utility;

public class IntPoint {
    int x;
    int y;
    public IntPoint(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public IntPoint addPointToPoint(IntPoint otherPoint) {
        return new IntPoint(otherPoint.getX() + getX(), otherPoint.getY() + getY());
    }

    public IntPoint addPairToPoint(int x, int y) {
        return new IntPoint(x + getX(), y + getY());
    }

    public IntPoint multiplyByScalar(int scalar) {
        return new IntPoint(getX() * scalar, getY() * scalar);
    }

    public DoublePoint castToDoublePoint() {
        return new DoublePoint(getX(), getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
