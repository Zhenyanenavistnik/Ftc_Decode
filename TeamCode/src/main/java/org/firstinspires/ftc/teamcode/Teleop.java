package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "XIXIXIXIXI")
public class Teleop extends OpMode {
    Player1 pl_1;
    Player2 pl_2;
    @Override
    public void init (){
        pl_1 = new Player1(this);
        pl_2 = new Player2(this);
    }

    public void init_loop(){}

    public void start() {
        pl_1.opActive = true;
        pl_1.start();
        pl_2.opActive = true;
        pl_2.start();
        this.telemetry.addLine("Start");
    }

    public void loop() {
        this.telemetry.update();
    }

    public void stop(){
        pl_2.opActive = false;
        pl_1.opActive = false;
        this.telemetry.addLine("Stop");
    }
}
