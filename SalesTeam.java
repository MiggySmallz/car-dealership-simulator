import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

/**
 * @author Miguel Nobre
 * Student Number: 500807779
 */

public class SalesTeam
{
    LinkedList<String> list = new LinkedList<String>() ;

    public SalesTeam() //contructor that adds team members to the team or list
    {
        list.addFirst("Hellen");
        list.addFirst("Dave");
        list.addFirst("Henery");
        list.addFirst("Julie");
        list.addFirst("Richard");
    }

    public String person() //returns a random name from the team
    {
        Random rand = new Random();
	    int num = rand.nextInt(list.size());
        return list.get(num);
    }

    public String displayTeam()
    {
        String p = "";
        ListIterator<String> iterator = list.listIterator() ;
		 
		while (iterator.hasNext())
		{
			p = p + (String) iterator.next() + " ";
        }
        return "Team: " + p;
    }

    public int teamSize()
    {
        return list.size();
    }

    public String getPerson(int index)
    {
        return list.get(index);
    }

}