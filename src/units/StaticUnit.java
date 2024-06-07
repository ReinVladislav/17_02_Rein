
package units;

import tanks.Cell;


public abstract class StaticUnit extends GameUnit{
    
    public StaticUnit(Cell cell) {
        super(cell);
        //Стандартно у статических объектов есть одна жизнь
        _HP = 1;
    }
    
}
