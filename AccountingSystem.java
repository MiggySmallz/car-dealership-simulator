import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */

public class AccountingSystem
{
    ArrayList<Transaction> newTransactions = new ArrayList<Transaction>();
    int transactionID = 0;
    Transaction trans;
    Car car;
    String salesPerson;
    String type;
    double price;    
         
    public String addTransaction(Calendar date, Car car, String salesPerson, String type, double salePrice)  //creates a transation object with given variables and adds it to ArrayList newTransactions
    {
       
        AccountingSystem test = new AccountingSystem();
        Random rand = new Random();
        transactionID = rand.nextInt(99) + 1;                                              //creates random transaction ID from 1->99

        trans = new Transaction(date, car, salesPerson, type, salePrice, transactionID);

        newTransactions.add(new Transaction(date, car, salesPerson, type, salePrice, transactionID));

        test.setID(transactionID);
        test.setCar(car);
        test.setSalesPerson(salesPerson);
        test.setSalePrice(salePrice);
        test.setType(type);
        
                
        return "ID: " + transactionID + " " + trans.display(); 
    }


    public Transaction getTransaction(int id) //returns Transaction related to the given Transaction ID
    {
        Transaction transaction = null;
        for (int i = 0; i<newTransactions.size(); i++)
        {
            Transaction trans = newTransactions.get(i);
            if (trans.transactionID == id)
            {
                transaction = newTransactions.get(i);
            }        
        }
        
        return transaction;
    }

  
   public void setID(int id)
   {
        this.transactionID = id;
   }

   public void setCar(Car car)
   {
        this.car = car;
   }

   public void setSalesPerson(String person)
   {
        this.salesPerson = person;
   }

   public void setSalePrice(double price)
   {
        this.price = price;
   }

   public void setType(String type)
   {
        this.type = type;
   }

   
}

