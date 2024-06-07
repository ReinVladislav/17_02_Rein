
package Events;


import java.util.EventObject;
import units.Tank;


public class TankEvent extends EventObject {
     public Tank _tank;
    
    public TankEvent(Object source, Tank tank)
    {
        super(source);
        _tank = tank;
        
    }
}
