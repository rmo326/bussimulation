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
    public static Continuous2D yard = new Continuous2D(1.0,500,500);
    double randomMultiplier = 0.1;
    public Network Whichstop = new Network(false);
    public Network Buslist = new Network(false);
    public int numpersons = 50;
    public int numpersonsB = 12;
    public int numpersonsC = 50;
    public int numbusstops = 5;
    public int numbus= 1;
    public Bag persons;
    public Scanner stopReader;
    public people(long seed){
        super(seed);
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
                String myColor = stopReader.next();
                String myName = stopReader.next();
                Double2D q = new Double2D(x,y);
                Color color;
                try {
                    Field field = Color.class.getField(myColor);
                    color = (Color)field.get(null);
                }
                catch(Exception e){
                    color = Color.gray;
                }
                busstops b = new busstops(color,q);
                
                people.yard.setObjectLocation(b,q);
                Buslist.addNode(b);
            }
        }
        catch(FileNotFoundException e){
        e.printStackTrace();
        }
            
        
        
        
        
        
        
        //add some bus stops. because of the constructor for bus stops, we can pass colors in
        /*for(int i = 0; i < numbusstops; i++){        
            Double2D x =  new Double2D(people.yard.getWidth()-(i*50)-25,people.yard.getHeight()-(i*50)-25);  
            busstops b = new busstops(bcolors.get(i), x);
            people.yard.setObjectLocation(b,x);           
            schedule.scheduleRepeating(b);
            Buslist.addNode(b);
            }     */
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
        //should make each person friends with one bus stop. We are making them friends with that bus stop in an organized fashion-should make it easier to put them in a bag at an appropriate time.
        for(int q=0;q<(numpersonsB);q++){ 
            Object a = persons.get(q);
            int bw = 0; 
            Object b = stops.get(bw);
            Object bq = stops.get(bw+1);
            busstops bs = (busstops)b;
            personsA pa = (personsA)a;
            pa.setPersonColor(bs.getColor());
            pa.setWork((busstops)bq);
            pa.setHome((busstops)b);
            double walktostop = 1;
            Whichstop.addEdge(a, b, new Double(walktostop));
            
            }
            
         for(int z=12;z<(numpersonsC);z++){ 
            Object f = persons.get(z);
            int bz = 1; 
            Object ba = stops.get(bz);
            Object bb = stops.get(bz-1);
            busstops bst = (busstops)ba;
            personsA pas= (personsA)f;
            pas.setPersonColor(bst.getColor());
            pas.setWork((busstops)bb);
            pas.setHome((busstops)ba);
            double walktostop = 1;
            Whichstop.addEdge(f, ba, new Double(walktostop));
            }
            
        /*//add a bus route
         for (int ib = 0; ib < numbus; ib++){
             BusRoute br = new BusRoute();
         }*/
         
        //add a bus
         for(int i = 0; i < numbus; i++){            
            bus q = new bus();
            q.addStop((busstops) stops.get(0));
            q.addStop((busstops) stops.get(1));
            q.addStop((busstops) stops.get(2));
            q.addStop((busstops) stops.get(3));
            q.addStop((busstops) stops.get(4));
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
