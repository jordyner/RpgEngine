package fel.cvut.jordaji1.rpg_engine.states;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import fel.cvut.jordaji1.rpg_engine.graphics.Animation;
import fel.cvut.jordaji1.rpg_engine.objects.Enemy;
import fel.cvut.jordaji1.rpg_engine.objects.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.Random;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/*
Class where combat state is done. Combat needs player and enemy and they attack in turns and they switch between each other.
*/
public class Combat {
    private final static Logger LOGGER = Logger.getLogger(Combat.class.getName());
    
    private GamePanel gamePanel;
    private Player player;
    private Enemy enemy;
    
    private Random random;
    private int criticalHit;
    private boolean criticalVisual;
    private int counter; // for critical visualisation
    
    private Animation currentAnimationForEnemy;
    private Animation currentAnimationForPlayer;
    
    private int timer = 45; // timer sets how fast damage will be dealt
    private int turn = 1; // turn 1 = player, turn 2 = enemy
    
    public Combat(Player player, Enemy enemy, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.enemy = enemy;
        currentAnimationForEnemy = enemy.getIdleRight();
        currentAnimationForPlayer = player.getIdleSide();
        
        random = new Random();
        criticalVisual = false;
        counter = 0;
    }
    
    public void render(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        currentAnimationForPlayer.drawAnimation(graphics, WINDOW_WIDTH/2 + WINDOW_HEIGHT/35, WINDOW_HEIGHT/4, 300, 300);
        currentAnimationForEnemy.drawAnimation(graphics, WINDOW_WIDTH/2/2 + WINDOW_HEIGHT/10, WINDOW_HEIGHT/4, 300, 300);
        
        Font playerHealth = new Font("arial", Font.BOLD, 15);
        graphics.setFont(playerHealth);
        graphics.setColor(Color.RED);
        graphics.drawString(Integer.toString(player.getHealthPoints()), WINDOW_WIDTH/2 + WINDOW_HEIGHT/5, WINDOW_HEIGHT/4);
        
        Font enemyHealth = new Font("arial", Font.BOLD, 15);
        graphics.setFont(enemyHealth);
        graphics.setColor(Color.RED);
        graphics.drawString(Integer.toString(enemy.getHealth()), WINDOW_WIDTH/2/2 + WINDOW_HEIGHT/4, WINDOW_HEIGHT/4);
        
        if(criticalVisual){
            if(counter <= 1500){
                Font forCrit = new Font("arial", Font.BOLD, 20);
                graphics.setFont(forCrit);
                graphics.setColor(Color.RED);
                graphics.drawString("CRITICAL HIT!", WINDOW_WIDTH/2/2 + WINDOW_HEIGHT/10, WINDOW_HEIGHT/3);
                
            } else {
                criticalVisual = false;
                counter = 0;
            }
            counter++;
        }
        
    }

    public void update() {
        currentAnimationForEnemy.runAnimation();
        currentAnimationForPlayer.runAnimation();
        
        timer ++;
        
        if(timer == 50){
            // Player turn
            if(turn == 1){
                currentAnimationForPlayer = player.getSideAttackLeft();
                // chace 25% on critical hit.. critical hit is player damage * 2;
                criticalHit = random.nextInt(4);
                if(criticalHit == 0){
                    enemy.setHealth(enemy.getHealth() - player.getDamage()*2);
                    criticalVisual = true;
                } else {
                    enemy.setHealth(enemy.getHealth() - player.getDamage());
                }
                
                if(enemy.getHealth() <= 0){
                    LOGGER.log(SEVERE, "Dead enemies: " + (gamePanel.getMouseListener().getGameState().getDeathEnemyCount()+1));
                    currentAnimationForEnemy = enemy.getEnemyDeath();
                    
                    enemy.setDeath(true);
                    gamePanel.setState(States.GAME);
                }
                
                turn = 2;
                timer = 0;
            // Enemy turn
            } else if(turn == 2){
                currentAnimationForEnemy = enemy.getEnemyAttack();
                
                player.setHealthPoints(player.getHealthPoints() - enemy.getDamage());
                
                if(player.getHealthPoints() <= 0){
                    LOGGER.log(SEVERE, "Player health below 0! Player should die.");
                    player.setDead(true);
                    gamePanel.setState(States.GAME);
                }
                
                turn = 1;
                timer = 0;
            } else {
                currentAnimationForEnemy = enemy.getIdleRight();
                currentAnimationForPlayer = player.getIdleSide();
            }
            
            
        }
    }
}
