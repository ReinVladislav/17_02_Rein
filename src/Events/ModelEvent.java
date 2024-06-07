
package Events;

import java.util.EventObject;


public class ModelEvent extends EventObject {
    public String _message;
    public ModelEvent(Object source, String message) {
        super(source);
        _message = message;
    }
    
}
