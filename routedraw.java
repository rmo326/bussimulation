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

public class routedraw extends SimState{
    public static Continuous2D routeyard = new Continuous2D(1.0,800,800);
    public Scanner stopReader;
    public Scanner routeReader;
    public Bag routeobjectHolder;
    public Color myrouteColor;
    public Network Buslist = new Network(false);
    public routedraw(long seed){
        super(seed);
    }
    public void start(){
        super.start();
        // clear the yard
        routeyard.clear();
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
        
        //lets make some route pieces
              try{
            File routeList = new File("/home/mathlab/Mason/mason/sim/app/bussimulation/Route.txt");
            routeReader = new Scanner(routeList);
            routeobjectHolder = new Bag();
            
            while(routeReader.hasNext()){
                Integer myNumericName = routeReader.nextInt();
                Integer count = routeReader.nextInt();                               
                if (myNumericName==0){
                    Color myrouteColor = new Color(255,255,255);
                }
                else if (myNumericName == 1){
                   Color myrouteColor = new Color (100,20,100);
                }
                Integer x = routeReader.nextInt();    
                for(int k=0; k<count;k++){
                
                    Integer y = routeReader.nextInt();
                    busstops a = (busstops)stops.get(x);
                    busstops b = (busstops)stops.get(y);
                    Double2D aLocation = a.getLocation();
                    Double2D bLocation = b.getLocation();
                    int c = (int)(aLocation.getX());
                    int d = (int)(aLocation.getY());
                    int e =(int)(bLocation.getX());
                    int f = (int)(bLocation.getY());    
                    routeobject r =new routeobject(myrouteColor, c, d, e, f);
                    routeobjectHolder.add(r);     
                }                          
            }
        }
        catch(FileNotFoundException e){
        e.printStackTrace();
        }                          

    }
    public static void main(String[] args){
        doLoop(routedraw.class, args);
        System.exit(0);
    }
}
