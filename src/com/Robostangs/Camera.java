package com.Robostangs;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Robostangs
 */
public class Camera {
	NetworkTable table = null;
	
	public Camera() {
		table = NetworkTable.getTable("camera");
	}
	
	public boolean isConnected() {
		return table.isConnected();
	}
	
	public void outputTest() {
		System.out.println(table.getString("test value", "fail"));
	}
	
}
