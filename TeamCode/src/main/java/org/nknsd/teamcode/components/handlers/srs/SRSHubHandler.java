package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class SRSHubHandler implements NKNComponent {
    private SRSHub hub;
    private short[][] distArray;

    public short[][] getDistances() {
        hub.update();
        SRSHub.VL53L5CX distData = hub.getI2CDevice(1, SRSHub.VL53L5CX.class);

        distArray = new short[8][8];

        for (int i = 0; i < 64; i++) {
            distArray[7 - i / 8][i % 8] = distData.distances[i];
        }
        return distArray;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        // All ports default to NONE, buses default to empty
        SRSHub.Config config = new SRSHub.Config();
        config.addI2CDevice(1, new SRSHub.VL53L5CX(SRSHub.VL53L5CX.Resolution.GRID_8x8));

        hub = hardwareMap.get(
                SRSHub.class,
                "srshub"
        );
        hub.init(config);
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {
        telemetry.addLine("Waiting for SRSHub");
        while (!hub.ready()) ;
        telemetry.addLine("SRSHub Ready!");
    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "SRSHub";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        if (distArray != null) {
            for (int y = 0; y < 8; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < 8; x++) {
                    sb.append(distArray[x][y]);
                    sb.append(", ");
                }
                telemetry.addData("row: " + y, sb.toString());
            }
        }
    }
}
