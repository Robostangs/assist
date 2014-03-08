/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Robostangs;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Laptop
 */
public class XBoxController extends Joystick{
    
    public XBoxController(int port){
        super(port);
    }
    public boolean aButton(){
        return getRawButton(1);
    }
    public boolean bButton(){
        return getRawButton(2);
    }
    public boolean xButton (){
        return getRawButton(3);
    }
    public boolean yButton() {
        return getRawButton(4);
    }
    public boolean lBumper() {
        return getRawButton(5);
    }
    public boolean rBumper() {
        return getRawButton(6);
    }
    public boolean backButton() {
        return getRawButton(7);
    }
    public boolean startButton(){
        return getRawButton(8);
    }   
    public boolean leftJoystickButton(){
        return getRawButton(9);
    }
    public boolean rightJoystickButton(){
        return getRawButton(10);
    }
    public double leftStickXAxis(){
        return getRawAxis(1);
    }
    public double leftStickYAxis(){
        return -getRawAxis(2);
    }
    public double rightStickXAxis(){
        return getRawAxis(4);
    }
    public double rightStickYAxis(){
        return -getRawAxis(5);
    }
    public double triggerAxis(){
        return getRawAxis(3);
    }
 
    }
