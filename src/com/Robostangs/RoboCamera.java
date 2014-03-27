package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author Robostangs
 */
public class RoboCamera {
	//NetworkTable table = null;
	AxisCamera cam;
 	
 	public RoboCamera() {
 		//table = NetworkTable.getTable("camera");
		cam = AxisCamera.getInstance("10.5.48.11");
	}
 	
	/*
 	public boolean isConnected() {
 		return table.isConnected();
 	}
 	
 	public void outputTest() {
 		System.out.println(table.getString("test value", "fail"));
 	}
	*/
	
	public void savePicture() {
		try {
			ColorImage image = cam.getImage();
			image.write("../../images/" + Timer.getFPGATimestamp() + ".jpg");
		} catch (AxisCameraException ex) {
			ex.printStackTrace();
		} catch (NIVisionException ex) {
			ex.printStackTrace();
		}
		
	}
	
}
