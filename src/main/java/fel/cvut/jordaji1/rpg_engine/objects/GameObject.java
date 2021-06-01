package fel.cvut.jordaji1.rpg_engine.objects;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
Abstract class for Every gameobject. Every gameobject has some coordinates and width and height. Moreover contains bounds Rectangle which is used in collisions.
*/
public abstract class GameObject {
    private final GamePanel gamePanel;
    
    protected int objectCoordinateX;
    protected int objectCoordinateY;
    
    protected int objectWidth;
    protected int objectHeight;
    
    protected Rectangle bounds;
    
    protected GameObject theCollision;
    
    protected boolean active = true;
    
    public GameObject(int objectCoordinateX, int objectCoordinateY, int objectWidth, int objectHeight, SpriteManager spriteManager, GamePanel gamePanel) {
        this.objectCoordinateX = objectCoordinateX;
        this.objectCoordinateY = objectCoordinateY;
        this.objectWidth = objectWidth;
        this.objectHeight = objectHeight;
        this.gamePanel = gamePanel;
        
        bounds = new Rectangle(0,0, objectWidth, objectHeight);
    }
    
    
    public abstract void update();
    
    public abstract void render(Graphics graphics);

    /**
    * @return new rectangle which has is located in objectCoordinate (where is in map) + bounds (how big that object is) + offset (where player wants to go) 
    * 
    */
    public Rectangle getCollisionBounds(int xOffset, int yOffset){
        return new Rectangle((int) (objectCoordinateX + bounds.x + xOffset), (int) (objectCoordinateY + bounds.y + xOffset), bounds.width, bounds.height);
    }
    
    /**
    * @return loops through all objects in game and if any object is in range then returns true, if nothing is in range then it is false
    * 
    */
    public boolean checkObjectCollisions(int xOffset, int yOffset){
        for(GameObject object : gamePanel.getMouseListener().getGameState().getAllObjects()){
            if(object.equals(this)){
                continue;
            }
            if(object.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOffset, yOffset))){
                theCollision = object;
                return true;
            }
        }
        return false;
    }

    
    /* Getters and Setters */
    public int getObjectCoordinateX() {
        return objectCoordinateX;
    }

    public void setObjectCoordinateX(int objectCoordinateX) {
        this.objectCoordinateX = objectCoordinateX;
    }

    public int getObjectCoordinateY() {
        return objectCoordinateY;
    }

    public void setObjectCoordinateY(int objectCoordinateY) {
        this.objectCoordinateY = objectCoordinateY;
    }

    public int getObjectWidth() {
        return objectWidth;
    }

    public void setObjectWidth(int objectWidth) {
        this.objectWidth = objectWidth;
    }

    public int getObjectHeight() {
        return objectHeight;
    }

    public void setObjectHeight(int objectHeight) {
        this.objectHeight = objectHeight;
    }  


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public GameObject getTheCollision() {
        return theCollision;
    }
    
    
}
