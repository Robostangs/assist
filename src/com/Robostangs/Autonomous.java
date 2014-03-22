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
            DriveTrain.restartEncoders();
	    
	    Shooter.loadCompleted = true;
	    while (timer.get() < 2.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_DISTANCE)) {
	        DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
	    }
	    while (timer.get() < 3.0) {
	        Arm.setPIDShoot();
	        DriveTrain.stop();
	    }
	    if (hot) {
		while (timer.get() < 5.0) {
		    if (Shooter.loadCompleted) {
			Shooter.shooShoot();
		    }
		}
	    } else {
		while (timer.get() < 5.0) {
		    DriveTrain.stop();
		}
		while (timer.get() < 8.0) {
		    if (Shooter.loadCompleted) {
			Shooter.shooShoot();
		    }
		}
	    }
	    
	    Arm.stop();
	    timer.stop();
	    done = true;
        }
        Shooter.manualLoad();
    }
    
    /*
    public static void hotSideTwoBalls() {
	if (!done) {
	    timer.reset();
	    timer.start();
	    DriveTrain.resetEncoders();
	    while (timer.get() < 1.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_FORWARD_DISTANCE)) {
		DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
	    }
	    while (timer.get() < 1.5) {
		    
	    }
	}
    }
    
    public static void nonHotTwoBalls() {
	
    }
    
    public static void twoBallAutonomous() {
	if (hot) {
	    hotSideTwoBalls();
	} else {
	    nonHotTwoBalls();
	}
    }
    */
    
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
	    Shifting.LowGear();
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
     	    Shooter.manualLoad();
	    DriveTrain.resetBooleans();
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
		if (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
		    Arm.setPIDIngest();
		}
                Shooter.manualLoad();
		DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, Constants.AUTON_TURN_ANGLE);
		Ingestor.stop();
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 4.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_SECOND_FORWARD_DISTANCE)) {
		if (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
		    Arm.setPIDIngest();
		}
		Shooter.manualLoad();
		Ingestor.ingest();
		DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 5.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_BACK_DISTANCE)) {
                Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
		DriveTrain.driveStraightEncoder(-Constants.AUTON_2B_DRIVE_POWER);
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 7.0) {
		DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, -Constants.AUTON_TURN_ANGLE);
	    }
	    while (timer.get() < 8.0 && !Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
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