package com.Robostangs;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Laptop
 */
public class Pneumatics {
    private static Relay compressor;
    private static DigitalInput pressureSensor;
    private static Pneumatics instance = null;
    
    private Pneumatics() {
        compressor = new Relay(Constants.RELAY_COMPRESSOR_POS);
        pressureSensor = new DigitalInput(Constants.DIGITAL_INPUT_PRESSURE);
    }
    
    public static Pneumatics getInstance() {
        if (instance == null) {
            instance = new Pneumatics (); 
        }
        return instance;
    }
    
    /**
     * Checks the digital input pressureSensor to find out if the compressor is full.
     * If the pressureSensor returns false, it sets the Relay compressor to on (kForward)
     * or true, which turns the compressor off (kOff).
     * @param pressureSensor digital input checks air pressure levels.
     * @param compressor relay controlling the compressor.
     */
    public static void checkPressure() {
        if (!pressureSensor.get()) {
            //Turn on compressor
            compressor.set(Relay.Value.kForward);
        } else {
            //Turn off compressor
            compressor.set(Relay.Value.kOff);
        }
    }
    
    /**
     * When the method is called, the Relay compressor turns on (kForward).
     * @param compressor relay controlling the compressor.
     */
    public static void compressorOn() {
        compressor.set(Relay.Value.kForward);
    }
    
    /**
     * When the method is called, the Relay compressor turns off (kOff).
     * @param compressor relay controlling the compressor.
     */
    public static void compressorOff() {
        compressor.set(Relay.Value.kOff);
    }
    
    public static boolean getCompressor() {
	return pressureSensor.get();
    }
}