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
    public static int currentPID = 0;
    public static double pidDiff = 0;

    private Arm(){
        apot = new Potentiometer(Constants.ARM_POT_POS);
        motorOutput = new ArmMotors();
        pid = new PIDController(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd, apot, motorOutput);
        
        pid.setInputRange(Constants.ARM_POT_IN_MIN, Constants.ARM_POT_IN_MAX);
        pid.setOutputRange(Constants.ARM_POT_OUT_MIN, Constants.ARM_POT_OUT_MAX);
	pid.setAbsoluteTolerance(0.0);
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
     * @param power arm speed
     */
    public static void setArmSpeed(double power) {
        if (pid.isEnable()) {
            pid.disable();
        }
        
        if (power > 0) {
            if (getArmAngle() > (Constants.ARM_MAX_ANGLE - 40) && getArmAngle() < Constants.ARM_MAX_ANGLE) {
                ArmMotors.set(power * 0.3);
            } else if (getArmAngle() < Constants.ARM_MAX_ANGLE) {
		ArmMotors.set(power);
	    } else {
                ArmMotors.stop();
            }
        } else if (power < 0) {
           if (getArmAngle() > Constants.ARM_MIN_ANGLE) {
               ArmMotors.set(power);
           } else {
               ArmMotors.stop();
           }
        } else {
            ArmMotors.stop();
        }
    }
    
    public static void setArmCoarseAdjustment(double power) {
	setArmSpeed(Constants.ARM_FAST_SPEED * power);
    }
    
    public static void setArmFineAdjustment(double power) {
	setArmSpeed(Constants.ARM_SLOW_SPEED * power);
    }
        
    public static void setPIDPosition(double pos) {
        pid.setSetpoint(pos);
        pid.enable();
    }
    
    /**
     * DON'T TOUCH THIS
     */
    public static void setPIDShoot() {
	pid.setSetpoint(Constants.ARM_SHOOT_ANGLE);
	pidDiff = pid.getError();
	
	if (pidDiff < Constants.ARM_SHOOT_UP_TOLERANCE || pidDiff > Constants.ARM_SHOOT_DOWN_TOLERANCE) {
	    if (currentPID != 9) {
		pid.reset();
		pid.setPID(Constants.ARM_SHOOT_FIRST_P, Constants.ARM_SHOOT_FIRST_I, Constants.ARM_SHOOT_FIRST_D);
		currentPID = 9;
	    }
	} else {
	    if (currentPID != 10) {
		pid.reset();
		pid.setPID(Constants.ARM_SHOOT_SECOND_P, Constants.ARM_SHOOT_SECOND_I, Constants.ARM_SHOOT_SECOND_D);
		currentPID = 10;
	    }
	}
	pid.setSetpoint(Constants.ARM_SHOOT_ANGLE);
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
    public static void setPIDHumanLoad() {
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
    public static void setPIDShooShot() {
        if (currentPID != 3) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
	    currentPID = 3;
        }        
        pid.setSetpoint(Constants.ARM_SHOO_SHOT);
        pid.enable();
    }    
    
    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDRunningShot() {
        if (currentPID != 4) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 4;
        }    
        pid.setSetpoint(Constants.ARM_RUN_SHOT);
        pid.enable();
    }
    
    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDGoalLineShot() {
        if (currentPID != 5) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 5;
        }    
        pid.setSetpoint(Constants.ARM_GOAL_LINE_SHOT);
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
    
    /**
     * Sets arm positions to top, middle, load, or ingest
     */
    public static void setPIDAutonShot() {
        if (currentPID != 7) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_AKp, Constants.ARM_SHOOT_AKi, Constants.ARM_SHOOT_AKd);
            currentPID = 7;
        }        
        pid.setSetpoint(Constants.ARM_AUTON_SHOT);
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
    }
}
