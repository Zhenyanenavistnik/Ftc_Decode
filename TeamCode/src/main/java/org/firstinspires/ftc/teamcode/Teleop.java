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
        this.telemetry.addLine("Start");
    }

    public void loop() {
        pl_1.run();
        pl_2.run();
        this.telemetry.update();

    }

    public void stop(){
        this.telemetry.addLine("Stop");
    }
}
