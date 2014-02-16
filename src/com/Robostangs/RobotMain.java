/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.Robostangs;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private static XBoxController xboxDriver;
    //private static XBoxController xboxManip;
    private static boolean enable = false;
    
    public void robotInit() {
        xboxDriver = new XBoxController(Constants.XBOX_DRIVER_POS);
	//xboxManip = new XBoxController(Constants.XBOX_MANIP_POS);
	DriveTrain.getInstance();
	Shifting.getInstance();
        //Arm.getInstance();
	Pneumatics.getInstance();
        /*Ingestor.getInstance();
	Shooter.getInstance();
	LiveWindow.addActuator("Arm", "Motor", ArmMotors.arm_jag);
	LiveWindow.addSensor("Arm", "Pot", Arm.apot);
	LiveWindow.addSensor("Arm", "PID", Arm.pid);
        */
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
	DriveTrain.driveXbox(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	/*
	if (xboxDriver.aButton()) {
	    Arm.setArmLimited(-0.25);
	} else if(xboxDriver.bButton()) {
	    Arm.setArmLimited(0.5);
	} else {
	    Arm.stop();
	}
	*/
	if (xboxDriver.xButton()) {
	    Shifting.LowGear();
	} else if (xboxDriver.yButton()) {
	    Shifting.HighGear();
	}
	/*
	if (xboxDriver.lBumper()) {
	    Shooter.load();
	} else if (xboxDriver.rBumper()) {
	    Shooter.solenoidEnable();
	    Shooter.set(0);
	} else {
	    Shooter.solenoidDisable();
	    Shooter.set(0);
	}
	
	if (Math.abs(xboxDriver.triggerAxis()) > 0.2) {
	    Ingestor.setSpeed(-xboxDriver.triggerAxis());
	} else {
	    Ingestor.setSpeed(0);
	} 
	*/
	Pneumatics.checkPressure();
	/*
	SmartDashboard.putNumber("Gyro", DriveTrain.getGyro());
    	SmartDashboard.putNumber("Pot", Arm.getArmAngle());
	SmartDashboard.putNumber("Left Encoder", DriveTrain.getLeftEncoder());
	SmartDashboard.putNumber("Right Encoder", DriveTrain.getRightEncoder());
	SmartDashboard.putNumber("Shooter Encoder", Shooter.getEncoderDistance());
        */
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        /*
	    LiveWindow.run();
	    SmartDashboard.putNumber("Pot", Arm.getArmAngle());
        */
    }
    
}
