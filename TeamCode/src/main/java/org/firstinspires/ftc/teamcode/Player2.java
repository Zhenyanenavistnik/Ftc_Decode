package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Player2{
    OpMode op;
    Gun gun;
    ServoMan servoMan;
    Cylinder2 cylinder;
    public Player2(OpMode op){
        this.op = op;
        gun = new Gun(op);
        gun.init();
        servoMan = new ServoMan(op);
        servoMan.init();
        cylinder = new Cylinder2(op,servoMan);
        cylinder.init();
    }
    public void run() {
        cylinder.run();
        if (op.gamepad2.right_bumper) {
            gun.setOff();
        }
        if (op.gamepad2.left_bumper) {
                gun.setOn();
        }
        if(op.gamepad2.a){
            gun.aFlag(1);
        }
        if(op.gamepad2.b){
            gun.aFlag(0.7);
        }
        if(op.gamepad2.x){
            servoMan.up();
        }
        if(op.gamepad2.y){
            servoMan.down();
        }

        if(op.gamepad2.right_trigger >0.1){
            servoMan.turner.setPosition(servoMan.turner.getPosition()+0.0005);
        }
        if(op.gamepad2.left_trigger >0.1){
            servoMan.turner.setPosition(servoMan.turner.getPosition()-0.0005);
        }
        if(op.gamepad2.dpad_left){
            servoMan.plusCatch();
        }
        if(op.gamepad2.dpad_right){
            servoMan.minusCatch();
        }
        if(op.gamepad2.dpad_up){
            servoMan.plusGun();
        }
        if(op.gamepad2.dpad_down){
            servoMan.minusGun();
        }
        //op.telemetry.addData("pusher",servoMan.pusher.getPosition());
        //op.telemetry.addData("turner",servoMan.turner.getPosition());
        //op.telemetry.addData("gun1",gun.motor.getPower());
        //op.telemetry.addData("gun2",gun.motor2.getPower());

    }
}
