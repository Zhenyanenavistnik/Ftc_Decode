package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "auto")
public class Test extends LinearOpMode {
    Camera camera;
    Detected[] motif;
    Gun gun;
    ServoMan servoMan;
    Cylinder2 cylinder;
    ElapsedTime elapsedTime;
    boolean gunes =true;
    boolean canShot;
    Mecanum mecanum;
    Odometry odometry;
    int shots;
    Robot robot;
    boolean mec;
    public void runOpMode()  {
        odometry = new Odometry(this);
        mecanum = new Mecanum(this);
        gun = new Gun(this);
        gun.init();
        servoMan = new ServoMan(this);
        servoMan.init();
        cylinder = new Cylinder2(this,servoMan);
        cylinder.init();
        camera = new Camera(this);
        camera.init();
        motif  = new Detected[3];
        elapsedTime = new ElapsedTime();
        mecanum.init();
        robot = new Robot(this);
        robot.init();
        while (!isStarted() && !isStopRequested()){
            camera.update();
            motif = camera.motif;
            this.telemetry.addData("motif",camera.motif[0]);
            this.telemetry.addData("motif",camera.motif[1]);
            this.telemetry.addData("motif",camera.motif[2]);
            telemetry.update();
        }
        waitForStart();
        cylinder.motif =motif;
        while (opModeIsActive()){
            if(shots >=3 && elapsedTime.milliseconds() > 500){
                shots = 0;
                mec = true;
                gun.setOff();
            }
            odometry.run();
            cylinder.run();
            gun.run();
            if(mec){
                robot.goToPoint(new Position(1,-1,0),10,odometry);
            }
            if(!cylinder.catcherMode && !mec){
                gun.setOn();
                gunes = false;
                this.telemetry.addData("1",cylinder.catcherMode);
            }
            if(!cylinder.catcherMode && !canShot){
                elapsedTime.reset();
               canShot = true;
                this.telemetry.addData("2",cylinder.catcherMode);
            }
            if(!cylinder.catcherMode && elapsedTime.milliseconds() > 2800){
                servoMan.up();
                servoMan.shot(true);
                canShot = false;
                shots++;
                elapsedTime.reset();
                this.telemetry.addData("3",cylinder.catcherMode);
            }
            this.telemetry.addData("shots",shots);
            this.telemetry.update();
        }
    }

}
