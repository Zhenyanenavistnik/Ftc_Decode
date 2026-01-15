package org.firstinspires.ftc.teamcode;

public class Utils {
    public static double minMaxClip(double value, double min, double max) {
        return Math.min(Math.max(min, value), max);
    }
    public static int minMaxClip(int value, int min, int max) {
        return Math.min(Math.max(min, value), max);
    }
}
