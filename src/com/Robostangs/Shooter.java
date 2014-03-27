package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * @author Thunderbird
 */
public class Shooter {
    private static CANJaguar shooterJag;
    private static Shooter instance = null;
    private static Encoder shooterEncoder;
    private static Solenoid shooterSolenoidOn, shooterSolenoidOff;
    private static DigitalInput shooterLimit;
    private static Timer shooterTimer;
    public static boolean shooshoo, loadCompleted = false, isShooting = false;
    
    private Shooter() {
        try {
            shooterJag = new CANJaguar(Constants.SHOOTER_JAG_POS);
        } catch (CANTimeoutException ex) {
            System.out.println("SHOOTER CAN ERROR");
        }
        
        Ingestor.getInstance();        
        shooterEncoder = new Encoder(Constants.SHOOTER_ENCODER_1, Constants.SHOOTER_ENCODER_2);
        shooterLimit = new DigitalInput(Constants.SHOOTER_LIMIT_POS);
        shooterSolenoidOn = new Solenoid(Constants.SHOOTER_CYCLINDER_IN_POS);
        shooterSolenoidOff = new Solenoid(Constants.SHOOTER_CYCLINDER_OUT_POS);
        shooterTimer = new Timer();
        
	solenoidDisable();
	
	shooterEncoder.reset();
	shooterEncoder.start();
        shooshoo = false;
    }
    
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    public static void set(double power) {
	    try {
		shooterJag.setX(power);
	    } catch (CANTimeoutException ex) {
                System.out.println("SHOOTER CAN ERROR");
	    }
    }

    public static void load() {
        if (shooterLimit.get()) {
            set(Constants.SHOOTER_LOAD_POWER);
        } else {
            set(0);
        }
    }
    
    public static void manualLoad() {
	if (isShooting) {
	    shooterTimer.reset();
	    shooterTimer.start();
	    if (shooterTimer.get() > 0.5) {
		isShooting = false;
		shooterTimer.stop();
	    }
	}
	if (!loadCompleted) {
	    if (shooterLimit.get()) {
		solenoidDisable();
		set(Constants.SHOOTER_LOAD_POWER);
	    } else {
		set(0);
		loadCompleted = true;
	    }
	}
    }

    public static void shoot() {
	solenoidEnable();
	set(0);
	Ingestor.stop();
	
	/*
        if (shooshoo) {
            shooterTimer.start();
            solenoidEnable();
            set(0);
            shooshoo = false;
        }
        
        //Ingestor.stop();
        
        if (shooterTimer.get() > Constants.SHOOTER_SHOOT_DELAY_TIME) {
            Ingestor.setSpeed(Constants.INGESTOR_EXGEST_SPEED);
        } else if (shooterTimer.get() > Constants.SHOOTER_SHOOT_STOP_TIME) {
            shooterTimer.stop();     
        }*/
    }
    
    public static void shooShoot() {
	if (shooshoo) {
	    Shooter.load();
	        if (!shooterLimit.get()) {
	            shooshoo = false;
	        }
	} else {
            isShooting = true;
	    shoot();
	    loadCompleted = false;
	}
    }
    
    public static void resetShoot() {
        shooterTimer.reset();
        shooshoo = true;
    }

    public static void solenoidEnable() {
	shooterSolenoidOff.set(false);
	shooterSolenoidOn.set(true);
    }

    public static void solenoidDisable() {
	shooterSolenoidOff.set(true);
	shooterSolenoidOn.set(false);
    }
    
    public static void stop() {
        solenoidDisable();
        resetShoot();
        set(0);
    }
    
    public static double getEncoderDistance() {
	return shooterEncoder.getRaw();
    }
    
    public static boolean getLimit() {
        return shooterLimit.get();
    }
    
    /*
    public static void increaseShooterTime() {
        Constants.SHOOTER_SHOOT_DELAY_TIME += 0.001;
    }
    
    public static void decreaseShooterTime() {
        Constants.SHOOTER_SHOOT_DELAY_TIME -= 0.001;
    }*/
}
