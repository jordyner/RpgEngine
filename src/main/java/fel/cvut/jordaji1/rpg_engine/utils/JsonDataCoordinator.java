package fel.cvut.jordaji1.rpg_engine.utils;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.ui.ResolutionConfiguration;
import java.util.ArrayList;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/*
This class manages player data and pass them into specific ArrayLists so in play game state we can easily manage them.
*/
public class JsonDataCoordinator {
    private final static Logger LOGGER = Logger.getLogger(JsonDataCoordinator.class.getName());
    
    private ArrayList<String> tileNames;
    private ArrayList<Integer> mapSize;
    private ArrayList<Integer> playerData; // 1. X coord, 2. Y coord
    private ArrayList<Integer> enemyData;
    private ArrayList<Integer> chestData;
    private ArrayList<Integer> keyData;
    
    private GamePanel gamePanel;
    
    private int currentDataIndex = 0;
    private int maxValueInData;

    public JsonDataCoordinator(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        maxValueInData = gamePanel.getLoadData().getDataToRead().size();
        
        tileNames = new ArrayList<>();
        playerData = new ArrayList<>();
        enemyData = new ArrayList<>();
        chestData = new ArrayList<>();
        keyData = new ArrayList<>();
        mapSize = new ArrayList<>();
        
        fillMapSize(gamePanel);
        fillTileNames(gamePanel);
        fillPlayerData(gamePanel);
        fillEnemyData(gamePanel);
        fillChestData(gamePanel);
        fillKeyData(gamePanel);
    }
    
    private void fillMapSize(GamePanel gamePanel){
        LOGGER.log(SEVERE, "Adding map width and height.");
        
        mapSize.add(Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex)));

        currentDataIndex++;
        mapSize.add(Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex)));

        currentDataIndex++;
    }

    private void fillTileNames(GamePanel gamePanel) {
        LOGGER.log(SEVERE, "Adding tile names.");
        boolean lastTileName = false;
        while(!lastTileName){
            String temp = gamePanel.getLoadData().getDataToRead().get(currentDataIndex);
            if(temp.contains(".png")){
                tileNames.add(temp);
                currentDataIndex++;
            } else {
                lastTileName = true;
            }
        }  
        
        
    }

    private void fillEnemyData(GamePanel gamePanel) {
        LOGGER.log(SEVERE, "Adding enemy information.");
        
        int enemyCount = Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex++));
        enemyData.add(enemyCount);
        for(int i = 0; i < (enemyCount*2 + 2); i++){
            int enemyInfo = Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex));
            enemyData.add(enemyInfo);
            currentDataIndex++;
        }

    }

    private void fillPlayerData(GamePanel gamePanel) {
        LOGGER.log(SEVERE, "Adding player information.");
        
        for(int i = 0; i < 4; i++){
            int playerInfo = Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex));
            playerData.add(playerInfo);
            currentDataIndex++;
        }
        
        
    }
    
    private void fillChestData(GamePanel gamePanel){
        LOGGER.log(SEVERE, "Adding chest information.");
        
        int chestCount = Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex++));
        chestData.add(chestCount);
        for(int i = 0; i < chestCount*2; i++){
            int chestInfo = Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex));
            chestData.add(chestInfo);

            currentDataIndex++;
        }
    }
    
    private void fillKeyData(GamePanel gamePanel){
        LOGGER.log(SEVERE, "Adding key information.");
        
        for(int i = 0; i < 2; i++){
            int keyInfo = Integer.parseInt(gamePanel.getLoadData().getDataToRead().get(currentDataIndex));
            keyData.add(keyInfo);
            
            if(maxValueInData-1 != currentDataIndex){
                currentDataIndex++;
            }
        }
    }

    /* Getter and Setters */
    public ArrayList<String> getTileNames() {
        return tileNames;
    }

    public ArrayList<Integer> getPlayerData() {
        return playerData;
    }

    public ArrayList<Integer> getEnemyData() {
        return enemyData;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public ArrayList<Integer> getChestData() {
        return chestData;
    }

    public ArrayList<Integer> getKeyData() {
        return keyData;
    }

    public ArrayList<Integer> getMapSize() {
        return mapSize;
    }
    
    
    
}
