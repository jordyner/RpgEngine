package fel.cvut.jordaji1.rpg_engine.objects;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.graphics.ImageLoader;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
Chest render its own sprite and also contains boolean which says whetever this chest is opened or is not.
*/
public class Chest extends GameObject {
    private final GamePanel gamePanel;
    
    private boolean opened;
    
    private BufferedImage chestImage;

    public Chest(int objectCoordinateX, int objectCoordinateY, int objectWidth, int objectHeight, SpriteManager spriteManager, GamePanel gamePanel) {
        super(objectCoordinateX, objectCoordinateY, objectWidth, objectHeight, spriteManager, gamePanel);
        this.gamePanel = gamePanel;
        opened = false;
        
        chestImage = ImageLoader.loadImage("/Collectibles/Chest.png");
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics graphics) {
        if(!opened){
            graphics.drawImage(chestImage,
                (int) (objectCoordinateX - gamePanel.getCamera().getxOffset()),
                (int) (objectCoordinateY - gamePanel.getCamera().getyOffset()),
                null);
        }
    }
    
    

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
    
    
}
