 package fel.cvut.jordaji1.rpg_engine.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;

 /*
 Class that processes ALL inputs that comes from keyboard.
 */
public class KeyHandler implements KeyListener {
    
    /**
     * Detects which keys are currently pressed. This boolean array uses player (for player movement), Editor (for camera movement), PlayGame (player presses E to open chests)
     */
    public boolean[] allKeys;
    
    private GamePanel game;

    public KeyHandler(GamePanel game) {
        this.game = game;
        allKeys = new boolean[120];
    }
    
    // This method has to be here because of 
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();
        if(pressedKey < allKeys.length){
            allKeys[pressedKey] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int releasedKey = e.getKeyCode();
        if(releasedKey < allKeys.length){
            allKeys[releasedKey] = false;
        }
    }

    public boolean[] getAllKeys() {
        return allKeys;
    }
    
    
    
}
