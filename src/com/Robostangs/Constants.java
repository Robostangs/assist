/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

/**
 *
 * @author Laptop
 */
public class Constants {
    
    //Robot Main
    public static final int XBOX_DRIVER_POS = 1;
    public static final int XBOX_MANIP_POS = 2;
    
    public static final double INGEST_SPEED = 0.22;
    
    public static final double SLOW_ARM_SPEED = 1.0;
    public static final double NORMAL_ARM_SPEED = 0.5;
    
    //DriveTrain
    public static final int DRIVE_LEFT_ENCODER_1 = 1;
    public static final int DRIVE_LEFT_ENCODER_2 = 2;
    public static final int DRIVE_RIGHT_ENCODER_1 = 3;
    public static final int DRIVE_RIGHT_ENCODER_2 = 4;
    public static final int DRIVE_JAG_1_POS = 1;
    public static final int DRIVE_JAG_2_POS = 2;
    public static final int DRIVE_JAG_3_POS = 3;
    public static final int DRIVE_JAG_4_POS = 4;
    public static final int GYRO_POS = 1;
    public static final double LEFT_DISTANCE_PER_PULSE = 0.001;
    public static final double RIGHT_DISTANCE_PER_PULSE = 0.001;
    
    //Arm
    public static final int ARM_JAG = 6;
    
    public static final int ARM_MIN_ANGLE = 450;
    public static final int ARM_MAX_ANGLE  = 800;
    
    public static final int ARM_POWER_COEFFICIENT = 0;
    
    public static final int ARM_INGEST = 0;
    public static final int ARM_LOAD = 0;
    public static final int ARM_SHOOT = 0;
    
    public static final int ARM_POT_POS = 2;
    public static final int ARM_POT_MAX = 0;
    public static final int ARM_POT_MIN = 0;
    
    //Arm PID
    public static final double ARM_SHOOT_AKp = 0.05;
    public static final double ARM_SHOOT_AKi = 0;
    public static final double ARM_SHOOT_AKd = 0;    
    public static final double ARM_LOAD_AKp = 0;
    public static final double ARM_LOAD_AKi = 0;
    public static final double ARM_LOAD_AKd = 0; 
    public static final double ARM_INGEST_AKp = 0;
    public static final double ARM_INGEST_AKi = 0;
    public static final double ARM_INGEST_AKd = 0;   
    
    //Ingestor
    public static final int INGESTOR_RIGHT_JAG = 5;
    public static final int INGESTOR_LEFT_JAG = 8;
    
    //Shooter
    public static final int SHOOTER_JAG_POS = 7;
    public static final int SHOOTER_ENCODER_1 = 11;
    public static final int SHOOTER_ENCODER_2 = 12;
    public static final int SHOOTER_CYCLINDER_IN_POS = 3;
    public static final int SHOOTER_CYCLINDER_OUT_POS = 4;
    public static final double SHOOTER_KP = 0;
    public static final double SHOOTER_KI = 0;
    public static final double SHOOTER_KD = 0;
    public static final double SHOOTER_LOAD_POSITION = 0;
    public static final boolean SHOOTER_AIR_SHOOT = true;
    
    //Pneumatics
    public static final int RELAY_COMPRESSOR_POS = 1;
    public static final int DIGITAL_INPUT_PRESSURE = 9;
    
    //Shifting
    public static final int HIGH_GEAR_SOLENOID_POS = 1;
    public static final int LOW_GEAR_SOLENOID_POS = 2;
}
