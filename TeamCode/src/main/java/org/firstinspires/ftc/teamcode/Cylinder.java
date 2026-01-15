package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class Cylinder {
    RevColorSensorV3 colorSensor;
    OpMode op;


    public Cylinder(OpMode op) {
        this.op = op;
    }

    public void init() {
        colorSensor = op.hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.enableLed(true);
    }

    public void run() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float red = colors.red;
        float green = colors.green;
        float blue = colors.blue;
        float alpha = colors.alpha;
        Detected d = detect();
        op.telemetry.addData("red", red);
        op.telemetry.addData("green", green);
        op.telemetry.addData("blue", blue);
        op.telemetry.addData("alpha", alpha);
        op.telemetry.addData("Color",d  );
    }

    public Detected detect() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float red = colors.red;
        float green = colors.green;
        float blue = colors.blue;
        float alpha = colors.alpha;
        if (alpha < 0.06){
            return Detected.UNKNON;}
        if( blue > green ) return Detected.PURPLE;
        if(green > red *1.35f && green > blue*1.20f ) return Detected.GREEN;
        return Detected.UNKNON;
    }
}

