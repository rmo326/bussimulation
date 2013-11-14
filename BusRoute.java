package sim.app.bussimulation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import sim.util.*;
public class BusRoute<E> implements Iterable{
    File Route = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/Route.txt");
    public ArrayList<E> myRoute;

    public BusRoute(ArrayList<E> routeFound){
        myRoute = routeFound;
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
 
