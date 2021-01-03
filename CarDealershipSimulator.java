import java.util.ArrayList;
import java.util.Scanner;
import java.io.* ;
import java.io.IOException;
import java.io.FileNotFoundException ;
import java.lang.RuntimeException;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */
  
public class CarDealershipSimulator 
{
  public static void main(String[] args) throws IOException
  {
		CarDealership dealership = new CarDealership();
		ArrayList<Car> newCars = new ArrayList<Car>();
		String currentCar = "";
		int carIndex = -1;
		int vinNum = 0;
		int lastTransactionNum = -1;
		String inputLine = "";
		boolean awd = false;
		Car.Model carType = null;
		Vehicle.PowerSource engineType = null;
		int month = 0;

		try 
		{                                                         //tries for any exceptions
		Scanner sc = new Scanner(new File("cars.txt"));
		
		for (int i=1; i<=30; i++)                                //Reads from all lines of the "cars.txt" file and adds all cars to the newCars array 
    {
			
			String manu = sc.next();
			String color = sc.next();
			String ct = sc.next();
			if (ct.equals("SEDAN"))
			{
				carType = Car.Model.SEDAN;
			}
			else if (ct.equals("SPORTS"))
			{
				carType = Car.Model.SPORTS;
			}
			else if (ct.equals("MINIVAN"))
			{
				carType = Car.Model.MINIVAN;
			}
			else if (ct.equals("SUV"))
			{
				carType = Car.Model.SUV;
			}

			String eng = sc.next();
			if (eng.equals("GAS_ENGINE"))
			{
				engineType = Vehicle.PowerSource.GAS_ENGINE;
			}
			else if (eng.equals("ELECTRIC_MOTOR"))
			{
				engineType = Vehicle.PowerSource.ELECTRIC_MOTOR;
			}
			 
			double sr = sc.nextDouble(); 
			int range = sc.nextInt();
			if (sc.next().equals("AWD"))
			{
				awd = true;
			}
			int price = sc.nextInt();
			if (eng.equals("ELECTRIC_MOTOR")) //adds car as ElectricCar if the scanner reads the motor as an electric motor
			{
				int electric = sc.nextInt();
				newCars.add(new ElectricCar(manu,color,carType,engineType,sr,range,awd,price,electric)); 
			}
			if (eng.equals("GAS_ENGINE")) //adds car as Car if the scanner reads the motor as a regular motor
			{
				newCars.add(new Car(manu,color,carType,engineType,sr,range,awd,price));
			}
			
		}


	}
	catch(IOException e)                                 
	{
		System.out.println("Exception " + e);						//catches IOException 															
	}
	catch (NumberFormatException e)										
  {
      System.out.println("Input is not formatted correctly");        //catches NumberFormatException 
	}
	catch(NoSuchElementException e)
	{
		System.out.println("File is empty");           //catches NoSuchElementException 
	}
	  
		Scanner scanner = new Scanner(System.in);
	  System.out.print(">");
		
		
		
		
	  while (scanner.hasNextLine())
	  {
		  inputLine = scanner.nextLine();                                                 
		  if (inputLine == null || inputLine.equals("")) 
          {
			System.out.print("\n>");
            continue;
		  }
			
			Scanner commandLine = new Scanner(inputLine);
			String command = commandLine.next();
			
		  
		  if (command == null || command.equals(""))   //if command has a space, check SALES TEAM
		  {
             System.out.print("\n>");
	         continue;
			}
			else if (inputLine.indexOf(' ')>0) 
			{
				if (inputLine.equals("SALES TEAM"))
		    {
			  	dealership.salesTeam();
				}
				else if (inputLine.equals("SALES TOPSP"))
		    {
			  	dealership.topSP();
				}
				else if (inputLine.equals("SALES STATS"))
		    {                                           
					dealership.salesStats();
				} 
				else if (command.equals("SALES"))
		    {
					try
					{																							//tries for any exceptions
					month = commandLine.nextInt();  
					dealership.salesMonth(month);
					}
					catch (InputMismatchException e)
    			{
      			System.out.print("Invalid month");
					}
					catch (NoSuchElementException e)
    			{
      			System.out.print("Enter valid month");
					}

					
				} 
				else if (command.equals("BUY"))
		    {
					
					try
					{
					vinNum = commandLine.nextInt();  
					}
					catch (InputMismatchException e)
    			{
      			System.out.print("");
					}
					


					try
					{																								//tries to catch exceptions such as Invalid VIN number
					currentCar = dealership.buyCar(vinNum); 

					Scanner test = new Scanner(currentCar);
					String command2 = test.next();
					lastTransactionNum = test.nextInt();    
					}
					catch (NullPointerException e)
    			{
      			System.out.print("The car list is empty");
					}
					catch (NoSuchElementException e)
    			{
      			System.out.println("Invalid VIN number");
					}

			  	if (currentCar != null)                                                            
			  	{																																																									 
				  	System.out.println(currentCar);                   
			  	}																																				
			  	else                                                                     
			    	System.out.println("Invalid argument");          										      
				} 
				else if (command.equals("FPR"))
		  	{
			  	double minPrice = 0; 
			  	double maxPrice = 0;

			  	if (!commandLine.hasNextDouble())
			  	{				  
					  System.out.println("Invalid arguements");
				 	 continue;
			  	}
				  minPrice = commandLine.nextDouble();
				  if (!commandLine.hasNextDouble())
				  {				  
					  System.out.println("Invalid arguements");
					  continue;
				  }	  
		  	    maxPrice = commandLine.nextDouble();
		  	    if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice)
		  	    {				  
					  System.out.println("Invalid arguements");
					  continue;
				  }	
				  dealership.filterByPrice(minPrice,maxPrice);
		 	  }
			}
		  else if (command.equals("L"))
		  {
			  dealership.displayInventory();
		  }
		  else if (command.equals("Q") || command.equals("QUIT"))
		  {	  
			  System.exit(0);
		  }
		  else if (command.equals("RET"))
			{

			  if (lastTransactionNum != -1 && dealership.lastBoughtDate != null)                                                        
			  {																																						         
			  dealership.returnCar(lastTransactionNum);                                            
				currentCar = "";   
				lastTransactionNum = -1;                                               
				}
				else                                                                       
			  System.out.println("There is no recently bought car"); 
		  }
		  else if (command.equals("ADD"))
		  {
			  dealership.addCars(newCars);
		  }
		  else if (command.equals("SPR"))
		  {
			  dealership.sortByPrice();
		  }
		  else if (command.equals("SSR"))
		  {
			  dealership.sortBySafetyRating();
		  }
		  else if (command.equals("SMR"))
		  {
			  dealership.sortByMaxRange();
		  }
		  else if (command.equals("FEL"))
		  {
			  dealership.filterByElectric();
		  }
		  else if (command.equals("FAW"))
		  {
			  dealership.filterByAWD();
			}
			else if (command.equals("FCL"))
		  {
			  dealership.filtersClear();
			}
			else if (command.equals("SALES"))
		  {
			  dealership.yearTransactions();
			}
			
		  System.out.print("\n>");
		}
	
	
  }
}
