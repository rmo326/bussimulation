/*package sim.app.bussimulation;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import sim.util.*;
public class BusRoute{
File Route = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/Route.txt");
public ArrayList<busstops> myRoute = new ArrayList<busstops>();
public BusRoute(){
    Scanner sc = new Scanner(Route);
     try{
        while(sc.hasNextLine()){
            String line = sc.nextLine();               
            //myRoute.add(line);
        }
        sc.close();
    }
    catch (FileNotFoundException e) {
       e.printStackTrace();
    }  
}
   
}*/
