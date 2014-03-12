package com.Robostangs;

import edu.wpi.first.wpilibj.PIDController;

/**
 * @author Thunderbird
 */
public class Arm {
    private static Arm instance = null;
    public static Potentiometer apot;
    public static ArmMotors motorOutput;
    public static PIDController pid;
    public static int currentPID = 0;
    public static double pidDiff = 0;
    public static boolean isLow = false;

    private Arm(){
        apot = new Potentiometer(Constants.ARM_POT_POS);
        motorOutput = new ArmMotors();
        pid = new PIDController(Constants.ARM_SHOOT_P, Constants.ARM_SHOOT_I, Constants.ARM_SHOOT_D, apot, motorOutput);
        
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
            if (getArmAngle() > (Constants.ARM_MAX_ANGLE - Constants.ARM_ANGLE_THRESHOLD) 
                    && getArmAngle() < Constants.ARM_MAX_ANGLE) {
                ArmMotors.set(power * 0.3);
            } else if (getArmAngle() < Constants.ARM_MAX_ANGLE) {
		ArmMotors.set(power);
	    } else {
                ArmMotors.stop();
            }
        } else if (power < 0) {
            if (getArmAngle() < (Constants.ARM_MIN_ANGLE + Constants.ARM_ANGLE_THRESHOLD) 
                    && getArmAngle() > Constants.ARM_MIN_ANGLE) {
                ArmMotors.set(power * 0.2);
            } else if (getArmAngle() > Constants.ARM_MIN_ANGLE) {
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

    public static void setPIDCoarse() {
        if (currentPID != 11) {
            pid.reset();
            pid.setPID(Constants.ARM_COARSE_P, Constants.ARM_COARSE_I, Constants.ARM_COARSE_D);
            currentPID = 11;
        }
    }
    /**
     * DON'T TOUCH THIS
     */
    public static void setPIDShoot() {
	pid.setSetpoint(Constants.ARM_SHOOT_ANGLE);
	pidDiff = pid.getError();
	
	if (pidDiff < Constants.ARM_SHOOT_UPPER_BOUNDARY || pidDiff > Constants.ARM_SHOOT_LOWER_BOUNDARY) {
            setPIDCoarse();
        } else {
	    if (currentPID != 12) {
		pid.reset();
		pid.setPID(Constants.ARM_SHOOT_FINE_P, Constants.ARM_SHOOT_FINE_I, Constants.ARM_SHOOT_FINE_D);
		currentPID = 12;
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
            pid.setPID(Constants.ARM_INGEST_P, Constants.ARM_INGEST_I, Constants.ARM_INGEST_D);
            currentPID = 1;
	}
	pid.setSetpoint(Constants.ARM_INGEST_ANGLE);
        pid.enable();
	
	isLow = true;
    }
    
    /**
     * Sets arm position to load
     */
    public static void setPIDHumanLoad() {
        if (currentPID != 2) {
            pid.reset();
            pid.setPID(Constants.ARM_LOAD_P, Constants.ARM_LOAD_I, Constants.ARM_LOAD_D);
            currentPID = 2;
        }        
        pid.setSetpoint(Constants.ARM_LOAD_ANGLE);
        pid.enable();
    }

    /**
     * Sets arm position to long shot
     */
    public static void setPIDLongShot() {
        if (currentPID != 3) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_P, Constants.ARM_SHOOT_I, Constants.ARM_SHOOT_D);
	    currentPID = 3;
        }        
        pid.setSetpoint(Constants.ARM_LONG_SHOT_ANGLE);
        pid.enable();
    }    
    
    /**
     * Sets arm position goal line shot
     */
    public static void setPIDGoalLineShot() {
        if (currentPID != 5) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_P, Constants.ARM_SHOOT_I, Constants.ARM_SHOOT_D);
            currentPID = 5;
        }    
        pid.setSetpoint(Constants.ARM_GOAL_LINE_ANGLE);
        pid.enable();
    }
    
    /**
     * Sets arm position truss pass
     */
    public static void setPIDTrussPass() {
        if (currentPID != 6) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_P, Constants.ARM_SHOOT_I, Constants.ARM_SHOOT_D);
            currentPID = 6;
        }
        pid.setSetpoint(Constants.ARM_TRUSS_ANGLE);
        pid.enable();
    }
    
    /**
     * Sets arm position to autonomous shot
     */
    public static void setPIDAutonShot() {
        if (currentPID != 7) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_P, Constants.ARM_SHOOT_I, Constants.ARM_SHOOT_D);
            currentPID = 7;
        }        
        pid.setSetpoint(Constants.ARM_AUTON_SHOT_ANGLE);
        pid.enable();
    }
    
    /**
     * Sets arm position to autonomous shot
     */
    public static void setPIDAutonLow() {
        if (currentPID != 8) {
            pid.reset();
            pid.setPID(Constants.ARM_SHOOT_P, Constants.ARM_SHOOT_I, Constants.ARM_SHOOT_D);
            currentPID = 8;
        }        
        pid.setSetpoint(Constants.ARM_AUTON_LOW_ANGLE);
        pid.enable();
    }
    
    public static boolean isArmInShootAngle() {
        //3 right now because we don't have setPIDAccurateShot()
        if (getArmAngle() < (Constants.ARM_SHOOT_ANGLE + 3/*Constants.ARM_SHOOT_ANGLE_TOLERANCE*/)
                && getArmAngle() > (Constants.ARM_SHOOT_ANGLE - 3/*Constants.ARM_SHOOT_ANGLE_TOLERANCE*/)) {
            isLow = false;
	    return true;
        }
        return false;
    }
    
   /**
    * @return returns arm angle
    */
    public static double getArmAngle() {
        return apot.getAverageValue();
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
    
    public static boolean isInPosition(int setpoint) {
	if (pid.getSetpoint() != setpoint) {
	    pid.setSetpoint(setpoint);
	}
	return getArmAngle() < (pid.getSetpoint() + 2) && getArmAngle() > (pid.getSetpoint() - 2);
    }
    
    public static void setPIDConstants(double p, double i, double d, double setpoint) {
	pid.reset();
	pid.setPID(p, i, d);
	pid.setSetpoint(setpoint);
    }   
}