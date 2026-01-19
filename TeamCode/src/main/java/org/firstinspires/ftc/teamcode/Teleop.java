package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "XIXIXIXIXI")
public class Teleop extends OpMode {
    Player1 pl_1;
    Player2 pl_2;
    Camera camera;
    Detected[] motif;
    @Override
    public void init (){
        pl_1 = new Player1(this);
        pl_2 = new Player2(this);
        camera = new Camera(this);
        camera.init();
        motif  = new Detected[3];
    }

    public void init_loop(){
        camera.update();
        motif = camera.motif;
        this.telemetry.addData("motif",camera.motif[0]);
        this.telemetry.addData("motif",camera.motif[1]);
        this.telemetry.addData("motif",camera.motif[2]);
        telemetry.update();
    }

    public void start() {
        this.telemetry.addLine("Start");
        pl_2.motifSet(motif);
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
