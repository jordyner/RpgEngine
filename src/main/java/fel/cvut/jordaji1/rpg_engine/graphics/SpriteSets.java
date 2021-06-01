package fel.cvut.jordaji1.rpg_engine.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


public class SpriteSets {
    private Map<String, BufferedImage> fileWithAnimation;

    public SpriteSets() {
        this.fileWithAnimation = new HashMap<>();
    }

    public void addSheet(String name, BufferedImage fileWithAnimation) {
        this.fileWithAnimation.put(name, fileWithAnimation);
    }
    
    public BufferedImage get(String name) {
        return fileWithAnimation.get(name);
    }

    public Map<String, BufferedImage> getFileWithAnimation() {
        return fileWithAnimation;
    }
    
    
}
