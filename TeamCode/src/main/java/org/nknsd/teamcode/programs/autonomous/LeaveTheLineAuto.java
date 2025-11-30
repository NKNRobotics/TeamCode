package org.nknsd.teamcode.programs.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.nknsd.teamcode.components.handlers.WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER;

@Autonomous(name="Leave the Line")
public class LeaveTheLineAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER wheelHandlerTODODELETEMEUSEPOWERINPUTMIXER = new WheelHandlerTODODELETEMEUSEPOWERINPUTMIXER();

        waitForStart();

        double startTime = getRuntime();
        wheelHandlerTODODELETEMEUSEPOWERINPUTMIXER.relativeVectorToMotion(0, 0.5, 0);

        while (opModeIsActive()) {
            if (getRuntime() > startTime + 1000) {
                wheelHandlerTODODELETEMEUSEPOWERINPUTMIXER.relativeVectorToMotion(0, 0, 0);
                break;
            }
        }
    }
}
