
package Events;

import java.util.EventListener;

public interface TankListener extends EventListener {
    
    void ExplosiveTank(TankEvent e);
    
    void RotateTank(TankEvent e);
    
    void MoveTank(TankEvent e);
    
    void SkipStep(TankEvent e);
    
    void FireTank(TankEvent e);
}
