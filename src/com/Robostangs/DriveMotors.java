package com.Robostangs;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Laptop
 */
public class DriveMotors implements PIDOutput{
    public static CANJaguar leftJag1, leftJag2, rightJag1, rightJag2;
    public static DriveMotors instance = null;
    
    public DriveMotors() {
        try {
            leftJag1 = new CANJaguar(Constants.DT_LEFT_JAG_1_POS);
            leftJag2 = new CANJaguar(Constants.DT_LEFT_JAG_2_POS);
            rightJag1 = new CANJaguar(Constants.DT_RIGHT_JAG_1_POS);
            rightJag2 = new CANJaguar(Constants.DT_RIGHT_JAG_2_POS);
            
            leftJag1.configFaultTime(Constants.JAG_FAULT_TIME);
            leftJag2.configFaultTime(Constants.JAG_FAULT_TIME);
            rightJag1.configFaultTime(Constants.JAG_FAULT_TIME);
            rightJag2.configFaultTime(Constants.JAG_FAULT_TIME);
        } catch (CANTimeoutException ex) {
            System.out.println("CANJAG ERROR IN DRIVEMOTORS");
        }
    }
    
    public static DriveMotors getInstance() {
        if (instance == null) {
            instance = new DriveMotors();
        }
        return instance;
    }
        
    /**
     * Sets motor speed according to a, b, c, d
     * @param leftSpeed1 motor speed
     * @param leftSpeed2 motor speed
     * @param rightSpeed1 motor speed
     * @param rightSpeed2 motor speed

     */
    public static void set(double leftSpeed1, double leftSpeed2, double rightSpeed1, double rightSpeed2) {
        try {
            leftJag1.setX(leftSpeed1);
            leftJag2.setX(leftSpeed2);
            rightJag1.setX(-rightSpeed1);
            rightJag2.setX(-rightSpeed2);
        } catch (CANTimeoutException ex) {
            System.out.println("CANJAG ERROR IN DRIVEMOTORS");
        }
    }
    
    /**
     * Sets motor speed according to output
     * @param output motor speed
     */
    public void pidWrite(double output) {
        try {
            leftJag1.setX(output);
            leftJag2.setX(output);
            rightJag1.setX(output);
            rightJag2.setX(output);
        } catch (CANTimeoutException ex) {
            System.out.println("CANJAG ERROR IN DRIVEMOTORS");
        }
    }
}
