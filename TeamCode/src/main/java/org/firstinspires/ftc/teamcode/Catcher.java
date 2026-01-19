package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Catcher {
    DcMotorEx motor;
    OpMode op;
    double a = 1;
    public Catcher(OpMode op){
        this.op = op;
    }
    public void init(){
        motor = op.hardwareMap.get(DcMotorEx.class, "Catcher");
    }
    public void setOn(){
        motor.setPower(a);
    }
    public void setOff(){
        motor.setPower(0);
    }
    public void setOnMinus(){
        motor.setPower(-a);
    }
}
