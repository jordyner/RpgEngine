package fel.cvut.jordaji1.rpg_engine.ui;

import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class GameUI {

    private Rectangle2D save;
    private Rectangle2D menu;
    private Rectangle2D exit;


    public GameUI() {
        save = new Rectangle2D.Double(15, WINDOW_HEIGHT - 80 , 100, 30);
        menu = new Rectangle2D.Double(15 + 100 + 10, WINDOW_HEIGHT - 80 , 60, 30);
        exit = new Rectangle2D.Double(125 + 60 + 10, WINDOW_HEIGHT - 80, 60, 30);
    }
    
    /**
    * Creates graphical representation of clickable buttons in editor such as SAVE, MENU, EXIT
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    */
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

    /**
    * This method displays HP of player and damage of player in the right lower corner.
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    * @param healtPoints count of current player health
    * @param playerDamage count of current player damage
    */
    public void renderUI(Graphics2D graphics2d, int healthPoints, int playerDamage) {
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(WINDOW_WIDTH - 100, WINDOW_HEIGHT - 80 , 70, 30);
        graphics2d.fillRect(WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        Font playerHealth = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(playerHealth);
        graphics2d.setColor(Color.RED);
        graphics2d.drawString("HP: " + Integer.toString(healthPoints), WINDOW_WIDTH - 100 + 5, WINDOW_HEIGHT - 80 + 20);
        
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(WINDOW_WIDTH - 200, WINDOW_HEIGHT - 80, 80, 30);
        graphics2d.fillRect(WINDOW_WIDTH, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        Font forPlayerDamage = new Font("arial", Font.BOLD, 15);
        graphics2d.setFont(forPlayerDamage);
        graphics2d.setColor(Color.YELLOW);
        graphics2d.drawString("DMG: " + Integer.toString(playerDamage), WINDOW_WIDTH - 200 + 5, WINDOW_HEIGHT - 80 + 20);
    }
    
    /**
    * If player press E and collides with any chest then this "window" pops up and player can choose his desired buff.
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    */
    public void displayUpgrade(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(WINDOW_WIDTH/2 - 50, WINDOW_HEIGHT/2 - 100, 120, 120);
        
        Font hpBonus = new Font("arial", Font.BOLD, 15);
        graphics.setFont(hpBonus);
        graphics.setColor(Color.RED);
        graphics.drawString("Health: + 30%", WINDOW_WIDTH/2 - 50 + 5, WINDOW_HEIGHT/2 - 100 + 20);
        
        Font damageBonus = new Font("arial", Font.BOLD, 15);
        graphics.setFont(damageBonus);
        graphics.setColor(Color.YELLOW);
        graphics.drawString("Damage: + 20%", WINDOW_WIDTH/2 - 50 + 5, WINDOW_HEIGHT/2 - 100 + 65);
        
        Font speedBonus = new Font("arial", Font.BOLD, 15);
        graphics.setFont(speedBonus);
        graphics.setColor(Color.GREEN);
        graphics.drawString("Speed bonus", WINDOW_WIDTH/2 - 50 + 5, WINDOW_HEIGHT/2 - 100 + 110);
    }
    
    /**
    * If player dies, this pops up and player can choose whether he wants to play again, go to menu or exit.
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    */
    public void displayGameOver(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(WINDOW_WIDTH/2 - 50, WINDOW_HEIGHT/2 - 100, 100, 120);
        
        Font winnerBanner = new Font("arial", Font.BOLD, 20);
        graphics.setFont(winnerBanner);
        graphics.setColor(Color.WHITE);
        graphics.drawString("GAME OVER!", WINDOW_WIDTH/2 - 70, WINDOW_HEIGHT/2 - 150);
        
        Font forRestart = new Font("arial", Font.BOLD, 15);
        graphics.setFont(forRestart);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Restart", WINDOW_WIDTH/2 - 50 + 5 + 10+5, WINDOW_HEIGHT/2 - 100 + 20);
        
        Font forMenu = new Font("arial", Font.BOLD, 15);
        graphics.setFont(forMenu);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Menu", WINDOW_WIDTH/2 - 50 + 5 + 10+10, WINDOW_HEIGHT/2 - 100 + 65);
        
        Font forExit = new Font("arial", Font.BOLD, 15);
        graphics.setFont(forExit);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Exit", WINDOW_WIDTH/2 - 50 + 5 + 10+15, WINDOW_HEIGHT/2 - 100 + 110);
    }
    
    /**
    * If player wins, this pops up and player can choose whether he wants to play again, go to menu or exit.
    * This method is almost identical to displayGameOver
    * 
    * @param graphics2d graphical component that is provided by render in gamePanel.
    */
    public void displayPlayerWins(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(WINDOW_WIDTH/2 - 50, WINDOW_HEIGHT/2 - 100, 100, 120);
        
        Font winnerBanner = new Font("arial", Font.BOLD, 20);
        graphics.setFont(winnerBanner);
        graphics.setColor(Color.WHITE);
        graphics.drawString("YOU WON THIS GAME!", WINDOW_WIDTH/2 - 100, WINDOW_HEIGHT/2 - 150);
        
        Font forRestart = new Font("arial", Font.BOLD, 15);
        graphics.setFont(forRestart);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Play again", WINDOW_WIDTH/2 - 50 + 5 + 10+5, WINDOW_HEIGHT/2 - 100 + 20);
        
        Font forMenu = new Font("arial", Font.BOLD, 15);
        graphics.setFont(forMenu);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Menu", WINDOW_WIDTH/2 - 50 + 5 + 10+10, WINDOW_HEIGHT/2 - 100 + 65);
        
        Font forExit = new Font("arial", Font.BOLD, 15);
        graphics.setFont(forExit);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Exit", WINDOW_WIDTH/2 - 50 + 5 + 10+15, WINDOW_HEIGHT/2 - 100 + 110);
    }
    
}
