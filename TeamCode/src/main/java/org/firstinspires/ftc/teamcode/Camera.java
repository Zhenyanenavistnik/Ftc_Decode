package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

public class Camera {
    VisionPortal visionPortal;
    AprilTagProcessor aprilTagProcessor;
    Detected[] motif;
    int lastId;
    OpMode op;
    public Camera(OpMode op){
        this.op = op;
    }
    public void init(){
        motif = new Detected[3];
        aprilTagProcessor = new AprilTagProcessor.Builder().build();
        visionPortal = new VisionPortal.Builder().setCamera(op.hardwareMap.get(WebcamName.class,"webcamName")).addProcessor(aprilTagProcessor).build();
    }
    public void update(){
        AprilTagDetection best = pickBest(aprilTagProcessor.getDetections());
        if(best == null){
            return;
        }
        lastId = best.id;
        motif = idToMotif(best.id);

    }
    private AprilTagDetection pickBest(List<AprilTagDetection> detections) {
        if (detections == null || detections.isEmpty()) return null;

        AprilTagDetection best = null;
        double bestScore = -1;

        for (AprilTagDetection d : detections) {
            if (d.ftcPose != null) {
                double score = 1.0 / Math.max(0.001, d.ftcPose.range); // ближе = лучше
                if (score > bestScore) {
                    bestScore = score;
                    best = d;
                }
            } else {
                if (best == null) best = d;
            }
        }
        return best;
    }
    private Detected[] idToMotif(int id) {
        if (id == 21) return Motif.GPP;
        if (id == 22) return Motif.PGP;
        if (id == 23) return Motif.PPG;
        return Motif.UNKNOWN;
    }
}
