package exercise3.part1;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class ConfigDist {
	
	private OpticalDistanceSensor sensor;
	private DifferentialPilot pilot;
	private final float MAX_RANGE = 800;
	
	
	public ConfigDist() {
		pilot = new DifferentialPilot(5.6, 16.0, Motor.B, Motor.C);
		sensor = new OpticalDistanceSensor(SensorPort.S1);
	}
	
	
	public void run() {
		float range = sensor.getRange();
		float normalisedRange = range / MAX_RANGE;
		float speed = (normalisedRange * 300f);
		pilot.setTravelSpeed((int)speed);
		pilot.forward();
		Thread.yield();
		if(range <= 10) {
			pilot.stop();
			pilot.rotate(90);
		}
	}
	
	/**
	 * Exits the program when the 'ENTER' button is pressed on the robot.
	 */
	public void buttonExit() {
		Button.ENTER.addButtonListener(new ButtonListener() {
			public void buttonPressed(Button b) {
				System.exit(0);
			}

			public void buttonReleased(Button b) {
				System.exit(0);
			}
		});
	}
	
	
	public static void main(String[] args) {
		ConfigDist robot = new ConfigDist();
		Button.waitForAnyPress();
		while(true) {
			robot.run();
		}
		
	}
	
	

}
