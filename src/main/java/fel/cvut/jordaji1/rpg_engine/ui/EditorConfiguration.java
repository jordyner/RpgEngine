package fel.cvut.jordaji1.rpg_engine.ui;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import fel.cvut.jordaji1.rpg_engine.states.Editor;
import fel.cvut.jordaji1.rpg_engine.states.States;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditorConfiguration extends JFrame implements ActionListener{
    private JFrame frame;
    private JButton submit;
    
    private JTextField mapWidth;
    private JTextField mapHeight;
    
    private JTextField playerHealth;
    private JTextField playerDamage;
    
    private JTextField enemyHealth;
    private JTextField enemyDamage;
    
    private GamePanel gamePanel;
    
    private Editor editor;

    public EditorConfiguration(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        
        frame = new JFrame("Editor configuration");
        frame.setLocation(200,400);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JLabel mapWidthLabel = new JLabel("MAPWIDTH:");
        panel.add(mapWidthLabel);
        
        mapWidth = new JTextField();
        panel.add(mapWidth);
        
        JLabel mapHeightLabel = new JLabel("MAPHEIGHT:");
        panel.add(mapHeightLabel);
        
        mapHeight = new JTextField();
        panel.add(mapHeight);
        
        JLabel playerHealthLabel = new JLabel("PLAYER HEALTH:");
        panel.add(playerHealthLabel);
        
        playerHealth = new JTextField();
        panel.add(playerHealth);
        
        JLabel playerDamageLabel = new JLabel("PLAYER DAMAGE:");
        panel.add(playerDamageLabel);
        
        playerDamage = new JTextField();
        panel.add(playerDamage);
        
        JLabel enemyHealthLabel = new JLabel("ENEMY HEALTH:");
        panel.add(enemyHealthLabel);
        
        enemyHealth = new JTextField();
        panel.add(enemyHealth);
        
        JLabel enemyDamageLabel = new JLabel("ENEMY DAMAGE:");
        panel.add(enemyDamageLabel);
        
        enemyDamage = new JTextField();
        panel.add(enemyDamage);
        
        submit = new JButton("Submit");
        submit.addActionListener(this);
        panel.add(submit);
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editor = new Editor(gamePanel, gamePanel.getKeyListener());
        gamePanel.setState(States.EDITOR);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public int getMapWidth() {
        if(mapWidth.getText().equals("")){
            return 50;
        } else {
            return Integer.parseInt(mapWidth.getText());
        }
    }

    public int getMapHeight() {
        if(mapHeight.getText().equals("")){
            return 50;
        } else {
            return Integer.parseInt(mapHeight.getText());
        }
    }
    
    public int getPlayerHealth() {
        if(playerHealth.getText().equals("")){
            return 1000;
        } else {
            return Integer.parseInt(playerHealth.getText());
        }
    }

    public int getPlayerDamage() {
        if(playerDamage.getText().equals("")){
            return 200;
        } else {
            return Integer.parseInt(playerDamage.getText());
        }
    }
    
    public int getEnemyHealth() {
        if(enemyHealth.getText().equals("")){
            return 700;
        } else {
            return Integer.parseInt(enemyHealth.getText());
        }
    }

    public int getEnemyDamage() {
        if(enemyDamage.getText().equals("")){
            return 100;
        } else {
            return Integer.parseInt(enemyDamage.getText());
        }
    }

    public Editor getEditor() {
        return editor;
    }
    
    
    
    
}
