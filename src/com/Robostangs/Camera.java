/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Robostangs;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author sky
 */
public class Camera {
    private static NetworkTable table = null;
    private static Camera _instance = null;
    private static AxisCamera cam;
        	
    private Camera() {
    	table = NetworkTable.getTable("camera");
        cam = AxisCamera.getInstance("10.5.48.11");
    }
    
    public static Camera getInstance() {
        if (_instance == null) {
            _instance = new Camera();
        }
        return _instance;
    }
	
    public static boolean isConnected() {
    	return table.isConnected();
    }

    /**
     * For verifying connection via console
     */
    public static void outputTest() {
	System.out.println(table.getString("test value", "fail"));
    }

    /**
     * Checks to see if the closest goal is hot or not
     * @return hot
     */
    public static boolean hot() {
	return table.getBoolean("hot", false);
    }
    
    /**
     * Saves an image to the crio
     */
    public static void saveImage() {
        if (cam.freshImage()) {
            ColorImage image = null;
            try {
                image = cam.getImage();
                image.write("../../images/orig" + Timer.getFPGATimestamp() + ".jpg");
                image.free();
            } catch (AxisCameraException ex) {
                ex.printStackTrace();
            } catch (NIVisionException ex) {
                ex.printStackTrace();
            }        
        }
    }

}
