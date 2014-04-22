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
    public static boolean shooshoo = true, loadCompleted = false, isShooting = false, encoderInit = false, loading = false;
    
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
	proxSwitch = new DigitalInput(Constants.SHOOTER_PROX_SWITCH_POS);
	proxPower = new Solenoid(Constants.SHOOTER_PROX_POWER_POS);
        shooterTimer = new Timer();
        
	solenoidDisable();
	
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

    public static void set(double power) {
	    try {
		shooterJag.setX(power);
	    } catch (CANTimeoutException ex) {
                System.out.println("SHOOTER CAN ERROR");
	    }
    }

    public static void load() {
        if (proxSwitch.get()) {
            set(Constants.SHOOTER_LOAD_POWER);
        } else {
            set(0);
        }
    }
    
    public static void autoLoad() {
	if (isShooting) {
	    if (shooterTimer.get() > 0.5) {
		shooshoo = true;
		isShooting = false;
		shooterTimer.stop();
                shooterTimer.reset();
	    }
	} else {
            if (!loadCompleted) {
                if (proxSwitch.get()) {
                    solenoidDisable();
                    set(Constants.SHOOTER_LOAD_POWER);
                    loading = true;
                } else {
                    set(0);
                    loading = false;
                    loadCompleted = true;
                    shooshoo = false;
                }
            }
        }
    }
    
    public static void loadAutonomous() {
	if (isShooting) {
	    if (shooterTimer.get() > 0.5) {
		shooshoo = true;
		isShooting = false;
		shooterTimer.stop();
                shooterTimer.reset();
	    }
	} else {
            if (!loadCompleted) {
                if (proxSwitch.get()) {
                    solenoidDisable();
                    set(0.75);
                } else {
                    set(0);
                    loadCompleted = true;
                    shooshoo = false;
                }
            }
        }
    }

    //PROTOTYPE - DON'T USE THIS
    public static void loadEncoder() {
        if (getCurrent() < 0.1) {
            set(0.2);
        } else {
            if (!encoderInit) {
                shooterEncoder.reset();
                encoderInit = true;
            }
            if (!loadCompleted) {
                if (shooterEncoder.getRaw() < 500) {
                    set(Constants.SHOOTER_LOAD_POWER);
                } else if (shooterEncoder.getRaw() < 700) {
                    set(0.75);
                } else {
                    set(0);
                }
            }
        }
    }
    
    public static void shoot() {
	solenoidEnable();
	set(0);
	Ingestor.stop();
	loadCompleted = false;
	isShooting = true;
        shooterTimer.start();
	
	/*
        if (shooshoo) {
            shooterTimer.start();
            solenoidEnable();
            set(0);
            shooshoo = false;
        }
        
        if (shooterTimer.get() > Constants.SHOOTER_SHOOT_DELAY_TIME) {
            Ingestor.setSpeed(Constants.INGESTOR_EXGEST_SPEED);
        } else if (shooterTimer.get() > Constants.SHOOTER_SHOOT_STOP_TIME) {
            shooterTimer.stop();     
        }*/
    }
    
    public static void shooShoot() {
	if (shooshoo) {
	    autoLoad();
	} else {
	    shoot();
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
	return proxSwitch.get();
        //return shooterLimit.get();
    }
    
    public static double getCurrent() {
        try {
            return shooterJag.getOutputCurrent();
        } catch (CANTimeoutException ex) {
            return -1;
        }
    }
    
    /*
    public static void increaseShooterTime() {
        Constants.SHOOTER_SHOOT_DELAY_TIME += 0.001;
    }
    
    public static void decreaseShooterTime() {
        Constants.SHOOTER_SHOOT_DELAY_TIME -= 0.001;
    }*/
}
