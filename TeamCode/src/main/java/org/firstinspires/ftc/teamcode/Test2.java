package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "auto2")
public class Test2 extends LinearOpMode {
    ElapsedTime elapsedTime;
    Mecanum mecanum;
    Odometry odometry;
    Robot robot;
    boolean mec;
    public void runOpMode()  {
        odometry = new Odometry(this);
        mecanum = new Mecanum(this,odometry);
        elapsedTime = new ElapsedTime();
        mecanum.init();
        robot = new Robot(this,odometry,mecanum);
        robot.init();
        while (!isStarted() && !isStopRequested()){
            telemetry.update();
        }
        waitForStart();
        robot.resetPID();
        while (opModeIsActive()) {
            odometry.run();
            robot.goToPoint(new Position(1, 0, 0), 15);
            telemetry.update();
        }
    }

}

