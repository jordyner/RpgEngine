package fel.cvut.jordaji1.rpg_engine.ui;

import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class GameButtons {

    private Rectangle2D save;
    private Rectangle2D menu;
    private Rectangle2D exit;


    public GameButtons() {
        save = new Rectangle2D.Double(15, WINDOW_HEIGHT - 80 , 100, 30);
        menu = new Rectangle2D.Double(15 + 100 + 10, WINDOW_HEIGHT - 80 , 60, 30);
        exit = new Rectangle2D.Double(125 + 60 + 10, WINDOW_HEIGHT - 80, 60, 30);
    }
    
    public void renderButtons(Graphics2D graphics2d){
        Font forSave = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forSave);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Save", 35 + 12, WINDOW_HEIGHT - 60);
        graphics2d.draw(save);
        
        Font forMenu = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forMenu);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Menu", 125 + 12, WINDOW_HEIGHT - 60);
        graphics2d.draw(menu);
        
        Font forExit = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forExit);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Exit", 200 + 12, WINDOW_HEIGHT - 60);
        graphics2d.draw(exit);

    }
    
    public void updateButtons(){
        save = new Rectangle2D.Double(15, WINDOW_HEIGHT - 80 , 100, 30);
        menu = new Rectangle2D.Double(15 + 100 + 10, WINDOW_HEIGHT - 80 , 60, 30);
        exit = new Rectangle2D.Double(125 + 60 + 10, WINDOW_HEIGHT - 80, 60, 30);
    }

    public void renderUI(Graphics2D graphics2d, int healthPoints) {
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(WINDOW_WIDTH - 100, WINDOW_HEIGHT - 80 , 70, 30);
        graphics2d.fillRect(WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        Font playerHealth = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(playerHealth);
        graphics2d.setColor(Color.RED);
        graphics2d.drawString("HP: " + Integer.toString(healthPoints), WINDOW_WIDTH - 100 + 5, WINDOW_HEIGHT - 80 + 20);
    }
    
}
