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
	if (!dunzo) {
	    timer.start();
	    
	    if (hot) {
		while (timer.get() < 2.0 && !DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_DISTANCE)) {
		    DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
		}
		while (timer.get() < 3.0) {
		    Arm.setPIDAutonShot();
		    DriveTrain.stop();
		}
		while (timer.get() < 5.0) {
		    Shooter.shooShoot();
		    Arm.stop();
		}
		Shooter.manualLoad();
		dunzo = true;
	    } else {
		while (timer.get() < 2.0 && !DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_DISTANCE)) {
		    DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
		}
		while (timer.get() < 3.0) {
		    Arm.setPIDAutonShot();
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
		dunzo = true;
	    }
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
        if (!dunzo) {
	    timer.reset();
            timer.start();
	    while (timer.get() < 1.5 && !Arm.isInPosition(Constants.ARM_LONG_SHOT_ANGLE)) {
		Arm.setPIDLongShot();
		Shooter.manualLoad();
	    }
            while (timer.get() < 2.5) {
		Arm.stop();
		if (Shooter.loadCompleted) {
	            Shooter.shooShoot();
		}
            }
	    DriveTrain.resetEncoders();
	    while (timer.get() < 3.5 && !DriveTrain.driveStraightDistance(-500)) {
		DriveTrain.drive(-0.3, -0.3);
		Shooter.manualLoad();
	    }
	    while (timer.get() < 5.0 && !Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
		DriveTrain.stop();
		Ingestor.ingest();
		Arm.setPIDIngest();
		Shooter.manualLoad();
	    }
	    DriveTrain.resetEncoders();
            while (timer.get() < 6.0 && !DriveTrain.driveStraightDistance(5000)) {
                Arm.stop();
		DriveTrain.drive(0.4, 0.4);
		Ingestor.ingest();
            }
            while (timer.get() < 7.0 && !Arm.isInPosition(Constants.ARM_AUTON_SHOT_ANGLE)) {
		DriveTrain.stop();
		Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
                Arm.setPIDAutonShot();
            }
            while (timer.get() < 8.0) {
                Arm.stop();
                Ingestor.stop();
		if (Shooter.loadCompleted) {
		    Shooter.shooShoot();
		}
            }
            dunzo = true;
        }
        Shooter.manualLoad();
    }
    
    public static void reset() {
	    dunzo = false;
    }
}