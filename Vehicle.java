import java.util.Random;

/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */


public class Vehicle 
{
	public static void main(String[] args)
	{
		Vehicle test;
		
		test = new Vehicle("Honda", "red", 4, Vehicle.PowerSource.ELECTRIC_MOTOR);	
		System.out.println(test.display());
	}
	public enum PowerSource
	{
		GAS_ENGINE, DIESEL_ENGINE, ELECTRIC_MOTOR;
	}
	
	public PowerSource power;
	String manuf;
	String color;
	int	numWheels, vin = 100;
	
	public Vehicle()
	{
		this.manuf = "";
	}
	

	/**
 	* Creates a new Vehicle object
 	* @param manuf
 	* @param color
 	* @param numWheels
 	* @param power	
 	*/


	public Vehicle(String manuf, String color, int numWheels, PowerSource power)
	{
	  this.manuf     = manuf;
	  this.color     = color;
	  this.numWheels = numWheels;
	  this.power     = power;
	  Random rand = new Random();
	  this.vin = vin + rand.nextInt(499);
	}
	
	public String display()
	{
		return  "VIN: " + vin + " " + manuf + " " + color;
	}
	
	public boolean equals(Object other)
	{
		Vehicle otherV = (Vehicle) other;
		return power == otherV.power && manuf.equals(otherV.manuf) && numWheels == otherV.numWheels;
	}
}
