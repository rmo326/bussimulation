//This section will create bus stops that do not move. The persons 
//walking around will be friends with all bus stops. 
//This way, people should walk towards the nearest bus stop.
package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
public class busstops implements Steppable{
    public void step(SimState state){
        //Should be setting up the yard
        people busstop= (people) state; 
        Continuous2D yard = people.yard;
        Double2D me = people.yard.getObjectLocation(this);
        MutableDouble2D sumStay = new MutableDouble2D();
        // Bus stops will not move
        people.yard.setObjectLocation(this, new Double2D(sumStay));
    }
}


