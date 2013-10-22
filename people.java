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

public class people extends SimState{
    public static Continuous2D yard = new Continuous2D(1.0,100,100);
    double randomMultiplier = 0.1;
    public Network Whichstop = new Network(false);
    public Network Buslist = new Network(false);
    public int numpersons = 25;
    public int numpersonsB = 12;
    public int numpersonsC = 25;
    public int numbusstops = 2;
    public int numbus= 1;
    public Bag persons;
    
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
     
        //Below is the list of bus stop (and therefore people) colors
        ArrayList<Color> bcolors = new ArrayList<Color>();
        bcolors.add(Color.green);
        bcolors.add(Color.red);
        
        //add some bus stops. because of the constructor for bus stops, we can pass colors in
        for(int i = 0; i < numbusstops; i++){        
            Double2D x =  new Double2D(people.yard.getWidth()-(i*50)-25,people.yard.getHeight()-(i*50)-25);  
            busstops b = new busstops(bcolors.get(i), x);
            people.yard.setObjectLocation(b,x);
            
            schedule.scheduleRepeating(b);
            Buslist.addNode(b);
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
        //should make each person friends with one bus stop. We are making them friends with that bus stop in an organized fashion-should make it easier to put them in a bag at an appropriate time.
        for(int q=0;q<(numpersonsB);q++){ 
            Object a = persons.get(q);
            int bw = 0; 
            Object b = stops.get(bw);
            busstops bs = (busstops)b;
            personsA pa = (personsA)a;
            pa.setColor(bs.getColor());
            double walktostop = 1;
            Whichstop.addEdge(a, b, new Double(walktostop));
            }
            
         for(int z=12;z<(numpersonsC);z++){ 
            Object f = persons.get(z);
            int bz = 1; 
            Object ba = stops.get(bz);
            busstops bst = (busstops)ba;
            personsA pas= (personsA)f;
            pas.setColor(bst.getColor());
            double walktostop = 1;
            Whichstop.addEdge(f, ba, new Double(walktostop));
            }
            
             /*for(int q=0;q<(numpersonsA);q++){ 
            Object a = persons.get(q);
            int bw = random.nextInt(stops.numObjs); 
            Object b = stops.get(bw);
            busstops bs = (busstops)b;
            personsA pa = (personsA)a;
            pa.setColor(bs.getColor());
            double walktostop = 1;
            Whichstop.addEdge(a, b, new Double(walktostop));
            }*/
            
        //add a bus
         for(int i = 0; i < numbus; i++){            
            bus q = new bus();
            q.addStop((busstops) stops.get(0));
            q.addStop((busstops) stops.get(1));
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
