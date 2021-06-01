package fel.cvut.jordaji1.rpg_engine.states;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import fel.cvut.jordaji1.rpg_engine.ui.MenuUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
First thing player experiences when he launches this engine. He can adjust resolution and create new rpg or load map.
*/
public class Menu{    
    
    private MenuUI buttonsForMenu; 
    
    private GamePanel gamePanel;

    public Menu(GamePanel gamePanel) {
        buttonsForMenu = new MenuUI();
        this.gamePanel = gamePanel;
        
        
    }
     
    public void render(Graphics g){
        Graphics2D graphics2d = (Graphics2D)g;
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        buttonsForMenu.renderButtons(graphics2d);
        
    }

    public void update() {
        buttonsForMenu.updateButtons();
    }
    
    
}
