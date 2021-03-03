package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Encoder Config", group = "15118")
public class EncoderConfig extends LinearOpMode
{
    DcMotor fl, fr, bl, br;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();

        waitForStart();

        while (opModeIsActive())
        {
            if(gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0 || gamepad1.right_stick_x != 0)
            {
                move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            }

            if(gamepad1.dpad_up)
            {
                move(0, -.5, 0);
            }
            if(gamepad1.dpad_down)
            {
                move(0, .5, 0);
            }
            if(gamepad1.dpad_left)
            {
                move(-.5, 0, 0);
            }
            if(gamepad1.dpad_right)
            {
                move(.5, 0, 0);
            }

            if((gamepad1.left_stick_x == 0 || gamepad1.left_stick_y == 0 || gamepad1.right_stick_x == 0) || gamepad1.a)
            {
                telemetry.addLine("Encoders Before Stop:\n" +
                        "   Front Right: " + fr.getCurrentPosition() + "\n" +
                        "   Front Left: " + fl.getCurrentPosition() + "\n" +
                        "   Back Right: " + br.getCurrentPosition() + "\n" +
                        "   Back Left: " + bl.getCurrentPosition() + "\n");
                telemetry.update();
                gradientStop();
                //Remove either one above or this one depending on what happens
                telemetry.addLine("Encoders After Stop:\n" +
                        "   Front Right: " + fr.getCurrentPosition() + "\n" +
                        "   Front Left: " + fl.getCurrentPosition() + "\n" +
                        "   Back Right: " + br.getCurrentPosition() + "\n" +
                        "   Back Left: " + bl.getCurrentPosition() + "\n");
            }
            telemetry.addData("Front Right Encoder: ", fr.getCurrentPosition());
            telemetry.addData("Front Left Encoder: ", fl.getCurrentPosition());
            telemetry.addData("Back Right Encoder: ", br.getCurrentPosition());
            telemetry.addData("Back Left Encoder: ", bl.getCurrentPosition());
            telemetry.update();
        }
    }

    private void initialize()
    {
        fl = hardwareMap.get(DcMotor.class, "front_left");
        fr = hardwareMap.get(DcMotor.class, "front_right");
        bl = hardwareMap.get(DcMotor.class, "back_left");
        br = hardwareMap.get(DcMotor.class, "back_right");

        br.setDirection(DcMotorSimple.Direction.REVERSE);

        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void move(double strafe, double forward, double turn)
    {
        forward *= -1;

        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower(forward - turn + strafe);
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
}
