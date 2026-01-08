package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoMan {
    public Servo pusher;
    public Servo turner;
    OpMode op;

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
}