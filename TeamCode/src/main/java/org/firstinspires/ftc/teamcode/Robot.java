package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    boolean timeFlagg;
    ElapsedTime delayTime;
    Mecanum mecanum;
    Odometry odometry;
    LinearOpMode op;
    boolean moveComplete;
    double I = 0.7;
    double kPang = 0.02;
    double kPlin = 0.2;
    public final PID pidLinearX = new PID(kPlin,0.000005,0.0001, -I,I);
    public final PID pidLinearY = new PID(kPlin,0.000005,0.0001, -I,I);
    public final PID pidAngular = new PID(kPang,0.000005,0.000001, -I,I);

    public Robot(LinearOpMode op){
        this.op = op;
        odometry = new Odometry(op);
        mecanum = new Mecanum(op,odometry);
    }
    public void init(){
        mecanum.init();
    }
    public void goToPoint(Position position, double seconds) {
        if(!timeFlagg){
            delayTime = new ElapsedTime();
            timeFlagg = true;
            pidLinearX.reset();
            pidLinearY.reset();
            pidAngular.reset();
        }
        while (op.opModeIsActive() && !moveComplete) {
            if (delayTime.seconds() >= seconds) {
                mecanum.offMotors();
                moveComplete = false;
                timeFlagg = false;
                return;
            }
            odometry.run();
            boolean errorPosDone = false;
            boolean errorHeadingDone = false;

            double linearVel; // Линейная скорость робота

            Vector2 errorPos = new Vector2();

            // Находим ошибку положения
            errorPos.x = position.getX() - odometry.globalPosition.getX();
            errorPos.y = position.getY() - odometry.globalPosition.getY();
            double errorHeading = position.getHeading() - odometry.globalPosition.getHeading();

            // Направление движения
            Vector2 targetVel = new Vector2(errorPos);
            targetVel.normalize();
            targetVel.rotate(-odometry.globalPosition.getHeading());

            linearVel = 22;

            if (errorPos.length() < 0.02) {
                errorPosDone = true;
                linearVel = 0;
            }

            if (Math.abs(errorHeading) < Math.toRadians(0.5)) {
                errorHeadingDone = true;
                //kPang = 0.0;
            }

            pidAngular.setPID(kPang, 0, 0);

            targetVel.multyplie(linearVel);

            // Передаем требуемые скорости в ПИД для расчета напряжения на моторы
            double speedPIDX = pidLinearX.calculate(targetVel.x, odometry.velocity.x);
            double speedPIDY = pidLinearY.calculate(targetVel.y, odometry.velocity.y);
            //double angularPID = pidAngular.calculate(position.getHeading(), odometry.heading);

            if (errorPosDone && errorHeadingDone) {
                moveComplete = true;
                delayTime.reset();
                mecanum.offMotors();
                timeFlagg = false;
            } else {
                 moveComplete = false;

                mecanum.setDrivePowers(speedPIDX, -speedPIDY, 0);
            }
        }
    }
}

