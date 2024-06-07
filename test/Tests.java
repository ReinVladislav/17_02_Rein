
import Coordination.Coordinate;
import Coordination.Direction;
import java.util.ArrayList;

import Coordination.Rotation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tanks.Cell;
import tanks.GameModel;
import units.Bullet;
import units.Tank;

public class Tests {
    
    public Tests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    GameModel gameModel = new GameModel();

    
    @Test
    public void TestMoveTank(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        Cell cell = tank.getCell();
        tank.move();
        assertEquals(cell.nextCell(tank.getDirection()), tank.getCell());
    }
    @Test
    public void TestMoveTank1(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        Cell cell = tank.getCell();
        tank.rotate(Rotation.Right());
        tank.move();
        assertEquals(cell.nextCell(tank.getDirection()), tank.getCell());
    }
    @Test
    public void TestMoveTank2(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(2, 2)));
        Cell cell = tank.getCell();
        tank.rotate(Rotation.Left());
        tank.move();
        assertEquals(cell.nextCell(tank.getDirection()), tank.getCell());
    }
    @Test
    public void TestMoveTank3(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(2, 2)));
        Cell cell = tank.getCell();
        tank.rotate(Rotation.Right());
        tank.rotate(Rotation.Right());
        tank.move();
        assertEquals(cell.nextCell(tank.getDirection()), tank.getCell());
    }
    
    @Test
    public void TestMoveTankToWater(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(1, 4)));
        tank.rotate(Rotation.Right());
        Cell cell = tank.getCell();
        tank.move();
        assertEquals(cell, tank.getCell());
    }

    @Test
    public void TestMoveTankToWaterAfterShoot(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(1, 4)));
        tank.rotate(Rotation.Right());
        Cell cell = tank.getCell().nextCell(tank.getDirection());
        tank.shoot(new Bullet(tank.getCell(),tank.getDirection(),9));
        assertEquals(cell.getUnit(), tank.getCell().nextCell(tank.getDirection()).getUnit());
    }
    
    @Test
    public void TestMoveTankToWall(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(3, 2)));
        tank.rotate(Rotation.Right());
        Cell cell = tank.getCell();
        tank.move();
        assertEquals(cell, tank.getCell());
    }

    @Test
    public void TestMoveTankToWallAfterShoot() throws InterruptedException {
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(3, 2)));
        tank.rotate(Rotation.Right());
        Cell cell = tank.getCell();
        tank.skipStep();
        tank.skipStep();
        tank.skipStep();
        tank.shoot(new Bullet(tank.getCell(),tank.getDirection(),9));
        Thread.sleep(100);
        tank.move();
        assertEquals(cell.nextCell(tank.getDirection()), tank.getCell());
    }
    
    @Test
    public void TestMoveTankToBound(){
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(1, 9)));
        tank.rotate(Rotation.Right());
        Cell cell = tank.getCell();
        tank.move();
        assertEquals(cell, tank.getCell());
    }

    @Test
    public void TestExplosionWall() throws InterruptedException {
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(3, 2)));
        tank.rotate(Rotation.Right());
        tank.skipStep();
        tank.skipStep();
        tank.skipStep();
        tank.shoot(new Bullet(tank.getCell(),tank.getDirection(),9));
        Thread.sleep(100);
        assertNull(tank.getCell().nextCell(tank.getDirection()).getUnit());
    }

    @Test
    public void TestExplosionWater() throws InterruptedException {
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(1, 3)));
        tank.rotate(Rotation.Right());
        Cell cell = tank.getCell().nextCell(tank.getDirection());
        tank.skipStep();
        tank.skipStep();
        tank.skipStep();
        tank.shoot(new Bullet(tank.getCell(),tank.getDirection(),9));
        Thread.sleep(100);
        assertEquals(tank.getCell().nextCell(tank.getDirection()).getUnit(), cell.getUnit());
    }

    @Test
    public void TestExplosionTank() throws InterruptedException {
        gameModel.startGame();
        Tank tank = gameModel.getCurrentTank();
        tank.setCell(gameModel.field().getCell(new Coordinate(4, 8)));
        tank.rotate(Rotation.Right());
        int hp = tank.getCell().nextCell(tank.getDirection()).getUnit().getHP();
        gameModel.skipCurrentTank();
        gameModel.skipCurrentTank();
        gameModel.skipCurrentTank();
        gameModel.skipCurrentTank();
        gameModel.skipCurrentTank();
        tank.shoot(new Bullet(tank.getCell(),tank.getDirection(),9));
        Thread.sleep(100);
        assertEquals(hp-1,tank.getCell().nextCell(tank.getDirection()).getUnit().getHP());
    }

    @Test
    public void TestExplosionFort() throws InterruptedException {
        gameModel.startGame();
        assertTrue(gameModel.gameIsRunning());
        boolean a = gameModel.gameIsRunning();
        gameModel.field().get_forts()[0].explode();
        assertFalse(gameModel.gameIsRunning());
    }
}
