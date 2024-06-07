
package units;

import tanks.Cell;


public class Water extends StaticUnit{
    
    public Water(Cell cell) {
        super(cell);
        _HP = GameUnit.NOT_DESTROYED_AND_MISS;
    }
    
}
