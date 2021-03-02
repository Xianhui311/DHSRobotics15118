package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvPipeline;


@Autonomous (name = "Autonomous 15118", group = "15118")
public class Autonomous15118 extends TeleOp15118 {
    ElapsedTime timer;
    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        super.initialize();
        timer = new ElapsedTime();
        timer.reset();
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"));
        webcam.setPipeline(new AutoPipeline());
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640,480);
            }
        });

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        telemetry.addData("Frame Count", webcam.getFrameCount());
        telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
        telemetry.addData("Total Frame Time ms", webcam.getTotalFrameTimeMs());
        telemetry.addData("Pipeline Time ms", webcam.getPipelineTimeMs());
        telemetry.addData("Overhead Time ms", webcam.getOverheadTimeMs());
        telemetry.addData("Theoretical Max FPS", webcam.getCurrentPipelineMaxFps());
        telemetry.update();

        while(opModeIsActive())
        {

        }
    }
    public void park()
    {
        while(timer.seconds() <= 1.4)
        {
            super.move(0, -1, 0);
        }
    }

    public void shoot()
    {
        intakeRaiser.setPosition(1);
        outtake.setPower(0.9);
    }

    class AutoPipeline extends OpenCvPipeline
    {
        @Override
        public Mat processFrame(Mat input)
        {
            return input;
        }
    }
}