package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "XIXIXIXIXI")
public class Teleop extends OpMode {
    Thread pl1,pl2;
    private boolean threadStarted = false;
    @Override
    public void init (){
        pl1 = new Thread(new Player1(this));
    }

    public void init_loop(){}

    public void start() {
        this.telemetry.addLine("Start");
    }

    public void loop() {
        if(!threadStarted){
            pl1.start();
            threadStarted = true;
        }
    }

    public void stop(){
        this.telemetry.addLine("Stop");
    }
}
