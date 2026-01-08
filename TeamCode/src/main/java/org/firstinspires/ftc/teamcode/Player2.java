package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Player2 extends Thread{
    OpMode op;
    Gun gun;
    ServoMan servoMan;
    public boolean opActive = false;
    public Player2(OpMode op){
        this.op = op;
        gun = new Gun(op);
        servoMan = new ServoMan(op);
    }
    @Override
    public void run() {
        while (opActive){
            if (op.gamepad2.right_bumper) {
                gun.setOff();
            }
            if (op.gamepad2.left_bumper) {
                gun.setOn();
            }
            if(op.gamepad2.a){
                servoMan.pusher.setPosition(servoMan.pusher.getPosition()+0.05);
            }
            if(op.gamepad2.b){
                servoMan.pusher.setPosition(servoMan.pusher.getPosition()-0.05);
            }
            if(op.gamepad2.x){
                servoMan.up();
            }
            if(op.gamepad2.y){
                servoMan.down();
            }
            op.telemetry.addData("",servoMan.pusher.getPosition());
        }
    }
}
