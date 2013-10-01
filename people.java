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

public class people extends SimState{
    public Continuous2D yard = new Continuous2D(1.0,100,100);
    double randomMultiplier = 0.1;
    //public Network Whichstop = new Network(false);
    public int numpersonsA = 25;
    public int numbusstops = 5;
    
    public people(long seed){
        super(seed);
    }
    public void start(){
        super.start();
        // clear the yard
        yard.clear();
        // clear the buddies
        //Whichstop.clear();
        //add some people to the yard
        for(int i = 0; i < numpersonsA; i++){
            personsA p = new personsA();
            people.yard.setObjectLocation(p,
            new Double2D(people.yard.getWidth() * 0.5 + random.nextDouble() - 0.5,
            people.yard.getHeight() * 0.5 + random.nextDouble() - 0.5));
            schedule.scheduleRepeating(p);
            }  
        //add some bus stops
        for(int i = 0; i < numbusstops; i++){
            busstops b = new busstops();
            people.yard.setObjectLocation(b,
            new Double2D(people.yard.getWidth() * 0.5 + random.nextDouble() - 0.5,
            people.yard.getHeight() * 0.5 + random.nextDouble() - 0.5));
            schedule.scheduleRepeating(b);
            }     
    }
    public static void main(String[] args){
        doLoop(people.class, args);
        System.exit(0);
    }
}
