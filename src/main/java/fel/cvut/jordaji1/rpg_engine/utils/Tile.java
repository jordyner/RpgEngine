package fel.cvut.jordaji1.rpg_engine.utils;


import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import java.awt.Image;

/*
Map consists of Tiles and tiles is place where our game can be played. Each tile has its own sprite, name (saved data are based on saved tiles) and then here is information whetever tile is wall or is not.
Tile has its own coordinates so it is possible to track where is player located or track collisions.
*/
public class Tile {
    private Image sprite;
    private String tileName;
    private boolean isWall = false;
    
    private int tileCoordX;
    private int tileCoordY;

    public Tile(SpriteManager spriteLibrary, String tileName){
        this.tileName = tileName;
        this.sprite = spriteLibrary.getTile(tileName);
    }
    
    public Tile(String tileName){
        this.tileName = tileName;
    }
    
    /* Getters and Setters */
    public Image getSprite(){
        return sprite;  
    }

    public Tile(Image sprite) {
        this.sprite = sprite;
    }

    public int getTileCoordX() {
        return tileCoordX;
    }

    public int getTileCoordY() {
        return tileCoordY;
    }

    public void setTileCoordX(int tileCoordX) {
        this.tileCoordX = tileCoordX;
    }

    public void setTileCoordY(int tileCoordY) {
        this.tileCoordY = tileCoordY;
    }
    

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public boolean isIsWall() {
        return isWall;
    }

    public void setIsWall(boolean isWall) {
        this.isWall = isWall;
    }
}
