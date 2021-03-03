package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp 15118", group = "15118")
public class TeleOp15118 extends LinearOpMode
{
    DcMotor fl, fr, bl, br, intake, outtake, wa;
    Servo intakeSweeper, intakeRaiser, clasp;

    boolean clasped = false;
    boolean boxRaised = false;
    boolean clicked = false;

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();

        waitForStart();
        while(opModeIsActive())
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
            if(gamepad1.b)
            {
                if(outtake.getPower() == 0.9)
                {
                    outtake.setPower(0);
                } else
                {
                    outtake(true);
                }
            }
            if(gamepad1.y)
            {
                if(outtake.getPower() == 0.75)
                {
                    outtake.setPower(0);
                } else
                {
                    outtake(false);
                }
            }
            if(gamepad1.left_bumper)
            {
                wa.setPower(0.15);
            }
            if(gamepad1.right_bumper)
            {
                wa.setPower(-0.15);
            }
            if(gamepad1.start)
            {
                wa.setPower(0);
            }
            if(gamepad1.back)
            {
                if(clasped)
                {
                    clasp.setPosition(-1);
                } else
                {
                    clasp.setPosition(1);
                }
                sleep(100);
                clasped = !clasped;
            }
            if(gamepad1.right_trigger != 0 || gamepad1.left_trigger != 0)
            {
                intake();
            }
            if(gamepad1.a)
            {
                intakeSweeper.setPosition(-1);
                sleep(250);
                intakeSweeper.setPosition(0.5);
            }
            if(gamepad1.x && !boxRaised && !clicked)
            {
                intakeRaiser.setPosition(1);
                clicked = true;
                boxRaised = true;
            }
            if(gamepad1.x && boxRaised && !clicked)
            {
                intakeRaiser.setPosition(-1);
                boxRaised = false;
                clicked = true;
            }
            if (!gamepad1.x)
            {
                clicked = false;
            }
            if(gamepad1.left_stick_x == 0 || gamepad1.left_stick_y == 0 || gamepad1.right_stick_x == 0)
            {
                move(0, 0,0);
            }
        }
    }

    public void initialize()
    {

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
    }

    public void move(double strafe, double forward, double turn)
    {
        forward *= -1;

        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower(forward - turn + strafe);
    }


    public void intake()
    {
        //CHANGE THE POWER OF THE INTAKE HERE
        if(gamepad1.right_trigger > 0)
        {
            intake.setPower(1);
        }
        if(gamepad1.left_trigger > 0)
        {
            intake.setPower(-1);
        }
        else
        {
            intake.setPower(0);
        }
    }

    public void outtake(boolean power)
    {
        if(power)
        {
            outtake.setPower(0.9);
        } else
        {
            outtake.setPower(0.75);
        }
    }
}
