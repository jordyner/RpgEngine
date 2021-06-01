package fel.cvut.jordaji1.rpg_engine.ui;

import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class EditorUI{
    
    private Rectangle2D saveAndPlay;
    private Rectangle2D save;
    private Rectangle2D menu;
    private Rectangle2D lock;
    private Rectangle2D lockEnemy;
    private Rectangle2D lockChest;

    public EditorUI() {
        saveAndPlay = new Rectangle2D.Double(15, WINDOW_HEIGHT - 80 , 100, 30);
        save = new Rectangle2D.Double(15 + 100 + 10, WINDOW_HEIGHT - 80 , 60, 30);
        menu = new Rectangle2D.Double(125 + 60 + 10, WINDOW_HEIGHT - 80, 60, 30);
        lock = new Rectangle2D.Double(WINDOW_WIDTH - 60, 5, 40, 20);
    }
    
    /**
    * Creates graphical representation of clickable buttons in editor such as SAVE AND PLAY, SAVE, MENU, LOCK
    * LOCK: locks object on gameboard.
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    */
    public void renderButtons(Graphics2D graphics2d){
        Font forSaveAndPlay = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forSaveAndPlay);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Save & Play", 15 + 7, WINDOW_HEIGHT - 60);
        graphics2d.draw(saveAndPlay);
        
        Font forSaveGame = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forSaveGame);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Save", 125 + 12, WINDOW_HEIGHT - 60);
        graphics2d.draw(save);
        
        Font forMenu = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forMenu);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Menu", 195 + 12, WINDOW_HEIGHT - 60);
        graphics2d.draw(menu);
        
        Font forLock = new Font("arial", Font.BOLD, 12);
        graphics2d.setFont(forLock);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("LOCK", WINDOW_WIDTH - 55, 20);
        graphics2d.draw(lock);
    }
    
    /**
    * This method displays HP of player in the right lower corner.
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    * @param healtPoints count of player's HP
    */
    public void renderUI(Graphics2D graphics2d, int healtPoints){
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(WINDOW_WIDTH - 100, WINDOW_HEIGHT - 80 , 70, 30);
        graphics2d.fillRect(WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        Font playerHealth = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(playerHealth);
        graphics2d.setColor(Color.RED);
        graphics2d.drawString("HP: " + Integer.toString(healtPoints), WINDOW_WIDTH - 100 + 5, WINDOW_HEIGHT - 80 + 20);
    }
    
    /**
    * If player add object outside game board, then this message pops up and player is unable to save the game.
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    */
    public void renderSaveFailed(Graphics2D graphics2d) {
        Font saveFailed = new Font("arial", Font.BOLD, 20);
        graphics2d.setFont(saveFailed);
        graphics2d.setColor(Color.CYAN);
        graphics2d.drawString("Save failed. Some object is out of game map! Go to Menu and start again.", WINDOW_WIDTH/2 - 350, WINDOW_HEIGHT/2);
    }
}
