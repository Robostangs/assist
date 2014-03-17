package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Thunderbird
 */
public class Autonomous {
    public static Autonomous instance = null;
    private static Timer timer;
    private static boolean hot = true, done = false;
    
    private Autonomous() {
	Arm.getInstance();
	DriveTrain.getInstance();
	Shooter.getInstance();
	
        timer = new Timer();
    }
    
    public static Autonomous getInstance() {
	if (instance == null) {
	    instance = new Autonomous();
	}
	return instance;
    }
    
    public static void fallBack() {
        if (!done) {
            timer.reset();
            timer.start();
            DriveTrain.stopEncoders();
            DriveTrain.resetEncoders();
            DriveTrain.startEncoders();
            if (hot) {
                Shooter.loadCompleted = true;
                while (timer.get() < 2.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_DISTANCE)) {
                    DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
                }
                while (timer.get() < 3.0) {
                    Arm.setPIDShoot();
                    DriveTrain.stop();
                }
                while (timer.get() < 5.0) {
                    if (Shooter.loadCompleted) {
                        Shooter.shooShoot();
                    }
                }
                Arm.stop();
                done = true;
            } else {
                Shooter.loadCompleted = true;
                while (timer.get() < 2.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_DISTANCE)) {
                    DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
                }
                while (timer.get() < 3.0) {
                    Arm.setPIDShoot();
                    DriveTrain.stop();
                }
                while (timer.get() < 5.0) {
                    Shooter.manualLoad();
                    Arm.stop();
                    DriveTrain.stop();
                }
                while (timer.get() < 8.0) {
                    if (Shooter.loadCompleted) {
                        Shooter.shooShoot();
                    }
                }
                done = true;
            }
        }
        Shooter.manualLoad();
    }
    
    /**
     * 2 ball autonomous
     * 1. move the arm to the shoot position
     * 2. shoot
     * 3. lower the arm, ingest, winch, and move back
     * 4. keep winching, ingest, and move forward
     * 5. ingest the ball
     * 6. move the arm to the shoot position and check if the robot ingested the ball
     * 7. if the robot ingested, shoot. if not, stop
     */
    public static void twoBallz() {
        if (!done) {
            timer.reset();
            timer.start();
            while (timer.get() < 1.0 && !Arm.isInPosition(Constants.ARM_AUTON_FIRST_SHOT_ANGLE)) {
                Arm.setPIDAutonFirstShot();
            }
            Shooter.loadCompleted = true;
            while (timer.get() < 2.0) {
                Arm.stop();
                if (Shooter.loadCompleted) {
                    Shooter.shooShoot();
                }
            }
            DriveTrain.stopEncoders();
            DriveTrain.resetEncoders();
            DriveTrain.startEncoders();
            while (timer.get() < 4.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_BACK_DISTANCE)) {
                Shooter.manualLoad();
                Ingestor.ingest();
                DriveTrain.drive(-Constants.AUTON_DRIVE_POWER, -Constants.AUTON_DRIVE_POWER);
                while (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
                    Arm.setPIDIngest();
                }
            }
            DriveTrain.stopEncoders();
            DriveTrain.resetEncoders();
            DriveTrain.startEncoders();
            while (timer.get() < 6.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_FORWARD_DISTANCE)) {
                Shooter.manualLoad();
                Ingestor.ingest();
                DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
                while (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
                    Arm.setPIDIngest();
                }
            }
            while (timer.get() < 7.0 && !Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
                DriveTrain.stop();
                Ingestor.stop();
                Arm.setPIDShoot();
            }
            while (timer.get() < 8.0) {
                Arm.stop();
                if (Shooter.loadCompleted && Ingestor.hasBall()) {
                    Shooter.shooShoot();
                }
            }
            done = true;
        }
        Shooter.manualLoad();
    }
    
    public static void reset() {
        DriveTrain.resetEncoders();
        DriveTrain.encoderInit = false;
        done = false;
    }
}