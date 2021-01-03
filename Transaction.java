import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */

public class Transaction extends Car
{
    int transactionID;
    Calendar date;
    Car car;
    String salesPerson, transactionType;
    Double price;
    String type;



/**
 * Creates a new Car object
 * @param date
 * @param car
 * @param salesPerson
 * @param salePrice
 * @param type
 * @param transactionID
 */

    public Transaction(Calendar date, Car car, String salesPerson, String type, double salePrice, int transactionID) 
    {
        this.date = date;
        this.car = car;
        this.salesPerson = salesPerson;
        this.transactionType = type;
        this.price = salePrice;
        this.transactionID = transactionID;
        this.type = type;
    }

    public String display()
  {
      SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");                
      return sdf.format(date.getTime()) + " " + transactionType + " SalesPerson: " + salesPerson + " " + car.display(); //there also used to be price here too
      
  }

  public Calendar getDate(Transaction t)
  {
      
      return t.date;
  }

  public int getID()
  {
      return transactionID;
  }

  
}