package com.Robostangs;

/**
 *
 * @author Laptop
 */
public class Constants {
    //Arm
    public static final int ARM_JAG = 6;
    public static final int ARM_POT_POS = 2;

    public static final double ARM_SLOW_SPEED = 0.5;
    public static final int ARM_MIN_ANGLE = 450;
    public static final int ARM_MAX_ANGLE  = 800;    
    public static final int ARM_INGEST = 475;
    public static final int ARM_LOAD = 600;
    public static final int ARM_MAX_STATIC_SHOT = 781;
    public static final int ARM_FENDER_SHOT = 824;
    public static final int ARM_CLOSE_SHOT = 837;
    public static final int ARM_TRUSS_PASS = 801;
    
    public static final int ARM_POT_IN_MIN = ARM_INGEST - 25;
    public static final int ARM_POT_IN_MAX = ARM_CLOSE_SHOT + 25;
    public static final int ARM_POT_OUT_MIN = -1;
    public static final int ARM_POT_OUT_MAX = 1;
    
    //Arm PID
    public static final double ARM_SHOOT_AKp = 0.003;
    public static final double ARM_SHOOT_AKi = 0.0000028;
    public static final double ARM_SHOOT_AKd = 0.0;    
    public static final double ARM_LOAD_AKp = 0.003;
    public static final double ARM_LOAD_AKi = 0.0000028;
    public static final double ARM_LOAD_AKd = 0.0;
    public static final double ARM_INGEST_AKp = 0.003;
    public static final double ARM_INGEST_AKi = 0.0000028;
    public static final double ARM_INGEST_AKd = 0.0;   
    
    //CANJaguars
    public static final double JAG_FAULT_TIME = 5.0;
    
    //DriveTrain
    public static final int DT_LEFT_JAG_1_POS = 3;
    public static final int DT_LEFT_JAG_2_POS = 4;
    public static final int DT_RIGHT_JAG_1_POS = 1;
    public static final int DT_RIGHT_JAG_2_POS = 2;
    public static final int DT_LEFT_ENCODER_1 = 4;
    public static final int DT_LEFT_ENCODER_2 = 5;
    public static final int DT_RIGHT_ENCODER_1 = 7;
    public static final int DT_RIGHT_ENCODER_2 = 8;
    public static final int DT_GYRO_POS = 1;
    public static final double DT_LEFT_ENCODER_DPP = 0.001;
    public static final double DT_RIGHT_ENCODER_DPP = 0.001;
    public static final double DT_GYRO_SLOW_MOD = 0.85;
    public static final double DT_GYRO_FAST_MOD = 1.15;
    public static final double DT_ENCODER_SLOW_MOD = 0.93;
    public static final double DT_ENCODER_FAST_MOD = 1.20;
    
    //Ingestor
    public static final int INGESTOR_LEFT_JAG = 8;
    public static final int INGESTOR_RIGHT_JAG = 5;
    public static final double INGESTOR_INGEST_SPEED = -0.8;
    public static final double INGESTOR_EXGEST_SPEED = 1.0;
    public static final double INGESTOR_SHOOT_EXGEST_SPEED = 1.0;
    public static final double INGESTOR_CONSTANT_INGEST_SPEED = -0.2;
    
    //Pneumatics
    public static final int RELAY_COMPRESSOR_POS = 1;
    public static final int DIGITAL_INPUT_PRESSURE = 9;
    
    //Robot Main
    public static final int XBOX_DRIVER_POS = 1;
    public static final int XBOX_MANIP_POS = 2;
    
    //Shifting
    public static final int HIGH_GEAR_SOLENOID_POS = 1;
    public static final int LOW_GEAR_SOLENOID_POS = 2;
    
    //Shooter
    public static final int SHOOTER_JAG_POS = 7;
    public static final int SHOOTER_ENCODER_1 = 11;
    public static final int SHOOTER_ENCODER_2 = 12;
    public static final int SHOOTER_LIMIT_POS = 14;
    public static final int SHOOTER_CYCLINDER_IN_POS = 3;
    public static final int SHOOTER_CYCLINDER_OUT_POS = 4;

    public static final double SHOOTER_LOAD_POWER = 0.75;
    public static double SHOOTER_SHOOT_DELAY_TIME = 0;
    public static final double SHOOTER_SHOOT_STOP_TIME = 1.0;    
}