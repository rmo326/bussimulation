package sim.app.bussimulation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import sim.util.*;
public class BusRoute<E> implements Iterable{
    File Route = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/Route.txt");
    public ArrayList<E> myRoute;
    public String myName;
    
    public BusRoute(ArrayList<E> routeFound){
        myRoute = routeFound;
        myName = "Highland";
    }
    public void setName(String name){
        myName = name;
    }
    
    public String getName (){
        return myName;
    }
    
    public E getStop(int i) {
        return myRoute.get(i);
    }

    public int size() {
        return myRoute.size();
    }

    public RouteIterator<E> iterator (){
        return new RouteIterator<E>(this);
    }
}
 
