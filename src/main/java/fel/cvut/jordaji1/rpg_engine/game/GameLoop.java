package fel.cvut.jordaji1.rpg_engine.game;

/*
This class is main source for rendering and updating. Main goal is that rendering is done as often as possible and update is done once per 60 frames.
*/
public class GameLoop implements Runnable{
    
    private GamePanel game = new GamePanel();
    

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1000000000.0 / 60;
        double changeInSeconds = 0;

        while(true){
            long now = System.nanoTime();
            
            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            
            while(changeInSeconds >= 1){
                update();
                changeInSeconds = 0;
            }
            
            render();

            
            lastTime = now;
        }
    }
    
    // all rendering starts here, goes as fast as possible
    public void render(){
        game.render();
    }
    
    // all updates starts here, goes only 60 frames per second
    public void update(){
        game.update();
    }
    
    
    
}
