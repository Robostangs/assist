/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Robostangs;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 * @author sky
 */
public class Camera {
    private static NetworkTable table = null;
    private static Camera _instance = null;
        	
    private Camera() {
    	table = NetworkTable.getTable("camera");
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

}
