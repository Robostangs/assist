package com.Robostangs;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * @author Laptop
 */
public class Potentiometer extends AnalogChannel implements PIDSource{
    public Potentiometer(int port) {
        super(port);
    }

    public double pidGet() {
        return 1000.0 - super.getAverageValue();
    } 
    
    public int getAverageValue() {
        return 1000 - super.getAverageValue();
    }
}