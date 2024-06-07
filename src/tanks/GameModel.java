
package tanks;

import units.Bullet;
import units.Tank;
import Coordination.Coordinate;
import Coordination.Direction;
import Coordination.Rotation;
import Events.FortEvent;
import Events.FortListener;
import Events.ModelEvent;
import Events.TankEvent;
import java.util.ArrayList;
import java.util.List;
import Events.TankListener;
import Events.ModelListener;
import units.Fort;


public class GameModel {
    //------------Field-------------
    private GameField _field;

    private Tank _currentTank;
    private boolean _gameRunning = false;

    //Создание игрового поля
    private void createGameField() {
        _field = new GameField(9, 9);
        _field.generateFortsAndTanks();

        
        listeningTanksAndForts();        
        _currentTank = _field.getTanks()[0];
        
        _field.generateWalls();
        _field.generateWaters();
    }
    
    public GameField field(){
        return _field;
    }

    //-----------Game---------------
    
    //Начало игры
    public void startGame() {
        //Установить индикатор идущей игры
        _gameRunning = true;
        InformAboutStartGame("");
        createGameField(); 
    }
    
    public Tank getCurrentTank(){
        return _currentTank;
    }
    
    public Tank[] getTanks(){
        return _field.getTanks();
    }

    //Сделать текущим следующий танк
    void nextCurrentTank() {
        for(int i = 0; i < _field.getTanks().length; i++){
            if(_currentTank == _field.getTanks()[i]){
                if(i < _field.getTanks().length-1)
                    _currentTank = _field.getTanks()[i+1];
                else 
                    _currentTank = _field.getTanks()[0];
                InformChangeCurrentTank("");
                return;
            }
        }
    }

    //Отнять жизнь игрока
    private void hitTank(Tank tank) {
        if (tank.getHP() > 0) {
            _field.tankToStartPosition(tank);
            nextCurrentTank();
            printField();
        }
        else{
            theEnd(tank);
        }        
    }

    public void rotateCurrentTank(Rotation rotation){
        _currentTank.rotate(rotation);
    }
    public void moveCurrentTank(){
        _currentTank.move();
    }

    public void shootCurrentTank(){
        Bullet ammo = new Bullet(_currentTank.getCell(), _currentTank.getDirection(), 9);
        _currentTank.shoot(ammo);
    }

    public void skipCurrentTank(){
        _currentTank.skipStep();
    }
    
    //Испустить событие изменения ситуации игры
    private void printField(){
        InformAboutRebuildField();
    }
    
    //Конец игры
    private void theEnd(Tank luseTank){
        
        String mess = "Выиграл красный танк";
        if(luseTank == _field.getTanks()[0])
            mess = "Выиграл синий танк";
        InformAboutEndGame(mess);
        finishGame();
    }
    
    //Прекращение игры
    public void finishGame(){
        _gameRunning = false;
        if(_field.getTanks() != null){
            _field.destroy();
            _currentTank = null;
        }
    }
    
    public boolean gameIsRunning(){
        return _gameRunning;
    }
    

    //---------События танка------------
    TankListener listenTanks = new TankEventsForModel();
    FortListener listenFort = new FortEvents();
    
    private void listeningTanksAndForts(){
            Tank.AddListener(listenTanks);
            Fort.AddListener(listenFort);
    }
    
    public class TankEventsForModel implements TankListener{
        @Override
        public void ExplosiveTank(TankEvent e){
            hitTank(e._tank);
            System.out.println("hit tank "+e._tank);
            
        }

        @Override
        public void RotateTank(TankEvent e) {
            System.out.println("rotate tank "+e._tank);
            printField();
        }

        @Override
        public void MoveTank(TankEvent e) {
            nextCurrentTank();
            System.out.println("move tank "+e._tank);
            printField();
        }

        @Override
        public void SkipStep(TankEvent e) {
            System.out.println("skip tank "+e._tank);
            nextCurrentTank();
        }

        @Override
        public void FireTank(TankEvent e) {
            System.out.println("fire tank "+e._tank);
            nextCurrentTank();
        }
    }
    
    //-------Fort event-------
    public class FortEvents implements FortListener{

        @Override
        public void FortExplose(FortEvent e) {
            theEnd(e._fort.getTank());
        }
        
    }
    
    
    //-------Event model------
    static private ArrayList<ModelListener> _listenersRebuild = new ArrayList<ModelListener>();
    
    public static void AddListener(ModelListener list)
    {
        _listenersRebuild.add(list);
    }
    

    private void InformAboutRebuildField()
    {
        ModelEvent event = new ModelEvent(this, "");
        for(ModelListener i : _listenersRebuild){
            i.RebuildFieldEvent(event);
        }
    }
    
    private void InformChangeCurrentTank(String mess)
    {
        ModelEvent event = new ModelEvent(this, mess);
        for(ModelListener i : _listenersRebuild){
            i.ChangeCurrentTank(event);
        }
    }
    
    private void InformAboutStartGame(String mess)
    {
        ModelEvent event = new ModelEvent(this, mess);
        for(ModelListener i : _listenersRebuild){
            i.StartGame(event);
        }
    }
    
    private void InformAboutEndGame(String mess)
    {
        ModelEvent event = new ModelEvent(this, mess);
        for(ModelListener i : _listenersRebuild){
            i.EndGame(event);
        }
    }
    
}
