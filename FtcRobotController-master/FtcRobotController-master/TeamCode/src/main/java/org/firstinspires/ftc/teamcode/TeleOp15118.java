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
                move(0, -(2/3), 0);
            }
            if(gamepad1.dpad_down)
            {
                move(0, (2/3), 0);
            }
            if(gamepad1.dpad_left)
            {
                move(-(2/3), 0, 0);
            }
            if(gamepad1.dpad_right)
            {
                move((2/3), 0, 0);
            }
            if((gamepad1.right_bumper || gamepad1.left_bumper) && outtake.getPower() == 0)
            {
                if(gamepad1.right_bumper)
                {
                    outtake(true);
                } else
                {
                    outtake(false);
                }
            } else if((gamepad1.right_bumper || gamepad1.left_bumper) && outtake.getPower() != 0)
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
            if(gamepad1.x && !raised && clicked == false)
            {
                intakeRaiser.setPosition(1);
                clicked = true;
                raised = true;
            }
            if(gamepad1.x && raised && clicked == false)
            {
                intakeRaiser.setPosition(-1);
                raised = false;
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

        br.setDirection(DcMotorSimple.Direction.REVERSE);
        outtake.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void move(double strafe, double forward, double turn)
    {
        forward *= -1;
        strafe *= 0.75;
        forward *= 0.75;
        turn *= 0.75;

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

    public void outtake(boolean power)
    {
        if(power)
        {
            outtake.setPower(1);
        } else
        {
            outtake.setPower(0.75);
        }
    }

    public void lift()
    {

    }
}
