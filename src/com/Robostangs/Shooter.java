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
    private static Solenoid shooterSolenoidOn, shooterSolenoidOff, proxPower;
    private static DigitalInput shooterLimit, proxSwitch;
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
        proxSwitch = new DigitalInput(Constants.SHOOTER_PROX_SWITCH_POS);
        proxPower = new Solenoid(Constants.SHOOTER_PROX_POWER_POS);
        
	proxPower.set(true);
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
        if (proxSwitch.get()) {
            set(Constants.SHOOTER_LOAD_POWER);
        } else {
            set(0);
        }
    }
    
    public static void autoLoad() {
	if (isShooting) {
	    shooterTimer.reset();
	    shooterTimer.start();
	    if (shooterTimer.get() > 0.5) {
		isShooting = false;
		shooterTimer.stop();
		shooshoo = true;
	    }
	}
	if (!loadCompleted) {
	    if (proxSwitch.get()) {
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
	loadCompleted = false;
	isShooting = true;
    }
    
    public static void shooShoot() {
	if (shooshoo) {
	    Shooter.load();
	        if (!proxSwitch.get()) {
	            shooshoo = false;
	        }
	} else {
	    shoot();
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
	return proxSwitch.get();
        //return shooterLimit.get();
    }
    
    /*
    public static void increaseShooterTime() {
        Constants.SHOOTER_SHOOT_DELAY_TIME += 0.001;
    }
    
    public static void decreaseShooterTime() {
        Constants.SHOOTER_SHOOT_DELAY_TIME -= 0.001;
    }*/
}
