package com.Robostangs;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * @author Laptop
 */
public class Shifting {
    private static Solenoid highGear, lowGear;
    private static Shifting instance = null;
    
    private Shifting() {
        highGear = new Solenoid(Constants.HIGH_GEAR_SOLENOID_POS);
        lowGear = new Solenoid(Constants.LOW_GEAR_SOLENOID_POS);
    }
    
    public static Shifting getInstance() {
        if (instance == null) {
            instance = new Shifting();
        }
        return instance;
    }
    
    /**
     * When the method is called, the solenoids shift to high gear.
     * @param highGear solenoid triggering high gear
     * @param lowGear solenoid triggering low gear
     */
    public static void HighGear() {
        highGear.set(true);
        lowGear.set(false);
    }
    
    /**
     * When the method is called, the solenoids shift to low gear.
     * @param highGear solenoid triggering high gear
     * @param lowGear solenoid triggering low gear
     */
    public static void LowGear() {
        highGear.set(false);
        lowGear.set(true);
    }
}