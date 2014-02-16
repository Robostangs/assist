/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Laptop
 */
public class Ingestor {
    private static CANJaguar rightJag, leftJag;
    private static Ingestor instance = null;
    
    public Ingestor() {
        try {
            rightJag = new CANJaguar(Constants.INGESTOR_RIGHT_JAG);
            leftJag = new CANJaguar(Constants.INGESTOR_LEFT_JAG);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            System.out.println("CAN Error at Ingestor class");
        }
    }
    
    public static Ingestor getInstance() {
        if (instance == null) {
            instance = new Ingestor();
        }
        return instance;
    }
    
    /**
    sets speed of ingestor
    @param speed speed of ingestor
    */
    public static void setSpeed(double speed) {
        try {
            rightJag.setX(-speed);
            leftJag.setX(speed);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    // need methods for ingest / exgest with no arguments, off method
    
    
}
