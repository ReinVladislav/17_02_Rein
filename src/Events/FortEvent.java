
package Events;
import java.util.EventObject;
import units.Fort;

public class FortEvent extends EventObject{
    public Fort _fort;
    
    public FortEvent(Object source, Fort fort) {
        super(source);
        _fort = fort;
    }
    
}
