package org.nknsd.teamcode.programs.tests.thisYear.artifactSystem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.nknsd.teamcode.components.handlers.color.BallColor;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.Arrays;
import java.util.List;

@TeleOp(name = "slot ordering unit test", group = "Tests")
public class SlotOrderingUnitTest extends NKNProgram {

    private int[] testMe(BallColor[] colorOrder, BallColor[] slotColors) {
        int[] orderToLaunch = new int[]{4, 4, 4};
        for (int i = 0; i < 3; i++) {
            for (int c = 0; c < 3; c++) {
                if (colorOrder[i] == slotColors[c]) {
                    orderToLaunch[i] = c;
                    slotColors[c] = null;
                    break;
                }
            }
        }

//        if a slot could not be matched to the pattern's color, fill with whatever is left
        for (int e = 0; e < 3; e++) {
            if (orderToLaunch[e] == 4) {
                for (int s = 0; s < 3; s++) {
                    if (slotColors[s] != null) {
                        orderToLaunch[e] = s;
                        slotColors[s] = null;
                        break;
                    }
                }
            }
        }

        RobotLog.v("order! 0: " + orderToLaunch[0] + ", 1: " + orderToLaunch[1] + ", 2:" + orderToLaunch[2]);
        return orderToLaunch;
    }

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
        BallColor[] colorOrder = new BallColor[]{BallColor.PURPLE, BallColor.PURPLE, BallColor.GREEN};
        BallColor[] slotColors = new BallColor[]{BallColor.PURPLE, BallColor.PURPLE, BallColor.GREEN};
        int[] result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{0, 1, 2})) {
            throw new NullPointerException("Failed test case 1");
        }

        colorOrder = new BallColor[]{BallColor.GREEN, BallColor.GREEN, BallColor.GREEN};
        slotColors = new BallColor[]{BallColor.PURPLE, BallColor.PURPLE, BallColor.GREEN};
        result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{2, 0, 1})) {
            throw new NullPointerException("Failed test case 2");
        }

        colorOrder = new BallColor[]{BallColor.PURPLE, BallColor.GREEN, BallColor.PURPLE};
        slotColors = new BallColor[]{BallColor.NOTHING, BallColor.PURPLE, BallColor.GREEN};
        result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{1, 2, 0})) {
            throw new NullPointerException("Failed test case 3");
        }

        colorOrder = new BallColor[]{BallColor.PURPLE, BallColor.GREEN, BallColor.PURPLE};
        slotColors = new BallColor[]{BallColor.GREEN, BallColor.PURPLE, BallColor.GREEN};
        result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{1, 0, 2})) {
            throw new NullPointerException("Failed test case 4");
        }

        colorOrder = new BallColor[]{BallColor.PURPLE, BallColor.GREEN, BallColor.GREEN};
        slotColors = new BallColor[]{BallColor.PURPLE, BallColor.PURPLE, BallColor.GREEN};
        result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{0, 2, 1})) {
            throw new NullPointerException("Failed test case 5");
        }

        colorOrder = new BallColor[]{BallColor.GREEN, BallColor.PURPLE, BallColor.PURPLE};
        slotColors = new BallColor[]{BallColor.NOTHING, BallColor.NOTHING, BallColor.PURPLE};
        result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{0, 2, 1})) {
            throw new NullPointerException("Failed test case 6");
        }

        colorOrder = new BallColor[]{BallColor.PURPLE, BallColor.GREEN, BallColor.PURPLE};
        slotColors = new BallColor[]{BallColor.GREEN, BallColor.GREEN, BallColor.GREEN};
        result = testMe(colorOrder, slotColors);

        if (!Arrays.equals(result, new int[]{1, 0, 2})) {
            throw new NullPointerException("Failed test case 7");
        }

//            for (int i = 0; i < 3; i++) {
//                slotColors[i]
//            }

//        assigns order of slots based on colors

    }
}