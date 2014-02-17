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
    public static CANJaguar rightJag1, rightJag2, leftJag1, leftJag2;
    public static DriveMotors instance = null;
    
    public DriveMotors() {
        try {
            rightJag1 = new CANJaguar(Constants.DT_RIGHT_JAG_1_POS);
            rightJag2 = new CANJaguar(Constants.DT_RIGHT_JAG_2_POS);
            leftJag1 = new CANJaguar(Constants.DT_LEFT_JAG_1_POS);
            leftJag2 = new CANJaguar(Constants.DT_LEFT_JAG_2_POS);
            // fault time should be a constant
            rightJag1.configFaultTime(0.5);
            rightJag2.configFaultTime(0.5);
            leftJag1.configFaultTime(0.5);
            leftJag2.configFaultTime(0.5);
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
            rightJag1.setX(output);
            rightJag2.setX(output);
            leftJag1.setX(output);
            leftJag2.setX(output);
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
            rightJag1.setX(a);
            rightJag2.setX(b);
            leftJag1.setX(c);
            leftJag2.setX(d);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
}
