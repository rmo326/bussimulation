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
    public int numpersonsA = 25;
    public int numbusstops = 2;
    public int numbus= 1;

    
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
            busstops b = new busstops(bcolors.get(i));
            people.yard.setObjectLocation(b,
            new Double2D(people.yard.getWidth()-(i*50)-25,people.yard.getHeight()-(i*50)-25));
            schedule.scheduleRepeating(b);
            Buslist.addNode(b);
            }     
        Bag stops = Buslist.getAllNodes();
        //place people
         for(int i = 0; i < numpersonsA; i++){
            personsA p = new personsA();
            people.yard.setObjectLocation(p,
            new Double2D(people.yard.getWidth()*random.nextDouble(),
            people.yard.getHeight() * random.nextDouble()));
            schedule.scheduleRepeating(p);
            Whichstop.addNode(p);
            }  
            
        Bag persons = Whichstop.getAllNodes();
        //should make each person friends with one bus stop
        for(int q=0;q<(numpersonsA);q++){ 
            Object a = persons.get(q);
            int bw = random.nextInt(stops.numObjs); 
            Object b = stops.get(bw);
            busstops bs = (busstops)b;
            personsA pa = (personsA)a;
            pa.setColor(bs.getColor());
            double walktostop = 1;
            Whichstop.addEdge(a, b, new Double(walktostop));
            }
        //add a bus
         for(int i = 0; i < numbus; i++){            
            bus q = new bus();
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
