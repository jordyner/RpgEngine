package fel.cvut.jordaji1.rpg_engine.utils;

import fel.cvut.jordaji1.rpg_engine.objects.GameObject;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import java.awt.Rectangle;

public class Camera {
    private float xOffset, yOffset;
    private final GamePanel gamePanel;

    public Camera(GamePanel gamePanel, float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.gamePanel = gamePanel;
    }
    
     /**
    * This method places player in the middle of frame and everythig other has to be subtracted/added. In other words if player moves then player doesnt move but everything other moves.player 
    */
    public void centerOnPlayer(GameObject player){
        xOffset = player.getObjectCoordinateX() - WINDOW_WIDTH / 2 + player.getObjectWidth() / 2;
        yOffset = player.getObjectCoordinateY() - WINDOW_HEIGHT / 2 + player.getObjectHeight() / 2;
    }
    
    /**
    * Computation works completely same as centerOnPlayer method.
    * 
    * @param rect takes any rectangle but this method is specifically determined for editor camera because camera has to be attached to something (this rectangle is only created but never rendered)
    */
    public void centerOnRect(Rectangle rect){
        xOffset = rect.x - WINDOW_WIDTH / 2 + rect.width / 2;
        yOffset = rect.y - WINDOW_HEIGHT / 2 + rect.height / 2;
    }
    
    /* Getters and Setters */
    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
    
    
    
    
}
