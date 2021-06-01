package fel.cvut.jordaji1.rpg_engine.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.IMAGE_SIZE;

public class AnimationManager {
    private SpriteSets spriteSet; // potrebuju SpriSet, kterej ma prehravat
    private BufferedImage currentAnimationSheet; // BufferedImagep rotoze chci ten getSubimage
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int nextRow = 0;
    private int subImageNumber = 0;

    public AnimationManager(SpriteSets spriteSet) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 20; // cim nizsi cislo sem dam, tim se bude animace prehravat rychleji
        this.frameIndex = 0;
        this.currentFrameTime = 0;
    }

    public Image getSprite() {
        return currentAnimationSheet.getSubimage(frameIndex * IMAGE_SIZE, nextRow * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
    }
 
   public void update(int subImageCount) {
        nextRow = 0;
        currentFrameTime++;
        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;
            subImageNumber++;
            
            if(subImageNumber == subImageCount){
                frameIndex = 0;
                nextRow = 0;
                subImageNumber = 0;
            }
            // aby se animace zase zacala prehravat od zacatku
            if(frameIndex >= currentAnimationSheet.getWidth() / IMAGE_SIZE) {
                    nextRow++;
                    frameIndex = 0;
            }
        }
    }

    public void playAnimation(String name) {
        this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
    }

}
