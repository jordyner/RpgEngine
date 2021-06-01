package fel.cvut.jordaji1.rpg_engine.states;

import fel.cvut.jordaji1.rpg_engine.game.GameLoop;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import fel.cvut.jordaji1.rpg_engine.graphics.ImageLoader;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import fel.cvut.jordaji1.rpg_engine.handlers.KeyHandler;
import fel.cvut.jordaji1.rpg_engine.objects.GameObject;
import fel.cvut.jordaji1.rpg_engine.ui.EditorUI;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.IMAGE_SIZE;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import fel.cvut.jordaji1.rpg_engine.utils.Map;
import fel.cvut.jordaji1.rpg_engine.utils.Tile;
import java.awt.Color;
import java.awt.Rectangle;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;
import java.util.ArrayList;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/*
Big class for EDITOR. In this class player can choose what he wants to build. He can add one player, one key and up to 50 enemies or chests. When player is satisified with his level, he can save and play
or just save what he has just created.
*/
public class Editor{  
    private final static Logger LOGGER = Logger.getLogger(Editor.class.getName());
    
    private final Rectangle2D background;
    private final Rectangle rect;
    private final EditorUI buttonsForEditor;
    
    private final SpriteManager spriteManager;
    private final GamePanel gamePanel;
    private final KeyHandler keyListener;
    
    private final int mapWidth;
    private final int mapHeight;
    
    private final Map gameMap;
    private Tile[][] tiles;
    private int currentTile;
    
   // private boolean playerAdded = false;
    //private boolean displayPlayer = false;
    
    // Variables that concern adding player.
    private boolean playerSelected = false;
    private boolean playerAlreadyAdded = false;
    private int playerCoordX = 16;
    private int playerCoordY = 16;
    private int playerHealth;
    private int playerDamage;
    // Variables that concern adding a key. (both key and player can be added only once)
    private boolean keySelected = false;
    private boolean keyAlreadyAdded = false;
    private int keyCoordX;
    private int keyCoordY;
    // Variables for enemies
    private boolean[] enemySelected = new boolean[50];
    private int[] enemyCoordsX = new int[50];
    private int[] enemyCoordsY = new int[50];
    private int enemyCount = 0;
    private int currentEnemy = 0;
    private boolean addingEnemy = false;
    private int enemyHealth;
    private int enemyDamage;
    // Variables for chests
    private boolean[] chestSelected = new boolean[50];
    private int[] chestCoordX = new int[50];
    private int[] chestCoordY = new int[50];
    private int chestCount = 0;
    private int currentChest = 0;
    private boolean addingChest = false;
    
    //private boolean imagesDisplayed = false;
    //private boolean tilesDisplayed = false;
    
    private ArrayList<String> gameData;
    //private final ArrayList<GameObject> allObjects = new ArrayList<>();
    
    private int cameraSpeed = 20; // default 20, it means how fast will camera move
    
    // If player decides to add game object outside gameMap, this will deny the save and tells him to start over.
    private boolean saveAllowed;
    private boolean saveFailed = false;
    
    
    //private int x,y; // for checkWhichTile

    public Editor(GamePanel gamePanel, KeyHandler keyListener) {
        this.gamePanel = gamePanel;   
        this.keyListener = keyListener;
        this.spriteManager = new SpriteManager();
        
        background = new Rectangle2D.Double(0, 0 , WINDOW_WIDTH, WINDOW_HEIGHT);
        buttonsForEditor = new EditorUI();
        mapWidth = gamePanel.getMouseListener().getEditorConfiguration().getMapWidth();
        mapHeight = gamePanel.getMouseListener().getEditorConfiguration().getMapHeight();
        gameMap = new Map(spriteManager, mapWidth, mapHeight);
        currentTile = 13; // default Tile
        rect = new Rectangle(100,100,100,100); // need something what camera can focus on
        gameData = new ArrayList<>();
        
        playerHealth = gamePanel.getMouseListener().getEditorConfiguration().getPlayerHealth();
        playerDamage = gamePanel.getMouseListener().getEditorConfiguration().getPlayerDamage();
        enemyHealth = gamePanel.getMouseListener().getEditorConfiguration().getEnemyHealth();
        enemyDamage = gamePanel.getMouseListener().getEditorConfiguration().getEnemyDamage();
    }
    
    public void update() {
        checkWhichTile(gamePanel.getMouseListener().getClickImageX(), gamePanel.getMouseListener().getClickImageY());
        checkWhichObject(gamePanel.getMouseListener().getClickImageX(), gamePanel.getMouseListener().getClickImageY());
        gamePanel.getCamera().centerOnRect(rect);
        editorMovement();
    }
         
    public void render(Graphics g){      
        Graphics2D graphics2d = (Graphics2D)g;
        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        renderMap(graphics2d);
        renderObjects(graphics2d);
        renderImage(graphics2d);
        
        if(saveFailed){
            buttonsForEditor.renderSaveFailed(graphics2d);
        }
        
        buttonsForEditor.renderButtons(graphics2d);
        
    }
    
    // Renders palette of everything that player can use for creating his map
    private void renderImage(Graphics2D graphics){
        graphics.drawImage(ImageLoader.loadImage("/MapPalette.png"), 0, 0, null);
    }

    private void renderMap(Graphics2D graphics) {
        tiles = gameMap.getTiles();
        
        if(!playerSelected || !keySelected){
            editBoard(tiles);
        }
        
        for(int x = 0; x < mapWidth; x++){
            for(int y = 0; y < mapHeight; y++){
                if(x == 0 || y == 0 || x == mapWidth-1 || y == mapHeight-1){
                    tiles[x][y].setSprite(spriteManager.getTile("tile02.png"));
                    tiles[x][y].setTileName("tile02.png");
                } 
                graphics.drawImage(tiles[x][y].getSprite(),
                        (int) (x * IMAGE_SIZE/4 - gamePanel.getCamera().getxOffset()),
                        (int) (y * IMAGE_SIZE/4 - gamePanel.getCamera().getyOffset()),
                        null);
            }
        }
    }
    
    private void editBoard(Tile[][] tiles){
        if(gamePanel.getMouseListener().isIsPressed()){
            // this condition prevents player from editing board outside game map (it would crash)
            if(((gamePanel.getMouseListener().getGridX()/16 + gamePanel.getCamera().getxOffset()/16) >= 0) &&
                    (gamePanel.getMouseListener().getGridX()/16 + gamePanel.getCamera().getxOffset()/16) <= mapWidth-1 &&
                    (gamePanel.getMouseListener().getGridY()/16 + gamePanel.getCamera().getyOffset()/16) <= mapHeight-1 && 
                    (gamePanel.getMouseListener().getGridY()/16 + gamePanel.getCamera().getyOffset()/16) >= 0) {
                int xCoord = (int) ((gamePanel.getMouseListener().getGridX() + gamePanel.getCamera().getxOffset())/16);
                int yCoord = (int) ((gamePanel.getMouseListener().getGridY() + gamePanel.getCamera().getyOffset())/16);

                tiles[xCoord][yCoord].setSprite(spriteManager.getTile("tile0" + currentTile + ".png"));
                
                tiles[xCoord][yCoord].setTileName("tile0" + currentTile + ".png");
            }
        }
    }  
    
    
    
    // Every game object needs his own coordinates so I can add multiple objects
    private void renderObjects(Graphics2D graphics){
        if(playerSelected && !gamePanel.getMouseListener().isLockObject() ){
            playerCoordX = (int) (gamePanel.getMouseListener().getGridForEntityX() + gamePanel.getCamera().getxOffset());
            playerCoordY = (int) (gamePanel.getMouseListener().getGridForEntityY() + gamePanel.getCamera().getyOffset());
        }
        
        graphics.drawImage(ImageLoader.loadImage("/Character/singlePlayerSprite.png"),
                        (int) (playerCoordX - gamePanel.getCamera().getxOffset()),
                        (int) (playerCoordY - gamePanel.getCamera().getyOffset()),
                        null);
        
        if(enemyCount > 0){
            if(enemySelected[currentEnemy-1] && !gamePanel.getMouseListener().isLockObject()){
            enemyCoordsX[currentEnemy-1] = (int) (gamePanel.getMouseListener().getGridForEntityX() + gamePanel.getCamera().getxOffset());
            enemyCoordsY[currentEnemy-1] = (int) (gamePanel.getMouseListener().getGridForEntityY() + gamePanel.getCamera().getyOffset());
            }

            for(int i = 0; i < enemyCount; i++){
                graphics.drawImage(ImageLoader.loadImage("/Enemy/singleEnemySprite.png"),
                        (int) (enemyCoordsX[i] - gamePanel.getCamera().getxOffset()),
                        (int) (enemyCoordsY[i] - gamePanel.getCamera().getyOffset()),
                        null);
            }
        }
        
        if(chestCount > 0){
            if(chestSelected[currentChest-1] && !gamePanel.getMouseListener().isLockObject()){
            chestCoordX[currentChest-1] = (int) (gamePanel.getMouseListener().getGridForEntityX() + gamePanel.getCamera().getxOffset());;
            chestCoordY[currentChest-1] = (int) (gamePanel.getMouseListener().getGridForEntityY() + gamePanel.getCamera().getyOffset());
            }

            for(int i = 0; i < chestCount; i++){
                graphics.drawImage(ImageLoader.loadImage("/Collectibles/Chest.png"),
                        (int) (chestCoordX[i] - gamePanel.getCamera().getxOffset()),
                        (int) (chestCoordY[i] - gamePanel.getCamera().getyOffset()),
                        null);
            }
        }
        
        
        if(keySelected && !gamePanel.getMouseListener().isLockObject()){
            keyCoordX = (int) (gamePanel.getMouseListener().getGridForEntityX() + gamePanel.getCamera().getxOffset());;
            keyCoordY = (int) (gamePanel.getMouseListener().getGridForEntityY() + gamePanel.getCamera().getyOffset());
        }
        graphics.drawImage(ImageLoader.loadImage("/Collectibles/keyPic.png"),
                            (int) (keyCoordX - gamePanel.getCamera().getxOffset()),
                            (int) (keyCoordY - gamePanel.getCamera().getyOffset()),
                            null);
    }
    
    
    public void editorMovement(){
        if(keyListener.allKeys[VK_D] && keyListener.allKeys[VK_W]){
            rect.x += cameraSpeed;
            rect.y -= cameraSpeed;
            
        } else if(keyListener.allKeys[VK_A] && keyListener.allKeys[VK_W]){
            rect.x -= cameraSpeed;
            rect.y -= cameraSpeed;

        } else if(keyListener.allKeys[VK_S] && keyListener.allKeys[VK_A]){
            rect.x -= cameraSpeed;
            rect.y += cameraSpeed;

        } else if(keyListener.allKeys[VK_S] && keyListener.allKeys[VK_D]){
            rect.x += cameraSpeed;
            rect.y += cameraSpeed;

            // Normalni
       } else if(keyListener.allKeys[VK_W]){
            rect.y -= cameraSpeed;

        } else if(keyListener.allKeys[VK_S]){
            rect.y += cameraSpeed;

        } else if(keyListener.allKeys[VK_A]){
            rect.x -= cameraSpeed;

        } else if(keyListener.allKeys[VK_D]){
            rect.x += cameraSpeed;
        }
    } 
    
    // Board is saved in way that all game data are put in an ArrayList in a specific order and then in loading they are read in the same order.
    public void saveBoard(){    
        LOGGER.log(SEVERE, "Saving game data from Editor state.");
        
        gameData.add(Integer.toString(mapWidth));
        gameData.add(Integer.toString(mapHeight));

        for(int x = 0; x < mapWidth; x++){
            for(int y = 0; y < mapHeight; y++){
                gameData.add(tiles[x][y].getTileName());
            }
        }
        // player coordinates
        gameData.add(Integer.toString(playerCoordX));
        gameData.add(Integer.toString(playerCoordY));
        gameData.add(Integer.toString(playerHealth));
        gameData.add(Integer.toString(playerDamage));



        gameData.add(Integer.toString(enemyCount));
        for(int i = 0; i < enemyCount; i++){
            gameData.add(Integer.toString(enemyCoordsX[i]));
            gameData.add(Integer.toString(enemyCoordsY[i]));
        }
        gameData.add(Integer.toString(enemyHealth));
        gameData.add(Integer.toString(enemyDamage));

        gameData.add(Integer.toString(chestCount));
        for(int i = 0; i < chestCount; i++){
            gameData.add(Integer.toString(chestCoordX[i]));
            gameData.add(Integer.toString(chestCoordY[i]));
        }

        gameData.add(Integer.toString(keyCoordX));
        gameData.add(Integer.toString(keyCoordY));
        
    }
    
    // This method makes sure that player placed all objects on board and not outside board. In case player places any object outside board, then retValue is false and player cant save map.
    public boolean checkObjectFinalLocation(){
        boolean retValue = false;
        int outSideMapCounter = 0;
        
        for(int i = 0; i < enemyCount; i++){
            if(enemyCoordsX[i]/16 < 0 || enemyCoordsX[i]/16 > mapWidth-1 || enemyCoordsY[i]/16 < 0 || enemyCoordsY[i]/16 > mapHeight-1){
                outSideMapCounter ++;
            }
        }
        
        if(playerCoordX/16 < 0 || playerCoordX/16 > mapWidth-1 || playerCoordY/16 < 0 || playerCoordY/16 > mapHeight-1){
            outSideMapCounter ++;
        }
        
        if(keyCoordX/16 < 0 || keyCoordX/16 > mapWidth-1 || keyCoordY/16 < 0 || keyCoordY/16 > mapHeight-1){
            outSideMapCounter ++;
        }
        
        if(outSideMapCounter == 0){
            retValue = true;
        }
        
        LOGGER.log(SEVERE, "Checking of object outside game map. Number of objects outside game map: " + outSideMapCounter);
        
        return retValue;
    }
    
    private void checkWhichObject(int x, int y){
        
        if(x >= 112 && x <= 128){
            if(y >= 112 && y <= 128){
                if(!playerAlreadyAdded){
                    gamePanel.getMouseListener().setClickImageX(0);
                    gamePanel.getMouseListener().setClickImageY(0);
                    playerSelected = true;
                    gamePanel.getMouseListener().setLockObject(false);
                }
                
            }
        }
        
        if(x >= 128 && x <= 146){
            if(y >= 112 && y <= 128){
                if(playerAlreadyAdded && keyAlreadyAdded && enemyCount < 50){
                    if(chestCount > 0){
                         chestSelected[currentChest-1] = false;
                    }
                    gamePanel.getMouseListener().setClickImageX(0);
                    gamePanel.getMouseListener().setClickImageY(0);
                    enemySelected[currentEnemy++] = true;
                    addingEnemy = true;
                    enemyCount ++;
                    gamePanel.getMouseListener().setLockObject(false);
                }
            }
        }  
        
        if(x >= 0 && x <= 20){
            if(y >= 128 && y <= 142){
               if(playerAlreadyAdded && keyAlreadyAdded && chestCount < 50){
                    if(enemyCount > 0){
                         enemySelected[currentEnemy-1] = false;
                    }
                    gamePanel.getMouseListener().setClickImageX(0);
                    gamePanel.getMouseListener().setClickImageY(0);
                    chestSelected[currentChest++] = true;
                    addingChest = true;
                    chestCount ++;
                    gamePanel.getMouseListener().setLockObject(false);
                }
            }
        }
        
        if(x >= 21 && x <= 37){
            if(y >= 128 && y <= 144){
                if(!keyAlreadyAdded &&  playerAlreadyAdded){
                    gamePanel.getMouseListener().setClickImageX(0);
                    gamePanel.getMouseListener().setClickImageY(0);
                    keySelected = true;
                    gamePanel.getMouseListener().setLockObject(false);
                }
            }
        }
    }
    
    // detects which tiles from palette players selected. It works that each pixel location is associated with some tile number and each tile number has different sprite.
    private void checkWhichTile(int x, int y) {        
        for(int i = 0; i < 16 * 10; i+=16){
            for(int j = 0; j < 16 * 8; j+=16){
                if(x >= i && x <= i+16){
                    if(y >= j && y <= j+16){
                        currentTile = (i+16)/16 + j/16*10;
                    }
                }
            }
        }
    }
    
    /* Getters and Setters */
    public ArrayList<String> getGameData() {
        return gameData;
    }
    
    public int tileDatSize(){
        return gameData.size();
    }
    
    public Map getGameMap() {
        return gameMap;
    }
    
    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setCurrentChest(int currentChest) {
        this.currentChest = currentChest;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setPlayerAlreadyAdded(boolean playerAlreadyAdded) {
        this.playerAlreadyAdded = playerAlreadyAdded;
    }
    
    public boolean isPlayerSelected() {
        return playerSelected;
    }

    public void setPlayerSelected(boolean playerSelected) {
        this.playerSelected = playerSelected;
    } 

    public int getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(int currentEnemy) {
        this.currentEnemy = currentEnemy;
    }

    public void setPlayerCoordX(int playerCoordX) {
        this.playerCoordX = playerCoordX;
    }

    public void setPlayerCoordY(int playerCoordY) {
        this.playerCoordY = playerCoordY;
    }

    public int getPlayerCoordX() {
        return playerCoordX;
    }

    public int getPlayerCoordY() {
        return playerCoordY;
    }

    public boolean isKeySelected() {
        return keySelected;
    }

    public boolean[] getEnemySelected() {
        return enemySelected;
    }

    public boolean[] getChestSelected() {
        return chestSelected;
    }

    public void setKeySelected(boolean keySelected) {
        this.keySelected = keySelected;
    }

    public void setKeyAlreadyAdded(boolean keyAlreadyAdded) {
        this.keyAlreadyAdded = keyAlreadyAdded;
    }

    public boolean isAddingEnemy() {
        return addingEnemy;
    }

    public boolean isAddingChest() {
        return addingChest;
    }

    public void setAddingEnemy(boolean addingEnemy) {
        this.addingEnemy = addingEnemy;
    }

    public void setAddingChest(boolean addingChest) {
        this.addingChest = addingChest;
    }

    public void setSaveFailed(boolean saveFailed) {
        this.saveFailed = saveFailed;
    }
    
    
}