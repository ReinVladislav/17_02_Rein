
package units;

import Events.BulletEvent;
import Coordination.Direction;
import java.util.ArrayList;
import Events.BulletListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import tanks.Cell;


public class Bullet extends AbstractAmmo{
    
    
    public Bullet(Cell cell, Direction direction, int lengthFly) {
        super(cell, direction, lengthFly);
    }

    
    @Override
    public void runShoot(){
        //Если есть следующая ячейка
            if(_cell.nextCell(_direct) != null){
                move();
                int countLengthFly = 1;
                while((_cell.getUnit() == null || (_cell.getUnit() != null && _cell.getUnit().getHP() == GameUnit.NOT_DESTROYED_AND_MISS)) && _cell.nextCell(_direct) != null && countLengthFly<getLengthFly()){
                    try {
                        bulletThread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    move();
                    countLengthFly++;
                }
                //Взорваться
                explode();
            }
    }
}
