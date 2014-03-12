package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Thunderbird
 */
public class Autonomous {
    public static Autonomous instance = null;
    private static Timer timer;
    private static boolean hot = true;
    
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
	timer.start();

	if (hot) {
	    while (timer.get() < 2.0 && !DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_DISTANCE)) {
		DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
	    }
	    while (timer.get() < 3.0) {
		Arm.setPIDShoot();
		DriveTrain.stop();
	    }
	    while (timer.get() < 5.0) {
		Shooter.shooShoot();
		Arm.stop();
	    }
	    Shooter.manualLoad();
	} else {
	    while (timer.get() < 2.0 && !DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_DISTANCE)) {
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
	    Shooter.manualLoad();
	}
    }
    
    /**
     * 2 ball autonomous
     * 1. drive straight to push the ball on the ground and to line up at the line
     * 2. set the arm to the long shot position
     * 3. shoot
     * 4. winch while lowing the arm to ingest position
     * 5. set the arm to the shoot position
     * 6. shoot
     * 7. stop
     */
    public static void twoBallz() {
	timer.reset();
	timer.start();
	DriveTrain.resetEncoders();
	while (timer.get() < 1.0 && !Arm.isInPosition(Constants.ARM_AUTON_FIRST_SHOT_ANGLE)) {
	    Arm.setPIDAutonFirstShot();
	    Shooter.loadCompleted = true;
	}
	while (timer.get() < 2.0) {
	    Arm.stop();
	    if (Shooter.loadCompleted) {
	        Shooter.shooShoot();
	    } else {
		Shooter.manualLoad();
	    }
	}
	while (timer.get() < 4.0 && !DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_BACK_DISTANCE)) {
	    Shooter.manualLoad();
	    Ingestor.ingest();
	    DriveTrain.drive(-Constants.AUTON_DRIVE_POWER, -Constants.AUTON_DRIVE_POWER);
	    while (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
	        Arm.setPIDIngest();
	    }
	}
	DriveTrain.resetEncoders();	
	while (timer.get() < 6.0 && !DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_FORWARD_DISTANCE)) {
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
	    if (Shooter.loadCompleted) {
	        Shooter.shooShoot();
	    }
	}
	Shooter.manualLoad();
    }
}