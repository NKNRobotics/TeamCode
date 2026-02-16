package org.nknsd.teamcode.frameworks;


import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.nknsd.teamcode.components.utility.PositionTransform;

import java.util.LinkedList;
import java.util.List;

public abstract class ProgramPart {

    public abstract void createComponents(List<NKNComponent> components,List<NKNComponent> telemetryEnabled);
}
