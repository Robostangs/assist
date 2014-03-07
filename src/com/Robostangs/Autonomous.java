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
	    while (timer.get() < 1.0) {
		DriveTrain.driveStraightDistance(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_DISTANCE);
	    }
	    while (timer.get() < 3.0) {
		Arm.setPIDAutonShot();
		DriveTrain.stop();
	    }
	    while (timer.get() < 7.0) {
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
    
    public static void reset() {
	    dunzo = false;
    }
}