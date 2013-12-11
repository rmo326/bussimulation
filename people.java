package sim.app.bussimulation;
import sim.portrayal.network.*;
import sim.field.continuous.*;
import sim.portrayal.*;
import sim.engine.*;
import sim.display.*;
import sim.field.network.*;
import sim.portrayal.simple.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import sim.util.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
public class people extends SimState{
    public static Continuous2D yard = new Continuous2D(1.0,800,800);
    double randomMultiplier = 0.1;
    public Network Whichstop = new Network(false);
    public Network Buslist = new Network(false);
    public int numpersons = 50;
    public int numpersonsB = 12;
    public int numpersonsC = 50;
    public int numbus= 2;
    public Bag persons;
    public Bag busrouteList = new Bag();
    public Scanner stopReader;
    public Scanner routeReader;
    public Bag myNameList;
    public String routeName;
    public Bag routeList;
 
    public people(long seed){
        super(seed);
        //Bag routeList = new Bag();
        //routeList = busrouteList;
    }
    public Bag getBag(){
        return routeList;
    }
    public void start(){
        super.start();
        // clear the yard
        yard.clear();
        // clear the buddies
        Whichstop.clear();
        //add some people to the yard
        Buslist.clear();
        

        //Lets Make Some bus stops
        try{
            File stopList = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/BusStopsList.txt");
            stopReader = new Scanner(stopList);
            while(stopReader.hasNext()){
                int x = stopReader.nextInt();
                int y = stopReader.nextInt();
                int rgb1 = stopReader.nextInt();
                int rgb2 = stopReader.nextInt();
                int rgb3 = stopReader.nextInt();
                String myName = stopReader.next();
                Double2D q = new Double2D(x,y);
                Color color = new Color(rgb1,rgb2,rgb3);
                busstops b = new busstops(color,q);
                
                people.yard.setObjectLocation(b,q);
                Buslist.addNode(b);
            }
        }
        catch(FileNotFoundException e){
        e.printStackTrace();
        }                                
        Bag stops = Buslist.getAllNodes();
        
       //place people
         for(int i = 0; i < numpersons; i++){
            
            personsA p = new personsA();
            people.yard.setObjectLocation(p,
            new Double2D(people.yard.getWidth()*random.nextDouble(),
            people.yard.getHeight() * random.nextDouble()));
            schedule.scheduleRepeating(p);
            Whichstop.addNode(p);
            }  
            
        persons = Whichstop.getAllNodes();
        //below also sets which bus they want to take
        //should make each person friends with one bus stop. We are making them friends with that bus stop in an organized fashion-should make it easier to put them in a bag at an appropriate time.
        for (int q=0; q<numpersons; q++){
            Object a = persons.get(q);
            int bw = 0; 
            int workplace =(int)(Math.random()*(stops.size()));
            int homeplace = (int)(Math.random()*(stops.size()));
            if (homeplace == workplace) {
                workplace++;
            }
            Object b = stops.get(homeplace);
           
            Object bq = stops.get(workplace);
            
            busstops bs = (busstops)b;
            personsA pa = (personsA)a;
            pa.setPersonColor(bs.getColor());
            pa.setWork((busstops)bq);
            pa.setHome((busstops)b);
            double walktostop = 1;
            Whichstop.addEdge(a, b, new Double(walktostop));
            
        }
  
        //add the bus routes
        
        try{
            File routeList = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/Route.txt");
            routeReader = new Scanner(routeList);
            while(routeReader.hasNext()){
                Integer myNumericName = routeReader.nextInt();
                Integer count = routeReader.nextInt();
                ArrayList<busstops> routeHolder = new ArrayList<busstops>();
                for(int k=0; k<count;k++){
                    Integer x = routeReader.nextInt();
                    routeHolder.add((busstops)stops.get(x));
                }           
                BusRoute<busstops> br = new BusRoute<busstops>(routeHolder); 

                
                if (myNumericName==0){
                    String routeName = "Shreveport";
                }
                else if (myNumericName == 1){
                    String routeName= "Bossier";
                }
                br.setName(routeName);
                busrouteList.add(br);                  
            }
        }
        catch(FileNotFoundException e){
        e.printStackTrace();
        }                               
         
        //add a bus
         for(int i = 0; i < numbus; i++){
            BusRoute mybr = (BusRoute)busrouteList.get(i);            
            bus q = new bus((mybr.getName()),((BusRoute<busstops>)busrouteList.get(i)).iterator());
            for(int p = 0; p< stops.size(); p ++){
                q.addStop((busstops) stops.get(p));
            }

            people.yard.setObjectLocation(q,
            new Double2D(people.yard.getWidth()-75,people.yard.getHeight()-50));
            schedule.scheduleRepeating(q);
        }
    

}
    
    public static void main(String[] args){
        doLoop(people.class, args);
        System.exit(0);
    }
}
