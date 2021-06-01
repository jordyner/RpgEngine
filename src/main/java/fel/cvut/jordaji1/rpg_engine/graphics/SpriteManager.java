package fel.cvut.jordaji1.rpg_engine.graphics;

import fel.cvut.jordaji1.rpg_engine.game.GameLoop;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/*
All object sprites, tile images and other are loaded through SpriteManager class to HashMaps so programmer can comfortably choose what he needs.
*/
public class SpriteManager {
    
    private final static Logger LOGGER = Logger.getLogger(GameLoop.class.getName());

    private final static String CHARACTER_FOLDER_LOCATION = "src/main/resources/Character";
    private final static String TILE_FOLDER_LOCATION = "src/main/resources/Tile";
    private final static String ENEMY_FOLDER_LOCATION = "src/main/resources/Enemy";
    private final static String COLLECTIBLE_FOLDER_LOCATION = "src/main/resources/Collectibles";

    private final Map<String, SpriteSet> character;
    private final Map<String, Image> tiles;
    private final Map<String, SpriteSet> enemy;
    private final Map<String, SpriteSet> collectible;

    public SpriteManager() {
        character = new HashMap<>();
        tiles = new HashMap<>();
        enemy = new HashMap<>();
        collectible = new HashMap<>();
        findAndLoadSpritesFromDisk();
    }
 
   private void findAndLoadSpritesFromDisk() {
        loadSpriteForCharacter();
        loadSpriteForEnemy();
        loadSpriteForTiles();
        loadSpriteForKey();
    }
    
    // Loads sprites for Tiles and puts them to its HashMap
    private void loadSpriteForTiles() {
        String[] imagesInFolder = getSheetsInFolder(TILE_FOLDER_LOCATION);
        
        for(int i = 0; i < imagesInFolder.length; i++){
            String imageName = imagesInFolder[i].substring(imagesInFolder[i].lastIndexOf("\\") + 1);
            tiles.put(imageName, ImageLoader.loadImage("/Tile/" + imageName));
        }
        
    }
    
    // Loads sprites for Key and puts them to its HashMap
    private void loadSpriteForKey(){
        String dirName = COLLECTIBLE_FOLDER_LOCATION.substring(COLLECTIBLE_FOLDER_LOCATION.length() - 12);
        SpriteSet spriteSetsForKey = new SpriteSet();
        
        String[] sheetsInFolder = getSheetsInFolder(COLLECTIBLE_FOLDER_LOCATION);
        
        for(int i = 0; i < sheetsInFolder.length; i++){
            String spriteName = sheetsInFolder[i].substring(sheetsInFolder[i].lastIndexOf("\\") + 1);
            spriteSetsForKey.addSheet(spriteName, ImageLoader.loadImage("/Collectibles/" + spriteName));
        }
        character.put(dirName, spriteSetsForKey);
    }
    
    // Loads sprites for Player and puts them to its HashMap
    private void loadSpriteForCharacter(){
        String dirName = CHARACTER_FOLDER_LOCATION.substring(CHARACTER_FOLDER_LOCATION.length() - 9);
        SpriteSet spriteSetForCharacter = new SpriteSet();
        
        String[] sheetsInFolder = getSheetsInFolder(CHARACTER_FOLDER_LOCATION);
        
        for(int i = 0; i < sheetsInFolder.length; i++){
            String spriteName = sheetsInFolder[i].substring(sheetsInFolder[i].lastIndexOf("\\") + 1);
            spriteSetForCharacter.addSheet(spriteName, ImageLoader.loadImage("/Character/" + spriteName));
        }
        character.put(dirName, spriteSetForCharacter);
    }
    
    // Loads sprites for Enemy and puts them to its HashMap
    private void loadSpriteForEnemy() {
        String dirName = ENEMY_FOLDER_LOCATION.substring(ENEMY_FOLDER_LOCATION.length() - 5);
        SpriteSet spriteSetForEnemy = new SpriteSet();
        
        String[] sheetsInFolder = getSheetsInFolder(ENEMY_FOLDER_LOCATION);
        
        for(int i = 0; i < sheetsInFolder.length; i++){
            String spriteName = sheetsInFolder[i].substring(sheetsInFolder[i].lastIndexOf("\\") + 1);
            spriteSetForEnemy.addSheet(spriteName, ImageLoader.loadImage("/Enemy/" + spriteName));
        }
        enemy.put(dirName, spriteSetForEnemy);
    }
    
        
    private String[] getSheetsInFolder(String pathToFolder) {
        File[] eachSheet = new File(pathToFolder).listFiles(File::isFile);
        String[] sheetToString = new String[eachSheet.length];
        for(int i = 0; i < eachSheet.length; i++){
            sheetToString[i] = eachSheet[i].toString();
        }
        return sheetToString;
    }  

    /* Getters and Setters */
    public Image getTile(String name){
        return tiles.get(name);
    }

    public Map<String, Image> getTiles() {
        return tiles;
    }

    public Map<String, SpriteSet> getCharacter() {
        return character;
    }

    public Map<String, SpriteSet> getEnemy() {
        return enemy;
    }

    public Map<String, SpriteSet> getCollectible() {
        return collectible;
    }
    
    


    
    
    
}
