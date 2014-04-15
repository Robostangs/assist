//STATE CHAMP

package com.Robostangs;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Thunderbird
 */
public class RobotMain extends IterativeRobot {
    private static XBoxController xboxDriver;
    private static XBoxController xboxManip;
    public static CheesyVisionServer vision = CheesyVisionServer.getInstance();
    
    public void robotInit() {
        xboxDriver = new XBoxController(Constants.XBOX_DRIVER_POS);
	xboxManip = new XBoxController(Constants.XBOX_MANIP_POS);
        
        Arm.getInstance();
	Autonomous.getInstance();
	CheesyDrive.getInstance();
	DriveTrain.getInstance();
        Ingestor.getInstance();
	Pneumatics.getInstance();
	Shifting.getInstance();
	Shooter.getInstance();
        //Log.getInstance();
        vision.setPort(Constants.CHEESY_VISION_SERVER_PORT);
        vision.start();
        
	LiveWindow.addActuator("Arm", "Motor", ArmMotors.armJag);
	LiveWindow.addSensor("Arm", "Pot", Arm.apot);
	LiveWindow.addSensor("Arm", "PID", Arm.pid);
    }

    public void autonomousInit() {
        vision.reset();
        vision.startSamplingCounts();
    }
    
    public void disabledInit() {
        vision.stopSamplingCounts();
        Autonomous.reset();
    }
    
    public void autonomousPeriodic() {
        Autonomous.setHot(vision.getLeftStatus());
	Autonomous.oneBallAutonomous();
        //Autonomous.setHot(vision.getRightStatus());
	//Autonomous.twoBallAutonomous();
        //Autonomous.threeBallAutonomous();
        
        //Log.write("Autonomous," + ArmMotors.getBatteryVoltage() + "," + ArmMotors.getJagCurrent() + "," + DriveMotors.getTotalJagCurrent() + "," + Arm.pid.isEnable() + "," + Arm.pid.getSetpoint() + "," + Arm.getArmAngle() + "," + Shooter.isShooting);
    }
    
    public void teleopInit() {
        Pneumatics.startTimer();
        //vision.reset();
        //vision.startSamplingCounts();
    }
    
    public void teleopPeriodic() {
        if (xboxDriver.bButton()) {
            DriveTrain.maintainPosition();
        } else {
            //CheesyDrive.humanDrive(xboxDriver.rightStickYAxis(), xboxDriver.leftStickXAxis());
            DriveTrain.humanDrive(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
            DriveTrain.encoderInit = false;
        }

        if (xboxDriver.lBumper()) {
            Shifting.lowGear();
        } else if (!xboxDriver.bButton()) {
            Shifting.highGear();
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
            Arm.setPIDCustomShot(510 + Constants.ARM_POT_DIFF);
        } else if (xboxManip.startButton()) {
            Arm.setPIDCustomShot(554);
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
        } else if (xboxManip.backButton()) {
            Shooter.set(Constants.SHOOTER_LOAD_POWER);
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

        Pneumatics.checkPressureTimer();

        //Log.write("Teleoperated," + ArmMotors.getBatteryVoltage() + "," + DriveMotors.getTotalJagCurrent() + "," + Arm.pid.isEnable() + "," + Arm.pid.getSetpoint() + "," + Arm.getArmAngle() + "," + Shooter.isShooting);
        SmartDashboard.putNumber("Pot", Arm.getArmAngle());
        SmartDashboard.putBoolean("Shooter Prox Sensor", Shooter.getLimit());
        //SmartDashboard.putBoolean("Is Hot", vision.getLeftStatus());
        //SmartDashboard.putNumber("Gyro", DriveTrain.getGyro());
        //SmartDashboard.putNumber("Left Encoder", DriveTrain.getLeftEncoder());
        //SmartDashboard.putNumber("Right Encoder", DriveTrain.getRightEncoder());
        //SmartDashboard.putNumber("Encoder Average", DriveTrain.getEncoderAverage());
    }

    public void testPeriodic() {
	LiveWindow.run();
	SmartDashboard.putNumber("Pot", Arm.getArmAngle());
    }
}