package com.Robostangs;

/**
 *
 * @author Bill Ding
 */
public class CheesyDrive {
    public static CheesyDrive instance;
    private static double oldWheel = 0.0;
    private static double quickStopAccumulator;
	
    private CheesyDrive() {
    	DriveMotors.getInstance();		
    }
	
    public static CheesyDrive getInstance() {
        if(instance == null) {
            instance = new CheesyDrive();
	}
            return instance;
    }
	
    public static void drive(double power, double turn) {
        boolean isQuickTurn = true;
        boolean isHighGear = Shifting.high;
        double wheelNonLinearity;
        double wheel = turn;
        double throttle = power;
        double negInertia = wheel - oldWheel;
	oldWheel = wheel;

	if (isHighGear) {
            wheelNonLinearity = 0.4;
	    // Apply a sin function that's scaled to make it feel better.
	    wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
            Math.sin(Math.PI / 2.0 * wheelNonLinearity);
	    wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
            Math.sin(Math.PI / 2.0 * wheelNonLinearity);
	} else {
	    wheelNonLinearity = 0.3;
	    // Apply a sin function that's scaled to make it feel better.
	    wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
            Math.sin(Math.PI / 2.0 * wheelNonLinearity);
	    wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
            Math.sin(Math.PI / 2.0 * wheelNonLinearity);
	    wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) /
            Math.sin(Math.PI / 2.0 * wheelNonLinearity);
	}
	double leftPwm, rightPwm, overPower;
	double sensitivity = 1.7;
	double angularPower;
	double linearPower;

	// Negative inertia!
	double negInertiaAccumulator = 0.0;
	double negInertiaScalar;
	if (isHighGear) {
            negInertiaScalar = 5.0;
	    sensitivity = Constants.CD_HIGH_SENSITIVITY;
	} else {
            if (wheel * negInertia > 0) {
                negInertiaScalar = 2.5;
	    } else {
		if (Math.abs(wheel) > 0.65) {
                    negInertiaScalar = 5.0;
		} else {
                    negInertiaScalar = 3.0;
		}
            }
	    sensitivity = Constants.CD_LOW_SENSITIVITY;

	    if (Math.abs(throttle) > 0.1) {
                //sensitivity = 1.0 - (1.0 - sensitivity) / Math.abs(throttle);
	    }
        }
	double negInertiaPower = negInertia * negInertiaScalar;
	negInertiaAccumulator += negInertiaPower;

	wheel = wheel + negInertiaAccumulator;
	if (negInertiaAccumulator > 1) {
            negInertiaAccumulator -= 1;
	} else if (negInertiaAccumulator < -1) {
	    negInertiaAccumulator += 1;
	} else {
	    negInertiaAccumulator = 0;
	}
	linearPower = throttle;

	// Quickturn!
	if (isQuickTurn) {
            if (Math.abs(linearPower) < 0.2) {
                double alpha = 0.1;
		quickStopAccumulator = (1 - alpha) * quickStopAccumulator + alpha *
		limit(wheel, 1.0) * 5;
	    }
	    overPower = 1.0;
	    if (isHighGear) {
                sensitivity = 1.0;
	    } else {
                sensitivity = 1.0;
	    }
	    angularPower = wheel;
	} else {
	    overPower = 0.0;
	    angularPower = Math.abs(throttle) * wheel * sensitivity - quickStopAccumulator;
	    if (quickStopAccumulator > 1) {
                quickStopAccumulator -= 1;
	    } else if (quickStopAccumulator < -1) {
		quickStopAccumulator += 1;
	    } else {
		quickStopAccumulator = 0.0;
	    }
	}

	rightPwm = leftPwm = linearPower;
	leftPwm += angularPower;
	rightPwm -= angularPower;

	if (leftPwm > 1.0) {
            rightPwm -= overPower * (leftPwm - 1.0);
	    leftPwm = 1.0;
	} else if (rightPwm > 1.0) {
	    leftPwm -= overPower * (rightPwm - 1.0);
	    rightPwm = 1.0;
	} else if (leftPwm < -1.0) {
	    rightPwm += overPower * (-1.0 - leftPwm);
	    leftPwm = -1.0;
	} else if (rightPwm < -1.0) {
	    leftPwm += overPower * (-1.0 - rightPwm);
	    rightPwm = -1.0;
	}

	DriveMotors.set(leftPwm, leftPwm, rightPwm, rightPwm);
    }
    
    public static void humanDrive(double leftPower, double rightPower) {
        if (Math.abs(leftPower) < 0.15) {
            leftPower = 0;
        }
        drive(leftPower, rightPower);
    }
	
    public static double limit(double v, double limit) {
        return (Math.abs(v) < limit) ? v : limit * (v < 0 ? -1 : 1);
    }
}
