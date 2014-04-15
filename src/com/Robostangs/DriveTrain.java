package com.Robostangs;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;

/**
 * @author Laptop
 */
public class DriveTrain {
    private static DriveTrain instance = null;
    private static Encoder leftEncoder, rightEncoder;
    private static Gyro gyro;
    private static Timer autoShift;
    private static boolean newGyroReadingDriveStraight = false;
    private static boolean newGyroReadingTurn = false;
    private static double initGyro, currentDistance = 0.0;
    public static double delta = 1.0;
    public static boolean encoderInit = false;
    public static boolean intermediate = false;
    
    private DriveTrain() {
        DriveMotors.getInstance();
        Shifting.getInstance();
        gyro = new Gyro(Constants.DT_GYRO_POS);
	leftEncoder = new Encoder(Constants.DT_LEFT_ENCODER_1, Constants.DT_LEFT_ENCODER_2);
        rightEncoder = new Encoder(Constants.DT_RIGHT_ENCODER_1, Constants.DT_RIGHT_ENCODER_2);
        
        leftEncoder.setDistancePerPulse(Constants.DT_LEFT_ENCODER_DPP);
        rightEncoder.setDistancePerPulse(Constants.DT_RIGHT_ENCODER_DPP);
        leftEncoder.reset();
	rightEncoder.reset();
        leftEncoder.start();
        rightEncoder.start();
	//autoShift.reset();
	//autoShift.start();
    }
    
    public static DriveTrain getInstance() {
        if(instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }
    
    public static void drive (double leftPower, double rightPower) {
        if(!Shifting.high && DriveMotors.getTotalJagCurrent() > Constants.DT_CURRENT_THRESHOLD) {
            leftPower *= Constants.DT_LOW_GEAR_REDUCTION;
            rightPower *= Constants.DT_LOW_GEAR_REDUCTION;
        }
        DriveMotors.set(leftPower, leftPower, rightPower, rightPower);
    }
    
    public static void autoDrive (double leftPower, double rightPower) {
	if(Math.abs(leftEncoder.getRate() + rightEncoder.getRate() / 2) > Constants.DT_AUTO_SHIFT_THRESHOLD) {
	    if(!intermediate) {
		Shifting.neutral();
		intermediate = true;
	    }
	    /*&& (autoShift.get() > Constants.DT_AUTO_SHIFT_TIME)) {
            autoShift.reset();
	    autoShift.start();*/
	    Shifting.highGear();
	} else { 
	    if(intermediate) {
                 Shifting.neutral();
		 intermediate = false;
	    }
            /*if (autoShift.get() > Constants.DT_AUTO_SHIFT_TIME) {
	    autoShift.reset();
	    autoShift.start();*/
	    Shifting.lowGear();
	}
	drive(leftPower, rightPower);
    }
    
    public static void humanDrive(double leftPower, double rightPower) {
        if (Math.abs(leftPower) < 0.2) {
            leftPower = 0;
        }
        if (Math.abs(rightPower) < 0.2) {
            rightPower = 0;
        }
        drive(leftPower, rightPower);
    }
    
    public static void driveStraightGyro(double power) {
        double leftMod = 1.0;
        double rightMod = 1.0;
    
        if (!newGyroReadingDriveStraight) {
            initGyro = gyro.getAngle();
            newGyroReadingDriveStraight = true;
        }
        
        if (power < 0) {
            if ((gyro.getAngle() - initGyro) < -5) {
                leftMod*=Constants.DT_GYRO_FAST_MOD;
                rightMod*=Constants.DT_GYRO_SLOW_MOD;
            } else if ((gyro.getAngle() - initGyro) > 5) {
                rightMod*=Constants.DT_GYRO_FAST_MOD;
                leftMod*=Constants.DT_GYRO_SLOW_MOD;
            }
        } else if (power < 0) {
            if ((gyro.getAngle() - initGyro) < -5) {
                leftMod*=Constants.DT_GYRO_SLOW_MOD;
                rightMod*=Constants.DT_GYRO_FAST_MOD;
            } else if ((gyro.getAngle() - initGyro) > 5) {
                rightMod*=Constants.DT_GYRO_SLOW_MOD;
                leftMod*=Constants.DT_GYRO_FAST_MOD;
            }
        }   
        
        drive((power * leftMod), (power * rightMod));
    }
    
     public static void driveStraightEncoder(double power) {
        double leftPower, rightPower;
        delta = Math.abs(getLeftEncoder()) - Math.abs(getRightEncoder());
	
        if (delta > Constants.DT_DELTA_OFFSET) {
	    leftPower = power * Constants.DT_ENCODER_SLOW_MOD;
	    rightPower = power * Constants.DT_ENCODER_FAST_MOD;
        } else if (delta < -Constants.DT_DELTA_OFFSET) {
	    leftPower = power * Constants.DT_ENCODER_FAST_MOD;
	    rightPower = power * Constants.DT_ENCODER_SLOW_MOD;
        } else {
	    leftPower = power;
	    rightPower = power;
	}
        drive(leftPower, rightPower);
    }
    
    public static boolean driveDistance(double distance) {
        if (!encoderInit) {
	    resetEncoders();
            currentDistance = 0.0;
            encoderInit = true;
        }
        
	if (distance > 0) {
	    while (currentDistance < distance) {
                currentDistance = getEncoderAverage();
		return false;
	    }
	} else if (distance < 0) {
	    while (currentDistance > distance) {
                currentDistance = getEncoderAverage();
		return false;
	    }
	}
	
	return true;
    }
    
    public static void turn(double power, double angle) {
        if (!newGyroReadingTurn) {
            initGyro = gyro.getAngle();
            newGyroReadingTurn = true;
        }
        
        if (angle >= 0) {
            drive(power, -power);
        } else {
            drive(-power, power);
        }
    }
    
    public static boolean isTurning(double angle) {
        if (!newGyroReadingTurn) {
            initGyro = gyro.getAngle();
            newGyroReadingTurn = true;
        }
        
        if (angle >= 0) {
            return (gyro.getAngle() - initGyro) < angle;
        } else {
            return (gyro.getAngle() - initGyro) > angle;
        }
    }
    
    public static void maintainPosition() {
        if (!encoderInit) {
            resetEncoders();
            encoderInit = true;
        }
        
        Shifting.lowGear();
        
        if (getEncoderAverage() < -100) {
            drive(Constants.DT_PUSH_POWER, Constants.DT_PUSH_POWER);
        } else if (getEncoderAverage() > 100) {
            drive(-Constants.DT_PUSH_POWER, -Constants.DT_PUSH_POWER);
        } else {
            stop();
        }
    }
    
    public static void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    public static void stopEncoders() {
        leftEncoder.stop();
        rightEncoder.stop();
    }
    
    public static void startEncoders() {
        leftEncoder.start();
        rightEncoder.start();
    }
    
    public static void restartEncoders() {
	encoderInit = false;
	stopEncoders();
	resetEncoders();
	startEncoders();
    }
    
    public static void stop() {
        drive(0,0);
    }
    
    public static double getLeftEncoder() {
        return leftEncoder.getRaw();
    }
    
    public static double getRightEncoder() {
        return -rightEncoder.getRaw();
    }
    
    public static double getEncoderAverage() {
        return (getRightEncoder() + getLeftEncoder()) / 2;
    }

    public static double getGyro() {
        return gyro.getAngle();
    }    
    
    public static void resetBooleans() {
	encoderInit = false;
	newGyroReadingTurn = false;
	newGyroReadingDriveStraight = false;
    }    
}