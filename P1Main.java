package pack;

/* PROJECT 1: Create Something Interesting
 * 
 * Ernest Curioso
 * Sandra Dheming Lemus
 * Priya Malavia
 *  
 * Comp 598EA: Embedded Applications
 * Section #15400
 * Professor Wiegley
 * Spring 2016
 */

import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.*;

public class P1Main {
	
	public static void main(String[] args) {
		
		EV3MediumRegulatedMotor r1 = new EV3MediumRegulatedMotor(MotorPort.A);
		
		EV3TouchSensor sensorRed = new EV3TouchSensor(SensorPort.S2);
		SensorMode touchRed = sensorRed.getTouchMode();
		float[] sampleRed = new float[touchRed.sampleSize()];
		// Touch sensor to end program
		
		EV3TouchSensor sensorYellow = new EV3TouchSensor(SensorPort.S3);
		SensorMode touchYellow = sensorYellow.getTouchMode();
		float[] sampleYellow = new float[touchYellow.sampleSize()];
		// Touch sensor to start/stop motor r1
		
		EV3ColorSensor clrSensor = new EV3ColorSensor(SensorPort.S1);
		SensorMode clrMode = clrSensor.getColorIDMode();
		float[] clrSample = new float[clrMode.sampleSize()];
		// Color sensor
		
		touchRed.fetchSample(sampleRed,0); // Fetches touch mode (1 is pressed, 0 is not pressed).
		
		do {
			touchRed.fetchSample(sampleRed,0); // Constant fetching (checking) of touch modes are needed.
			touchYellow.fetchSample(sampleYellow,0);
			
			while (sampleYellow[0] == 1){ // While yellow touch sensor is being pressed.
				touchRed.fetchSample(sampleRed,0);
				touchYellow.fetchSample(sampleYellow,0);
				r1.forward();
				
				clrMode.fetchSample(clrSample,0); // Fetches color mode (Numbers 1 through 7 correspond to a certain color).
				if (clrSample[0] == 0)
					System.out.println("Red");
				else if (clrSample[0] == 1)
					System.out.println("Green");
				else if (clrSample[0] == 2)
					System.out.println("Blue");
				else if (clrSample[0] == 3)
					System.out.println("Yellow");
				else 
					System.out.println(" ");
			}
			
			r1.stop(); // When yellow touch sensor is not pressed, motor stops.
		} while (sampleRed[0] == 0); // Run program while red touch sensor is not pressed.
		
		r1.close();
		sensorYellow.close();
		sensorRed.close();
		clrSensor.close();
	}
}
