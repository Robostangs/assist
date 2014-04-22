/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Robostangs;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 * @author Robostangs
 */
public class LED {
    public static LED instance = null;
    private static DigitalOutput di1, di2, di3, di4;
    
    private LED() {
        di1 = new DigitalOutput(1);
        di2 = new DigitalOutput(2);
        di3 = new DigitalOutput(3);
        di4 = new DigitalOutput(4);
    }
    
    public static LED getInstance() {
        if(instance == null) {
            instance = new LED();
        }
        return instance;
    }
    
    public static void clear() {
        di1.set(true);
    }
    
    public static void fill() {
        di2.set(true);
    }
    
    public static void rainbow() {
        di3.set(true);
    }
    
    public static void dot() {
        di4.set(true);
    }
}
