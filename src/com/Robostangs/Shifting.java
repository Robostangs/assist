package com.Robostangs;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * @author Laptop
 */
public class Shifting {
    private static Solenoid highGear, lowGear;
    private static Shifting instance = null;
    public static boolean high = true;
    
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
    
    public static void HighGear() {
        highGear.set(true);
        lowGear.set(false);
	high = true;
    }
    
    public static void LowGear() {
        highGear.set(false);
        lowGear.set(true);
    	high = false;
    }
}