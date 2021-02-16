package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp 15118", group = "15118")
public class TeleOp15118 extends LinearOpMode
{

    DcMotor fl, fr, bl, br, intake, outtake;
    Servo intakeSweeper, intakeRaiser;

    boolean raised = false;

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
            if(gamepad1.right_bumper || gamepad1.left_bumper)
            {
                outtake();
            } else
            {
                outtake.setPower(0);
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
            if(gamepad1.x)
            {
                if(raised)
                {
                    intakeRaiser.setPosition(-1);
                    raised = false;
                } else
                {
                    intakeRaiser.setPosition(1);
                    raised = true;
                }
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

        br.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void move(double strafe, double forward, double turn)
    {
        while(gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0 || gamepad1.right_stick_x != 0)
        {
            fl.setPower(forward + turn + strafe);
            fr.setPower(forward - turn - strafe);
            bl.setPower(forward + turn - strafe);
            br.setPower(forward - turn + strafe);
        }
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }

    public void intake()
    {
        //CHANGE THE POWER OF THE INTAKE HERE
        if(gamepad1.right_trigger > 0)
        {
            intake.setPower(gamepad1.right_trigger);
        }
        if(gamepad1.left_trigger > 0)
        {
            intake.setPower(gamepad1.left_trigger * -1);
        }
        else
        {
            intake.setPower(0);
        }
    }

    public void outtake()
    {
        outtake.setPower(1);
    }
}
