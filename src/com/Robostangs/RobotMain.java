//PRACTICE BOT

package com.Robostangs;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Thunderbird
 */
public class RobotMain extends IterativeRobot {
    private static XBoxController xboxDriver;
    private static XBoxController xboxManip;
    
    public void robotInit() {
        xboxDriver = new XBoxController(Constants.XBOX_DRIVER_POS);
	xboxManip = new XBoxController(Constants.XBOX_MANIP_POS);
        Arm.getInstance();
	Autonomous.getInstance();
	DriveTrain.getInstance();
	CheesyDrive.getInstance();
        Ingestor.getInstance();
	Pneumatics.getInstance();
	Shifting.getInstance();
	Shooter.getInstance();
	Log.getInstance();
        
	LiveWindow.addActuator("Arm", "Motor", ArmMotors.armJag);
	LiveWindow.addSensor("Arm", "Pot", Arm.apot);
	LiveWindow.addSensor("Arm", "PID", Arm.pid);
    }

    public void autonomousPeriodic() {
	Autonomous.oneBallAutonomous();
	//Autonomous.twoBallAutonomous();
	//Autonomous.twoHotChicks();
    }

    public void disabledPeriodic() {
	Autonomous.reset();
    }
    
    public void teleopPeriodic() {
	if (xboxDriver.lBumper() && xboxDriver.triggerAxis() < 0.4) {
	    DriveTrain.driveLimitLowGear(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	} else if (xboxDriver.triggerAxis() > 0.4) {
	    DriveTrain.humanDrive(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	    DriveTrain.startLimit = false;
        } else {
	    //CheesyDrive.drive(xboxDriver.rightStickYAxis(), xboxDriver.leftStickXAxis());
	    DriveTrain.driveLimitHighGear(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	    DriveTrain.startLimit = false;
	}
        
        if (xboxDriver.lBumper()) {
	    Shifting.LowGear();
	} else {
	    Shifting.HighGear();
	}
        
	if (Math.abs(xboxManip.rightStickYAxis()) > 0.2) {
	    Arm.setArmCoarseAdjustment(xboxManip.rightStickYAxis());
	} else if (Math.abs(xboxManip.leftStickYAxis()) > 0.2) {
	    Arm.setArmFineAdjustment(xboxManip.leftStickYAxis());
	} else if (xboxManip.aButton()) {
            Arm.setPIDIngest();
	} else if (xboxManip.bButton()) {
            Arm.setPIDShot();
        } else if (xboxManip.xButton()) {
            Arm.setPIDHumanLoad();
        } else if (xboxManip.yButton()) {
            Arm.setPIDTrussPass();
	} else if (xboxManip.startButton()) {
            Arm.setPIDLongShot();
        } else if (!Arm.isArmInShootAngle() && Arm.isLow) {
            Arm.setPIDShot();
        } else {
	    Arm.stop();
	}

	if (xboxManip.lBumper()) {
	    Shooter.stop();
        } else if (xboxManip.rBumper()) {
            Shooter.shooShoot();
	} else if (xboxDriver.rBumper()) {
	    Shooter.shoot();
	} else if (xboxDriver.startButton()) {
	    Shooter.shooShoot();
	} else {
	    Shooter.autoLoad();
        }
        
        if (xboxManip.triggerAxis() > 0.2) {
	    Ingestor.ingest();
        } else if (xboxManip.triggerAxis() < -0.2) {
            Ingestor.exgest();
        }
        
        if(!xboxManip.rBumper() && !xboxDriver.rBumper() && Math.abs(xboxManip.triggerAxis()) < 0.2
		&& !xboxDriver.startButton()) {
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
        }
	
        Pneumatics.checkPressure();
		    /*
		    if(xboxDriver.startButton()) {
		    Constants.DT_TURN+=1;
		    } else if (xboxDriver.backButton()) {
		    Constants.DT_TURN-=1;
		    }
		    if(xboxDriver.backButton()) {
		    DriveTrain.driveStraightEncoder(-0.8);
		    } else {
		    DriveTrain.humanDrive(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
		    }
		    
		    //DRIVE STRAIGHT
		    if(xboxDriver.bButton()) {
		    Constants.DT_DELTA_OFFSET += 0.1;
		    } else if (xboxDriver.aButton()) {
		    Constants.DT_DELTA_OFFSET -= 0.1;
		    } else if (xboxDriver.yButton()) {
		    Constants.DT_ENCODER_SLOW_MOD += 0.01;
		    } else if (xboxDriver.xButton()) {
		    Constants.DT_ENCODER_SLOW_MOD -= 0.01;
		    }
		    
		    //ARM PID
		    if (xboxManip.aButton()) {
		    Constants.ARM_SHOOT_FINE_P+=0.001;
		    } else if (xboxManip.bButton()) {
		    Constants.ARM_SHOOT_FINE_P-=0.001;
		    } else if (xboxManip.xButton()) {
		    Constants.ARM_SHOOT_FINE_I+=0.0001;
		    } else if (xboxManip.yButton()) {
		    Constants.ARM_SHOOT_FINE_I-=0.0001;
		    } else if (xboxManip.startButton()) {
		    Constants.ARM_SHOOT_FINE_D+=0.0005;
		    } else if (xboxManip.backButton()) {
		    Constants.ARM_SHOOT_FINE_D-=0.0005;
		    } else if (xboxManip.lBumper()) {
		    Constants.ARM_SHOOT_LOWER_BOUNDARY+=1;
		    } else if (xboxManip.rBumper()) {
		    Constants.ARM_SHOOT_LOWER_BOUNDARY-=1;
		    }*/
		    /*
		    Log.write(xboxDriver.leftStickYAxis() + "," + DriveTrain.leftEncoder.getRaw() + DriveTrain.leftEncoder.getRate() + "," + xboxDriver.rightStickYAxis() + "," + DriveTrain.rightEncoder.getRaw() + "," + DriveTrain.rightEncoder.getRate() + "," + "a");
		    */
        try {
	    Log.write(xboxDriver.leftStickYAxis() + "," + xboxDriver.rightStickYAxis() + ","
	    + DriveMotors.leftJag1.getOutputCurrent() + ","
	    + DriveMotors.leftJag2.getOutputCurrent() + ","
	    + DriveMotors.rightJag1.getOutputCurrent() + ","
	    + DriveMotors.rightJag2.getOutputCurrent() + ","
	    + -DriveTrain.leftEncoder.getRaw() + ","
	    + DriveTrain.rightEncoder.getRaw() + ","		    
	    + -DriveTrain.leftEncoder.getRate() + ","
	    + DriveTrain.rightEncoder.getRate() + "," + "a");
	} catch (CANTimeoutException ex) {
	    ex.printStackTrace();
	}
	
	SmartDashboard.putNumber("RRate", DriveTrain.rightEncoder.getRate());
	SmartDashboard.putNumber("LRate", DriveTrain.leftEncoder.getRate());
	SmartDashboard.putBoolean("Current", Ingestor.hasBall());
	SmartDashboard.putNumber("Gyro", DriveTrain.getGyro());
    	SmartDashboard.putNumber("Pot", Arm.getArmAngle());
	SmartDashboard.putNumber("Left Encoder", DriveTrain.getLeftEncoder());
	SmartDashboard.putNumber("Right Encoder", DriveTrain.getRightEncoder());
	SmartDashboard.putNumber("Encoder Average", DriveTrain.getEncoderAverage());
	SmartDashboard.putBoolean("Proximity Sensor", Shooter.getLimit());
	//SmartDashboard.putNumber("Shooter Encoder", Shooter.getEncoderDistance());
        //SmartDashboard.putBoolean("Shooter Limit Switch", Shooter.getLimit());
        //SmartDashboard.putNumber("Shooter Timer", Constants.SHOOTER_SHOOT_DELAY_TIME);
        //SmartDashboard.putNumber("Offset", Constants.DT_DELTA_OFFSET);
        //SmartDashboard.putNumber("Mod", Constants.DT_ENCODER_SLOW_MOD);
	//SmartDashboard.putNumber("delta", DriveTrain.delta);
	//SmartDashboard.putNumber("pidDiff", Arm.pidDiff);
	//SmartDashboard.putNumber("P", Arm.pid.getP());
	//SmartDashboard.putNumber("I", Arm.pid.getI());
	//SmartDashboard.putNumber("D", Arm.pid.getD());
	//SmartDashboard.putNumber("second P", Constants.ARM_SHOOT_FINE_P);
	//SmartDashboard.putNumber("second I", Constants.ARM_SHOOT_FINE_I);
	//SmartDashboard.putNumber("second D", Constants.ARM_SHOOT_FINE_D);
	//SmartDashboard.putNumber("HIGH Boundary", Constants.ARM_SHOOT_LOWER_BOUNDARY);
	//System.out.println(Constants.ARM_SHOOT_FINE_I);*/
    }

    public void testPeriodic() {
	    LiveWindow.run();
	    SmartDashboard.putNumber("Pot", Arm.getArmAngle());
    }   
}