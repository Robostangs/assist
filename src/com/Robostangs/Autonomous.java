package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Thunderbird
 */
public class Autonomous {
    public static Autonomous instance = null;
    public static boolean dunzo = false;
    private static Timer timer;
    private static boolean hot = true;
    
    private Autonomous() {
        timer = new Timer();
    }
    
    public static Autonomous getInstance() {
	if (instance == null) {
	    instance = new Autonomous();
	}
	return instance;
    }
    
    public static void fallBack() {
	if (!dunzo) {
	    timer.reset();
            timer.start();
            DriveTrain.resetBooleans();
            Shooter.manualLoad();
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
	    while (timer.get() < 2.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_DISTANCE)) {
		    DriveTrain.driveStraightEncoder(Constants.AUTON_DRIVE_POWER);
            }
            while (timer.get() < 3.0) {
		    Arm.setPIDShoot();
		    DriveTrain.stop();
            }
	    if (hot) {
		while (timer.get() < 5.0 && Ingestor.hasBall()) {
		    Shooter.shooShoot();
		}
	    } else {
		while (timer.get() > 5.0 && timer.get() < 8.0 && Ingestor.hasBall()) {
		    Shooter.shooShoot();
		}
	    }
            Arm.stop();
            Ingestor.stop();
            dunzo = true;
	}
        Shooter.manualLoad();
    }
    
    /**
     * 2 ball autonomous
     */
    public static void twoBallz() {
        if (!dunzo) {
	    timer.reset();
            timer.start();

	    DriveTrain.resetBooleans();
            Shifting.LowGear();
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
     	    Shooter.manualLoad();
            
	    while (timer.get() < 0.75 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_FIRST_FORWARD_DISTANCE)) {
		DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
	    }
	    while (timer.get() < 1.50 && !Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
		DriveTrain.stop();
		Arm.setPIDShoot();
	    }
	    while (timer.get() < 2.0 && Ingestor.hasBall()) {
		if (Shooter.loadCompleted) {
		    Shooter.shooShoot();
		}
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 3.0) {
                Arm.setPIDIngest();
                Shooter.manualLoad();
		DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, Constants.AUTON_TURN_ANGLE);
		Ingestor.stop();
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 4.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_SECOND_FORWARD_DISTANCE)) {
		Ingestor.ingest();
		DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
	    }
            while (timer.get() < 4.5) {
                DriveTrain.stop();
                Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
                Arm.setPIDShoot();
            }
            DriveTrain.resetBooleans();
	    while (timer.get() < 5.5 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_BACK_DISTANCE)) {
		DriveTrain.driveStraightEncoder(-Constants.AUTON_2B_DRIVE_POWER);
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 6.0) {
		DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, -Constants.AUTON_TURN_ANGLE);
	    }
	    while (timer.get() < 7.0) {
		DriveTrain.stop();
		Arm.setPIDShoot();
	    }
	    while (timer.get() < 10.0 && Ingestor.hasBall()) {
		if (Shooter.loadCompleted) {
		    Shooter.shooShoot();
		}
	    }
	    Arm.stop();
            Ingestor.stop();
	    dunzo = true;
        }
        Shooter.manualLoad();
    }
    
    /**
     * put the ball behind the robot
     */
    public static void twoHotChicks() {
        if (!dunzo) {
            timer.reset();
            timer.start();
            
            DriveTrain.resetBooleans();
            Shifting.LowGear();
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
            Shooter.manualLoad();

            while (timer.get() < 0.75 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_FIRST_FORWARD_DISTANCE)) {
                if (!Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
                    Arm.setPIDShoot();
                }
                DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
            }
            while (timer.get() < 1.5) {
                if (hot) {
                    DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, 60);
                } else {
                    DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, -60);
                }
            }
            while (timer.get() < 2.0 && Ingestor.hasBall()) {
                DriveTrain.stop();
                if (Shooter.loadCompleted) {
                    Shooter.shooShoot();
                }
            }
            DriveTrain.resetBooleans();
            while (timer.get() < 3.5) {
                Shooter.manualLoad();
                if (hot) {
                    DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, 120);
                } else {
                    DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, -120);
                }
                if (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
                    Arm.setPIDIngest();
                }
            }
            while (timer.get() < 4.5 && !DriveTrain.driveDistance(4800)) {
                DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
                Ingestor.ingest();
            }
            while (timer.get() < 5.0) {
                DriveTrain.stop();
            }
            DriveTrain.resetBooleans();
            while (timer.get() < 6.0 && !DriveTrain.driveDistance(-4800)) {
                DriveTrain.driveStraightEncoder(-Constants.AUTON_2B_DRIVE_POWER);
                Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
                if (!Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
                    Arm.setPIDShoot();
                }
            }
            while (timer.get() < 7.0) {
                if (hot) {
                    DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, 120);
                } else {
                    DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, -120);
                }
            }
            while (timer.get() < 7.5 && !Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
                DriveTrain.stop();
                Arm.setPIDShoot();
            }
            while (timer.get() < 8.5 && Ingestor.hasBall()) {
                if (Shooter.loadCompleted) {
                    Shooter.shooShoot();
                }
            }
            dunzo = true;
        }
        DriveTrain.stop();
        Arm.stop();
        Ingestor.stop();
        Shooter.manualLoad();
    }
    
    public static void reset() {
	    DriveTrain.resetBooleans();
	    dunzo = false;
    }
}