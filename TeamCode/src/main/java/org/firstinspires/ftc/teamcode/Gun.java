package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Gun {
    DcMotorEx motor;
    OpMode op;
    public Gun(OpMode op){
        this.op = op;
    }
    public void init(){
        motor = op.hardwareMap.get(DcMotorEx.class, "Gun");
    }
    public void setOn(){
        motor.setPower(1);
    }
    public void setOff(){
        motor.setPower(0);
    }
}
