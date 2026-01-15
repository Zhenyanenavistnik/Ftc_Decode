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
    public boolean catcherMode;
    RevColorSensorV3 colorSensor;
    double times,times2;
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
        positionDetect();
        setPosition();
        if(catcherMode){
            detectedArray[firstFreePosition] = colorDetect();
        }
        shotDetector();
        op.telemetry.addData("catchMode",catcherMode  );
        op.telemetry.addData("times",times  );
        op.telemetry.addData("firstFreePosition",firstFreePosition  );
        op.telemetry.addData("lastFullPosition",lastFullPosition);
        op.telemetry.addData("Color",detectedArray[firstFreePosition]);
    }
    void setPosition(){
        if(servoMan.pusher.getPosition() == 0){
            if(catcherMode){
                switch (firstFreePosition){
                    case 0:
                        servoMan.turner.setPosition(0);
                        break;
                    case 1:
                        servoMan.turner.setPosition(0.703);
                        break;
                    case 2:
                        servoMan.turner.setPosition(0.347);
                        break;
                }
            } else{
                switch (lastFullPosition){
                    case 0:
                        servoMan.turner.setPosition(0.533);
                        break;
                    case 1:
                        servoMan.turner.setPosition(0.163);
                        break;
                    case 2:
                        servoMan.turner.setPosition(0.867);
                        break;
                }
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
                break;
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
                times = 0;
                elapsedTime.reset();
                if( blue > green ) return Detected.PURPLE;
                if(green > red *1.35f && green > blue*1.20f ) return Detected.GREEN;
            }
        }
        return Detected.UNKNON;
    }
    void shotDetector(){
        if(servoMan.shotComplite){
            detectedArray[lastFullPosition] =Detected.UNKNON;
            servoMan.down();
            ElapsedTime elapsedTime = new ElapsedTime();
            times2 += elapsedTime.milliseconds();
            if (times2 > 1000){
                servoMan.down();
                times2 = 0;
            }
            times2 += elapsedTime.milliseconds();
            if (times2 > 1000){
                servoMan.shot(false);
                times2 = 0;
            }
            elapsedTime.reset();
        }
    }
}
