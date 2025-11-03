package org.nknsd.teamcode.components.handlers.color;

public class ColorClassifier {

    final int maxNothingLightness = 10;

    final int minSomethingLightness = 100;

    final int minPurpleHue = 1;
    final int maxPurpleHue = 4;

    final int minGreenHue = 5;
    final int maxGreenHue = 10;
    final private ColorReader colorReader;

    public ColorClassifier(ColorReader colorReader) {
        this.colorReader = colorReader;
    }

    public BallColor classifyColor() {
        int[] hl = colorReader.getHueLight();
        if (hl[1] <= maxNothingLightness) {
            return BallColor.NOTHING;
        }
        if (hl[1] >= minSomethingLightness) {
            if (hl[0] >= minPurpleHue && hl[0] <= maxPurpleHue) {
                return BallColor.PURPLE;
            }
            if (hl[0] >= minGreenHue && hl[0] <= maxGreenHue) {
                return BallColor.GREEN;
            }
        }
        return BallColor.UNSURE;
    }
}
