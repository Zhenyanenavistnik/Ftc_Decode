package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoMan {
    public Servo pusher;
    public Servo turner;
    OpMode op;
    boolean catchOrGun = false;

    public ServoMan(OpMode op) {
        this.op = op;
    }

    public void init() {
        pusher = op.hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "pusher");
        turner = op.hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "turner");
        pusher.scaleRange(0, 1);
        turner.scaleRange(0, 1);

        turner.setPosition(0.5);
        pusher.setPosition(0.5);
    }
    public void up(){
        pusher.setPosition(1);
    }
    public void down(){
        pusher.setPosition(0);
    }
    public void plusCatch(){
        if(!catchOrGun){
            double a = turner.getPosition()+ (double) 1/3;
            turner.setPosition(Math.min((double) 2/3,a));
        }else {
            catchOrGun = false;
            turner.setPosition(Math.min(turner.getPosition()+ (double) 1/6, (double) 2/3));
        }
    }
    public void minusCatch(){
        if(!catchOrGun){
            double a = turner.getPosition()- (double) 1/3;
            turner.setPosition(Math.max(0,a));
        }else {
            catchOrGun = false;
            turner.setPosition(turner.getPosition()- (double) 1/6);
        }
    }
    public void plusGun(){
        if(catchOrGun){
            double a = turner.getPosition()+ (double) 1/3;
            turner.setPosition(Math.min((double) 5/6,a));
        }else {
            catchOrGun = true;
            turner.setPosition(turner.getPosition()+ (double) 1/6);
        }
    }
    public void minusGun(){
        if(catchOrGun){
            double a = turner.getPosition() - (double) 1/3;
            turner.setPosition(Math.max((double) 1/6,a));
        }else {
            catchOrGun = true;
            turner.setPosition(Math.max(turner.getPosition() - (double) 1/6,0));
        }
    }
}