import java.io.PrintWriter;
package sim.app.bussimulation;
public class BusRouteMaker{
    public static void main(String[] args){
        int numbuses = Integer.parseInt(args[0]); //how many buses i have
        int numstops = Integer.parseInt(args[1]); //how many bus stops exist

        try{
            PrintWriter writer = new PrintWriter("Routes.txt", "UTF-8");
            for (int i=0; i<numbuses; i++){
                int HowMany = (int)(Math.random()*numstops);
                writer.print(""+HowMany);
                for (int q=0; q<HowMany;q++){
                    writer.print(""+(int)(Math.random()*numstops)+ " ");       
                }
                writer.println("route"+i);
            }
            writer.close();
        }
        catch(Exception e){
        }
    }
}
