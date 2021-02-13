package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.internal.tfod.Timer;

@Autonomous (name = "Autonomous 15118", group = "15118")
public class AutonomousOp15118 extends TeleOp15118 {
    ElapsedTime timer;
    @Override
    public void runOpMode() throws InterruptedException {
        super.initialize();
        timer.reset();
        while(timer.seconds() <= 1.5)
        {
            super.move(0, 1, 0);
        }
    }
}
