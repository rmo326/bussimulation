package sim.app.bussimulation;
import java.util.Iterator;
public class RouteIterator<E> implements Iterator<E>{
    
    private int where;
    private BusRoute<E> br;
    
    public RouteIterator(BusRoute<E> br) {
        this.br = br;
        where = 0;
    }
    
    public boolean hasNext() {
        return true;
    }
    
    public E next() {
        
        E b = br.getStop(where);
        where += 1;
        where %= br.size();
        return b;
    }
    
    public void remove(){
    
    }
}
    
