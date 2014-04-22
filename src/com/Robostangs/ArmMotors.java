package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * @author Laptop
 */
public class ArmMotors implements PIDOutput{
    public static CANJaguar armJag;
    
    public ArmMotors() {
        try {
            armJag = new CANJaguar(Constants.ARM_JAG);
            armJag.configFaultTime(Constants.JAG_FAULT_TIME);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
    }
 
     public void pidWrite(double output) {
        try {
            armJag.setX(-output);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
    }
     
    public static void set(double value) {
        try {
            armJag.setX(-value);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
    }
     
    public static double getPower() {
        try {
            return (armJag.getOutputVoltage());
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
        return 0;
    }
    
    public static void stop() {
        try {
            armJag.setX(0.0);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
    }
    
    public static double getBatteryVoltage() {
        double voltage = 0.0;
        try {
            voltage = armJag.getBusVoltage();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return voltage;
    }
    
    public static double getJagCurrent() {
        double voltage = 0.0;
        try {
            voltage = armJag.getOutputCurrent();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return voltage;
    }
}
