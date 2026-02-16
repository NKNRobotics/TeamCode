package org.nknsd.teamcode.components.utility;

public class PositionTransform {

    final double xOffset;
    final double yOffset;
    final double hOffset;
    final double xMult;
    final double yMult;
    final double hMult;

    public double[] adjustPos(double x,double y,double h){
        return new double[]{
                (x + xOffset) * xMult,
                (y + yOffset) * yMult,
                (h + hOffset) * hMult
        };
    }

    public PositionTransform(double xOffset, double yOffset, double hOffset, double xMult, double yMult, double hMult) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.hOffset = hOffset;
        this.xMult = xMult;
        this.yMult = yMult;
        this.hMult = hMult;
    }

}
