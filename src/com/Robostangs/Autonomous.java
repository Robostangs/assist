package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Thunderbird
 */
public class Autonomous {
    public static Autonomous instance = null;
    public static boolean dunzo = false;
    public static Timer timer;
    
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
	    while (timer.get() < 2.0) {
		DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_DISTANCE);
	    }
	    while (timer.get() < 4.0) {
		Arm.setPIDAutonShot();
		DriveTrain.stop();
	    }
	    while (timer.get() < 6.0) {
		Shooter.shooShoot();
		Arm.stop();
	    }
            while (timer.get() < 10.0) {
                Shooter.load();
            }
	    Shooter.stop();
	    dunzo = true;
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
            timer.start();
            while (timer.get() < 0.5) {
                DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_POWER, 500);
            }
            while (timer.get() < 1.5) {
                DriveTrain.stop();
                Arm.setPIDLongShot();
            }
            while (timer.get() < 3.5) {
                Arm.stop();
                Shooter.shooShoot();
            }
            while (timer.get() < 5.0) {
                Arm.setPIDIngest();
                Shooter.load();
            }
            while (timer.get() < 6.5) {
                Arm.stop();
                Shooter.load();
                Ingestor.ingest();
                DriveTrain.driveStraightDistance(0.5, 2300);
            }
            while (timer.get() < 8.0) {
                DriveTrain.stop();
                Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
                Arm.setPIDShoot();
            }
            while (timer.get() < 10.0) {
                Arm.stop();
                Shooter.shooShoot();
            }
            Shooter.stop();
            dunzo = true;
        }
    }
    
    public static void reset() {
	    dunzo = false;
    }
}