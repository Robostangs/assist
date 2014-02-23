//PRACTICE BOT

package com.Robostangs;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotMain extends IterativeRobot {
    private static XBoxController xboxDriver;
    private static XBoxController xboxManip;
    
    public void robotInit() {
        xboxDriver = new XBoxController(Constants.XBOX_DRIVER_POS);
	xboxManip = new XBoxController(Constants.XBOX_MANIP_POS);
        Arm.getInstance();
	DriveTrain.getInstance();
        Ingestor.getInstance();
	Pneumatics.getInstance();
	Shifting.getInstance();
	Shooter.getInstance();
        
	LiveWindow.addActuator("Arm", "Motor", ArmMotors.armJag);
	LiveWindow.addSensor("Arm", "Pot", Arm.apot);
	LiveWindow.addSensor("Arm", "PID", Arm.pid);
    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
        
	DriveTrain.driveXbox(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	
        if (xboxDriver.lBumper()) {
	    Shifting.LowGear();
	} else {
	    Shifting.HighGear();
	}
        
	if (Math.abs(xboxManip.rightStickYAxis()) > 0.2) {
	    Arm.setArmSpeed(-(0.5 * xboxManip.rightStickYAxis()));
	} else if (xboxManip.aButton()) {
            Arm.setPIDStaticShot();
        } else if (xboxManip.bButton()) {
            Arm.setPIDFenderShot();
        } else if (xboxManip.xButton()) {
            Arm.setPIDIngest();
        } else if (xboxManip.yButton()) {
            Arm.setPIDTrussPass();
        } else {
	    Arm.stop();
	}
	
	if (xboxDriver.lBumper()) {
	    Shooter.load();
        } else if (xboxManip.rBumper()) {
            Shooter.shoot();
	} else if (xboxDriver.rBumper()) {
	    Shooter.shoot();
	} else {
            Shooter.solenoidDisable();
            Shooter.resetShoot();
	    Shooter.set(0);
	}
        
        if (Math.abs(xboxManip.triggerAxis()) > 0.2) {
	    Ingestor.setSpeed(-xboxDriver.triggerAxis());
        }
        
        if(!xboxManip.rBumper() && !xboxDriver.rBumper() && Math.abs(xboxManip.triggerAxis()) < 0.2) {
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
        }
	
        /*
        if(xboxDriver.startButton()) {
            Shooter.increaseShooterTime();
        } else if (xboxDriver.backButton()) {
            Shooter.decreaseShooterTime();
        } 
        */
	Pneumatics.checkPressure();
	
	SmartDashboard.putNumber("Gyro", DriveTrain.getGyro());
    	SmartDashboard.putNumber("Pot", Arm.getArmAngle());
	SmartDashboard.putNumber("Left Encoder", DriveTrain.getLeftEncoder());
	SmartDashboard.putNumber("Right Encoder", DriveTrain.getRightEncoder());
	SmartDashboard.putNumber("Shooter Encoder", Shooter.getEncoderDistance());
        SmartDashboard.putBoolean("Shooter Limit Switch", Shooter.getLimit());
        SmartDashboard.putNumber("Shooter Timer", Constants.SHOOTER_SHOOT_DELAY_TIME);
    }

    public void testPeriodic() {
	    LiveWindow.run();
	    SmartDashboard.putNumber("Pot", Arm.getArmAngle());
    }
    
}
