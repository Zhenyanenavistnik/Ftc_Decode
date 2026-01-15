package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Gun {
    DcMotorEx motor;
    DcMotorEx motor2;
    OpMode op;
    double a = 1;
    public Gun(OpMode op){
        this.op = op;
    }
    public void init(){
        motor = op.hardwareMap.get(DcMotorEx.class, "Gun");
        motor2 = op.hardwareMap.get(DcMotorEx.class, "Gun2");
    }
    public void setOn(){
        motor.setPower(a);
        motor2.setPower(-a);
    }
    public void aFlag(double a){
        this.a = a;
    }

    public void setOff(){
        motor.setPower(0);
        motor2.setPower(0);
    }
}
