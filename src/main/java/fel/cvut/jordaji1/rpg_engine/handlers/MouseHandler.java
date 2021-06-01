package fel.cvut.jordaji1.rpg_engine.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import fel.cvut.jordaji1.rpg_engine.states.PlayGame;
import fel.cvut.jordaji1.rpg_engine.states.States;
import fel.cvut.jordaji1.rpg_engine.ui.EditorConfiguration;
import fel.cvut.jordaji1.rpg_engine.ui.ResolutionConfiguration;
import fel.cvut.jordaji1.rpg_engine.utils.SaveAndLoadMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;


/*
Class that manages all mouse input. It is most commonly used in editor for getting exact location where something should be placed.
*/
public class MouseHandler implements MouseListener, MouseMotionListener {
    private final static Logger LOGGER = Logger.getLogger(MouseHandler.class.getName());
    
    private ResolutionConfiguration resConfig;
    private EditorConfiguration editorConfiguration;
    private PlayGame gameState;
    private final GamePanel gamePanel;
    
    private boolean[] allMouseButtons = new boolean[10];
    
    // These coordinates are used for correct choosing of tile that should be placed
    private int clickImageX;
    private int clickImageY;
    // These coordinates are used for Tile placement
    private int gridX;
    private int gridY;
    // These coordinates are used for Object placement.
    private int gridForObjectX;
    private int gridForObjectY;
    // Detects if something is currently pressed or not.
    private boolean isPressed = false;
    
    private boolean lockPlayerOrKey = true;
    private int lockEnemy = 0;


    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // This class has to in this class due to MouseListener implementation.
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        clickImageX = e.getX();
        clickImageY = e.getY();
        
        // Every states has specific buttons which does something on click.
        if(gamePanel.getGameState() == States.MENU){
            try {
                menuMouseReaction(e.getX(), e.getY());
            } catch (IOException ex) {
                Logger.getLogger(MouseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(gamePanel.getGameState() == States.EDITOR){
            try {
                editorMouseReaction(e.getX(), e.getY());
            } catch (FileNotFoundException ex) {
                System.out.println("problem s nactenim mapy");
            }
        }
        if(gamePanel.getGameState() == States.GAME){
            gameMouseReaction(e.getX(), e.getY());
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
    }

    // This class has to in this class due to MouseListener implementation.
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    // This class has to in this class due to MouseListener implementation.
    @Override
    public void mouseExited(MouseEvent e) {
    }

    // 
    @Override
    public void mouseDragged(MouseEvent e) {
        isPressed = true;
        
        // I want to distinguish if Players is pressing left or right mouse button so game knows what shall be drawn on gameboard.
        if(SwingUtilities.isLeftMouseButton(e)){
            gridX = e.getX();
            gridY = e.getY();
        }
        if(SwingUtilities.isRightMouseButton(e)){
            gridForObjectX = e.getX();
            gridForObjectY = e.getY();
        }
    }

    // This class has to in this class due to MouseListener implementation.
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    /*
    Menu mouse reaction -> takes coordinates where player clicked and if it is in this location then relevant operation will happen.
    */
    private void menuMouseReaction(int xCoords, int yCoords) throws IOException {
        
        if(xCoords >= WINDOW_WIDTH/2 - 170 && xCoords <= WINDOW_WIDTH/2 - 170 + 275){
            if(yCoords >= 150 && yCoords <= 200){
                editorConfiguration = new EditorConfiguration(gamePanel);
            }
        }
        
        if(xCoords >= WINDOW_WIDTH/2 - 140 && xCoords <= WINDOW_WIDTH/2 - 140 + 200){
            if(yCoords >= 250 && yCoords <= 300){
                SaveAndLoadMap.load();
                gameState = new PlayGame(gamePanel.getKeyListener(), this, gamePanel.getSpriteManager(), gamePanel);
                gamePanel.setState(States.GAME);
            }
        }
        
        if(xCoords >= WINDOW_WIDTH/2 - 140 && xCoords <= WINDOW_WIDTH/2 - 140 + 200){
            if(yCoords >= 350 && yCoords <= 400){
                resConfig = new ResolutionConfiguration(gamePanel);
                
            }
        }
        
        if(xCoords >= WINDOW_WIDTH/2 - 90 && xCoords <= WINDOW_WIDTH/2 - 90 + 100){
            if(yCoords >= 450 && yCoords <= 500){
                gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gamePanel.setVisible(false);
                gamePanel.dispose();
                System.exit(0);
            }
        }
    }


    /*
    Editor mouse reaction
    */
    private void editorMouseReaction(int xCoords, int yCoords) throws FileNotFoundException {
        if(xCoords >= 15 && xCoords <= 115){
            if(yCoords >= WINDOW_HEIGHT - 80 && yCoords <= WINDOW_HEIGHT - 80 + 50){
                editorConfiguration.getEditor().saveBoard();
                if(editorConfiguration.getEditor().checkObjectFinalLocation()){
                    try {
                        SaveAndLoadMap.save(editorConfiguration.getEditor().getGameData());
                        SaveAndLoadMap.load();
                        gameState = new PlayGame(gamePanel.getKeyListener(), this, gamePanel.getSpriteManager(), gamePanel);
                        gamePanel.setState(States.GAME);
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, "Save and Load does not work properly.");
                    }
                    
                } else {
                   editorConfiguration.getEditor().setSaveFailed(true);
                }
                
            }
        }
        
        if(xCoords >= 125 && xCoords <= 185){
            if(yCoords >= WINDOW_HEIGHT - 80 && yCoords <= WINDOW_HEIGHT - 80 + 30){
                editorConfiguration.getEditor().saveBoard();
            }
        }
        if(xCoords >= 195 && xCoords <= 255){
            if(yCoords >= WINDOW_HEIGHT - 80 && yCoords <= WINDOW_HEIGHT - 80 + 30){
                gamePanel.setState(States.MENU);
            }
        }
        
        if(xCoords >= WINDOW_WIDTH - 60 && xCoords <= WINDOW_WIDTH - 60 + 40){
            if(yCoords >= 5 && yCoords <= 20){
                lockPlayerOrKey = true;
                if(editorConfiguration.getEditor().isPlayerSelected()){
                    editorConfiguration.getEditor().setPlayerSelected(false);
                    editorConfiguration.getEditor().setPlayerAlreadyAdded(true);
                }
                if(editorConfiguration.getEditor().isKeySelected()){
                    editorConfiguration.getEditor().setKeySelected(false);
                    editorConfiguration.getEditor().setKeyAlreadyAdded(true);
                }

            }
        }
    }

    /*
    Game mouse reaction
    */
    private void gameMouseReaction(int xCoords, int yCoords){
        if(gameState.isUpgrade()){
            if(xCoords >= WINDOW_WIDTH/2 - 50 && xCoords <= WINDOW_WIDTH/2 +50){
                if(yCoords >= WINDOW_HEIGHT/2 - 100 && yCoords <= WINDOW_HEIGHT/2 -60){
                    int playerHealthUpgrade = (gameState.getPlayerOriginalHealth()/100)*30;
                    gameState.setUpgrade(false);
                    gameState.getPlayer().setHealthPoints(gameState.getPlayer().getHealthPoints() + playerHealthUpgrade);
                }
            }
            if(xCoords >= WINDOW_WIDTH/2 - 50 && xCoords <= WINDOW_WIDTH/2 +50){
                if(yCoords >= WINDOW_HEIGHT/2 -60 && yCoords <= WINDOW_HEIGHT/2 -20){
                    int playerDamageUpgrade = (gameState.getPlayer().getDamage() / 100)*20;
                    gameState.setUpgrade(false);
                    gameState.getPlayer().setDamage(gameState.getPlayer().getDamage() + playerDamageUpgrade);
                }
            }
            if(xCoords >= WINDOW_WIDTH/2 - 50 && xCoords <= WINDOW_WIDTH/2 - 50 + 100){
                if(yCoords >= WINDOW_HEIGHT/2 -20 && yCoords <= WINDOW_HEIGHT/2 +20){
                    gameState.setUpgrade(false);
                    gameState.getPlayer().setSpeed(gameState.getPlayer().getSpeed() + 1);
                }
            }
        }
        
        if(gameState.getPlayer().isDead() || gameState.isPlayerWinner()){
            if(xCoords >= WINDOW_WIDTH/2 - 50 && xCoords <= WINDOW_WIDTH/2 +50){
                if(yCoords >= WINDOW_HEIGHT/2 - 100 && yCoords <= WINDOW_HEIGHT/2 -60){
                    SaveAndLoadMap.load();
                    gameState = new PlayGame(gamePanel.getKeyListener(), this, gamePanel.getSpriteManager(), gamePanel);
                    gamePanel.setState(States.GAME);
                }
            }
            if(xCoords >= WINDOW_WIDTH/2 - 50 && xCoords <= WINDOW_WIDTH/2 +50){
                if(yCoords >= WINDOW_HEIGHT/2 -60 && yCoords <= WINDOW_HEIGHT/2 -20){
                    LOGGER.log(Level.SEVERE, "Go to menu.");
                    gamePanel.setState(States.MENU);
                }
            }
            if(xCoords >= WINDOW_WIDTH/2 - 50 && xCoords <= WINDOW_WIDTH/2 - 50 + 100){
                if(yCoords >= WINDOW_HEIGHT/2 -20 && yCoords <= WINDOW_HEIGHT/2 +20){
                    gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gamePanel.setVisible(false);
                    gamePanel.dispose();
                    System.exit(0);
                }
            }
        }
        
        if(xCoords >= 15 && xCoords <= 115){
            if(yCoords >= WINDOW_HEIGHT - 80 && yCoords <= WINDOW_HEIGHT - 80 + 50){
                getGameState().saveBoard();
                try {
                    SaveAndLoadMap.save(getGameState().getGameData());
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Saving during playing did not go through.");
                }
            }
        }
        
        if(xCoords >= 125 && xCoords <= 185){
            if(yCoords >= WINDOW_HEIGHT - 80 && yCoords <= WINDOW_HEIGHT - 80 + 30){
                gamePanel.setState(States.MENU);
            }
        }
        if(xCoords >= 195 && xCoords <= 255){
            if(yCoords >= WINDOW_HEIGHT - 80 && yCoords <= WINDOW_HEIGHT - 80 + 30){
                gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gamePanel.setVisible(false);
                    gamePanel.dispose();
                    System.exit(0);
            }
        }
        
        
    }

    /* Getters and Setters */
    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public boolean isIsPressed() {
        return isPressed;
    }

    public int getGridForEntityX() {
        return gridForObjectX;
    }

    public int getGridForEntityY() {
        return gridForObjectY;
    }

    public int getClickImageX() {
        return clickImageX;
    }

    public int getClickImageY() {
        return clickImageY;
    }

    public PlayGame getGameState() {
        return gameState;
    }

    public void setClickImageX(int clickImageX) {
        this.clickImageX = clickImageX;
    }

    public void setClickImageY(int clickImageY) {
        this.clickImageY = clickImageY;
    }

    
    public boolean isLockObject() {
        return lockPlayerOrKey;
    }

    public void setLockObject(boolean lockObject) {
        this.lockPlayerOrKey = lockObject;
    }

    public EditorConfiguration getEditorConfiguration() {
        return editorConfiguration;
    }

    public ResolutionConfiguration getResConfig() {
        return resConfig;
    }

    
    
}
