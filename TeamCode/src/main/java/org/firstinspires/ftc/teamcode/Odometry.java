package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Consts.DISTANCE_BETWEEN_PODS_X;
import static org.firstinspires.ftc.teamcode.Consts.DISTANCE_BETWEEN_PODS_Y;
import static org.firstinspires.ftc.teamcode.Consts.METER_PER_COUNT;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Odometry{
    OpMode op;
    DcMotorEx par0,par1,perp;

    public double x,y,heading;
    double lastPar0,lastPar1,lastPerp;
    public boolean opActive;
    double dt;
    double oldTime;
    public Vector2 velocity,oldVelocity;
    ElapsedTime runtime;
    double dPar0,dPar1,dPepr;
    Position deltaPosition;                                   // Относительное перемещение
    Position globalPosition;

    public Odometry(OpMode op1){
        op = op1;
        runtime = new ElapsedTime();
        globalPosition = new Position();
        deltaPosition = new Position();
        velocity = new Vector2();
        oldVelocity = new Vector2();
        par0 = op.hardwareMap.get(DcMotorEx.class, "rightBack");
        par1 = op.hardwareMap.get(DcMotorEx.class, "leftBack");
        perp = op.hardwareMap.get(DcMotorEx.class, "leftFront");
        par0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        par1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        perp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        lastPar0 = -par0.getCurrentPosition();
        lastPar1 = par1.getCurrentPosition();
        lastPerp = -perp.getCurrentPosition();

    }
    public void update() {
        double curPar0 = -par0.getCurrentPosition();
        double curPar1 = par1.getCurrentPosition();
        double curPerp = -perp.getCurrentPosition();

        double d0 = curPar0-lastPar0;
        double d1 = curPar1-lastPar1;
        double d2 = curPerp-lastPerp;

        lastPar0 = curPar0;
        lastPar1 = curPar1;
        lastPerp = curPerp;

        dPar0 = d0*METER_PER_COUNT;
        dPar1 = d1*METER_PER_COUNT;
        dPepr = d2*METER_PER_COUNT;

        double fwd = (dPar0 + dPar1) / 2;
        double head = (dPar0 - dPar1) / DISTANCE_BETWEEN_PODS_Y;
        double str = dPepr - DISTANCE_BETWEEN_PODS_X * head;
        double mid = heading + head/2;

        x += fwd * Math.cos(mid) - str * Math.sin(mid);
        y += str * Math.cos(mid) + fwd * Math.sin(mid);
        heading += head;

        deltaPosition.setHeading(head);
        deltaPosition.setX(fwd);
        deltaPosition.setY(str);

        globalPosition.add(Vector2.rotate(deltaPosition.toVector(), globalPosition.getHeading()) , deltaPosition.getHeading());
        globalPosition.setHeading(globalPosition.getHeading());


        op.telemetry.addData("x",x);
        op.telemetry.addData("y",y);
        op.telemetry.addData("head",heading);
        op.telemetry.addData("heading",Math.toDegrees(heading));
        op.telemetry.addData("cPar0",curPar0);
        op.telemetry.addData("cPar1",curPar1);
        op.telemetry.addData("cPerp",curPerp);
    }
    private synchronized void updateVelocity(){
        dt = (runtime.milliseconds() - oldTime)/1000.0;// считаем время одного цикла
        oldTime = runtime.milliseconds();

        oldVelocity.x = velocity.x;
        oldVelocity.y = velocity.y;

        velocity.x = (deltaPosition.toVector().x)/dt;
        velocity.y = (deltaPosition.toVector().y)/dt;

    }

    public void run() {
        update();
        updateVelocity();
        op.telemetry.addData("dt",dt);
        op.telemetry.addData("oldTime",oldTime);
        op.telemetry.addData("velocity",velocity);
        op.telemetry.addData("oldVelocity",oldVelocity);
    }
}
