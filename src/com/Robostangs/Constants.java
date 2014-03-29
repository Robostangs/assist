package com.Robostangs;

/**
 * @author Thunderbird
 */
public class Constants {
    //Arm
    public static final int ARM_JAG = 6;
    public static final int ARM_POT_POS = 2;

    public static final double ARM_SLOW_SPEED = 0.5;
    public static final double ARM_FAST_SPEED = 0.75;
    public static final double ARM_PID_ABS_TOLERANCE = 0.0; 
    public static final int ARM_MIN_ANGLE = 250; //120 248 273
    public static final int ARM_MAX_ANGLE  = 690; //520 690
    public static final int ARM_ANGLE_THRESHOLD = 20;
    public static final int ARM_POT_IN_MIN = ARM_MIN_ANGLE - 20;
    public static final int ARM_POT_IN_MAX = ARM_MAX_ANGLE + 20;
    public static final int ARM_POT_OUT_MIN = -1;
    public static final int ARM_POT_OUT_MAX = 1;
    
    //Arm PID Default
    public static final double ARM_DEFAULT_P = 0.008;
    public static final double ARM_DEFAULT_I = 0.0;
    public static final double ARM_DEFAULT_D = 0.01;      
    //Arm PID Autonomous
    public static final int ARM_AUTON_SHOT_ANGLE = 485; //510 end of howell
    public static final double ARM_AUTON_P = 0.006;
    public static final double ARM_AUTON_I = 0.0003;
    public static final double ARM_AUTON_D = 0.01;
    //Arm PID Ingest
    public static final int ARM_INGEST_ANGLE = 267; //683    
    //Arm PID Load
    public static final int ARM_LOAD_ANGLE = 650;
    //Arm PID Shoot (In front of the line)
    public static final int ARM_SHOOT_ANGLE = 497; //485
    public static final int ARM_SHOOT_ANGLE_TOLERANCE = 3;
    public static final int ARM_SHOOT_UPPER_BOUNDARY = -20;
    public static final int ARM_SHOOT_LOWER_BOUNDARY = 20;
    public static final double ARM_SHOOT_COARSE_P = 0.008;
    public static final double ARM_SHOOT_COARSE_I = 0.0;
    public static final double ARM_SHOOT_COARSE_D = 0.005;
    public static final double ARM_SHOOT_FINE_P = 0.010;
    public static final double ARM_SHOOT_FINE_I = 0.0002;
    public static final double ARM_SHOOT_FINE_D = 0.02;
    //Long Shot (Behind the line)
    public static final int ARM_LONG_SHOT_ANGLE = 495;
    //Goal Line
    public static final int ARM_GOAL_LINE_ANGLE = 540;
    //Halfway Shot
    public static final int ARM_HALF_SHOT = 509; //365
    //Truss Pass
    public static final int ARM_TRUSS_ANGLE = 520;
    
    //Autonomous
    //1 Ball
    public static final double AUTON_DRIVE_DISTANCE = 2500;
    public static final double AUTON_DRIVE_POWER = 0.4;
    public static final int AUTON_ONE_BALL_ANGLE = 510;
    //2 Ballz
    public static final double AUTON_2B_DRIVE_POWER = 0.8;
    public static final double AUTON_DRIVE_FIRST_FORWARD_DISTANCE = 3800;
    public static final double AUTON_TURN_ANGLE = 120;
    public static final double AUTON_DRIVE_SECOND_FORWARD_DISTANCE = 1900;
    public static final double AUTON_DRIVE_BACK_DISTANCE = -2100;
    public static final int AUTON_TWO_BALL_FIRST_ANGLE = 510;
    
    //CANJaguars
    public static final double JAG_FAULT_TIME = 1.0;
    
    //DriveTrain
    public static final int DT_LEFT_JAG_1_POS = 2;
    public static final int DT_LEFT_JAG_2_POS = 3;
    public static final int DT_RIGHT_JAG_1_POS = 1;
    public static final int DT_RIGHT_JAG_2_POS = 4;
    public static final int DT_LEFT_ENCODER_1 = 1;
    public static final int DT_LEFT_ENCODER_2 = 2;
    public static final int DT_RIGHT_ENCODER_1 = 3;
    public static final int DT_RIGHT_ENCODER_2 = 4;
    public static final int DT_GYRO_POS = 1;
    
    public static final double DT_LEFT_ENCODER_DPP = 0.001;
    public static final double DT_RIGHT_ENCODER_DPP = 0.001;
    public static final double DT_ENCODER_FAST_MOD = 1.07;
    public static final double DT_GYRO_SLOW_MOD = 0.93;
    public static final double DT_GYRO_FAST_MOD = 1.15;
    public static double DT_ENCODER_SLOW_MOD = 0.98;
    public static double DT_DELTA_OFFSET = 0.0;
    public static double DT_PUSH_POWER = 0.8;
    
    //Ingestor
    public static final int INGESTOR_LEFT_JAG = 7;
    public static final int INGESTOR_RIGHT_JAG = 8;
    
    public static final double INGESTOR_INGEST_SPEED = -0.8;
    public static final double INGESTOR_EXGEST_SPEED = 1.0;
    public static final double INGESTOR_CONSTANT_INGEST_SPEED = -0.2;
    
    //Pneumatics
    public static final int RELAY_COMPRESSOR_POS = 1;
    public static final int DIGITAL_INPUT_PRESSURE = 5;
    
    //Robot Main
    public static final int XBOX_DRIVER_POS = 1;
    public static final int XBOX_MANIP_POS = 2;
    
    //Shifting
    public static final int HIGH_GEAR_SOLENOID_POS = 1;
    public static final int LOW_GEAR_SOLENOID_POS = 2;
    
    //Shooter
    public static final int SHOOTER_JAG_POS = 5;
    public static final int SHOOTER_ENCODER_1 = 6;
    public static final int SHOOTER_ENCODER_2 = 7;
    public static final int SHOOTER_LIMIT_POS = 8;
    public static final int SHOOTER_CYCLINDER_IN_POS = 3;
    public static final int SHOOTER_CYCLINDER_OUT_POS = 4;

    public static final double SHOOTER_LOAD_POWER = 1.0;
    public static final double SHOOTER_SHOOT_DELAY_TIME = 0.5;
    public static final double SHOOTER_SHOOT_STOP_TIME = 1.0;    
}