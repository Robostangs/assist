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
public class ArmMotors implements PIDOutput{
    
    public static CANJaguar arm_jag;
    
     public ArmMotors() {
        try {
            arm_jag = new CANJaguar(Constants.ARM_JAG);
        } catch (CANTimeoutException ex) {
            System.out.println("Arm Failure");
            ex.printStackTrace();
        }
    }
     
    /**
    Sets arm speed according to output
    @param output arm speed
    */
     public void pidWrite(double output) {
        try {
            arm_jag.setX(output);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
     
     /**
     Sets arm speed according to value
     @param set arm speed
     */
     public static void set(double value) {
        try {
            arm_jag.setX(value);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
     
     /**
     get average voltage of motor jags
     @return average voltage of jags
     */
     public static double getPower() {
        try {
            return (arm_jag.getOutputVoltage());
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
