package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Gun {
    DcMotorEx motor;
    DcMotorEx motor2;
    DcMotorEx led;
    OpMode op;
    double a = 1420;
    int tickPerRound = 28;
    int round =5500;
    public Gun(OpMode op){
        this.op = op;
    }
    public void init(){
        motor = op.hardwareMap.get(DcMotorEx.class, "Gun");
        motor2 = op.hardwareMap.get(DcMotorEx.class, "Gun2");
        led = op.hardwareMap.get(DcMotorEx.class, "led");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void setOn(){
        motor.setVelocity(a);
        motor2.setVelocity(-a);
    }
    public void aFlag(double a){
        this.a = a;
    }
    public void run(){
        if(motor.getVelocity() >= a){
            led.setPower(-1);
        }else{
            led.setPower(0);
        }
    }
    public void setOff(){
        motor.setVelocity(0);
        motor2.setVelocity(0);
    }
}
