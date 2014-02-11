package exercise3.part2;


import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Grid {

	DifferentialPilot pilot;
	private LightSensor lSensorLeft;
	private LightSensor lSensorRight;
	private Random r = new Random(2);
	
	public Grid() {
		pilot = new DifferentialPilot(5.6, 16.0, Motor.B, Motor.C);
		lSensorLeft = new LightSensor(SensorPort.S2);
		lSensorRight = new LightSensor(SensorPort.S3);
	}
	
	public void run() {
		pilot.setTravelSpeed(10);
		pilot.forward();
		int leftValue = lSensorLeft.readNormalizedValue();
		int rightValue = lSensorRight.readNormalizedValue();
		
		// Random chance to move left or right.
		if(leftValue < 470 && rightValue < 380) {
			Delay.msDelay(650);
			if(leftOrRight() == 1) {
				pilot.rotate(90);
				Delay.msDelay(500);
			} else {
				pilot.rotate(-90);
				Delay.msDelay(500);
			}
		}
		
	}
	
	
	/**
	 * Exits the program when the 'ENTER' button is pressed on the robot.
	 */
	public void buttonExit() {
		Button.ESCAPE.addButtonListener(new ButtonListener() {
			public void buttonPressed(Button b) {
				System.exit(0);
			}

			public void buttonReleased(Button b) {
				System.exit(0);
			}
		});
	}
	
	
	// Returns a value 1 (Left) or 2 (Right)
	public int leftOrRight() {
		return r.nextInt(2) + 1;
	}
	
	
	public static void main(String[] args) {
		Grid robot = new Grid();
		robot.buttonExit();
		Button.waitForAnyPress();
		while(true) {
			robot.run();
		}
		
		
	}
}
