package org.nknsd.teamcode.components.handlers.color;

import org.nknsd.teamcode.components.utility.RobotVersion;

public class ColorClassifier {



    final double maxDist = RobotVersion.INSTANCE.distSensorThreshold;
    final private ColorReader colorReader;

    public ColorClassifier(ColorReader colorReader) {
        this.colorReader = colorReader;
    }

    public BallColor classifyColor() {
        double[] colors = colorReader.getReading();
        if(colors[3] >= maxDist){
            return BallColor.NOTHING;
        }
        if(colors[1] > colors[0]  && colors[1] > colors[2]){
           return BallColor.GREEN;
        }
        if(colors[2] > colors[0]){
            return BallColor.PURPLE;
        }
        return BallColor.UNSURE;
    }
}
