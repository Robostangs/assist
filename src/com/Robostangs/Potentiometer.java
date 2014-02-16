/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Laptop
 */
public class Potentiometer extends AnalogChannel implements PIDSource{
   
    /*
     * @param port the port of the pot on the Analog Module
     */
    public Potentiometer(int port) {
        super(port);
    }

    public double pidGet() {
        return getAverageValue();
    }
    
}
