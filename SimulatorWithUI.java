//This will create a GUI for the simulation. No calculations take place in this code, 
// aside from those required to display the data visually. 
package sim.app.bussimulation;
import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.portrayal.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import sim.field.continuous.*;
import sim.field.network.*;
import java.lang.Object;
import java.awt.geom.*;
import java.awt.Graphics;
//The setup stuff that never changes
public class SimulatorWithUI extends GUIState{

    public Display2D display;
    public JFrame displayFrame;
    ContinuousPortrayal2D routePortrayal = new ContinuousPortrayal2D();
    ContinuousPortrayal2D peoplePortrayal = new ContinuousPortrayal2D();
    NetworkPortrayal2D stopsPortrayal = new NetworkPortrayal2D();
    public static void main(String[] args){
        SimulatorWithUI vid = new SimulatorWithUI();
        Console c = new Console(vid);
        c.setVisible(true);
    }
    public SimulatorWithUI() { super( new people( System.currentTimeMillis())); }
    public SimulatorWithUI(SimState state) { super(state); }
    public static String getName() { return "People and Bus Stops"; }
    public void start(){
        super.start();
        setupPortrayals();
        
    }
    public void load(SimState state){
        super.load(state);
        setupPortrayals();
        
    }
//If you need to add a portral, here is where you do it
    public void setupPortrayals(){
        people p = (people) state;
        peoplePortrayal.setField(people.yard);
        

        
        peoplePortrayal.setPortrayalForClass(bus.class, new sim.portrayal.simple.OvalPortrayal2D(5){
            
            public void draw (Object object, Graphics2D graphics, DrawInfo2D info){
                bus ba = (bus)object;               
                ba.setbusColor((new Color((ba.getcurrentLoad()), 0, 255 - (ba.getcurrentLoad()))));              
                paint =ba.getbusColor();
                super.draw(object, graphics, info);
            }
            
        }
                );
         peoplePortrayal.setPortrayalForClass(busstops.class, new sim.portrayal.simple.RectanglePortrayal2D(8){
            public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
                busstops ba = (busstops)object;        
                paint = ba.getColor();
                super.draw(object, graphics, info);
            }
         });

        peoplePortrayal.setPortrayalForClass(personsA.class, new OvalPortrayal2D(){
             public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
                personsA pa = (personsA)object;        
                paint = pa.getPersonColor();
                super.draw(object, graphics, info);
            }       
        });
        stopsPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());
        stopsPortrayal.setField( new SpatialNetwork2D(people.yard, p.Whichstop));
        
        
        routePortrayal.setField(routedraw.routeyard);

        routePortrayal.setPortrayalForClass(routeobject.class, new OvalPortrayal2D(){
             public void draw(Object object, Graphics2D graphics, DrawInfo2D info){
                routeobject r = (routeobject)object;        
                paint = r.getrouteColor();
                int startx = r.getStartx();
                int starty = r.getStarty();
                int endx = r.getEndx();
                int endy = r.getEndy();
                Graphics2D g2 = (Graphics2D) graphics;
                //g2.draw(new Line2D.Double(startx, starty, endx, endy));
                g2.drawLine(10, 10, 300, 300);
                super.draw(object, graphics, info);
            }       
        });

        // reschedule the displayer
        display.reset();
        display.setBackdrop(Color.white);
        // redraw the display
        display.repaint();
    }  
    public void init(Controller c){
        super.init(c);
        display = new Display2D(600,600,this);
        display.setClipping(false);
        displayFrame = display.createFrame();
        displayFrame.setTitle("People and Bus stops");
        c.registerFrame(displayFrame);
        // so the frame appears in the "Display" list
        displayFrame.setVisible(true);
        display.attach( peoplePortrayal, "Yard" );
        display.attach (stopsPortrayal, "Buddies");
        display.attach (routePortrayal, "Routes");
    }
   
    public void quit(){
        super.quit();
        if (displayFrame!=null) displayFrame.dispose();
        displayFrame = null;
        display = null;
        } 
    
    }
