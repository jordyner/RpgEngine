package fel.cvut.jordaji1.rpg_engine.states;

import fel.cvut.jordaji1.rpg_engine.objects.Enemy;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import fel.cvut.jordaji1.rpg_engine.objects.GameObject;
import fel.cvut.jordaji1.rpg_engine.objects.Player;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import fel.cvut.jordaji1.rpg_engine.handlers.MouseHandler;
import fel.cvut.jordaji1.rpg_engine.handlers.KeyHandler;
import fel.cvut.jordaji1.rpg_engine.objects.Chest;
import fel.cvut.jordaji1.rpg_engine.objects.Key;
import fel.cvut.jordaji1.rpg_engine.ui.GameUI;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.IMAGE_SIZE;
import fel.cvut.jordaji1.rpg_engine.utils.JsonDataCoordinator;
import fel.cvut.jordaji1.rpg_engine.utils.Map;
import fel.cvut.jordaji1.rpg_engine.utils.Tile;
import java.awt.Graphics2D;
import static java.awt.event.KeyEvent.VK_E;
import java.util.Random;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/*
PLAY GAME class is the whole game. It has access to read data from json file and render them in place where everything is suppose to render.
*/
public class PlayGame {
    private final static Logger LOGGER = Logger.getLogger(PlayGame.class.getName());
    
    // If player collides with enemy, COMBAT state is triggered and combat is played.
    private Combat combat;
    
    // buttons that player can use during game and also renders graphics if player dies, wins or opens some chest.
    private final GameUI buttonsForGame;
    
    private final GamePanel gamePanel;
    private final SpriteManager spriteManager;
    private final JsonDataCoordinator jsonData;
    
    private final ArrayList<GameObject> allObjects = new ArrayList<>(); // all objects -> player,enemy,key,chests
        
    private final Map gameMap;
    
    private int counterForTiles = 0;
    
    private final Player player;
    private final int playerOriginalHealth;
    
    //private Enemy[] enemy;
    private final Enemy[] enemy;
    private int enemyNumber = 0;
    
    private final Chest[] chest;
    private int chestNumber = 0;
    
    private final Key key;
    
    private boolean keyCollected = false;
    private boolean upgrade = false;
    private int cursedChestCalculator;
    private final Random random;
    
    private boolean playerWinsTheGame = false;
    private int deathEnemyCount;
    
    private final Tile[][] tiles;
    
    private final ArrayList<String> gameData; // data that are loaded from json file
    
    
    public PlayGame(KeyHandler keyListener, MouseHandler mouseListener, SpriteManager spriteManager, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.spriteManager = spriteManager;
        this.jsonData = new JsonDataCoordinator(gamePanel);
        buttonsForGame = new GameUI();
        gameData = new ArrayList<>();
        
        random = new Random();
        
        gameMap = new Map(spriteManager, jsonData.getMapSize().get(0), jsonData.getMapSize().get(1));
        
        /* S E T U P     P L A Y E R*/
        player = new Player(jsonData.getPlayerData().get(0) - 20,
                            jsonData.getPlayerData().get(1) - 18,
                            64, 64, 
                            jsonData.getPlayerData().get(2),
                            jsonData.getPlayerData().get(3), 
                            keyListener, mouseListener, spriteManager, gamePanel);
        playerOriginalHealth = player.getHealthPoints();
        /* S E T U P       E N E M Y*/
        
        enemy = new Enemy[jsonData.getEnemyData().get(0)];
        for(int i = 1; i <= jsonData.getEnemyData().get(0)*2; i+=2){
            enemy[enemyNumber] = new Enemy(jsonData.getEnemyData().get(i) - 20,
                                jsonData.getEnemyData().get(i+1) - 18,
                                64, 64,
                                jsonData.getEnemyData().get(jsonData.getEnemyData().get(0)*2+1),
                                jsonData.getEnemyData().get(jsonData.getEnemyData().get(0)*2+2),
                                spriteManager, mouseListener, gamePanel);
            allObjects.add(enemy[enemyNumber]);
            enemyNumber++;
        }
        
        /* S E T U P     K E Y*/
        key = new Key(jsonData.getKeyData().get(0), jsonData.getKeyData().get(1), 64, 64, spriteManager, gamePanel);
        allObjects.add(key);
        
        /* S E T U P       CH E S T*/
        chest = new Chest[jsonData.getChestData().get(0)];
        for(int i = 1; i <= jsonData.getChestData().get(0)*2; i+=2){
            chest[chestNumber] = new Chest(jsonData.getChestData().get(i), jsonData.getChestData().get(i+1), 64, 64, spriteManager, gamePanel);
            allObjects.add(chest[chestNumber]);
            chestNumber++;
        }
        


        /* MAKE TILES */
        tiles = gameMap.getTiles();
        for(int x = 0; x < jsonData.getMapSize().get(0); x++){
            for(int y = 0; y < jsonData.getMapSize().get(1); y++){
                tiles[x][y].setSprite(spriteManager.getTile(jsonData.getTileNames().get(counterForTiles)));
                if(jsonData.getTileNames().get(counterForTiles).equals("tile02.png") || jsonData.getTileNames().get(counterForTiles).equals("tile046.png") ||
                        jsonData.getTileNames().get(counterForTiles).equals("tile07.png") || jsonData.getTileNames().get(counterForTiles).equals("tile044.png") ||
                        jsonData.getTileNames().get(counterForTiles).equals("tile015.png") || jsonData.getTileNames().get(counterForTiles).equals("tile062.png") ||
                        jsonData.getTileNames().get(counterForTiles).equals("tile061.png") || jsonData.getTileNames().get(counterForTiles).equals("tile08.png")){
                    tiles[x][y].setIsWall(true);
                    tiles[x][y].setTileCoordX(x);
                    tiles[x][y].setTileCoordX(y);
                }
                counterForTiles++;
            }
        }
    }
    
    public void render(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        Graphics2D graphics2d = (Graphics2D)graphics;
        renderMap(graphics2d);
        
        // Chest rendering
        for(int i = 0; i < chest.length; i++){
            chest[i].render(graphics);
        }
        
        player.render(graphics);
        
        // key rendering only if key is not collected
        if(!key.isCollected()){
            key.render(graphics);
        }
        
        // rendering of every live enemy
        for(int i = 0; i < enemy.length; i++){
            if(!enemy[i].isDeath()){
               enemy[i].render(graphics);
            }
        }
        
        // if key is collected then upgrade is true so player can choose upgrade
        if(upgrade){
            buttonsForGame.displayUpgrade(graphics);
        }
        
        if(player.isDead()){
            buttonsForGame.displayGameOver(graphics);
        }
        
        if(playerWinsTheGame){
            buttonsForGame.displayPlayerWins(graphics);
        }
        
        buttonsForGame.renderUI(graphics2d, player.getHealthPoints(), player.getDamage());
        buttonsForGame.renderButtons(graphics2d);
    }

    
    public void update(){
        
        // collision between player and objects, it checks position where player wants to move to. Collisions work with objects and not just player or enemy but with gameObject. So that is the reason
        // why allObject arrayList is here. Without allObject arrayList I could not detect collisions.
        if(player.checkObjectCollisions(0, 0)){
            for(int i = 0; i < enemy.length; i++){
                if(allObjects.get(i) == player.getTheCollision() && !enemy[i].isDeath() && !player.isDead()){
                    LOGGER.log(SEVERE, "Player collides with an enemy! Combat mode is ON.");
                    combat = new Combat(player, enemy[i], gamePanel);
                    gamePanel.setState(States.COMBAT);
                }

            }
            // allObjects.get(enemy.length) -> on this index in arraylist is stored key object.
            if(allObjects.get(enemy.length) == player.getTheCollision()){
                key.setCollected(true);
            }
            
            for(int i = 1; i <= chest.length; i++){
                // chest objects are behind enemies and key
                if(allObjects.get(enemy.length + i) == player.getTheCollision()){
                    if(key.isCollected() && !chest[i-1].isOpened() && gamePanel.getKeyListener().allKeys[VK_E]){
                        // calculation of 33% chance that opened chest is cursed and player loses 50% of his health
                        cursedChestCalculator = random.nextInt(3);
                        if(cursedChestCalculator == 0){
                            LOGGER.log(SEVERE, "Chests was cursed. Player receives damage.");
                            player.setHealthPoints(player.getHealthPoints()/2);
                            chest[i-1].setOpened(true); 
                        } else{
                            LOGGER.log(SEVERE, "Chest opened. Player can choose desired bonus.");
                            upgrade = true;
                            chest[i-1].setOpened(true); 
                        }
                    }
                }
            }
        }
        
        if(!playerWinsTheGame){
            player.update();
        }
        
        key.update();
        
        deathEnemyCount = 0;
        for(int i = 0; i < enemy.length; i++){
            if(!enemy[i].isDeath()){
                enemy[i].update();
            } else {
                deathEnemyCount ++;

                
                if(jsonData.getEnemyData().get(0) == deathEnemyCount){
                    playerWinsTheGame = true;
                }
            }
        }
        
        for(int i = 0; i < chest.length; i++){
            chest[i].update();
        }
        
    }
    
    
    private void renderMap(Graphics2D graphics) {
        for(int x = 0; x < jsonData.getMapSize().get(0); x++){
            for(int y = 0; y < jsonData.getMapSize().get(1); y++){
                graphics.drawImage(tiles[x][y].getSprite(),
                        (int) (x * IMAGE_SIZE/4 - gamePanel.getCamera().getxOffset()),
                        (int) (y * IMAGE_SIZE/4 - gamePanel.getCamera().getyOffset()),
                        null);
            }
        }
    }
    
    /**
     * Saves board if mouse handler calls for it. (= if player pressed SAVE)
     */
    public void saveBoard(){
        LOGGER.log(SEVERE, "Board was saved.");
        
        gameData.add(Integer.toString(jsonData.getMapSize().get(0)));
        gameData.add(Integer.toString(jsonData.getMapSize().get(1)));
        
        int tileNum = 0;
        for(int x = 0; x < jsonData.getMapSize().get(0); x++){
            for(int y = 0; y < jsonData.getMapSize().get(1); y++){
                gameData.add(jsonData.getTileNames().get(tileNum++));
            }
        }
        // player coordinates
        gameData.add(Integer.toString(player.getObjectCoordinateX()+20));
        gameData.add(Integer.toString(player.getObjectCoordinateY()+18));
        gameData.add(Integer.toString(player.getHealthPoints()));
        gameData.add(Integer.toString(player.getDamage()));
        
        int enemyAlive = 0;
        for(int i = 0; i < jsonData.getEnemyData().get(0); i++){
            if(!enemy[i].isDeath()){
                enemyAlive++;
            }
        }
        
        gameData.add(Integer.toString(enemyAlive));
        for(int i = 0; i < jsonData.getEnemyData().get(0); i++){
            if(!enemy[i].isDeath()){
                gameData.add(Integer.toString(enemy[i].getObjectCoordinateX()+20));
                gameData.add(Integer.toString(enemy[i].getObjectCoordinateY()+18));
            }
            
        }
        gameData.add(Integer.toString(jsonData.getEnemyData().get(jsonData.getEnemyData().get(0)*2+1)));
        gameData.add(Integer.toString(jsonData.getEnemyData().get(jsonData.getEnemyData().get(0)*2+2)));
        
        
        int closedChests = 0;
        for(int i = 0; i < jsonData.getChestData().get(0); i++){
            if(!chest[i].isOpened()){
                closedChests++;
            }
        }
        gameData.add(Integer.toString(closedChests));
        for(int i = 0; i < jsonData.getChestData().get(0); i++){
            if(!chest[i].isOpened()){
                gameData.add(Integer.toString(chest[i].getObjectCoordinateX()));
                gameData.add(Integer.toString(chest[i].getObjectCoordinateY()));
            }
            
        }
        
        if(!keyCollected){
            gameData.add(Integer.toString(key.getObjectCoordinateX()));
            gameData.add(Integer.toString(key.getObjectCoordinateY()));
        } else {
            gameData.add(Integer.toString(player.getObjectCoordinateX()));
            gameData.add(Integer.toString(player.getObjectCoordinateY()));
        }
        
    }

    /* Getters and Setters */
    public Combat getCombat() {
        return combat;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public Chest[] getChest() {
        return chest;
    }

    public ArrayList<String> getGameData() {
        return gameData;
    }

    public boolean isPlayerWinner() {
        return playerWinsTheGame;
    }

    public int getPlayerOriginalHealth() {
        return playerOriginalHealth;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    
    public ArrayList<GameObject> getAllObjects() {
        return allObjects;
    }

    public int getDeathEnemyCount() {
        return deathEnemyCount;
    }
}
