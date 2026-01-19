package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Utils {
    public static double minMaxClip(double value, double min, double max) {
        return Math.min(Math.max(min, value), max);
    }
    public static int minMaxClip(int value, int min, int max) {
        return Math.min(Math.max(min, value), max);
    }
    public static void timer(LinearOpMode op,int a){
        boolean timerOn = true;
        while (op.opModeIsActive()){
            ElapsedTime timer = new ElapsedTime();
            while(timerOn){
                if(timer.milliseconds() > a){
                    timerOn = false;
                }
            }
        }
    }
}
