import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.NoSuchElementException;

/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */


public class CarDealership 
{
  ArrayList<Car> cars;
  
  // Filters
  boolean electricFilter = false;
  boolean priceFilter = false;
  double  priceMin    = 0;
  double  priceMax    = 0;
  boolean AWDFilter = false;
  Car carRemoved = null;
  int index = 0;
  int carSold = 0;
  int carReturned = 0;
  String randomPerson = "";
  Calendar lastBoughtDate;
  Calendar returnDate = new GregorianCalendar(2019,1,1);
  AccountingSystem accSys = new AccountingSystem();
  Map<String, Integer> salesPeople;
  Map<Integer, Integer> salesMonths;
  
	

  public CarDealership()
  {
    cars = new ArrayList<Car>();
    salesPeople = new TreeMap<String, Integer>();
    salesMonths = new TreeMap<Integer, Integer>();
    salesMonths.put(0, 0);
    salesMonths.put(1, 0);
    salesMonths.put(2, 0);
    salesMonths.put(3, 0);
    salesMonths.put(4, 0);
    salesMonths.put(5, 0);
    salesMonths.put(6, 0);
    salesMonths.put(7, 0);
    salesMonths.put(8, 0);
    salesMonths.put(9, 0);
    salesMonths.put(10, 0);
    salesMonths.put(11, 0);
    salesPeople.put("Hellen", 0);
	  salesPeople.put("Dave", 0);
	  salesPeople.put("Henery", 0);
    salesPeople.put("Julie", 0);
    salesPeople.put("Richard", 0);
  }
  
  public void addCars(ArrayList<Car> newCars)
  {
	  cars.addAll(newCars);
	  newCars.clear();
  }
  
  public String buyCar(int vin)                              
  {
    Car car = null;
    double price = 0;
    Calendar date;
    date = new GregorianCalendar(2019,10,28);

    Random rand = new Random(); 
    int month = rand.nextInt(11); //creates random month from Jan->Dec
    int day = rand.nextInt(28);   //creates random day from 1->28
   
    date.set(Calendar.MONTH, month);         
    date.set(Calendar.DAY_OF_MONTH, day);

    SalesTeam p = new SalesTeam();  
    randomPerson = p.person();    //gets a random person from the sales team using person() method

    int index = -1;
    for(int i = 0; i<cars.size();i++)
    {
      car = cars.get(i);
      
      if (car.vin == vin)
      {
        index = i;
        price = car.price;
      }
     
    }
    
    if (index == -1)
    { 
      return "";
    }
    
    
    
    else
    carRemoved = cars.get(index);
    cars.remove(index);

    lastBoughtDate = date;
    carSold++;


    for(Map.Entry<String,Integer> entry : salesPeople.entrySet())       //Adds a sale to the random person who was previously selected 
    {
        String key = entry.getKey();
        Integer value = entry.getValue();
        
        if (key.equals(randomPerson))
        {
        salesPeople.replace(randomPerson, value, value+1);
        }
    }




    return accSys.addTransaction(date, carRemoved, randomPerson, "BUY", price);

  }
  





  public void returnCar(int transaction)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");
    int lastBoughtDay = lastBoughtDate.get(Calendar.DAY_OF_MONTH);
    int lastBoughtMonth = lastBoughtDate.get(Calendar.MONTH);
        
    Random rand = new Random();
    int returnDay = lastBoughtDay + rand.nextInt(28-lastBoughtDay);   //creates a Return day that is later in the same month as the Buy transaction of the acar

    returnDate.set(Calendar.DAY_OF_MONTH, returnDay);
    returnDate.set(Calendar.MONTH, lastBoughtMonth);

    Transaction t = accSys.getTransaction(transaction);

    System.out.println(accSys.addTransaction(returnDate, t.car, t.salesPerson, "RET", t.price));


    for(Map.Entry<String,Integer> entry : salesPeople.entrySet())       //Similar to that in buyCar() method, but removes a sale from the given sales person
    {
        String key = entry.getKey();
        Integer value = entry.getValue();
        
        if (key.equals(t.salesPerson))
        {
        salesPeople.replace(t.salesPerson, value, value-1);
        }
    }

    
    carReturned++;
    lastBoughtDate = null;
    cars.add(carRemoved);

  }
  
  public void displayInventory()
  {
	  System.out.println("");
	  
	  for (int i = 0; i < cars.size(); i++)
	  {
		Car car = cars.get(i);
		
		if (priceFilter && (car.price < priceMin || car.price > priceMax))
		   continue;
		
		if (electricFilter && car.power != Vehicle.PowerSource.ELECTRIC_MOTOR)
		   continue;
		
		if (AWDFilter && !car.AWD)
		   continue;
		
		System.out.println(car.display());                           
    }
    
	  System.out.println("");
  }
  
  public void filtersClear() //clears all filters
  {
	  electricFilter = false;
	  priceFilter = false;
	  AWDFilter = false;
  }
  
  public void filterByPrice(double min, double max)
  {
	  priceFilter = true;
	  priceMin    = min;
	  priceMax    = max;
  }
  public void filterByElectric()
  {
	  electricFilter = true;
  }
  public void filterByAWD()
  {
	  AWDFilter = true;
  }
  
  public void sortByPrice() //sorts ArrayList of Cars by price from lowest to highest
  {
	  Collections.sort(cars);
  }
  
  private class SafetyRatingComparator implements Comparator<Car> //compares safetyRating between car a and b
  {
  	public int compare(Car a, Car b)
  	{
  	  if      (a.safetyRating < b.safetyRating) return  1;
  	  else if (a.safetyRating > b.safetyRating) return -1;
  	  else                                      return  0;		  
  	}
  }
  public void sortBySafetyRating() //sorts ArrayList of Cars by Safety Rating from highest to lowest
  {
	Collections.sort(cars,new SafetyRatingComparator());
  }
  
  private class RangeComparator implements Comparator<Car> //compares range between car a and b
  {
  	public int compare(Car a, Car b)
  	{
  	  if      (a.maxRange < b.maxRange) return  1;
  	  else if (a.maxRange > b.maxRange) return -1;
  	  else                              return  0;		  
  	}
  }
  public void sortByMaxRange() //sorts ArrayList of Cars by Range from highest to lowest
  {
	Collections.sort(cars,new RangeComparator());
  }
  
  public void yearTransactions() //prints all transactions for the year
  {
    for (int i=0; i<accSys.newTransactions.size(); i++)
    {
      System.out.println("ID: " + accSys.newTransactions.get(i).transactionID + " " + accSys.newTransactions.get(i).display());
    }
  }

  public void salesTeam() //prints the names of all the sales persons
  {
    SalesTeam team = new SalesTeam();
    System.out.println(team.displayTeam());
  }

  public void topSP() //prints the sales person who sold the most number of cars for the year. If there is a tie, print both.
  {
    int topVal = 0;
    String topSP = "";
    for(Map.Entry<String,Integer> entry : salesPeople.entrySet())
    {                                                                           //Iterates through Map of salesPeople to find the person with the most Sales
        String key = entry.getKey();
        Integer value = entry.getValue();
            
        if (value>topVal)
        {
        topVal = value;
        topSP = key;
        }
        else if (value==topVal)
        {
          topSP = topSP + " & " + key;
        }
            
    }
      System.out.println("Top SP: " + topSP + " " + topVal);
  }

  public void salesMonth(int m) //prints all transactions for a given month m
  {
    int count = 0;
    for (int i = 0; i<accSys.newTransactions.size();i++)
    {
      if (accSys.newTransactions.get(i).date.get(Calendar.MONTH) == m)
      {
      System.out.println("ID: " + accSys.newTransactions.get(i).transactionID + " " + accSys.newTransactions.get(i).display());
      count=1;
      }

    }
    if (m>11) 
    {
      System.out.println("Invalid month"); 
    }
						 
    else if (count==0)
    System.out.println("There are no transactions in this month");
  }

  public void salesStats() //prints all stats about the bought and returned cars of the year
  {
    double totalYearlyPrice = 0;
    int bestMonthCars =0;
    int bestMonthNum = -1;
    Calendar bestMonth = new GregorianCalendar();
    ArrayList<Integer> best = new ArrayList<Integer>();
    Integer key = 0;
    Integer value = 0;
    boolean set = false;
    int bestPrevious = -1;
    
    String result = "";
    

    for (int i = 0; i<accSys.newTransactions.size();i++)                              //Iterates through transaction list and Adds car price to TotalPrice if car is bought or subtracts from TotalPrice if car is Returned
    {
      if (accSys.newTransactions.get(i).type.equals("BUY"))                                
      {
        totalYearlyPrice = totalYearlyPrice + accSys.newTransactions.get(i).price;
      }
      else if (accSys.newTransactions.get(i).type.equals("RET"))
      {
        totalYearlyPrice = totalYearlyPrice - accSys.newTransactions.get(i).price;
      }     

      for(Map.Entry<Integer,Integer> entry : salesMonths.entrySet())       //Iterates through salesMonths Map
      {
        key = entry.getKey();
        value = entry.getValue();
        
        if (accSys.newTransactions.get(i).type.equals("BUY"))              //If there is a transaction of type "BUY" then 1 gets added to the related month
        {

          if(bestPrevious == best.size())
          {
            continue;
          }
          else if (key == accSys.newTransactions.get(i).date.get(Calendar.MONTH))
          {
            salesMonths.replace(key, value, value+1);
          }
          else continue;
            

          if (value+1>bestMonthCars)
          {
            bestMonthCars = value+1;
          }

        }

        if (accSys.newTransactions.get(i).type.equals("RET"))              //If transaction is of type "RET" then 1 gets subtracted from the related month
        {

          if(bestPrevious == best.size())
          {
            continue;
          }
          else if (key == accSys.newTransactions.get(i).date.get(Calendar.MONTH))
          {
            salesMonths.replace(key, value, value+-1);
          }
          
        }

      }    
               
    }

   

    for(Map.Entry<Integer,Integer> entry : salesMonths.entrySet())       //Checks if any other months are tied for best Month and adds them to an ArrayList if yes
      {
        key = entry.getKey();
        value = entry.getValue();

        if (value == bestMonthCars)
        {
          best.add(key);
          //System.out.println(key + ", " + value);
        }

      }

    for (int i=0; i<best.size();i++) 
            {
              bestMonth.set(Calendar.MONTH, best.get(i));
              result = bestMonth.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.CANADA) + " " + result;
            }
            //System.out.println(best.size());
    
    bestPrevious = best.size();

    
    //if (bestPrevious==0)
    //result = "No cars have been bought yet";

    double avgYearlyPrice = totalYearlyPrice / 12;

    System.out.println("Total Sales: " + totalYearlyPrice + " Total Sold: " + carSold + " Avg Sales: " + (int) avgYearlyPrice + 
                      " Total Returned: " + carReturned + " Best Month(s): " + result + " Cars Sold: " + bestMonthCars);

    
    for(Map.Entry<Integer,Integer> entry : salesMonths.entrySet())       //Iterates through salesMonths Map
    {
    key = entry.getKey();
    value = entry.getValue();  
    
      for (int i = 0; i<salesMonths.size();i++)                              //Iterates through transaction list and Adds car price to TotalPrice if car is bought or subtracts from TotalPrice if car is Returned
      {
       salesMonths.replace(key, value, 0);
      }

    }


  }
  

}
