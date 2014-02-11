package exercise3.part1;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class FollowLine {
	
	
	private DifferentialPilot pilot;
	private LightSensor lSensorLeft;
	private LightSensor lSensorRight;
	private boolean firstRun = true;
	private int stuck_count = 0;
	
	/** Constructor */
	public FollowLine() {
		pilot = new DifferentialPilot(5.6, 16.0, Motor.B, Motor.C);
		lSensorLeft = new LightSensor(SensorPort.S2);
		lSensorRight = new LightSensor(SensorPort.S3);
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
	
	
	
	/** Run method */
	public void run() {
		pilot.setTravelSpeed(20);
		if(firstRun) {
			pilot.forward();
		}
		//pilot.forward();
		int leftValue = lSensorLeft.readNormalizedValue();
		int rightValue = lSensorRight.readNormalizedValue();
		if(leftValue < 470 && rightValue < 380) {
			if(firstRun) {
				firstRun = false;
				return;
			}
			pilot.rotate(-90);
			pilot.forward();
		} else if(leftValue < 420) {
			pilot.rotate(20);
			pilot.forward();
		} else if(rightValue < 330) {
			pilot.rotate(-20);
			pilot.forward();
		}
		
	}
	
	/** Main method */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		FollowLine robot = new FollowLine();
		Delay.msDelay(1000);
		robot.buttonExit();
		while(true) {
			robot.run();
		}
	}

}
