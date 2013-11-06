/* This file will create a set of persons who fall under a classification. These persons will extend the people class.
 * For however many types of people we want, we can simply create another Persons class and change the attributes that 
 * need to be changed. */
package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import java.awt.Color;

 
public class personsA implements Steppable{

    public bus mybus;
    public Color myColor;
    public Color originalcolor;
    public boolean atStop = false;
    public busstops myWork;
    public busstops myHome;
    public busstops goal;  
    public personsA() {
        myColor = Color.blue;
    }
    
    public void setWork (busstops bh) {
        myWork = bh;
        goal = myWork;
    }
    public busstops getWork () {
        return myWork;
    }
   public void setHome (busstops bh) {
       myHome = bh;
   }
   public busstops getHome () {
       return myHome;
   }
   
    public Color getPersonColor() {
        return myColor;
    }
    
    public void setPersonColor(Color c) {
        myColor = c;
        originalcolor = c;
    }
   
    public void onBus(bus b) {
        mybus = b;
        if (mybus != null) {
            myColor = Color.blue;
        }
        else {
            myColor = originalcolor;
        }
    }
    
    public void toggleGoal(){
        if (goal == myWork){
                goal=myHome;
            }
        else{
            goal= myWork;
        }
    }
    public boolean leave (busstops bst){
        if (bst == goal){
            return true;
        }
        else{
            return false;
        }
    }
    public void setStop (boolean d){
        atStop=d;
    }
    
    public void step(SimState state){
        //Should be setting up the yard
        people person = (people) state; 
        Continuous2D yard = people.yard;
        Double2D me = people.yard.getObjectLocation(this);
        MutableDouble2D sumWill = new MutableDouble2D();
        MutableDouble2D forceVector = new MutableDouble2D();
        Bag out = person.Whichstop.getEdges(this, null);
        
        if (mybus == null) {
            //tells them to stay at work.  until they get on a bus. We dont want them walking home. 
            if (goal == myHome){
            Double2D him = people.yard.getObjectLocation(myWork);
            forceVector.setTo ((him.x - me.x), (him.y - me.y));
            //people get on the bus when appropriate
                if ( !atStop && forceVector.length() <5){
                busstops bs = (busstops)myWork;
                bs.mypeople.add(this);
                atStop = true;
                }
            sumWill.addIn(forceVector.normalize());
            }
            //tells them to stay at home until they get on a bus. We dont want them walking to work. 
            else if (goal ==myWork){
                Double2D him = people.yard.getObjectLocation(myHome);           
                forceVector.setTo ((him.x - me.x), (him.y - me.y));
                //people get on the bus when appropriate
                if ( !atStop && forceVector.length() <5){
                busstops bs = (busstops)myHome;
                bs.mypeople.add(this);
                atStop = true;
                }
            sumWill.addIn(forceVector.normalize());
           }
        }
   

        //makes them follow the bus
        else {
                 Double2D him = people.yard.getObjectLocation(mybus);
                 forceVector.setTo((him.x - me.x), (him.y - me.y)); 
                 sumWill.addIn(forceVector.normalize());
  
        }
        sumWill.addIn(me);
        people.yard.setObjectLocation(this, new Double2D(sumWill));
       
    }
}
