package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    boolean timeFlagg;
    ElapsedTime delayTime;
    Odometry odometry;
    LinearOpMode op;
    boolean moveComplete;
    double I = 0.6; //0.75;
    double kPang = 0.03;//1;
    double kPlin = 0.02;//0.024;
    Mecanum mecanum;
    public final PID pidLinearX = new PID(kPlin,0.0000015,0.00001, -I,I);
    public final PID pidLinearY = new PID(kPlin,0.0000015,0.00001, -I,I);
    public final PID pidAngular = new PID(kPang,0.0000015,0.00001, -I,I);

    public Robot(LinearOpMode op, Odometry odometry,Mecanum mecanum){
        this.op = op;
        this.odometry = odometry;
        this.mecanum = mecanum;
    }
    public void init(){
        mecanum.init();
    }
    public void goToPoint(Position position, double seconds) {
        if(!timeFlagg){
            delayTime = new ElapsedTime();
            timeFlagg = true;
        }
        if (op.opModeIsActive() && !moveComplete) {
            if (delayTime.seconds() >= seconds) {
                mecanum.offMotors();
                moveComplete = true;
                timeFlagg = false;
                return;
            }
            boolean errorPosDone = false;
            boolean errorHeadingDone = false;

            double linearVel; // Линейная скорость робота

            Vector2 errorPos = new Vector2();

            // Находим ошибку положения
            errorPos.x = position.getX() - odometry.globalPosition.getX();
            errorPos.y = position.getY() - odometry.globalPosition.getY();
            double errorHeading = normalizeAngle(normalizeAngle(position.getHeading())  - normalizeAngle(odometry.globalPosition.getHeading()));

            // Направление движения
            Vector2 targetVel = new Vector2(errorPos);
            targetVel.normalize();
            targetVel.rotate(normalizeAngle(-odometry.globalPosition.getHeading())); // Здесь минус потому что направление движения поворачивается в обратную сторону относительно поворота робота!!!

            // Выбираем скорости в зависимости от величины ошибки
            linearVel = 20;

            //if (Math.abs(errorHeading) < Math.toRadians(8)) {
             //   kPang = 0.45;
           // } else if (Math.abs(errorHeading) >= Math.toRadians(8) && Math.abs(errorHeading) < Math.toRadians(15)) {
            //    kPang = 0.50;
           // } else {
           //     kPang = 1;
           // }

             if (errorPos.length() < 0.03) {
                errorPosDone = true;
               // linearVel = 0;
            }

            if (Math.abs(errorHeading) < Math.toRadians(2)) {
                errorHeadingDone = true;
               // kPang = 0.0;
            }

            pidAngular.setPID(kPang, 0, 0);

            targetVel.multyplie(linearVel);

            // Передаем требуемые скорости в ПИД для расчета напряжения на моторы
            double speedPIDX = pidLinearX.calculate(targetVel.x, odometry.velocity.x);
            double speedPIDY = pidLinearY.calculate(targetVel.y, odometry.velocity.y);
            double angularPID = pidAngular.calculate(normalizeAngle(position.getHeading()), normalizeAngle(odometry.globalPosition.getHeading()));

            if (errorPosDone && errorHeadingDone) {
                moveComplete = true;
                delayTime.reset();
                mecanum.offMotors();
                timeFlagg = false;
            } else {
                 moveComplete = false;

                mecanum.setDrivePowers(speedPIDX, speedPIDY, angularPID);
            }
        }
    }
    public static double normalizeAngle(double angle) {
        // Нормализуем в диапазон [-π, π]
        while (angle > Math.PI) {
            angle -= 2 * Math.PI;
        }
        while (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }
    public void resetPID(){
        pidAngular.reset();
        pidLinearX.reset();
        pidLinearY.reset();
    }
}

