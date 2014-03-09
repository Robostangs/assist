package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * @author Laptop
 */
public class Ingestor {
    private static CANJaguar leftJag, rightJag;
    private static Ingestor instance = null;
    
    private Ingestor() {
        try {
            leftJag = new CANJaguar(Constants.INGESTOR_LEFT_JAG);
            rightJag = new CANJaguar(Constants.INGESTOR_RIGHT_JAG);
        } catch (CANTimeoutException ex) {
            System.out.println("CAN ERROR AT INGESTOR");
        }
    }
    
    public static Ingestor getInstance() {
        if (instance == null) {
            instance = new Ingestor();
        }
        return instance;
    }
    
    /**
     * sets speed of ingestor
     * @param speed speed of ingestor
     */
    public static void setSpeed(double speed) {
        try {
            rightJag.setX(-speed);
            leftJag.setX(speed);
        } catch (CANTimeoutException ex) {
            System.out.println("CAN ERROR AT INGESTOR");
        }
    }

    /**
     * ingest at a constant power
     * @param power motor speed
     */
    public static void ingest() {
        setSpeed(Constants.INGESTOR_INGEST_SPEED);
    }
    
    /**
     * ingest at a constant power
     * @param power motor speed
     */
    public static void exgest() {
        setSpeed(Constants.INGESTOR_EXGEST_SPEED);
    }
    
    /**
     * stop the ingestor
     */
    public static void stop() {
        setSpeed(0);
    }
}