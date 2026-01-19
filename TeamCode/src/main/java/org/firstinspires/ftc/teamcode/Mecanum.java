package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Mecanum {
    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightBack;
    public DcMotor rightFront;
    OpMode op;
    Odometry odometry;
    double b = 1;
    public Mecanum(OpMode op,Odometry odometry) {
        this.op = op;
        this.odometry = odometry;
    }
    public void init(){
        leftFront = op.hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = op.hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = op.hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = op.hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void setDrivePowers(double y,double x,double rx) {

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        leftFront.setPower((y + x + rx)/denominator/b);
        leftBack.setPower((y - x + rx)/denominator/b);
        rightBack.setPower((y + x - rx)/denominator/b);
        rightFront.setPower((y - x - rx)/denominator/b);
    }
    public void GoToPoint(double x,double y, double turn){
        double errorX  = x - odometry.x;
        double errorY = y - odometry.y;
        double errorT = turn - odometry.heading;
        if(Math.abs(errorX) >0.05 || Math.abs(errorY)>0.05 || Math.abs(errorT)>0.05){
            setDrivePowers(errorY,errorX,errorT);
        }
    }
}