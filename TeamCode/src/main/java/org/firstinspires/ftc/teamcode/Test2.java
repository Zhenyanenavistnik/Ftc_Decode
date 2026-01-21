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
            while (!robot.moveComplete && opModeIsActive()){
                odometry.run();
                robot.goToPoint(new Position(-0.07, 0.7, 0), 5,odometry);
                telemetry.update();
            }
            robot.moveComplete = false;
            while (!robot.moveComplete && opModeIsActive()){
                odometry.run();
                robot.goToPoint(new Position(odometry.globalPosition.getX(), odometry.globalPosition.getY(), -80), 3,odometry);
                telemetry.update();
            }
            robot.moveComplete = false;
            while (!robot.moveComplete && opModeIsActive()){
                odometry.run();
                robot.goToPoint(new Position(-1.07, odometry.globalPosition.getY(), odometry.globalPosition.getHeading()), 3,odometry);
                telemetry.update();
            }
        }
    }

}

