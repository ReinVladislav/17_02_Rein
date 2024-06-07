
package Events;


import java.util.EventObject;
import units.AbstractAmmo;
import units.Bullet;
import tanks.Cell;


public class BulletEvent extends EventObject{
    public Cell _cell;
    
    public BulletEvent(Object source, Cell cell)
    {
        super(source);
        _cell = cell;
    }
}
