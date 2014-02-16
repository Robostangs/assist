/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Laptop
 */
public class DriveMotors implements PIDOutput{
    public static CANJaguar jag1, jag2, jag3, jag4;
    public static DriveMotors instance = null;
    
    public DriveMotors() {
        try {
            jag1 = new CANJaguar(Constants.DRIVE_JAG_1_POS);
            jag2 = new CANJaguar(Constants.DRIVE_JAG_2_POS);
            jag3 = new CANJaguar(Constants.DRIVE_JAG_3_POS);
            jag4 = new CANJaguar(Constants.DRIVE_JAG_4_POS);
            // fault time should be a constant
            jag1.configFaultTime(0.5);
            jag2.configFaultTime(0.5);
            jag3.configFaultTime(0.5);
            jag4.configFaultTime(0.5);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    public static DriveMotors getInstance() {
        if (instance == null) {
            instance = new DriveMotors();
        }
        return instance;
    }
    
    /**
    Sets motor speed according to output
    @param output motor speed
    */
    public void pidWrite(double output) {
        try {
            jag1.setX(output);
            jag2.setX(output);
            jag3.setX(output);
            jag4.setX(output);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
    Sets motor speed according to a, b, c, d
    @param a motor speed
    @param b motor speed
    @param c motor speed
    @param d motor speed
    */
    public static void set(double a, double b, double c, double d) {
        try {
            jag1.setX(a);
            jag2.setX(b);
            jag3.setX(c);
            jag4.setX(d);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
}
