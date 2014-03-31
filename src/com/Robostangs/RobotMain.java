//LIVOOOOOOOOOOONIA

package com.Robostangs;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
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
	Autonomous.fallBack();
	//Autonomous.twoBallz();
        
        SmartDashboard.putNumber("Pot", Arm.getArmAngle());
        Log.write("Autonomous," + ArmMotors.getBatteryVoltage() + "," + ArmMotors.getJagCurrent() + "," + DriveMotors.getTotalJagCurrent() + "," + Arm.pid.isEnable() + "," + Arm.pid.getSetpoint() + "," + Arm.getArmAngle() + "," + Shooter.isShooting);
    }
    
    public void disabledPeriodic() {
        Autonomous.reset();
    }
    
    public void teleopInit() {
        Pneumatics.startTimer();
    }
    
    public void teleopPeriodic() {
        if (xboxDriver.bButton()) {
            DriveTrain.maintainPosition();
        } else {
            DriveTrain.humanDrive(xboxDriver.leftStickYAxis(), xboxDriver.rightStickYAxis());
            DriveTrain.encoderInit = false;
        }

        if (xboxDriver.lBumper()) {
            Shifting.LowGear();
        } else if (!xboxDriver.bButton()) {
            Shifting.HighGear();
        }

        if (Math.abs(xboxManip.rightStickYAxis()) > 0.2) {
            Arm.setArmCoarseAdjustment(xboxManip.rightStickYAxis());
        } else if (Math.abs(xboxManip.leftStickYAxis()) > 0.2) {
            Arm.setArmFineAdjustment(xboxManip.leftStickYAxis());
        } else if (xboxManip.aButton()) {
            Arm.setPIDIngest();
        } else if (xboxManip.bButton()) {
            Arm.setPIDShoot();
        } else if (xboxManip.xButton()) {
            Arm.setPIDHumanLoad();
        } else if (xboxManip.yButton()) {
            Arm.setPIDAutonShot(510);
        } else if (xboxManip.startButton()) {
            Arm.setPIDAutonShot(485);
        } else if (xboxManip.backButton()) {
            Arm.setPIDHalfwayShot();
        } else if (!Arm.isArmInShootAngle() && Arm.isLow) {
            Arm.setPIDShoot();
        } else {
            Arm.stop();
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

        if(!xboxManip.rBumper() && !xboxDriver.rBumper() && Math.abs(xboxManip.triggerAxis()) < 0.2
                && !xboxDriver.startButton()) {
            Ingestor.setSpeed(Constants.INGESTOR_CONSTANT_INGEST_SPEED);
        }

        Pneumatics.checkPressureTimer();

        Log.write("Teleoperated," + ArmMotors.getBatteryVoltage() + "," + DriveMotors.getTotalJagCurrent() + "," + Arm.pid.isEnable() + "," + Arm.pid.getSetpoint() + "," + Arm.getArmAngle() + "," + Shooter.isShooting);
        SmartDashboard.putNumber("Pot", Arm.getArmAngle());
        SmartDashboard.putBoolean("Shooter Limit Switch", Shooter.getLimit());
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