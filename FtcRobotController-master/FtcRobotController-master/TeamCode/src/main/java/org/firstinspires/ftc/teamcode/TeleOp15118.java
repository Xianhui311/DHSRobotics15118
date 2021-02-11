package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@TeleOp(name = "TeleOp 15118", group = "15118")
public class TeleOp15118 extends LinearOpMode
{

    DcMotor fl, fr, bl, br, intake, outtake;
    Servo intakeSweeper;
    
    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();

        waitForStart();
        Runnable checkP1 = new Runnable() {
            @Override
            public void run() {
                checkP1();
            }
        };
        Runnable checkP2 = new Runnable() {
            @Override
            public void run() {

            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(checkP1, 0, 1, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(checkP2, 0, 1, TimeUnit.MILLISECONDS);
        while(opModeIsActive());
    }

    private void temp()
    {
        System.out.println(intakeSweeper.getPosition());
    }

    private void initialize()
    {
        fl = hardwareMap.get(DcMotor.class, "front_left");
        fr = hardwareMap.get(DcMotor.class, "front_right");
        bl = hardwareMap.get(DcMotor.class, "back_left");
        br = hardwareMap.get(DcMotor.class, "back_right");
        
        intake = hardwareMap.get(DcMotor.class, "intake");
        outtake = hardwareMap.get(DcMotor.class, "outtake");

        intakeSweeper = hardwareMap.get(Servo.class, "intakeSweeper");
        System.out.println(intakeSweeper.getPosition());
        telemetry.addLine("Intake Sweeper Position: " + intakeSweeper.getPosition());
        telemetry.update();
        //CHANGE INTAKE SWEEPER RANGE HERE, CAN ALSO CHANGE VARIABLE OF SET POSITION IN INTAKESWEEPER() METHOD
        //maxRange = 50
        //intakeSweeper.scaleRange(0, (maxRange/270))
    }

    private void move(double strafe, double forward, double turn)
    {
        fl.setPower(forward + turn + strafe);
        fr.setPower(forward - turn - strafe);
        bl.setPower(forward + turn - strafe);
        br.setPower((forward - turn + strafe)*-1);
    }
    
    private void intake(boolean forwards, boolean backwards)
    {
        //CHANGE THE POWER OF THE INTAKE HERE
        float power = 0;
        if(forwards)
        {
            //FORWARDS POWER
            power = 1;
        }
        if(backwards)
        {
            //BACKWARDS POWER
            power = -1;
        }
        intake.setPower(power);
    }
    
    private void outtake(float power)
    {
        //REMOVE BELOW LINE TO ADD VARIABLE POWER
        power = 1;
        outtake.setPower(power);
    }
    
    private void intakeSweep(boolean sweep)
    {
        intakeSweeper.setPosition(1);
        intakeSweeper.setPosition(0);
    }

    private void checkP1()
    {
        if(!gamepad1.atRest())
        {
            doTask("move");
        }
        if(gamepad1.right_bumper || gamepad1.left_bumper)
        {
            doTask("intake");
        }
        if(gamepad1.right_trigger != 0)
        {
            doTask("outtake");
        }
        if(gamepad1.x)
        {
            doTask("intakeSweep");
        }
        if(gamepad1.a)
        {
            doTask("test");
        }
    }
    private void checkP2()
    {

    }

    private Thread doTask(String taskName)
    {
        Thread t;
        Runnable r = null;
        if(taskName.equals("move"))
        {
            r = new Runnable() {
                @Override
                public void run() {
                    move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
                }
            };
        }
        if(taskName.equals("intake"))
        {
            r = new Runnable() {
                @Override
                public void run() {
                    intake(gamepad1.right_bumper, gamepad1.left_bumper);
                }
            };
        }
        if(taskName.equals("outtake"))
        {
            r = new Runnable() {
                @Override
                public void run() {
                    outtake(gamepad1.right_trigger);
                }
            };
        }
        if(taskName.equals("intakeSweep"))
        {
            r = new Runnable() {
                @Override
                public void run() {
                    intakeSweep(gamepad1.x);
                }
            };
        }
        if(taskName.equals("test"))
        {
            r = new Runnable() {
                @Override
                public void run() {
                    temp();
                }
            };
        }
        t = new Thread(r);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        return t;
    }
}
