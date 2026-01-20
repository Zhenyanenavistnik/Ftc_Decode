package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Player1 {
    OpMode op;
    public Mecanum drive;
    Catcher catcher;
    double x,y,rx;
    Odometry odometry;
    public Player1(OpMode op){
        this.op = op;
        odometry = new Odometry(op);
        drive = new Mecanum(op,odometry);
        catcher = new Catcher(op);
        drive.init();
        catcher.init();
    }
    public void run() {
        odometry.run();
        x = -op.gamepad1.left_stick_y;
        y = op.gamepad1.left_stick_x ;
        rx = op.gamepad1.right_stick_x;
        drive.setDrivePowers(x, y, rx);
        if (op.gamepad1.a) {
            catcher.setOn();
        }
        if (op.gamepad1.b) {
            catcher.setOff();
        }
        if (op.gamepad1.y) {
            catcher.setOnMinus();
        }
        if(op.gamepad1.dpad_up){
            drive.b = 1;
        }
        if(op.gamepad1.dpad_down){
            drive.b = 2;
        }
    }
}
