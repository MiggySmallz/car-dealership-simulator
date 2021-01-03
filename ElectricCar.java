/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */

public class ElectricCar extends Car
{
  int    rechargeTime;
  String batteryType;
  

/**
 * Creates a new ElectricCar object
 * @param manuf
 * @param color
 * @param model
 * @param power
 * @param safety
 * @param range
 * @param awd
 * @param price
 * @param rch
 */

  public ElectricCar(String manuf, String color, Model model, Vehicle.PowerSource power, 
		     double safety, int range, boolean awd, double price, int rch)
  {
	  super(manuf, color, model, Vehicle.PowerSource.ELECTRIC_MOTOR, safety, range, awd, price);
	  rechargeTime = rch;
	  batteryType = "Lithium";
  }
  
  public void setRechargeTime(int time)
  {
	  rechargeTime = time;
  }
  
  public void batteryType(String type)
  {
	  batteryType = type;
  }
  
  public String display()
  {
	  return super.display() + " " + "EL, BAT: " + batteryType + " RCH: " + rechargeTime;
  }
}
