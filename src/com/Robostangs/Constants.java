package com.Robostangs;

/**
 * @author Thunderbird
 */
public class Constants {
    //Arm
    public static final int ARM_JAG = 6;
    public static final int ARM_POT_POS = 1;

    public static final double ARM_SLOW_SPEED = 0.5;
    public static final double ARM_FAST_SPEED = 0.75;
    public static final int ARM_MIN_ANGLE = 120; //420
    public static final int ARM_MAX_ANGLE  = 520; //820;
    public static final int ARM_ANGLE_THRESHOLD = 20;
    public static final double ARM_PID_ABSTOL = 0.0;
    
    //Arm PID Default
    public static final double ARM_DEFAULT_P = 0.008;
    public static final double ARM_DEFAULT_I = 0.0;
    public static final double ARM_DEFAULT_D = 0.01;
    
    //Arm PID Coarse
    public static final double ARM_COARSE_P = 0.008;
    public static final double ARM_COARSE_I = 0.0;
    public static final double ARM_COARSE_D = 0.005;

    //Arm PID Ingest
    public static final int ARM_INGEST_ANGLE = 140;
    
    //Arm PID Load
    public static final int ARM_LOAD_ANGLE = 490;
    
    //Arm PID Shoot
    public static final int ARM_SHOOT_ANGLE = 376;
    public static final int ARM_SHOOT_ANGLE_TOLERANCE = 3;
    public static final int ARM_SHOOT_UPPER_BOUNDARY = -20;
    public static final int ARM_SHOOT_LOWER_BOUNDARY = 20;
    public static final double ARM_SHOOT_FINE_P = 0.010;
    public static final double ARM_SHOOT_FINE_I = 0.0002;
    public static final double ARM_SHOOT_FINE_D = 0.02;
    //Long Shot
    public static final int ARM_LONG_SHOT_ANGLE = 407;
    //Goal Line
    public static final int ARM_GOAL_LINE_ANGLE = 530;

    public static final int ARM_RUN_SHOT = 365;
    
    //Arm PID Truss Pass
    public static final int ARM_TRUSS_ANGLE = 490;
    
    //Arm Pot input and output
    public static final int ARM_POT_IN_MIN = ARM_MIN_ANGLE - 10;
    public static final int ARM_POT_IN_MAX = ARM_MAX_ANGLE + 10;
    public static final int ARM_POT_OUT_MIN = -1;
    public static final int ARM_POT_OUT_MAX = 1;
    
    //Autonomous
    //1 Ball
    public static final double AUTON_DRIVE_DISTANCE = 2500;
    public static final double AUTON_DRIVE_POWER = 0.4;
    
    //2 Balls
    public static final double AUTON_2B_DRIVE_POWER = 0.8;
    public static final double AUTON_DRIVE_FIRST_FORWARD_DISTANCE = 3500.0;
    public static final double AUTON_TURN_ANGLE = 120;
    public static final double AUTON_DRIVE_SECOND_FORWARD_DISTANCE = 2000.0;
    public static final double AUTON_DRIVE_BACK_DISTANCE = -2000.0;
    
    
    
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
    public static final int DT_GYRO_POS = 2;
    public static final double DT_LEFT_ENCODER_DPP = 0.001;
    public static final double DT_RIGHT_ENCODER_DPP = 0.001;
    
    public static final double DT_GYRO_SLOW_MOD = 0.85;
    public static final double DT_GYRO_FAST_MOD = 1.15;
    public static final double DT_ENCODER_SLOW_MOD = 0.930;
    public static final double DT_ENCODER_FAST_MOD = 1.070;
    public static final double DT_DELTA_OFFSET = 0.0;
    public static final double DT_PUSH_POWER = 0.8;
    
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
    public static final int SHOOTER_LIMIT_POS = 13;
    public static final int SHOOTER_CYCLINDER_IN_POS = 3;
    public static final int SHOOTER_CYCLINDER_OUT_POS = 4;

    public static final double SHOOTER_LOAD_POWER = 1.0;
    public static final double SHOOTER_SHOOT_DELAY_TIME = 0.5;
    public static final double SHOOTER_SHOOT_STOP_TIME = 1.0;    
}