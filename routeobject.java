package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import java.awt.Color;

public class routeobject implements Steppable{
    public int startLocationx;
    public int startLocationy;
    public int endLocationx;
    public int endLocationy;
    public Color routeColor;
        public routeobject(Color c, int x, int y, int x2, int y2) {
        //make a simple array to pull colors out of..i guess    
        routeColor = c;
        int startLocationx = x;
        int startLocationy =  y;
        int endLocationx = x2;
        int endLocationy = y2;
    }
    public Color getrouteColor() {
        return routeColor;
    }
    public int getStartx(){
        return startLocationx;
    }
        public int getStarty(){
        return startLocationy;
    }
    public int  getEndx(){
        return endLocationx;
    }
        public int  getEndy(){
        return endLocationy;
    }
    public void step(SimState state){
        //Should be setting up the yard
        routedraw route= (routedraw) state; 
        Continuous2D routeyard = routedraw.routeyard;
        Double2D me = routedraw.routeyard.getObjectLocation(this);
        MutableDouble2D sumStay = new MutableDouble2D();
        // Bus routes will not move
        sumStay.addIn(new Double2D(0,0));        
        sumStay.addIn(me);
        routedraw.routeyard.setObjectLocation(this, new Double2D(sumStay));
    }
}
