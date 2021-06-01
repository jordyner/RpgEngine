package fel.cvut.jordaji1.rpg_engine.objects;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;
import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.graphics.Animation;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.IDLE_DOWN_IMAGE_COUNT;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.WALK_DOWN_IMAGE_COUNT;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.WALK_SIDE_IMAGE_COUNT;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.WALK_UP_IMAGE_COUNT;
import fel.cvut.jordaji1.rpg_engine.handlers.KeyHandler;
import fel.cvut.jordaji1.rpg_engine.handlers.MouseHandler;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.ATTACK_IMAGE_COUNT;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.PICK_UP;
import java.awt.Graphics;
import static java.awt.event.KeyEvent.VK_E;
import java.awt.image.BufferedImage;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/*
Player contains healthPoints, damage, speed, recognize if he is dead or alive and then he has his own animations.

Then he has access to Mouse and Key handlers so he can move and he can open chests.
*/
public class Player extends GameObject{
    private final static Logger LOGGER = Logger.getLogger(Player.class.getName());
    
    private int healthPoints;
    private int damage;
    private int speed = 2;
    
    private boolean dead = false;
    
    private KeyHandler playerKeyInput;
    private MouseHandler playerMouseInput;
    private SpriteManager spriteManager;
    private GamePanel gamePanel;
    
    private Animation idleDown;
    private Animation downWalk;
    private Animation upWalk;
    private Animation sideWalkRight;
    private Animation sideWalkLeft;
    private Animation sideAttackLeft;
    private Animation pickUp;
    private Animation currentAnimation;
    private Animation idleSide;
    
    public Player(int objectCoordinateX, int objectCoordinateY, int objectWidth, int objectHeight, int playerHealth, int playerDamage, KeyHandler playerInput, MouseHandler mouseInput, SpriteManager spriteManager, GamePanel gamePanel) {
        super(objectCoordinateX, objectCoordinateY, objectWidth, objectHeight, spriteManager, gamePanel);
        this.playerKeyInput = playerInput;
        this.playerMouseInput = mouseInput;
        this.spriteManager = spriteManager;
        this.gamePanel = gamePanel;
        healthPoints = playerHealth;
        damage = playerDamage;
        
        bounds.x = 24;
        bounds.y = 35;
        bounds.width = 10;
        bounds.height = 15;
        
        initAnimations();
    }
    

    @Override
    public void update() {  
        if(!dead){
            gamePanel.getCamera().centerOnPlayer(this);
        
            if(active){
                idleDown.runAnimation();
                downWalk.runAnimation();
                upWalk.runAnimation();
                sideWalkRight.runAnimation();
                sideWalkLeft.runAnimation();
                sideAttackLeft.runAnimation();
                pickUp.runAnimation();
                idleSide.runAnimation();

                move();
            }
        }
        
    }
    
    @Override
    public void render(Graphics graphics) {     
        if(!dead){
            currentAnimation.drawAnimation(graphics,
                (int) (objectCoordinateX - gamePanel.getCamera().getxOffset()),
                (int) (objectCoordinateY - gamePanel.getCamera().getyOffset()));
        }
        
    }
    
    // player can move if he is not colliding with Tiles that are walls, his default animation is set to Idle but if he moves then animation is set to the specific one according to location of player
    private void move(){
        
        currentAnimation = idleDown;
        
        if(playerKeyInput.allKeys[VK_W]){
            int ty = (int) (objectCoordinateY - speed + bounds.y) / 16;
            
            if(!collisionDetection((int) (objectCoordinateX + bounds.x)/16, ty)){
                objectCoordinateY -= speed;
                currentAnimation = upWalk;
            }
        } 
        
        if(playerKeyInput.allKeys[VK_S]){
            int ty = (int) (objectCoordinateY + speed + bounds.y + bounds.height) / 16;
            if(!collisionDetection((int) (objectCoordinateX + bounds.x)/16, ty)){
                objectCoordinateY += speed;
                currentAnimation = downWalk;
            }
        } 
        
        if(playerKeyInput.allKeys[VK_A]){
            int tx = (int) (objectCoordinateX - speed + bounds.x) / 16;
            if(!collisionDetection(tx, (int) (objectCoordinateY + bounds.y)/16)){
                objectCoordinateX -= speed;
                currentAnimation = sideWalkLeft;
            }
        } 
        
        if(playerKeyInput.allKeys[VK_D]){
            int tx = (int) (objectCoordinateX + speed + bounds.x + bounds.width) / 16;
            if(!collisionDetection(tx, (int) (objectCoordinateY + bounds.y)/16)){
                objectCoordinateX += speed;
                currentAnimation = sideWalkRight;
            }
        } 
        
        if(playerKeyInput.allKeys[VK_E]){
            currentAnimation = pickUp;
        } 
    }
    

    private void initAnimations(){
        initIdleSide();
        initIdleDown();
        initUpWalk();
        initDownWalk();
        initSideWalkRight();
        initSideWalkLeft();
        initSideAttackLeft();
        initPickUp();

        
        currentAnimation = idleDown;
        
        currentAnimation.runAnimation();
        
        LOGGER.log(SEVERE, "Player animations were initiliased!");
    }

    protected boolean collisionDetection(int x, int y){
        return playerMouseInput.getGameState().getTiles()[x][y].isIsWall();
    }
    
    
    /* Setting up animations and below are getters and setters */
    private void initIdleDown() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[IDLE_DOWN_IMAGE_COUNT];
        for(int i = 0; i < IDLE_DOWN_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("idleDown.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        
        idleDown = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4]);
    }

    private void initUpWalk() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[WALK_UP_IMAGE_COUNT];
        for(int i = 0; i < WALK_UP_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("upWalk.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        
        upWalk = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }

    private void initSideWalkRight() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[WALK_SIDE_IMAGE_COUNT];
        for(int i = 0; i < WALK_SIDE_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("sideWalkRight.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        
        sideWalkRight = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }

    private void initSideWalkLeft() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[WALK_SIDE_IMAGE_COUNT];
        for(int i = 0; i < WALK_SIDE_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("sideWalkLeft.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        
        sideWalkLeft = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }

    private void initPickUp() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[PICK_UP];
        for(int i = 0; i < PICK_UP; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("pickUp.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        
        pickUp = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4]);
    }

    private void initDownWalk() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[WALK_DOWN_IMAGE_COUNT];
        for(int i = 0; i < WALK_DOWN_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("downWalk.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        
        downWalk = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }
    
    private void initSideAttackLeft() {
        int x = 0;
        int y = 0;
        int newRow = 1;
        BufferedImage[] temp = new BufferedImage[ATTACK_IMAGE_COUNT];
        for(int i = 0; i < ATTACK_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("sideAttackLeft.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 1;
            }
        }
        
        sideAttackLeft = new Animation(15, temp[0], temp[1], temp[2]);
    }     
    
    
    private void initIdleSide() {
        int x = 0;
        int y = 0;
        int newRow = 3;
        BufferedImage[] temp = new BufferedImage[IDLE_DOWN_IMAGE_COUNT];
        for(int i = 0; i < IDLE_DOWN_IMAGE_COUNT; i++){
            temp[i] = spriteManager.getCharacter().get("Character").get("idleSide.png").getSubimage(x, y, 64, 64);    
            x += 64;
            
            if(i == newRow){
                x = 0;
                y += 64;
                newRow += 3;
            }
        }
        idleSide = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4]);
    }


    public Animation getIdleDown() {
        return idleDown;
    }

    public Animation getIdleSide() {
        return idleSide;
    }

    public Animation getSideAttackLeft() {
        return sideAttackLeft;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}

