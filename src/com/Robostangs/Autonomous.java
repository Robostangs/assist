package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Thunderbird
 */
public class Autonomous {
    public static Autonomous instance = null;
    private static Timer timer;
    private static boolean hot = true, done = false;
    
    Autonomous() {
        timer = new Timer();
    }
    
    public static Autonomous getInstance() {
	if (instance == null) {
	    instance = new Autonomous();
	}
	return instance;
    }
    
    public static void oneBallAutonomous() {
        if (!done) {
            timer.reset();
            timer.start();
            DriveTrain.restartEncoders();
	    
	    Shooter.loadCompleted = true;
	    while (timer.get() < 2.0 && !DriveTrain.driveDistance(Constants.AUTON_DRIVE_DISTANCE)) {
	        DriveTrain.drive(Constants.AUTON_DRIVE_POWER, Constants.AUTON_DRIVE_POWER);
	    }
	    while (timer.get() < 4.0) {
	        Arm.setPIDCustomShot(Constants.AUTON_ONE_BALL_ANGLE);
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
    
    public static void twoBallAutonomous() {
        if (!done) {
	    timer.reset();
            timer.start();
	    
	    Shifting.LowGear();
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
     	    Shooter.manualLoad();
	    DriveTrain.resetBooleans();
	    
	    while (timer.get() < 1.0 && !DriveTrain.driveDistance(Constants.AUTON_2B_DRIVE_FIRST_FORWARD_DISTANCE)) {
                Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
                Arm.setPIDCustomShot(Constants.AUTON_2B_FIRST_SHOT_ANGLE);

                //DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
		DriveTrain.drive(Constants.AUTON_2B_DRIVE_POWER, Constants.AUTON_2B_DRIVE_POWER);
     	    }
	    while (timer.get() < 2.0 /*&& !Arm.isInPosition(Constants.AUTON_2B_FIRST_SHOT_ANGLE)*/) {
		DriveTrain.stop();
		Arm.setPIDCustomShot(Constants.AUTON_2B_FIRST_SHOT_ANGLE);
	    }
	    while (timer.get() < 2.35) {
		if (Shooter.loadCompleted) {
		    Shooter.shooShoot();
		}
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 4.0 && DriveTrain.isTurning(Constants.AUTON_2B_TURN_ANGLE)) {
		if (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
		    Arm.setPIDIngest();
		}
                Shooter.manualLoad();
		DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, Constants.AUTON_2B_TURN_ANGLE);
		Ingestor.stop();
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 5.0 && !DriveTrain.driveDistance(Constants.AUTON_2B_DRIVE_SECOND_FORWARD_DISTANCE)) {
		if (!Arm.isInPosition(Constants.ARM_INGEST_ANGLE)) {
		    Arm.setPIDIngest();
		}
		Shooter.manualLoad();
		Ingestor.ingest();
                
		//DriveTrain.driveStraightEncoder(Constants.AUTON_2B_DRIVE_POWER);
		DriveTrain.drive(Constants.AUTON_2B_DRIVE_POWER, Constants.AUTON_2B_DRIVE_POWER);
	    }
            double initial = timer.get();
            while (timer.get() - initial < 0.5) {
                DriveTrain.stop();
                Ingestor.ingest();
            }
            DriveTrain.resetBooleans();
	    while (timer.get() < 6.5 && !DriveTrain.driveDistance(Constants.AUTON_2B_DRIVE_BACK_DISTANCE)) {
                Ingestor.ingest();
                
		//DriveTrain.driveStraightEncoder(-Constants.AUTON_2B_DRIVE_POWER);
		DriveTrain.drive(-Constants.AUTON_2B_DRIVE_POWER, -Constants.AUTON_2B_DRIVE_POWER);
	    }
            DriveTrain.resetBooleans();
	    while (timer.get() < 7.75 && DriveTrain.isTurning(Constants.AUTON_2B_TURN_BACK_ANGLE)) {
		DriveTrain.turn(Constants.AUTON_2B_DRIVE_POWER, Constants.AUTON_2B_TURN_BACK_ANGLE);
	    }
	    while (timer.get() < 9.0 && !Arm.isInPosition(Constants.ARM_SHOOT_ANGLE)) {
		DriveTrain.stop();
		Arm.setPIDShot();
	    }
	    while (timer.get() < 10.0 && Ingestor.hasBall()) {
		Shooter.shooShoot();
		Arm.stop();
	    }
	    Arm.stop();
            Ingestor.stop();
	    //Shooter.shooShoot();
	    timer.stop();
	    done = true;
        }
	Shifting.HighGear();
        //Shooter.manualLoad();        
    }
    
    public static void reset() {
	DriveTrain.resetBooleans();
        done = false;
    }
}