package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.nknsd.teamcode.components.handlers.WheelHandler;

@Autonomous(name="Leave the Line")
public class LeaveTheLineAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        WheelHandler wheelHandler = new WheelHandler();

        waitForStart();

        double startTime = getRuntime();
        wheelHandler.relativeVectorToMotion(0, 0.5, 0);

        while (opModeIsActive()) {
            if (getRuntime() > startTime + 1000) {
                wheelHandler.relativeVectorToMotion(0, 0, 0);
                break;
            }
        }
    }
}
