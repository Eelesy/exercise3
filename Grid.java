package exercise3.part2;

import java.util.ArrayList;
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
	private ArrayList<Integer> instrArray;

	public Grid() {
		pilot = new DifferentialPilot(5.6, 16.0, Motor.B, Motor.C);
		lSensorLeft = new LightSensor(SensorPort.S2);
		lSensorRight = new LightSensor(SensorPort.S3);
		instrArray = new ArrayList<Integer>();
	}

	public void run() {
		pilot.setTravelSpeed(10);
		pilot.forward();
		int leftValue = lSensorLeft.readNormalizedValue();
		int rightValue = lSensorRight.readNormalizedValue();

		// Random chance to move left or right.
		if (leftValue < 470 && rightValue < 380) {
			if (instrArray.size() == 0) {
				pilot.forward();
				Delay.msDelay(2000);
				System.exit(0);
			}
			int topElement = instrArray.get(0);
			Delay.msDelay(650);
			
			/*
			 * if(leftOrRight() == 1) { 
			 * 		pilot.rotate(90); 
			 * 		Delay.msDelay(500); 
			 * }
			 * else { 
			 *		pilot.rotate(-90); 
			 * 		Delay.msDelay(500); 
			 * }
			 */

			if (topElement == 0) {
				pilot.rotate(90);
				instrArray.remove(0);
				Delay.msDelay(500);
			} else {
				pilot.rotate(-90);
				instrArray.remove(0);
				Delay.msDelay(500);
			}
		}

	}

	/**
	 * Acquires the list of instructions for the robot to follow.
	 */
	public void instructions() {
		while (!Button.ENTER.isDown()) {
			Button.waitForAnyPress();
			if (Button.LEFT.isDown()) {
				instrArray.add(0);
				System.out.println("left");
			}

			if (Button.RIGHT.isDown()) {
				instrArray.add(1);
				System.out.println("right");
			}
		}
		System.out.println(instrArray);
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
/*	public int leftOrRight() {
		return r.nextInt(2) + 1;
	}*/

	public static void main(String[] args) {
		Grid robot = new Grid();
		robot.buttonExit();
		robot.instructions();
		while (true) {
			robot.run();
		}

	}
}
