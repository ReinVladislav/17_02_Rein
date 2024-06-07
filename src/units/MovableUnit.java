
package units;

import Coordination.Direction;
import Coordination.Rotation;
import tanks.Cell;


public abstract class MovableUnit extends GameUnit{
    
    protected Direction _direct;
    
    public MovableUnit(Cell cell, Direction direction) {
        super(cell);
        _direct = direction;
        _HP = 1;
    }

    public void setDirection(Direction _direct) {
        this._direct = _direct;
    }

    /**
    *Установить поворот
    */
    
    public Direction getDirection(){
        return new Direction(_direct.direct());
    }
    
    /**
    *Повернуть
    */
    public void rotate(Rotation rotation){
        _direct.rotate(rotation);
    }
    
    /**
    *Движение вперед в соответствии с поворотом
    */
    public boolean move(){
        Cell newCell = _cell.nextCell(_direct);
        //Если можно переместиться
        if (newCell != null ) {
            _cell.remove(this);
            _cell = newCell;
            _cell.setUnit(this);
            return true;
        }
        return false;
    }
    
    /**
    *Возможность движения в двнном направлении
    */
    public boolean canMove(Direction direct){
        return _cell.nextCell(_direct) != null;
    }
}
