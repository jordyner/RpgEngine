package fel.cvut.jordaji1.rpg_engine.objects;

import fel.cvut.jordaji1.rpg_engine.game.GameLoop;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.graphics.Animation;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.KEY_IMAGE_COUNT;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/* 
Key class contains its own animation (just flipping key) and update with render.
*/
public class Key extends GameObject {
    private final static Logger LOGGER = Logger.getLogger(Key.class.getName());
    
    private final GamePanel gamePanel;
    private Animation keyAnimation;
    private final SpriteManager spriteManager;
    
    private boolean collected = false;
    
    private int displayOnlyOnce = 0;

    public Key(int objectCoordinateX, int objectCoordinateY, int objectWidth, int objectHeight, SpriteManager spriteManager, GamePanel gamePanel) {
        super(objectCoordinateX, objectCoordinateY, objectWidth, objectHeight, spriteManager, gamePanel);
        this.gamePanel = gamePanel;
        this.spriteManager = spriteManager;
        
        initKeyAnimation();
    }
    

    @Override
    public void update() {
        keyAnimation.runAnimation();
    }

    @Override
    public void render(Graphics graphics) {
        keyAnimation.drawAnimation(graphics,
                (int) (objectCoordinateX - gamePanel.getCamera().getxOffset()),
                (int) (objectCoordinateY - gamePanel.getCamera().getyOffset()));
    }

    private void initKeyAnimation() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[KEY_IMAGE_COUNT];
        for(int i = 0; i < KEY_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Collectibles").get("Key.png").getSubimage(x, y, 16, 16);    
            x += 16;
        }
        
        keyAnimation = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9], temp[10], temp[11]);
        LOGGER.log(SEVERE, "Key animation was initiliased!");
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        if(displayOnlyOnce == 0){
            LOGGER.log(SEVERE, "Key Collected. Player can open chests now.");
            displayOnlyOnce ++;
        }
        
        this.collected = collected;
    }
    
    

}
