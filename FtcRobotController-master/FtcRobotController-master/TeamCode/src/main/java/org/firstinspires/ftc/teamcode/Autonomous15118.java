package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.vision.UGContourRingPipeline;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvPipeline;



@Autonomous (name = "Autonomous 15118", group = "15118")
public class Autonomous15118 extends LinearOpMode
{
    //MOTORS
    DcMotor fl, fr, bl, br, intake, outtake, wa;

    //SERVOS
    Servo intakeSweeper, intakeRaiser, clasp;

    //CAMERA VALUES
    private static final int CAMERA_WIDTH = 320;
    private static final int CAMERA_HEIGHT = 240;
    private static final int HORIZON = 100;
    private static final String WEBCAM_NAME = "webcam";

    UGContourRingPipeline pipeline;
    OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, WEBCAM_NAME), cameraMonitorViewId);

        camera.setPipeline(pipeline = new UGContourRingPipeline(telemetry, true));

        UGContourRingPipeline.Config.setCAMERA_WIDTH(CAMERA_WIDTH);
        UGContourRingPipeline.Config.setHORIZON(HORIZON);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT);
                }
            }
        );

        waitForStart();

        clasp.setPosition(1);
        sleep(100);

        //To be replaced later
        while (opModeIsActive())
        {
            telemetry.addData("[Frame Count] >>", camera.getFrameCount());
            telemetry.addData("[FPS] >>", String.format("%.2f", camera.getFps()));
            telemetry.addData("[Total Frame Time] >>", camera.getTotalFrameTimeMs());
            telemetry.addData("[Pipeline Time] >>", camera.getPipelineTimeMs());
            telemetry.addData("[Overhead Time] >>", camera.getOverheadTimeMs());
            telemetry.addData("[Theoretical Max FPS] >>", camera.getCurrentPipelineMaxFps());
            telemetry.addData("[Rings] >>", pipeline.getHeight());
            telemetry.update();
        }
    }

    public void initialize() {
        fl = hardwareMap.get(DcMotor.class, "front_left");
        fr = hardwareMap.get(DcMotor.class, "front_right");
        bl = hardwareMap.get(DcMotor.class, "back_left");
        br = hardwareMap.get(DcMotor.class, "back_right");

        intake = hardwareMap.get(DcMotor.class, "intake");
        outtake = hardwareMap.get(DcMotor.class, "outtake");

        intakeRaiser = hardwareMap.get(Servo.class, "intake_raiser");
        intakeSweeper = hardwareMap.get(Servo.class, "intake_sweeper");

        wa = hardwareMap.get(DcMotor.class, "wobble_arm");
        clasp = hardwareMap.get(Servo.class, "clasp");

        br.setDirection(DcMotorSimple.Direction.REVERSE);
        outtake.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void gradientStop()
    {
        for(int i = 0; i < 10; i++)
        {
            fl.setPower(fl.getPower()/2);
            fr.setPower(fr.getPower()/2);
            bl.setPower(bl.getPower()/2);
            br.setPower(br.getPower()/2);
        }
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    private void goToPosition(int flPos, int frPos, int blPos, int brPos)
    {
        fl.setTargetPosition(flPos);
        fr.setTargetPosition(frPos);
        bl.setTargetPosition(blPos);
        br.setTargetPosition(brPos);
        while (opModeIsActive() && fl.getCurrentPosition() < fl.getTargetPosition() && fr.getCurrentPosition() < fr.getTargetPosition() && bl.getCurrentPosition() < bl.getTargetPosition() && br.getCurrentPosition() < br.getTargetPosition())
        {
            telemetry.addData("[Front Left] Distance From Target >>", fl.getTargetPosition() - fl.getCurrentPosition());
            telemetry.addData("[Front Right] Distance From Target >>", fr.getTargetPosition() - fr.getCurrentPosition());
            telemetry.addData("[Back Left] Distance From Target >>", bl.getTargetPosition() - bl.getCurrentPosition());
            telemetry.addData("[Back Right] Distance From Target >>", br.getTargetPosition() - br.getCurrentPosition());
        }
    }
}

