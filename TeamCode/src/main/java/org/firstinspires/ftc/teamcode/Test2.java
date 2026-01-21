package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "auto2")
public class Test2 extends LinearOpMode {
    double metrX = 0.9;
    double metrY = 1.35;
    ElapsedTime elapsedTime;
    Robot robot;
    Odometry odometry;
    boolean mec;
    public void runOpMode()  {
        elapsedTime = new ElapsedTime();
        robot = new Robot(this);
        robot.init();
        odometry = new Odometry(this);
        while (!isStarted() && !isStopRequested()){
            telemetry.update();
        }
        waitForStart();
        while (opModeIsActive()) {
            odometry.run();
            robot.goToPoint(new Position(0.9*2, 1.36, 0), 15,odometry);
            telemetry.update();
        }
    }

}

