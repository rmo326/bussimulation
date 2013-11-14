//writes step functions for the bus. 
package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import java.awt.Color;

//import java.io.Route;
public class bus implements Steppable{

    public Color busColor; 
    public Bag myriders = new Bag();
    public Bag mylocations = new Bag();
    public int waittime;
    public int current = 0;
    public static int WAITTIME = 20;
    public int capacity = 40;
    public int busLoad;
    public int currentLoad;
    public boolean stopbus = false;
    public boolean BusatStop = false;
    public int busInfo;
    public RouteIterator<busstops> busRoute;
    public busstops whereAmI;
    public busstops whereWasI;
    
    public bus (RouteIterator<busstops> busRoute){

        busColor = Color.gray;
        waittime = 0;
        currentLoad = 1;
        busLoad = 1;
        busInfo = 10000;
        this.busRoute= busRoute;
        whereAmI = busRoute.next();
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
    

    public void setbusLoad (int z){
        busLoad = z;
    }
    
    public int getcurrentLoad (){
        return 255*((myriders.size())/capacity);
    }
    
    public int getbusLoad (){
        return busLoad;
    }
    public void setbusInfo(int s){
        busInfo = s;
    }
    
    public int getbusInfo(){
        return busInfo;
    }
    
    
    public void step(SimState state){
        
        



        //Should be setting up the yard
        people bus = (people) state; 
        Continuous2D yard = people.yard;
        Double2D me = people.yard.getObjectLocation(this);
        MutableDouble2D sumDrive = new MutableDouble2D();
        MutableDouble2D driveVector = new MutableDouble2D();
        Double2D goal = whereAmI.getLocation();
//        Double2D goal = ((busstops) mylocations.get(current)).getLocation();
        
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
        if ( !BusatStop && driveVector.length() <.99){              
            BusatStop = true;
            waittime = WAITTIME;
                        
            busstops bst = whereAmI;

            //drops people off 
            for(int i =0;i<myriders.size();i++){
                personsA peop = (personsA)myriders.get(i);
                if (peop.leave(bst)) {
                    myriders.remove(peop);//
                    peop.onBus(null);
                    peop.toggleGoal();
                    i--;                   
                }  
            }
            
            //picks people up
            while (myriders.size() < capacity && bst.mypeople.size() > 0) {
                personsA peep = (personsA)((bst).mypeople.remove(0));
                peep.onBus(this);
                peep.setStop(false);
                myriders.add(peep);            
            }
            whereWasI = whereAmI;
            whereAmI = busRoute.next();
//            current++;
//            current %= mylocations.size();
        }  
        if (BusatStop && driveVector.length() <.95){
            
//            int s =current-1;
//            setbusInfo(s);
//            if (current == 0 ){
//                setbusInfo(mylocations.size()-1);
//            }
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
        System.out.println ("where?" +busInfo);
        System.out.println("waittime = " + waittime);
        System.out.println("BusAtStop = " + BusatStop);
        System.out.println("driveVector = " + driveVector.length());       
        System.out.println("goalx = " + goal.x);
        System.out.println("goaly = " + goal.y);
        System.out.println("current = " + current);
        
    }
}
