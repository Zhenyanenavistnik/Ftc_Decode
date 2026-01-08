package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Consts.DISTANCE_BETWEEN_PODS_X;
import static org.firstinspires.ftc.teamcode.Consts.DISTANCE_BETWEEN_PODS_Y;
import static org.firstinspires.ftc.teamcode.Consts.METER_PER_COUNT;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Base64;

public class Odometry extends Thread {
    OpMode op;
    DcMotorEx par0,par1,perp;
    double par0M = 0,par1M, perpM;

    public double x,y,head;
    public boolean opActive;

    public Odometry(OpMode op1){
        op = op1;
        par0 = op.hardwareMap.get(DcMotorEx.class, "leftFront");
        par1 = op.hardwareMap.get(DcMotorEx.class, "leftFront");
        perp = op.hardwareMap.get(DcMotorEx.class, "leftFront");

    }
    public void update() {
        par0M = par0.getCurrentPosition() / METER_PER_COUNT - par0M;
        par1M = par1.getCurrentPosition() / METER_PER_COUNT - par1M;
        perpM = perp.getCurrentPosition() / METER_PER_COUNT - perpM;

        double fwd = (par0M + par1M) / 2;
        head = (par0M + par1M) / DISTANCE_BETWEEN_PODS_Y;
        double str = perpM - DISTANCE_BETWEEN_PODS_X * head;
        if(head != 0){
            double r0 = fwd/head;
            double r1 = str/head;

            double relX = r0 * Math.sin(head) - r1 * (1- Math.cos(head));
            double relY = r1 * Math.sin(head) - r0 * (1- Math.cos(head));

            x += relX * Math.cos(head) - relY * Math.sin(head);
            y += relY * Math.cos(head) + relX * Math.sin(head);
        }else{
            x += fwd * Math.cos(head) - str * Math.sin(head);
            y += str * Math.cos(head) + fwd * Math.sin(head);
        }
        op.telemetry.addData("",x);
        op.telemetry.addData("",y);
        op.telemetry.addData("",head);
    }

    @Override
    public void run() {
        while (opActive){
            update();
        }
    }
}
