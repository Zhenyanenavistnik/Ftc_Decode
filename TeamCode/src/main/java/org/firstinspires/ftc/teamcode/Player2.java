package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Player2{
    OpMode op;
    Gun gun;
    ServoMan servoMan;
    Cylinder2 cylinder;
    boolean timer;
    boolean canShot;
    ElapsedTime elapsedTime;
    public Player2(OpMode op){
        this.op = op;
        gun = new Gun(op);
        gun.init();
        servoMan = new ServoMan(op);
        servoMan.init();
        cylinder = new Cylinder2(op,servoMan);
        cylinder.init();
        elapsedTime = new ElapsedTime();
    }
    public void run() {
        cylinder.run();
        gun.run();
        if(cylinder.light){
            gun.led.setPower(-1);
        }else{
            gun.led.setPower(0);
        }
        if (op.gamepad2.right_bumper) {
            gun.setOff();
        }
        if (op.gamepad2.left_bumper) {
                gun.setOn();
        }
        if(op.gamepad2.a){
            gun.aFlag(1435);
        }
        if(op.gamepad2.b){
            gun.aFlag(1125);
        }
        if(op.gamepad2.x){
                if (!timer && cylinder.catcherMode){
                    timer = true;
                    cylinder.catcherMode = false;
                    elapsedTime.reset();
                }
                canShot = true;

        }
        op.telemetry.addData("",gun.motor.getVelocity());
        if(op.gamepad2.y){
            servoMan.down();
        }
        if(op.gamepad2.dpad_down){
            cylinder.cylinderFull = Cylinder2.CylinderFull.noFull;
            cylinder.catcherMode = true;
            cylinder.firstFreePosition = 0;
            cylinder.lastFullPosition = 0;
            cylinder.priorityCell = 0;
            cylinder.detectedArray[0] = Detected.UNKNON;
            cylinder.detectedArray[1] = Detected.UNKNON;
            cylinder.detectedArray[2] = Detected.UNKNON;
        }
        if(canShot){
            if(timer && !cylinder.catcherMode){
                if(elapsedTime.milliseconds() > 500){
                    servoMan.up();
                    servoMan.shot(true);
                    canShot = false;
                    timer = false;
                }
            }else if(!timer){
                servoMan.up();
                servoMan.shot(true);
                canShot = false;
            }
        }
        if(op.gamepad2.dpad_right){
            servoMan.turner.setPosition(servoMan.turner.getPosition() -0.005);
        }
        if(op.gamepad2.dpad_left){
            servoMan.turner.setPosition(servoMan.turner.getPosition() +0.005);
        }
        //op.telemetry.addData("pusher",servoMan.pusher.getPosition());
        op.telemetry.addData("turner",servoMan.turner.getPosition());
        //op.telemetry.addData("gun1",gun.motor.getPower());
        //op.telemetry.addData("gun2",gun.motor2.getPower());

    }
    public void motifSet(Detected[] motif){
        cylinder.motif = motif;
    }
}
