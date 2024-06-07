
package Events;

import Events.BulletEvent;
import java.util.EventListener;


public interface BulletListener extends EventListener{
    void ExplosiveBullet(BulletEvent e);
    
    void MoveBullet(BulletEvent e);
    
    void StartShootBullet(BulletEvent e);
}
