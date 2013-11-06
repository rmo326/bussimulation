package sim.app.bussimulation;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import sim.util.*;
public class BusRoute{
File Route = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/Route.txt");
public Bag myRoute = new Bag();
public BusRoute(){
     try{
        Scanner sc = new Scanner(Route);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            System.out.println(""+line);    
            myRoute.add(line);
        }
        sc.close();
    }
    catch (FileNotFoundException e) {
       e.printStackTrace();
    }  
}
   
}
