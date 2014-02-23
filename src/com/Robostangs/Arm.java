/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.PIDController;

/**
 *
 * @author Laptop
 */
public class Arm {
    
    private static Arm instance = null;
    public static Potentiometer apot;
    private static ArmMotors motorOutput;
    public static PIDController pid;

    public Arm(){
        apot = new Potentiometer(Constants.ARM_POT_POS);
        motorOutput = new ArmMotors();
        pid = new PIDController(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd, apot, motorOutput);
        pid.setInputRange(Constants.ARM_POT_MIN, Constants.ARM_POT_MAX);
        pid.setOutputRange(-1, 1);  // a constant
        pid.disable();
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
    
    /**
    sets range for pid
    @param a sets minimum
    @param b sets maximum
    */

    public static void setOutRange(float a, float b) {
        pid.setOutputRange(a, b);
    }

   /**
    @return returns arm angle
   */
    public static double getArmAngle() {
        return apot.getAverageValue();
    }
    
    /**
    sets arm speed
    @param speed arm speed
    */
    public static void setArmSpeed(double speed) {
        if (pid.isEnable()) {
            pid.disable();
        }
        
        if (speed > 0) {
            if (getArmAngle() > Constants.ARM_MAX_ANGLE) {
                ArmMotors.set(speed * Constants.ARM_POWER_COEFFICIENT);
            } else {
                ArmMotors.set(0.0);
            }
        } else if (speed < 0) {
           if (getArmAngle() < Constants.ARM_MIN_ANGLE) {
               ArmMotors.set(speed * Constants.ARM_POWER_COEFFICIENT);
           } else {
               ArmMotors.set(0.0);
           }
        } else {
            ArmMotors.set(0.0);
        }
    }
    
    /**
    Sets arm positions to top, middle, load, or ingest
    */
    public static void setPIDShoot() {
        pid.reset();
        pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
    }
    
    /**
    Sets arm position to load
    */
    public static void setPidLoad() {
        pid.reset();
        pid.setPID(Constants.ARM_LOAD_AKp, Constants.ARM_LOAD_AKi, Constants.ARM_LOAD_AKd);
    }

    /**
    Sets arm position to ingest
    */
    public static void setPidIngest() {
        pid.reset();
        pid.setPID(Constants.ARM_INGEST_AKp, Constants.ARM_INGEST_AKi, Constants.ARM_INGEST_AKd);
    }
    
    /**
    Stop PID and arm
    */
    public static void stop() {
        //pid.disable();
        motorOutput.set(0.0);
    }
        
    /**
    @return returns potentiometer voltage
    */
    public static double getPotVoltage() {
        return apot.getVoltage();
    }
    
    /**
    @return returns true if PID is enabled
    */
    public static boolean pidEnabled() {
        return pid.isEnable();
    }

    public static void setArmLimited(double speed) {
        if (speed > 0) {
            if (getArmAngle() < Constants.ARM_MAX_ANGLE) {
                ArmMotors.set(speed);
            } else {
                ArmMotors.set(0.0);
            }
        } else if (speed < 0) {
           if (getArmAngle() > Constants.ARM_MIN_ANGLE) {
               ArmMotors.set(speed);
           } else {
               ArmMotors.set(0.0);
           }
        } else {
            ArmMotors.set(0.0);
        }
    }

    public static void disablePID() {
	pid.disable();
    }
    
    public static void setPIDConstants(double p, double i, double d, double setpoint) {
	pid.reset();
	pid.setPID(p, i, d);
	pid.setSetpoint(setpoint);
    }
    
    public static void setPIDPosition(double pos) {
        pid.setSetpoint(pos);
        pid.enable();
    }
    
    public static void enablePID() {
	pid.enable();
    }
}
