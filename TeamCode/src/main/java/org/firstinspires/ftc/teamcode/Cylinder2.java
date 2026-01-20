package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.reflect.Array;

public class Cylinder2 {
    public enum CylinderFull{ full, noFull , nofing}
    int firstFreePosition;
    int greenPosition;
    int lastFullPosition;
    Detected[] detectedArray;
    OpMode op;
    ServoMan servoMan;
    public CylinderFull cylinderFull;
    public boolean catcherMode;
    RevColorSensorV3 colorSensor;
    double times,times2;
    ElapsedTime elapsedTime1,elapsedTime2, elapsedTime3;
    boolean elaps1,elaps2;
    Detected[] motif;
    int shotCompletes;
    int priorityCell;
    int greenQuantity;
    int purpleQuantity;
    boolean haveCombination;
    int lastPriorityCell;
    public boolean light;
    float alpha;
    public Cylinder2(OpMode op,ServoMan servoMan){
        this.op = op;
        this.servoMan = servoMan;
    }
    public void init(){
        colorSensor = op.hardwareMap.get(RevColorSensorV3.class, "colorSensor");
        colorSensor.enableLed(true);
        catcherMode = true;
        elaps2 = false;
        elaps1 = false;
        firstFreePosition = 0;
        cylinderFull = CylinderFull.noFull;
        elapsedTime2 = new ElapsedTime();
        elapsedTime1 = new ElapsedTime();
        elapsedTime3 = new ElapsedTime();
        detectedArray = new Detected[3];
        detectedArray[0] = Detected.UNKNON;
        detectedArray[1] = Detected.UNKNON;
        detectedArray[2] = Detected.UNKNON;
        motif = new Detected[3];
    }
    public void run(){
        if(light){
            if(elapsedTime3.milliseconds() > 1000){
                light = false;
            }
        }
        if(cylinderFull == CylinderFull.full){
            catcherMode = false;
        }else if(cylinderFull == CylinderFull.noFull){
            catcherMode = true;
            shotCompletes = 0;
            haveCombination = false;
        }
        if(catcherMode){
            detectedArray[firstFreePosition] = colorDetect();
        }
        shotPriorityDetect();
        shotDetector();
        positionDetect();
        setPosition();
        op.telemetry.addData("catchMode",catcherMode  );
        op.telemetry.addData("times",times  );
        op.telemetry.addData("firstFreePosition",firstFreePosition  );
        op.telemetry.addData("lastFullPosition",lastFullPosition);
        op.telemetry.addData("Color",detectedArray[firstFreePosition]);
        op.telemetry.addData("cell0",detectedArray[0]);
        op.telemetry.addData("cell1",detectedArray[1]);
        op.telemetry.addData("cell2",detectedArray[2]);
        op.telemetry.addData("priorityCell", priorityCell);
        op.telemetry.addData("shotCompletes", shotCompletes);
        op.telemetry.addData("alpha", alpha);
    }
    void setPosition(){
        if(!servoMan.shotComplite){
            if(catcherMode){
                switch (firstFreePosition){
                    case 0:
                        servoMan.turner.setPosition(0);
                        break;
                    case 1:
                        servoMan.turner.setPosition(0.4);
                        break;
                    case 2:
                        servoMan.turner.setPosition(0.785);
                        break;
                }
            } else{
                switch (priorityCell){
                    case 0:
                        servoMan.turner.setPosition(0.6);
                        break;
                    case 1:
                        servoMan.turner.setPosition(0.97);
                        break;
                    case 2:
                        servoMan.turner.setPosition(0.21);
                        break;
                }
            }
        }
    }
    public void shotPriorityDetect(){
        for(int i = 0; i < 3; i++){
            if (motif[shotCompletes] == detectedArray[i]) {
                priorityCell = i;
                break;
            }else {
                priorityCell = lastFullPosition;
            }
        }
    }
    void positionDetect() {
        int fullCell = 0;
        for (int i = 0; i < 3; i++) {
            if (detectedArray[i] != Detected.UNKNON) {
                lastFullPosition = i;
                if (detectedArray[i] == Detected.GREEN) {
                    greenPosition = i;
                }
                fullCell += 1;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (detectedArray[i] == Detected.UNKNON) {
                firstFreePosition = i;
                break;
            }
        }
        switch (fullCell) {
            case 0:
                cylinderFull = CylinderFull.noFull;
                catcherMode = true;
                break;
            case 3:
                cylinderFull = CylinderFull.full;
                break;
            default:
                cylinderFull = CylinderFull.nofing;

                break;
        }
    }
    Detected colorDetect(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float red = colors.red;
        float green = colors.green;
        float blue = colors.blue;
         alpha = colors.alpha;
        if(!elaps1){
            elaps1 = true;
            elapsedTime1.reset();
        }
        if (alpha < 0.1){
            elaps1 =false;
            elapsedTime1.reset();
            return Detected.UNKNON;
        }else{
            if(elapsedTime1.milliseconds() >= 650){
                elaps1 =false;
                if( blue > green ){
                    elapsedTime3.reset();
                    light = true;
                    return Detected.PURPLE;
                }
                if(green > red *1.35f && green > blue*1.20f ) {
                    elapsedTime3.reset();
                    light = true;
                    return Detected.GREEN;
                }
            }
        }
        return Detected.UNKNON;
    }
    void shotDetector(){
        if(servoMan.shotComplite){
            if(!elaps2){
                elapsedTime2.reset();
                elaps2 = true;
            }
            if (elapsedTime2.milliseconds() > 500){
                servoMan.down();
            }
            if (elapsedTime2.milliseconds() > 1000){
                servoMan.shot(false);
                elapsedTime2.reset();
                elaps2 = false;
                shotCompletes += 1;
                detectedArray[priorityCell] =Detected.UNKNON;
            }
        }
    }
}
//hkk