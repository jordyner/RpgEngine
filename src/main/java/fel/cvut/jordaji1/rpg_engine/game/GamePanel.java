package fel.cvut.jordaji1.rpg_engine.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import fel.cvut.jordaji1.rpg_engine.states.Menu;
import fel.cvut.jordaji1.rpg_engine.states.States;
import fel.cvut.jordaji1.rpg_engine.handlers.KeyHandler;
import fel.cvut.jordaji1.rpg_engine.handlers.MouseHandler;
import fel.cvut.jordaji1.rpg_engine.utils.Camera;
import fel.cvut.jordaji1.rpg_engine.utils.SaveAndLoadMap;

/*
    Main class of the project. Most important stuff is set up here and rendering/updating of states is coordinated here aswell.
*/
public class GamePanel extends JFrame {
    // Default window parameters when u launch the game engine
    public static int WINDOW_WIDTH = 1366;
    public static int WINDOW_HEIGHT = (WINDOW_WIDTH/16)*9;
    
    // Canvas is place where everything is drawing
    private final Canvas canvas;
    // KeyHandler is responsible for key input.
    private final KeyHandler keyListener = new KeyHandler(this);
    // MouseHandler is responsible for mouse input.
    private final MouseHandler mouseListener = new MouseHandler(this);
    // SpriteManager loads all images for each object to Hashmaps from specific folders.
    private final SpriteManager spriteManager = new SpriteManager();
    
    private final Camera camera;
    private final JFrame frame;
    
    // Load
    private SaveAndLoadMap loadData;
    
    // Only Menu state is created here because menu state is the first one and other states are processed only under certain circumstances.
    private States state = States.MENU;
    private Menu menu;

    public GamePanel() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); 
        frame.setTitle("RPG Game Engine"); 
        frame.setBackground(Color.yellow);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        canvas.setBackground(Color.BLACK);
        frame.add(canvas); 
        canvas.createBufferStrategy(3);
        
        canvas.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        
        camera = new Camera(this, 0, 0);
        loadData = new SaveAndLoadMap();

        menu = new Menu(this);

        
    }
    
    public void update(){
             
        if(state == States.MENU){
            menu.update();
        } else if(state == States.EDITOR){
            mouseListener.getEditorConfiguration().getEditor().update();
        } else if(state == States.GAME){
            mouseListener.getGameState().update();
        } else if(state == States.COMBAT){
            mouseListener.getGameState().getCombat().update();
        }
        
        
    }
    
    public void render(){
        BufferStrategy bufferStrat = canvas.getBufferStrategy();        
        Graphics graphics = bufferStrat.getDrawGraphics();
        super.paint(graphics);
        
        if(state == States.MENU){
            menu.render(graphics);
        } else if(state == States.EDITOR){
            mouseListener.getEditorConfiguration().getEditor().render(graphics);
        } else if(state == States.GAME){
            mouseListener.getGameState().render(graphics);
        } else if(state == States.COMBAT){
            mouseListener.getGameState().getCombat().render(graphics);
        }
        
        graphics.dispose();
        bufferStrat.show();
    }
    

    /*  Here is where everything begins. */
    public static void main(String[] args) {
        Thread gameThread = new Thread(new GameLoop());
        gameThread.start();
    }
    
    
    /*
    Getter and Setters
    */
    public Camera getCamera() {
        return camera;
    }
    public MouseHandler getMouseListener() {
        return mouseListener;
    }

    public KeyHandler getKeyListener() {
        return keyListener;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public States getGameState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public SaveAndLoadMap getLoadData() {
        return loadData;
    }

    public JFrame getFrame() {
        return frame;
    }

    public static int getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public static int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public static void setWINDOW_WIDTH(int WINDOW_WIDTH) {
        GamePanel.WINDOW_WIDTH = WINDOW_WIDTH;
    }

    public static void setWINDOW_HEIGHT(int WINDOW_HEIGHT) {
        GamePanel.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }

    
    
    
}
