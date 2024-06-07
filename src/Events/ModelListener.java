
package Events;

import java.util.EventListener;


public interface ModelListener extends EventListener{
    void RebuildFieldEvent(ModelEvent e);
    
    void ChangeCurrentTank(ModelEvent e);
    
    void StartGame(ModelEvent e);
    
    void EndGame(ModelEvent e);
}
