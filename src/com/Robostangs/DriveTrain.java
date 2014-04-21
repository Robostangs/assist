package com.Robostangs;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

/**
 * @author Laptop
 */
public class DriveTrain {
    private static DriveTrain instance = null;
    public static Encoder leftEncoder, rightEncoder;
    private static Gyro gyro;
    private static double initGyro;
    public static double delta = 1.0, currentDistance = 0;
    private static boolean newGyroReadingDriveStraight = false;
    public static boolean newGyroReadingTurn = false;
    public static boolean encoderInit = false;
    
    private DriveTrain() {
        DriveMotors.getInstance();
        Shifting.getInstance();
        gyro = new Gyro(Constants.DT_GYRO_POS);
	leftEncoder = new Encoder(Constants.DT_LEFT_ENCODER_1, Constants.DT_LEFT_ENCODER_2);
        rightEncoder = new Encoder(Constants.DT_RIGHT_ENCODER_1, Constants.DT_RIGHT_ENCODER_2);
        
        //leftEncoder.setDistancePerPulse(Constants.DT_LEFT_ENCODER_DPP);
        //rightEncoder.setDistancePerPulse(Constants.DT_RIGHT_ENCODER_DPP);
        leftEncoder.reset();
	rightEncoder.reset();
        leftEncoder.start();
        rightEncoder.start();
    }
    
    public static DriveTrain getInstance() {
        if(instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }
    
    /**
     * drive motors
     * @param leftPower left motor speed
     * @param rightPower right motor speed
     */
    public static void drive (double leftPower, double rightPower) {
        DriveMotors.set(leftPower, leftPower, rightPower, rightPower);
    }
    
    /**
     * drive motors according to outputs of xbox controller
     * @param leftPower value of Xbox left stick
     * @param rightPower value of Xbox right Stick
    */
    public static void humanDrive(double leftPower, double rightPower) {
        if (Math.abs(leftPower) < 0.2) {
            leftPower = 0;
        }
        if (Math.abs(rightPower) < 0.2) {
            rightPower = 0;
        }
        drive(leftPower, rightPower);
    }
    
    /**
     * drive straight using gyro
     * @param power motor speed
     */ 
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
    
    /**
     * drive straight using encoders
     * @param power motor speed
     */
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
    
    /**
     * drive straight for a certain distance
     * @param distance distance you want to travel
     * @return true if completed
     */
    public static boolean driveDistance(double distance) {
	if (!encoderInit) {
	    currentDistance = 0;
	    resetEncoders();
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
    
    /**
     * robot tries to maintain its position using encoders
     */
    public static void maintainPosition() {
        if (!encoderInit) {
            resetEncoders();
            encoderInit = true;
        }
        
        Shifting.LowGear();
        
        if (getEncoderAverage() < -100) {
            drive(Constants.DT_PUSH_POWER, Constants.DT_PUSH_POWER);
        } else if (getEncoderAverage() > 100) {
            drive(-Constants.DT_PUSH_POWER, -Constants.DT_PUSH_POWER);
        } else {
            stop();
        }
    }
    
    public static double left, right;
    public static boolean startLimit;
    public static void driveLimitLowGear(double leftPower, double rightPower) {
	if (startLimit) {
	    left = leftPower;
	    right = rightPower;
	    startLimit = false;
	}
	
	if (leftPower > 0.0 && rightPower > 0.0) {
	    if (DriveMotors.getLeftJag1Current() > 20 || DriveMotors.getLeftJag2Current() > 20
		    || DriveMotors.getRightJag1Current() > 24 || DriveMotors.getRightJag2Current() > 24) {
	        humanDrive(leftPower * 0.5, rightPower * 0.5);
	    } else {
		left = (left + leftPower) / 2;
		right = (right + rightPower) / 2;
		humanDrive(left, right);
	    }
	} else {
	    if (DriveMotors.getLeftJag1Current() > 23 || DriveMotors.getLeftJag2Current() > 23
		    || DriveMotors.getRightJag1Current() > 24 || DriveMotors.getRightJag2Current() > 24) {
	        humanDrive(leftPower * 0.5, rightPower * 0.5);
	    } else {
                left = (left + leftPower) / 2;
	        right = (right + rightPower) / 2;
                humanDrive(left, right);
	    }
	}
    }
    
    public static void driveLimitHighGear(double leftPower, double rightPower) {
	if (DriveMotors.getLeftJag1Current() > 45 || DriveMotors.getLeftJag2Current() > 45
		|| DriveMotors.getRightJag1Current() > 45 || DriveMotors.getRightJag2Current() > 45) {
	    left = leftPower * 0.6;
	    right = rightPower * 0.6;
	    humanDrive(left, right);
	} else {
	    //left = (left + leftPower) / 2;
	    //right = (right + rightPower) / 2;
	    humanDrive(leftPower, rightPower);
	}
    }
    
    /**
     * reset left and right encoders
     */
    public static void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }    
    
    /**
     * get distance of left encoder
     * @return left encoder distance
     */
    public static double getLeftEncoder() {
        return -leftEncoder.getRaw();
    }
    
    /**
     * get distance of right encoder
     * @return right encoder distance
     */
    public static double getRightEncoder() {
        return rightEncoder.getRaw();
    }
    
    public static double getEncoderAverage() {
        return (getRightEncoder() + getLeftEncoder()) / 2;
    }
    
    public static void stopEncoders() {
	rightEncoder.stop();
	leftEncoder.stop();
    }
    
    public static void startEncoders() {
	rightEncoder.start();
	leftEncoder.start();
    }
    
    public static void restartEncoders() {
	encoderInit = false;
	stopEncoders();
	resetEncoders();
	startEncoders();
    }

    /**
     * get gyro angle
     * @return gyro angle
     */
    public static double getGyro() {
        return gyro.getAngle();
    }
    
    public static void resetGyro() {
	gyro.reset();
    }
    
    public static void resetBooleans() {
        newGyroReadingDriveStraight = false;
        newGyroReadingTurn = false;
        encoderInit = false;
    }
    /**
     * stop driving
     */
    public static void stop() {
        drive(0,0);
    }
}