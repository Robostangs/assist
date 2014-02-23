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
 
    /**
     * Sets arm speed according to output
     * @param output arm speed
     */
     public void pidWrite(double output) {
        try {
            armJag.setX(output);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
    }
     
    /**
     * Sets arm speed according to value
     * @param set arm speed
     */
    public static void set(double value) {
        try {
            armJag.setX(value);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
    }
     
    /**
     * get average voltage of motor jags
     * @return average voltage of jags
     */
    public static double getPower() {
        try {
            return (armJag.getOutputVoltage());
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Motor Error");
        }
        return 0;
    }
}
