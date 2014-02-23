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
    private static int currentPID = 0;

    private Arm(){
        apot = new Potentiometer(Constants.ARM_POT_POS);
        motorOutput = new ArmMotors();
        pid = new PIDController(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd, apot, motorOutput);
        
        pid.setInputRange(Constants.ARM_POT_IN_MIN, Constants.ARM_POT_IN_MAX);
        pid.setOutputRange(Constants.ARM_POT_OUT_MIN, Constants.ARM_POT_OUT_MAX);
        pid.disable();
    }

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }
    
    /**
     * sets arm speed
     * @param speed arm speed
     */
    public static void setArmSpeed(double speed) {
        if (pid.isEnable()) {
            pid.disable();
        }
        
        if (speed > 0) {
            if (getArmAngle() > Constants.ARM_MAX_ANGLE) {
                ArmMotors.set(speed);
            } else {
                ArmMotors.stop();
            }
        } else if (speed < 0) {
           if (getArmAngle() < Constants.ARM_MIN_ANGLE) {
               ArmMotors.set(speed);
           } else {
               ArmMotors.stop();
           }
        } else {
            ArmMotors.stop();
        }
    }
        
    public static void setPIDPosition(double pos) {
        pid.setSetpoint(pos);
        pid.enable();
    }

    /**
     * Sets arm position to ingest
     */
    public static void setPIDIngest() {
        if (currentPID != 1) {
            pid.reset();
            pid.setPID(Constants.ARM_INGEST_AKp, Constants.ARM_INGEST_AKi, Constants.ARM_INGEST_AKd);
            currentPID = 1;
        }
        
        pid.setSetpoint(Constants.ARM_INGEST);
        pid.enable();
    }
    
    /**
     * Sets arm position to load
     */
    public static void setPIDLoad() {
        if (currentPID != 2) {
            pid.reset();
            pid.setPID(Constants.ARM_LOAD_AKp, Constants.ARM_LOAD_AKi, Constants.ARM_LOAD_AKd);
            currentPID = 2;
        }
        
        pid.setSetpoint(Constants.ARM_LOAD);
        pid.enable();
    }

    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDStaticShot() {
        if (currentPID != 3) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 3;
        }
        
        pid.setSetpoint(Constants.ARM_MAX_STATIC_SHOT);
        pid.enable();
    }    
    
    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDFenderShot() {
        if (currentPID != 4) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 4;
        }
        
        pid.setSetpoint(Constants.ARM_FENDER_SHOT);
        pid.enable();
    }
    
    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDCloseShot() {
        if (currentPID != 5) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 5;
        }
        
        pid.setSetpoint(Constants.ARM_CLOSE_SHOT);
        pid.enable();
    }
    
    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDTrussPass() {
        if (currentPID != 6) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 6;
        }
        
        pid.setSetpoint(Constants.ARM_TRUSS_PASS);
        pid.enable();
    }
    
    public static void setPIDConstants(double p, double i, double d, double setpoint) {
	pid.reset();
	pid.setPID(p, i, d);
	pid.setSetpoint(setpoint);
    }
        
    /**
     * sets range for pid
     * @param min sets minimum
     * @param max sets maximum
     */
    public static void setOutRange(float min, float max) {
        pid.setOutputRange(min, max);
    }

   /**
    * @return returns arm angle
    */
    public static double getArmAngle() {
        return apot.getAverageValue();
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
        
    /**
    Stop PID and arm
    */
    public static void stop() {
        if (pid.isEnable()) {
            pid.disable();
        }
        ArmMotors.stop();
        currentPID = 1;
    }
}
