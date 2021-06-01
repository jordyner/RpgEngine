package fel.cvut.jordaji1.rpg_engine.utils;

import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;

/*
Map consists of tiles and count of tiles is set by player (the count of tiles is mapWidth * mapHeight)
Default sprite of each Tile is tile013 = dirt
*/
public class Map {
    private Tile[][] tiles;
    private final int mapWidth;
    private final int mapHeight;

    public Map(SpriteManager spriteLibrary, int mapWidth, int mapHeight) {
        tiles = new Tile[mapWidth][mapHeight];
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                tiles[i][j] = new Tile(spriteLibrary, "tile013.png");
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    
}
