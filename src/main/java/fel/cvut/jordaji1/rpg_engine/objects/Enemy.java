package fel.cvut.jordaji1.rpg_engine.objects;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.graphics.Animation;
import fel.cvut.jordaji1.rpg_engine.graphics.SpriteManager;
import fel.cvut.jordaji1.rpg_engine.handlers.MouseHandler;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.ENEMY_ATTACK;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.ENEMY_DEATH;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.ENEMY_WALK;
import static fel.cvut.jordaji1.rpg_engine.utils.Constants.IDLE_ENEMY;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/*
Enemy has health and damage which is loaded into every map. Then enemy can recognize whetever he is alive or not.

Next enemy class has its own animations, then render and update.

Enemy can move from left right in random sequences. 
*/
public class Enemy extends GameObject {
    private final static Logger LOGGER = Logger.getLogger(Enemy.class.getName());
    
    private int health;
    private int damage;
    private final int speed = 1;

    
    private boolean death = false;
    
    private final SpriteManager spriteManager;
    private final MouseHandler mouseInput;
    private final GamePanel gamePanel;
    
    private boolean goLeft = true;
    private boolean goRight = false;
    private boolean standStill = true;
    
    private Animation enemyAttack;
    private Animation enemyWalkLeft;
    private Animation enemyWalkRight;
    private Animation idleLeft;
    private Animation idleRight;
    private Animation enemyDeath;
    
    private Animation currentAnimation;
    
    private final int enemyWidth = 64;
    private final int enemyHeight = 64; 
    
    private int counter = 0; // this counter is used in update so enemy doesnt move in every update calling but in a specific time.

    
    // this part concers random moving of enemies so all enemies doesnt move the same way but everyone has different time how long he stays or how far he goes. 
    private final Random generator = new java.util.Random();
    private final int MIN = 50;
    private final int MAX = 300;
    private int randomNumber = generator.nextInt(MAX - MIN) + MIN;

    public Enemy(int objectCoordinateX, int objectCoordinateY, int objectWidth, int objectHeight, int enemyHealth, int enemyDamage, SpriteManager spriteManager, MouseHandler mouseInput, GamePanel gamePanel) {
        super(objectCoordinateX, objectCoordinateY, objectWidth, objectHeight, spriteManager, gamePanel);
        this.spriteManager = spriteManager;
        this.mouseInput = mouseInput;
        this.gamePanel = gamePanel;
        health = enemyHealth;
        damage = enemyDamage;
        
        bounds.x = 20;
        bounds.y = 35;
        bounds.width = 20;
        bounds.height = 15;
       
        initAnimations();
        
    }

    @Override
    public void update() {
        if(active){
            enemyAttack.runAnimation();
            enemyWalkLeft.runAnimation();
            enemyWalkRight.runAnimation();
            idleLeft.runAnimation();
            idleRight.runAnimation();
            enemyDeath.runAnimation();

            move();
            
        }
        
    }
    
    
    private void move(){
        
        if(!standStill){
            if(goLeft){
                int tx = (int) (objectCoordinateX - speed + bounds.x) / 16;
                if(!collisionDetection(tx, (int) (objectCoordinateY + bounds.y)/16)){
                    setObjectCoordinateX((getObjectCoordinateX() - speed));
                    currentAnimation = enemyWalkLeft;
                }
                
                counter++;
                if(counter == 100){
                    goLeft = false;
                    standStill = true;
                    goRight = true;
                    counter = 0;
                }
            }
            if(goRight){
                int tx = (int) (objectCoordinateX + speed + bounds.x + bounds.width) / 16;
                if(!collisionDetection(tx, (int) (objectCoordinateY + bounds.y)/16)){
                    setObjectCoordinateX((getObjectCoordinateX() + speed));
                    currentAnimation = enemyWalkRight;
                }
                counter++;
                if(counter == 100){
                    goLeft = true;
                    goRight = false;
                    standStill = true;
                    counter = 0;
                }    
            }
        } else{
            if(!goLeft){
                currentAnimation = idleRight;
            }else{
                currentAnimation = idleLeft;
            }
            
            counter++;
            if(counter == randomNumber){
                standStill = false;
                counter = 0;
            }
        }
    }
    
    @Override
    public void render(Graphics graphics) {
        currentAnimation.drawAnimation(graphics,
                (int) (objectCoordinateX - gamePanel.getCamera().getxOffset()),
                (int) (objectCoordinateY - gamePanel.getCamera().getyOffset()));
        
    }
    
    
    protected boolean collisionDetection(int x, int y){
        return mouseInput.getGameState().getTiles()[x][y].isIsWall();
    }

    private void initAnimations(){
        initEnemyAttack();
        initEnemyWalkLeft();
        initEnemyWalkRight();
        initIdleLeft();
        initIdleRight();
        initEnemyDeath();
        
        currentAnimation = idleRight;
        currentAnimation.runAnimation();
        
        LOGGER.log(SEVERE, "Enemy animations were initiliased!");
    }
    
    /* 
    Just setting up animiations and below are getters and setters
    
    idleLeft = new Animation(10, temp[0], temp[1], temp[2], temp[3]); -> that magic number 10 means how fast will animation play
    */
    private void initIdleLeft() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[IDLE_ENEMY];
        for(int i = 0; i < IDLE_ENEMY; i++){
            temp[i] = spriteManager.getEnemy().get("Enemy").get("IdleLeft.png").getSubimage(x, y, 64, 64);    
            x += 64;
        }
        
        idleLeft = new Animation(10, temp[0], temp[1], temp[2], temp[3]);
    }
    
     private void initIdleRight() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[IDLE_ENEMY];
        for(int i = 0; i < IDLE_ENEMY; i++){
            temp[i] = spriteManager.getEnemy().get("Enemy").get("IdleRight.png").getSubimage(x, y, 64, 64);    
            x += 64;
        }
        
        idleRight = new Animation(10, temp[0], temp[1], temp[2], temp[3]);
    }
     
    private void initEnemyWalkRight() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[ENEMY_WALK];
        for(int i = 0; i < ENEMY_WALK; i++){
            temp[i] = spriteManager.getEnemy().get("Enemy").get("EnemyWalkRight.png").getSubimage(x, y, 64, 64);    
            x += 64;
        }
        
        enemyWalkRight = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }
    
    private void initEnemyWalkLeft() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[ENEMY_WALK];
        for(int i = 0; i < ENEMY_WALK; i++){
            temp[i] = spriteManager.getEnemy().get("Enemy").get("EnemyWalkLeft.png").getSubimage(x, y, 64, 64);    
            x += 64;
        }
        
        enemyWalkLeft = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }
    
    private void initEnemyAttack() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[ENEMY_ATTACK];
        for(int i = 0; i < ENEMY_ATTACK; i++){
            temp[i] = spriteManager.getEnemy().get("Enemy").get("EnemyAttack2.png").getSubimage(x, y, 64, 64);    
            x += 64;
        }
        
        enemyAttack = new Animation(10, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6]);
    }
    
    private void initEnemyDeath() {
        int x = 0;
        int y = 0;
        BufferedImage[] temp = new BufferedImage[ENEMY_DEATH];
        for(int i = 0; i < ENEMY_DEATH; i++){
            temp[i] = spriteManager.getEnemy().get("Enemy").get("EnemyDeath.png").getSubimage(x, y, 64, 64);    
            x += 64;
        }
        
        enemyDeath = new Animation(20, temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], temp[9], temp[10]);
    }

    public Animation getEnemyAttack() {
        return enemyAttack;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Animation getIdleLeft() {
        return idleLeft;
    }

    public Animation getIdleRight() {
        return idleRight;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public boolean isDeath() {
        return death;
    }

    public Animation getEnemyDeath() {
        return enemyDeath;
    }
}
