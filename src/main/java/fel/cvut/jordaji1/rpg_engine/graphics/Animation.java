package fel.cvut.jordaji1.rpg_engine.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
This class is responsible animation for object (player,enemy,key..). It is basically switching between coordinates in certain image.
*/
public class Animation {
    private int speed;
    private int frames;
    
    private int index = 0;
    private int count = 0;
    
    private boolean animationFinished = false;
    
    private BufferedImage[] images;
    private BufferedImage currentImg;
    
    public Animation(int speed, BufferedImage... args){
        this.speed = speed;
        images = new BufferedImage[args.length];
        
        for(int i = 0; i < args.length; i++){
            images[i] = args[i];
        }
        
        frames = args.length;
    }
    
    /**
     * In an object I call runAnimation if any animation should be played now. So if player presses W then I play animation which looks like player is going upwards.
     * 
     * loops through every given buffered images and changes them in a specific order with some speed which is given through constructor.
     */
    public void runAnimation(){
        index++;
        
        if(index > speed){
            index = 0;
            
            for(int i = 0; i < frames; i++){
                if(count == i){
                    currentImg = images[i];
                }
            }
        
            count++;

            if(count > frames){
                animationFinished = true;
                count = 0;
            }
        }
    }
    
    public void drawAnimation(Graphics g, int x, int y){
        g.drawImage(currentImg, x, y, null);
    }
    
    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
        g.drawImage(currentImg, x, y, scaleX, scaleY, null);
    }
    
    
    /*
    Getters and Setters
    */
    public boolean getAnimationFinished() {
        return animationFinished;
    }

    public int getIndex() {
        return index;
    }
    
    
}
