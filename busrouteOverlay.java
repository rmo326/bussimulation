/*
  Copyright 2009  by Sean Luke and Vittorio Zipparo
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/

package sim.app.bussimulation;
import sim.portrayal.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import sim.display.*;
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
/** A FieldPortrayal2D which has no field, but rather draws some text on the screen.  We use
    this to draw score information etc. */

public class busrouteOverlay extends FieldPortrayal2D
    {
    private static final long serialVersionUID = 1;

    SimulatorWithUI ui;


    public busrouteOverlay(SimulatorWithUI ui) { this.ui = ui; }
        

    public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
            /*personsA peop = new personsA();
            //people peop = new people(null);
            Bag myrouteList = new Bag();
            myrouteList = peop.getBag();
            BusRoute a = (BusRoute)myrouteList.get(0);
            busstops c = (busstops)(a.getStop(0));
            busstops d = (busstops)(a.getStop(1));
            Double2D first = c.getLocation();
            Double2D second = d.getLocation();
            int e = (int)first.x;
            int f = (int)first.y;
            int g = (int)second.x;
            int h = (int)second.y;
            graphics.drawLine(e, f, g, h);*/
        }
    }
