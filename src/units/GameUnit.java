
package units;

import tanks.Cell;

public abstract class GameUnit {
    protected int _HP = 1;//Количество жизней

    public static final Integer NOT_DESTROYED_AND_MISS = -2;//Показатель уровня жизни при котором объект является неубиваемым и пропускает снаряды
    
    protected Cell _cell;
        
    public GameUnit(Cell cell) {
        _cell = cell;
        _cell.setUnit(this);
    }
    
    /**
    *Установить ячейку
    */
    public void setCell(Cell cell){
        _cell.remove(this);
        _cell = cell;
        _cell.setUnit(this);
    }
    
    
    public Cell getCell(){
        return _cell;
    }
    
    public int getHP(){
        return _HP;
    }
    
    /**
    * Отнять жизнь
    */
    public void takeLife(){
        _HP --;
    }
        
    /**
    *Взрыв
    */
    public void explode(){
        if(_HP > 0){
            takeLife();
            if(_HP == 0){
                destroy();
            }
        }
    }
    
    protected void destroy(){
        _cell.remove(this);
        _cell = null;
    }
}
