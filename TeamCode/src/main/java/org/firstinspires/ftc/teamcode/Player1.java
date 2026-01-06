package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Player1 implements Runnable{
    OpMode op;
    Thread odom;
    public Mecanum drive;
    Catcher catcher;
    double x,y,rx;
    Odometry odometry;
    private boolean threadStarted = false;
    public Player1(OpMode op){
        this.op = op;
        odometry = new Odometry(op);
        drive = new Mecanum(op,odometry);
        odom = new Thread(odometry);
        catcher = new Catcher(op);
        drive.init();
        catcher.init();
    }
    @Override
    public void run() {
        if(!threadStarted){
            odom.start();
            threadStarted = true;
        }
        y = -op.gamepad1.left_stick_y;
        x = op.gamepad1.left_stick_x * 1.3;
        rx = op.gamepad1.right_stick_x;
        drive.setDrivePowers(y,x,rx);
        if(op.gamepad1.a){
            catcher.setOn();
        }
        if (op.gamepad1.b){
            catcher.setOff();
        }
    }
}
