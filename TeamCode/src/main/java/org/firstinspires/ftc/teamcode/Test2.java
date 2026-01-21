package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "auto2")
public class Test2 extends LinearOpMode {
    ElapsedTime elapsedTime;
    Robot robot;
    boolean mec;
    public void runOpMode()  {
        elapsedTime = new ElapsedTime();
        robot = new Robot(this);
        robot.init();
        while (!isStarted() && !isStopRequested()){
            telemetry.update();
        }
        waitForStart();
        robot.goToPoint(new Position(1, 0, 0), 15);
        while (opModeIsActive()) {
            telemetry.update();
        }
    }

}

