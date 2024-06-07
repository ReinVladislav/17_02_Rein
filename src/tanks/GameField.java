
package tanks;

import units.*;
import Coordination.Coordinate;
import Coordination.Direction;
import Coordination.Rotation;
import java.util.ArrayList;
import java.util.List;

public class GameField {

    private int width;
    private int height;


    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        //Создание ячеек
        generateCells();
    }
    
    public int width(){
        return width;
    }
    
    public int height(){
        return height;
    }

    //------------Cells--------------
    private Cell[][] field;

    /**
    *Сгенерировать матрицу ячеек
    */
    private void generateCells() {
        field = new Cell[height][width];

        //Заполнение матрицы ячеек
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                field[i][j] = new Cell(this);
            }
        }

        //Установка соседей ячеек
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int cnt = 0;
                Direction curDir = Direction.Up();
                do {
                    int nextDirX = j + Direction.x[cnt];
                    int nextDirY = i + Direction.y[cnt];
                    if (nextDirX >= 0 && nextDirX < width && nextDirY >= 0 && nextDirY < height) {
                        field[i][j].SetCell(field[nextDirY][nextDirX], curDir);
                    }
                    curDir = curDir.rotate(Rotation.Right());
                    ++cnt;
                } while (curDir.direct() != Direction.Up().direct());
            }
        }
    }

    
    /**
    *Вернуть ячейку по ее координате на поле
    */
    public Cell getCell(Coordinate coord) {
        if (coord.getX() > width || coord.getY() > height) {
            return null;
        }
        return field[coord.getY()-1][coord.getX()-1];
    }

    
    /**
    *Вернуть матрицу ячеек
    */
    public Cell[][] getCells(){
        return field;
    }
    
    
    /**
    *Вернуть координаты ячейки
    */
    public Coordinate getCoordinateCell(Cell cell){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(field[i][j] == cell){
                    return new Coordinate(j+1, i+1);
                }
            }
        }
        return null;
    }

    //---------------Tanks & Forts------------
    private Fort[] _forts = new Fort[2];
    private Tank[] _tanks = new Tank[2];
    private Cell[] _startPositios = new Cell[2];

    public Fort[] get_forts() {
        return _forts;
    }

    /**
    *Генерация баз и их танков
    */
    protected void generateFortsAndTanks() {
        //Форты
        _forts[0] = new Fort(getCell(new Coordinate(5, 1)));
        _forts[1] = new Fort(getCell(new Coordinate(5, height)));
        
        //Стандартные стартовые позиции танков
        _startPositios[0] = _forts[0].getCell().nextCell(Direction.Right());
        _startPositios[1] = _forts[1].getCell().nextCell(Direction.Left());

        //Танки
        _tanks[0] = new Tank(_startPositios[0], Direction.Right(), _forts[0]);
        _tanks[1] = new Tank(_startPositios[1], Direction.Left(), _forts[1]);
    }


    public Tank[] getTanks() {
        return _tanks;
    }
    
    
    /**
    *Премещение танка к его стартовой позиции
    */
    protected void tankToStartPosition(Tank tank) {
        for (int i = 0; i < _tanks.length; i++) {
            if(_tanks[i]==tank){
                tank.setCell(_startPositios[i]);
            }
        }
    }
    //---------------Walls------------

    private ArrayList<StaticUnit> _walls = new ArrayList<>();

    protected void generateWalls() {
        ArrayList<Coordinate> _wallsPositions = new ArrayList<>();
        _wallsPositions.add(new Coordinate(3, 3));
        _wallsPositions.add(new Coordinate(7, 3));
        _wallsPositions.add(new Coordinate(3, 8));
        _wallsPositions.add(new Coordinate(7, 8));
        _wallsPositions.add(new Coordinate(5, 4));
        _wallsPositions.add(new Coordinate(5, 7));
        for (int i = 0; i < _wallsPositions.size(); i++) {
            _walls.add(new Wall(getCell(_wallsPositions.get(i))));
        }
    }
    
    //--------------Waters------------
    private ArrayList<StaticUnit> _waters = new ArrayList<>();

    protected void generateWaters() {
        ArrayList<Coordinate> _watersPositions = new ArrayList<>();
        _watersPositions.add(new Coordinate(9, 5));
        _watersPositions.add(new Coordinate(9, 6));
        _watersPositions.add(new Coordinate(8, 5));
        _watersPositions.add(new Coordinate(8, 6));
        _watersPositions.add(new Coordinate(1, 5));
        _watersPositions.add(new Coordinate(1, 6));
        _watersPositions.add(new Coordinate(2, 5));
        _watersPositions.add(new Coordinate(2, 6));
        _watersPositions.add(new Coordinate(4, 5));
        _watersPositions.add(new Coordinate(4, 6));
        _watersPositions.add(new Coordinate(5, 5));
        _watersPositions.add(new Coordinate(5, 6));
        _watersPositions.add(new Coordinate(6, 5));
        _watersPositions.add(new Coordinate(6, 6));
        for (int i = 0; i < _watersPositions.size(); i++) {
            _waters.add(new Water(getCell(_watersPositions.get(i))));
        }
    }
    
    
    /**
    *Уничтожить поле
    */
    public void destroy(){
        //Уничтожить танки
        for(Tank tank:_tanks){
            tank.destroy();
        }
        //Уничтожить форты
        for(Fort f:_forts){
            f.destroy();
        }
        
        field = null;
        _walls = null;
        _tanks = null;
    }
}
