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

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.*;

public class ConveyorBelt {
	
	/*Function to rotate the first sorting arm*/
/*	public static void rotateArm1(){
		//initialize the motor
		EV3LargeRegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.B);
		r2.resetTachoCount();
		//Delay to allow time for the brick to reach the arm
		Delay.msDelay(500);
		//rotate the arm forward
		r2.rotate(60);
		Delay.msDelay(500);
		//rotate the arm back
		r2.rotate(-58);
		r2.rotate(-5); //minor adjustment to get the arm to original position
		r2.close();
		return;
	}
	
	/*Function to rotate the second sorting arm*/
/*	public static void rotateArm2(){
		//initialize the motor
		EV3LargeRegulatedMotor r3 = new EV3LargeRegulatedMotor(MotorPort.C);
		r3.resetTachoCount();
		//Delay to allow time for the brick to reach the arm
		Delay.msDelay(1025);
		//rotate the arm forward
		r3.rotate(60);
		Delay.msDelay(500);
		//rotate the arm back
		r3.rotate(-58);
		r3.rotate(-5); //minor adjustment to get the arm to original position
		r3.close();
		return;
	}
	*/
	public static void main(String[] args) {
		
		int redCount = 0, greenCount = 0, yellowCount = 0, blueCount = 0;
		
		//initialize the motor
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
				if (clrSample[0] == 0){
					//drawString(java.lang.String "Red", int 5, int 5)
					System.out.println("Red");
					redCount++;
					//Initialize thread each time 
					RotateArm1 thread1 = new RotateArm1();
					thread1.start();
					//rotateArm1();
				}
				else if (clrSample[0] == 1){
					System.out.println("Green");
					greenCount++;
					RotateArm2 thread2 = new RotateArm2();
					thread2.start();
					//rotateArm2();
				}
				else if (clrSample[0] == 2){
					System.out.println("Blue");
					blueCount++;
				}
				else if (clrSample[0] == 3){
					System.out.println("Yellow");
					yellowCount++;
				}
				else 
					System.out.println(" ");
				Delay.msDelay(160); //delay to allow time for next color brick
			}
			
			r1.stop(); // When yellow touch sensor is not pressed, motor stops.
		} while (sampleRed[0] == 0); // Run program while red touch sensor is not pressed.
		
		r1.close();
		sensorYellow.close();
		sensorRed.close();
		clrSensor.close();
	}

}

class RotateArm1 extends Thread {
	public void run() {
		//initialize the motor
		EV3LargeRegulatedMotor r2 = new EV3LargeRegulatedMotor(MotorPort.B);
		r2.resetTachoCount();
		//Delay to allow time for the brick to reach the arm
		Delay.msDelay(500);
		//rotate the arm forward
		r2.rotate(60);
		Delay.msDelay(500);
		//rotate the arm back
		r2.rotate(-58);
		r2.rotate(-5); //minor adjustment to get the arm to original position
		r2.close();
		return;
	}
}

class RotateArm2 extends Thread {
	public void run() {
		//initialize the motor
		EV3LargeRegulatedMotor r3 = new EV3LargeRegulatedMotor(MotorPort.C);
		r3.resetTachoCount();
		//Delay to allow time for the brick to reach the arm
		Delay.msDelay(1025);
		//rotate the arm forward
		r3.rotate(60);
		Delay.msDelay(500);
		//rotate the arm back
		r3.rotate(-58);
		r3.rotate(-5); //minor adjustment to get the arm to original position
		r3.close();
		return;
	}
}