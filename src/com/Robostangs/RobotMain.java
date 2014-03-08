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
	Autonomous.getInstance();
	DriveTrain.getInstance();
        Ingestor.getInstance();
	Pneumatics.getInstance();
	Shifting.getInstance();
	Shooter.getInstance();
        
	Shooter.solenoidDisable();
	
	LiveWindow.addActuator("Arm", "Motor", ArmMotors.armJag);
	LiveWindow.addSensor("Arm", "Pot", Arm.apot);
	LiveWindow.addSensor("Arm", "PID", Arm.pid);
    }

    public void autonomousPeriodic() {
	Autonomous.fallBack();
    }

    public void teleopPeriodic() {

	if (xboxDriver.bButton()) {
            DriveTrain.maintainPosition();
	} else {
	    DriveTrain.humanDrive(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	}
	
        if (xboxDriver.yButton()) {
            DriveTrain.encoderInit = false;
	    Autonomous.reset();
        }
        
        if (xboxDriver.lBumper()) {
	    Shifting.LowGear();
	} else if (!xboxDriver.bButton()) {
	    Shifting.HighGear();
	}
        
	if (Math.abs(xboxManip.rightStickYAxis()) > 0.2) {
	    Arm.setArmCoarseAdjustment(-xboxManip.rightStickYAxis());
	} else if (Math.abs(xboxManip.leftStickYAxis()) > 0.2) {
	    Arm.setArmFineAdjustment(-xboxManip.leftStickYAxis());
	} else if (xboxManip.aButton()) {
            //Arm.setPIDCustomIngest();
            Arm.setPIDIngest();
	    Arm.isLow = true;
	} else if (xboxManip.bButton()) {
            //Arm.setPIDAccurateShot();
            Arm.setPIDShoot();
        } else if (xboxManip.xButton()) {
            //Arm.setPIDCustomLoad();
            Arm.setPIDHumanLoad();
        } else if (xboxManip.yButton()) {
            //Arm.setPIDCustomLongShot();
            Arm.setPIDLongShot();
	} else if (xboxManip.startButton()) {
	    //Arm.setPIDCustomTruss();
            Arm.setPIDTrussPass();
        } else if (!Arm.isArmInShootAngle() && Arm.isLow && Arm.autoPID) {
            Arm.setPIDShoot();
        } else {
	    Arm.stop();
	}
	
        if (xboxManip.backButton()) {
            Arm.switchAutoPositioning();
        }
        
	if (xboxManip.lBumper()) {
	    Shooter.stop();
        } else if (xboxManip.rBumper()) {
            Shooter.shooShoot();
	} else if (xboxDriver.rBumper()) {
	    Shooter.shooShoot();
	} else if (xboxDriver.startButton()) {
	    Shooter.shoot();
	} else {
	    Shooter.manualLoad();
        }
        
        if (xboxManip.triggerAxis() > 0.2) {
	    Ingestor.ingest();
        } else if (xboxManip.triggerAxis() < -0.2) {
            Ingestor.exgest();
        }
        
        if(!xboxManip.rBumper() && !xboxDriver.rBumper() && Math.abs(xboxManip.triggerAxis()) < 0.2) {
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
        }
	
        Pneumatics.checkPressure();
        /*
	if (xboxDriver.backButton()) {
	    DriveTrain.encoderInit = false;
	    Autonomous.reset();
	}
        /*
        if(xboxDriver.startButton()) {
            Shooter.increaseShooterTime();
        } else if (xboxDriver.backButton()) {
            Shooter.decreaseShooterTime();
        } 
        
	if(xboxDriver.backButton()) {
	    DriveTrain.driveStraightEncoder(-0.8);
	} else {
	    DriveTrain.humanDrive(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
	}
	
	if(xboxDriver.bButton()) {
	    Constants.DT_DELTA_OFFSET += 0.1;
	} else if (xboxDriver.aButton()) {
	    Constants.DT_DELTA_OFFSET -= 0.1;
        } else if (xboxDriver.yButton()) {
	    Constants.DT_ENCODER_SLOW_MOD += 0.01;
	} else if (xboxDriver.xButton()) {
	    Constants.DT_ENCODER_SLOW_MOD -= 0.01;
	}
        
	if (xboxManip.aButton()) {
	    Constants.ARM_CUSTOM_SECOND_P+=0.001;
	} else if (xboxManip.bButton()) {
	    Constants.ARM_CUSTOM_SECOND_P-=0.001;
	} else if (xboxManip.xButton()) {
	    Constants.ARM_CUSTOM_SECOND_I+=0.0001;
	} else if (xboxManip.yButton()) {
	    Constants.ARM_CUSTOM_SECOND_I-=0.0001;
	} else if (xboxManip.startButton()) {
	    Constants.ARM_CUSTOM_SECOND_D+=0.0005;
	} else if (xboxManip.backButton()) {
	    Constants.ARM_CUSTOM_SECOND_D-=0.0005;
	}
	*/
	//SmartDashboard.putNumber("Gyro", DriveTrain.getGyro());
    	SmartDashboard.putNumber("Pot", Arm.getArmAngle());
	SmartDashboard.putNumber("Left Encoder", DriveTrain.getLeftEncoder());
	SmartDashboard.putNumber("Right Encoder", DriveTrain.getRightEncoder());
	SmartDashboard.putNumber("Encoder Average", DriveTrain.getEncoderAverage());
	//SmartDashboard.putNumber("Shooter Encoder", Shooter.getEncoderDistance());
        SmartDashboard.putBoolean("Shooter Limit Switch", Shooter.getLimit());
        //SmartDashboard.putNumber("Shooter Timer", Constants.SHOOTER_SHOOT_DELAY_TIME);
        //SmartDashboard.putNumber("Offset", Constants.DT_DELTA_OFFSET);
        //SmartDashboard.putNumber("Mod", Constants.DT_ENCODER_SLOW_MOD);
	//SmartDashboard.putNumber("delta", DriveTrain.delta);
        SmartDashboard.putBoolean("PID AUTOMATICALLY IN SHOOT POSITION", Arm.autoPID);
	SmartDashboard.putNumber("pidDiff", Arm.pidDiff);
	SmartDashboard.putNumber("P", Arm.pid.getP());
	SmartDashboard.putNumber("I", Arm.pid.getI());
	SmartDashboard.putNumber("D", Arm.pid.getD());
	//SmartDashboard.putNumber("second P", Constants.ARM_CUSTOM_SECOND_P);
	//SmartDashboard.putNumber("second I", Constants.ARM_CUSTOM_SECOND_I);
	//SmartDashboard.putNumber("second D", Constants.ARM_CUSTOM_SECOND_D);
	//System.out.println(Constants.ARM_CUSTOM_SECOND_I);
    }

    public void testPeriodic() {
	    LiveWindow.run();
	    SmartDashboard.putNumber("Pot", Arm.getArmAngle());
    }
    
}