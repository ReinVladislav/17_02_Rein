
package gui;

import java.util.Random;
import javax.swing.JFrame;
import tanks.GameModel;


public class MainView extends JFrame{
    private GameModel model;
    private GamePanel game;
    
    public MainView()
    {
        model = new GameModel();
        model.startGame();
        game = new GamePanel(model);
        game.setVisible(true);
    }
}
