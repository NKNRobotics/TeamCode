//package org.nknsd.teamcode.programs.tests;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.nknsd.teamcode.components.sensors.DistSensor;
//import org.nknsd.teamcode.components.sensors.DistHubSensor;
//import org.nknsd.teamcode.frameworks.NKNComponent;
//import org.nknsd.teamcode.frameworks.NKNProgram;
//
//import java.util.HashMap;
//import java.util.List;
//
//<<<<<<< HEAD
//@TeleOp(name = "Distance Sensor Test", group="Tests")@Disabled
//public class DistanceSensorTest extends NKNProgramTrue {
//=======
//@TeleOp(name = "Forward Distance Sensor Test", group="Tests")
//public class DistanceSensorTest extends NKNProgram {
//>>>>>>> 67db820610588792885f3f6102b14615e92e5d38
//    @Override
//    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {
//        DistSensor backwardSensor = new DistSensor("sensorBackDist");
//        DistSensor leftSensor = new DistSensor("sensorLeftDist");
//        DistSensor rightSensor = new DistSensor("sensorRightDist");
//        DistHubSensor allSensor = new DistHubSensor();
//        components.add(backwardSensor);
//        components.add(leftSensor);
//        components.add(rightSensor);
//        components.add(allSensor);
//        telemetryEnabled.add(allSensor);
//
//        HashMap<DistHubSensor.SensorNames, DistSensor> sensors = new HashMap<>();
//        sensors.put(DistHubSensor.SensorNames.BACK, backwardSensor);
//        sensors.put(DistHubSensor.SensorNames.LEFT, leftSensor);
//        sensors.put(DistHubSensor.SensorNames.RIGHT, rightSensor);
//        allSensor.link(sensors);
//    }
//}
