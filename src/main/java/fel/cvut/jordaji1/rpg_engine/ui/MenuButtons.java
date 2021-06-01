package fel.cvut.jordaji1.rpg_engine.ui;

import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class MenuButtons {
    
    private Rectangle2D createNewRPG = new Rectangle2D.Double(WINDOW_WIDTH/2 - 170, 150 , 275, 50);
    private Rectangle2D loadGame = new Rectangle2D.Double(WINDOW_WIDTH/2 - 140, 250 , 200, 50);
    private Rectangle2D resolution = new Rectangle2D.Double(WINDOW_WIDTH/2 - 140, 350 , 200, 50);
    private Rectangle2D exit = new Rectangle2D.Double(WINDOW_WIDTH/2 - 90, 450, 100, 50);

    public void renderButtons(Graphics2D graphics2d){
        Font font0 = new Font("arial", Font.BOLD, 50);
        graphics2d.setFont(font0);
        graphics2d.setColor(Color.WHITE);
        graphics2d.drawString("JAVA RPG ENGINE", WINDOW_WIDTH/2 - 240, 100);
        
       
        Font forCreateNewRPG = new Font("arial", Font.BOLD, 30);
        graphics2d.setFont(forCreateNewRPG);
        graphics2d.drawString("Create new RPG", WINDOW_WIDTH/2 - 150 , 150 + 35);
        graphics2d.draw(createNewRPG);
        
        Font forLoadGame = new Font("arial", Font.BOLD, 30);
        graphics2d.setFont(forLoadGame);
        graphics2d.drawString("Load game", WINDOW_WIDTH/2 - 115, 250 + 35);
        graphics2d.draw(loadGame);
        
        Font forResolution = new Font("arial", Font.BOLD, 30);
        graphics2d.setFont(forResolution);
        graphics2d.drawString("Resolution", WINDOW_WIDTH/2 - 115, 350 + 35);
        graphics2d.draw(resolution);
        
        Font forExit = new Font("arial", Font.BOLD, 30);
        graphics2d.setFont(forExit);
        graphics2d.drawString("Exit", WINDOW_WIDTH/2 - 65, 450 + 35);
        graphics2d.draw(exit);
    }

    public void updateButtons() {
        createNewRPG = new Rectangle2D.Double(WINDOW_WIDTH/2 - 170, 150 , 275, 50);
        loadGame = new Rectangle2D.Double(WINDOW_WIDTH/2 - 140, 250 , 200, 50);
        resolution = new Rectangle2D.Double(WINDOW_WIDTH/2 - 140, 350 , 200, 50);
        exit = new Rectangle2D.Double(WINDOW_WIDTH/2 - 90, 450, 100, 50);
    }

    
    
}
