//writes step functions for the bus. 
package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import java.awt.Color;
public class bus implements Steppable{
    public boolean BusatStop = false;
    public boolean stopbus = false;
    public Color busColor; 
    public Bag myriders = new Bag();
    public Bag mylocations = new Bag();
    public int waittime;
    public static int WAITTIME = 20;
    public int current = 0;
    public int capacity = 10;
    
    public bus (){
        
        //mylocations.add(new Double2D(25,75));
        //mylocations.add(new Double2D(75,75));
        //mylocations.add(new Double2D(75,25));
        //mylocations.add(new Double2D(25,25));       
        busColor = Color.gray;
        waittime = 0;
    }
    
    public void addStop(busstops bs){
        mylocations.add(bs);
    }
    
        public void setbusColor(Color d) {
        busColor = d;
    }
    public Color getbusColor() {
        return busColor;
    }
    
    public void step(SimState state){
        //Should be setting up the yard
        people bus= (people) state; 
        Continuous2D yard = people.yard;
        Double2D me = people.yard.getObjectLocation(this);
        MutableDouble2D sumDrive = new MutableDouble2D();
        MutableDouble2D driveVector = new MutableDouble2D();
        Double2D goal = ((busstops) mylocations.get(current)).getLocation();
        if (waittime == 0) {
            //pretty sure that the problem is the bus cant get away from the first goal fast enough-it gets caught back in
            //to alleviate this, we made the distance to the goal have to be less than .1 for it to catch us in the goal
             
            driveVector.setTo(-(me.x) + (goal.x), -(me.y) + (goal.y));
            if (driveVector.length()>=1){
                BusatStop=false;
                sumDrive.addIn(driveVector.normalize());        

            }
        } 
        
            
        else {
            waittime--;
            sumDrive.addIn(driveVector);
        } 
        
        //checks to see if the bus is close enough to the stop such that the people can get on it
        if ( !BusatStop && driveVector.length() <.95){              
            BusatStop = true;
            waittime = WAITTIME;
            busstops bst = (busstops) mylocations.get(current);
            while (myriders.size() < capacity && bst.mypeople.size() > 0) {
                personsA peep = (personsA)((bst).mypeople.remove(0));
                peep.onBus(this);
                myriders.add(peep);            
            }
            current++;
            current %= mylocations.size();
        }  
        
        /*if (waittime >0){
            //for(int z=0;z<1;z++){ 
            Bag vals = new Bag(Buslist.getEdgesIn(0));                  
                for(int y=0;y<(vals.size());y++){
                    Edge e = (Edge)(vals.get(y));
                    myriders.add(e.getOtherNode(this));
                }
            //}
        }*/
        
            
        sumDrive.addIn(me);
            
        people.yard.setObjectLocation(this, new Double2D(sumDrive));
        System.out.println("waittime = " + waittime);
        System.out.println("BusAtStop = " + BusatStop);
        System.out.println("driveVector = " + driveVector.length());       
        System.out.println("goalx = " + goal.x);
        System.out.println("goaly = " + goal.y);
        System.out.println("current = " + current);
    }
}
