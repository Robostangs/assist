package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Laptop
 */
public class Shooter {
    private static CANJaguar shooterJag;
    private static Shooter instance = null;
    private static Encoder shooterEncoder;
    private static Solenoid shooterSolenoidOn, shooterSolenoidOff;
    private static DigitalInput shooterLimit;
    private static Timer shooterTimer;
    private static boolean shooshoo, loadCompleted = false;
    
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

    /**
     * sets the shooter motor speed
     * @param power motor speed
     */
    public static void set(double power) {
	    try {
		shooterJag.setX(power);
	    } catch (CANTimeoutException ex) {
                System.out.println("SHOOTER CAN ERROR");
	    }
    }

    /**
     * pull the ratchet at the constant power
     * stop when the shooter hits the limit switch
     */
    public static void load() {
        if (shooterLimit.get()) {
            set(Constants.SHOOTER_LOAD_POWER);
        } else {
            set(0);
        }
    }
    
    public static void manualLoad() {
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

    /**
     * release the ratchet and spin the ingestor outward after a delay
     */
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
            Ingestor.setSpeed(Constants.INGESTOR_SHOOT_EXGEST_SPEED);
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
	    shoot();
	    shooterTimer.start();
	    loadCompleted = false;
	    if (shooterTimer.get() > Constants.SHOOTER_SHOOT_DELAY_TIME) {
		shooterTimer.reset();
	    }
	}
    }
    
    public static void resetShoot() {
        shooterTimer.reset();
        shooshoo = true;
    }

    public static void solenoidEnable() {
	shooterEncoder.reset();
	shooterSolenoidOff.set(false);
	shooterSolenoidOn.set(true);
    }

    public static void solenoidDisable() {
	shooterSolenoidOff.set(true);
	shooterSolenoidOn.set(false);
    }
    
    /**
     * disable solenoid, reset shoot boolean, and stop the shooter motor
     */
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
