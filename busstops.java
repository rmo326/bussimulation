//This section will create bus stops that do not move. The persons 
//walking around will be friends with their bus stop. 
//This way, people should walk towards the nearest bus stop.
package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import java.awt.Color;
public class busstops implements Steppable{
    Double2D location;
    public Bag mypeople = new Bag();
    public Color stopColor;
    public busstops (Color c, Double2D loc){
        stopColor = c;
        location = loc;               
    }
    
    public Double2D getLocation() {
        return location;
    }
    
    public Color getColor() {
        return stopColor;
    }
    
    public void step(SimState state){
        //Should be setting up the yard
        people busstop= (people) state; 
        Continuous2D yard = people.yard;
        Double2D me = people.yard.getObjectLocation(this);
        MutableDouble2D sumStay = new MutableDouble2D();
        // Bus stops will not move
        sumStay.addIn(new Double2D(0,0));        
        sumStay.addIn(me);
        people.yard.setObjectLocation(this, new Double2D(sumStay));
    }
}


