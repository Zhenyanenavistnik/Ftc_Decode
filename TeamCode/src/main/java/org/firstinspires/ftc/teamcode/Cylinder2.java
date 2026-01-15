package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.reflect.Array;

public class Cylinder2 {
    int firstFreePosition;
    int greenPosition;
    int lastFullPosition;
    Detected[] detectedArray;
    OpMode op;
    ServoMan servoMan;
    boolean catcherMode;
    RevColorSensorV3 colorSensor;
    double times;
    public Cylinder2(OpMode op,ServoMan servoMan){
        this.op = op;
        this.servoMan = servoMan;
    }
    public void init(){
        colorSensor = op.hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.enableLed(true);
        catcherMode = true;
        firstFreePosition = 0;
        detectedArray = new Detected[3];
        detectedArray[0] = Detected.UNKNON;
        detectedArray[1] = Detected.UNKNON;
        detectedArray[2] = Detected.UNKNON;
    }
    public void run(){
        if(catcherMode){
            detectedArray[firstFreePosition] = colorDetect();
        }
        op.telemetry.addData("catchMode",catcherMode  );
        op.telemetry.addData("times",times  );
        op.telemetry.addData("firstFreePosition",firstFreePosition  );
        op.telemetry.addData("lastFullPosition",lastFullPosition);
        op.telemetry.addData("Color",detectedArray[firstFreePosition]);

        positionDetect();
        setPosition();
    }
    void setPosition(){
        if(catcherMode){
            switch (firstFreePosition){
                case 0:
                    servoMan.turner.setPosition(0);
                    break;
                case 1:
                    servoMan.turner.setPosition(0.37);
                    break;
                case 2:
                    servoMan.turner.setPosition(0.74);
                    break;
            }
        } else{
            switch (lastFullPosition){
                case 0:
                    servoMan.turner.setPosition(0.57);
                    break;
                case 1:
                    servoMan.turner.setPosition(0.92);
                    break;
                case 2:
                    servoMan.turner.setPosition(0.18);
                    break;
            }
        }
    }
    void positionDetect(){
        for(int i = 0; i < 3;i++){
            if(detectedArray[i] != Detected.UNKNON){
                lastFullPosition = i;
                if(detectedArray[i] == Detected.GREEN){
                    greenPosition = i;
                }
            }
            if(detectedArray[i] == Detected.UNKNON){
                firstFreePosition = i;
            }
        }
    }
    Detected colorDetect(){
        ElapsedTime elapsedTime = new ElapsedTime();
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float red = colors.red;
        float green = colors.green;
        float blue = colors.blue;
        float alpha = colors.alpha;
        if (alpha < 0.06){
            times = 0;
            elapsedTime.reset();
            return Detected.UNKNON;
        }else{
            times += elapsedTime.milliseconds();
            if(times >= 1000){
                if( blue > green ) return Detected.PURPLE;
                if(green > red *1.35f && green > blue*1.20f ) return Detected.GREEN;
            }
        }
        return Detected.UNKNON;
    }
}
