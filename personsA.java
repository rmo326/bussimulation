/* This file will create a set of persons who fall under a classification. These persons will extend the people class.
 * For however many types of people we want, we can simply create another Persons class and change the attributes that 
 * need to be changed. */
package sim.app.bussimulation;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
public class personsA implements Steppable{
    public void step(SimState state){
        //Should be setting up the yard
        people person = (people) state; 
        Continuous2D yard = people.yard;
        Double2D me = people.yard.getObjectLocation(this);
        MutableDouble2D sumWill = new MutableDouble2D();
        // We will walk around randomly
        sumWill.addIn(new Double2D(person.randomMultiplier * (person.random.nextDouble() * 1.0 - 0.5),
        person.randomMultiplier * (person.random.nextDouble() * 1.0 - 0.5)));        
        sumWill.addIn(me);
        people.yard.setObjectLocation(this, new Double2D(sumWill));
    }
}
