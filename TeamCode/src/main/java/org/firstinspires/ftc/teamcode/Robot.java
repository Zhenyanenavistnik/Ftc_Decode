package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    boolean timeFlagg;
    ElapsedTime delayTime;
    Mecanum mecanum;
    //Odometry odometry;
    LinearOpMode op;
    boolean moveComplete;
    double I = 0.7;
    double kPang = 0.45;
    double kPlin = 0.02;
    public final PID pidLinearX = new PID(kPlin,0.000003,0.0000, -I,I);
    public final PID pidLinearY = new PID(kPlin,0.00003,0.0000, -I,I);
    public final PID pidAngular = new PID(kPang,0.002,0.00001, -I,I);

    public Robot(LinearOpMode op){
        this.op = op;
        //odometry = new Odometry(op);
        mecanum = new Mecanum(op);
    }
    public void init(){
        mecanum.init();
    }
    public void goToPoint(Position position, double seconds,Odometry odometry) {
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
            double errorHeading = normalizeAngle(position.getHeading() - odometry.globalPosition.getHeading());

            // Направление движения
            Vector2 targetVel = new Vector2(errorPos);
            targetVel.normalize();
            targetVel.rotate(-odometry.globalPosition.getHeading());

            linearVel = 22;

            if (errorPos.length() < 0.01) {
                errorPosDone = true;
                linearVel = 0;
            }

            if (Math.abs(errorHeading) < Math.toRadians(0.30)) {
                errorHeadingDone = true;
                //kPang = 0.0;
            }

            pidAngular.setPID(kPang, 0, 0);

            targetVel.multyplie(linearVel);

            // Передаем требуемые скорости в ПИД для расчета напряжения на моторы
            double speedPIDX = pidLinearX.calculate(targetVel.x, odometry.velocity.x);
            double speedPIDY = pidLinearY.calculate(targetVel.y, odometry.velocity.y);
            double angularPID = pidAngular.calculate(position.getHeading(), odometry.globalPosition.getHeading());

            if (errorPosDone && errorHeadingDone) {
                moveComplete = false;
                delayTime.reset();
                mecanum.offMotors();
                timeFlagg = false;
                pidLinearX.reset();
                pidLinearY.reset();
                pidAngular.reset();

            } else {
                 moveComplete = false;

                mecanum.setDrivePowers(speedPIDX, -speedPIDY, angularPID);
            }
        }
    }
    private static double normalizeAngle(double a) {
        while (a > Math.PI) a -= 2 * Math.PI;
        while (a < -Math.PI) a += 2 * Math.PI;
        return a;
    }
}

