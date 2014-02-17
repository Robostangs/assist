/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Solenoid;
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
    private static PIDController shooterPID;
    private static DigitalInput shooterLimit;
    
    private Shooter() {
        try {
            shooterJag = new CANJaguar(Constants.SHOOTER_JAG_POS);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            System.out.println("Shooter CAN error");
        }
        
        shooterEncoder = new Encoder(Constants.SHOOTER_ENCODER_1, Constants.SHOOTER_ENCODER_2);
        shooterLimit = new DigitalInput(Constants.SHOOTER_LIMIT_POS);
        shooterSolenoidOn = new Solenoid(Constants.SHOOTER_CYCLINDER_IN_POS);
        shooterSolenoidOff = new Solenoid(Constants.SHOOTER_CYCLINDER_OUT_POS);
	shooterEncoder.reset();
	shooterEncoder.start();
	shooterPID = new PIDController(Constants.SHOOTER_KP, Constants.SHOOTER_KI, Constants.SHOOTER_KD, shooterEncoder, shooterJag);
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
		    ex.printStackTrace();
	    }
    }

    public static void load() {
    	if(shooterEncoder.getRaw() < 8400) {
	    set(0.75);
        } else {
	    set(0);    
        } 
    }

    public static void shoot() {
	if(shooterPID.isEnable()){
	    shooterPID.disable();
	}
	shooterSolenoidOn.set(Constants.SHOOTER_AIR_SHOOT);
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
    
    public static double getEncoderDistance() {
	return shooterEncoder.getRaw();
    }
    
    public static boolean getLimit() {
        return shooterLimit.get();
    }
}
