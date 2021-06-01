package fel.cvut.jordaji1.rpg_engine.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/*
This classed is used in SpriteManager and if I need some specific image.
*/
public class SpriteSet {
    private Map<String, BufferedImage> fileWithAnimation;

    public SpriteSet() {
        this.fileWithAnimation = new HashMap<>();
    }

    /**
     * @param name name is the name of file (it is for easier access to sprites)
     * @param fileWithAnimation file in specific folder (this can be for example in Character folder some PNG file)
     */
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
