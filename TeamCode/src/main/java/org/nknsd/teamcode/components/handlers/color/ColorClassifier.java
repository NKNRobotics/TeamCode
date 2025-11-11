package org.nknsd.teamcode.components.handlers.color;

public class ColorClassifier {



    final double maxDist = 35;
    final private ColorReader colorReader;

    public ColorClassifier(ColorReader colorReader) {
        this.colorReader = colorReader;
    }

    public BallColor classifyColor() {
        if(colorReader.getDistance() >= maxDist){
            return BallColor.NOTHING;
        }
        int[] colors = colorReader.getRGB();
        if(colors[1] > colors[0]  && colors[1] > colors[2]){
           return BallColor.GREEN;
        }
        if(colors[2] > colors[0]){
            return BallColor.PURPLE;
        }
        return BallColor.UNSURE;
    }
}
