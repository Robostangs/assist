/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author Laptop
 */
public class DriveTrain {
    private static DriveTrain instance = null;
    private static Encoder leftEncoder, rightEncoder;
   // private static Gyro gyro;
    private static boolean newGyroReading = false;
    private static double initGyro;
    private static double leftMod = 1;
    private static double rightMod = 1;
    
    private DriveTrain() {
        DriveMotors.getInstance();
        Shifting.getInstance();
        //gyro = new Gyro(Constants.GYRO_POS);
	leftEncoder = new Encoder(Constants.DRIVE_LEFT_ENCODER_1, Constants.DRIVE_LEFT_ENCODER_2);
        rightEncoder = new Encoder(Constants.DRIVE_RIGHT_ENCODER_1, Constants.DRIVE_RIGHT_ENCODER_2);
        
        leftEncoder.setDistancePerPulse(Constants.LEFT_DISTANCE_PER_PULSE);
        rightEncoder.setDistancePerPulse(Constants.RIGHT_DISTANCE_PER_PULSE);
        leftEncoder.reset();
	rightEncoder.reset();
        leftEncoder.start();
        rightEncoder.start();
    }
    
    public static DriveTrain getInstance(){ 
        if(instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }
    
    /**
    drive motors
    @param left left motor speed
    @param right right motor speed
    */
    public static void drive (double left, double right) {
        DriveMotors.set(-right, left, left, -right);
    }
    
    /**
    drive motors according to outputs of xbox controller
    @param leftStick value of Xbox left stick
    @param rightStick value of Xbox right Stick
    */
    public static void driveXbox(double leftStick, double rightStick) {
        if (Math.abs(leftStick) < .2) {
            leftStick = 0;
        }
        if (Math.abs(rightStick) < .2) {
            rightStick = 0;
        }
        drive(leftStick, rightStick);
    }
    
    /**
    drive straight
    @param speed motor speed
    *
    public static void driveStraight(double speed) {
        if (!newGyroReading) {
            initGyro = gyro.getAngle();
            newGyroReading = true;
            leftMod = 1;
            rightMod = 1;
        }
        
        if (speed < 0) {
            if ((gyro.getAngle() - initGyro) < -5) {
                leftMod*=1.15;
                rightMod*=0.85;
            } else if ((gyro.getAngle() - initGyro) > 5) {
                rightMod*=1.15;
                leftMod*=0.85;
            }
        } else if (speed < 0) {
            if ((gyro.getAngle() - initGyro) < -5) {
                leftMod*=0.85;
                rightMod*=1.15;
            } else if ((gyro.getAngle() - initGyro) > 5) {
                rightMod*=0.85;
                leftMod*=1.15;
            }
        }   
        
        drive((speed * leftMod), (speed * rightMod));
    }
    
    /**
    resets left and right encoders
    */
    public static void resetEncoder() {
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    /**
    get distance of left encoder
    @return left encoder distance
    */
    public static double getLeftEncoder() {
        return leftEncoder.getRaw();
    }
    
    /**
    get distance of right encoder
    @return right encoder distance
    */
    public static double getRightEncoder() {
        return rightEncoder.getRaw();
    }

    /**
    get gyro angle
    @return gyro angle
    *
    public static double getGyro() {
        return gyro.getAngle();
    }  */  
}
