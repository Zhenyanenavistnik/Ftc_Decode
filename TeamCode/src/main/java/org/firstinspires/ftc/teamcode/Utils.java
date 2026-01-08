package org.firstinspires.ftc.teamcode;

public class Utils {
    public static double minMaxClip(double value, double min, double max) {
        return Math.min(Math.max(min, value), max);
    }
}
