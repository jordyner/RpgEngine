package fel.cvut.jordaji1.rpg_engine.ui;

import fel.cvut.jordaji1.rpg_engine.game.GamePanel;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_HEIGHT;
import static fel.cvut.jordaji1.rpg_engine.game.GamePanel.WINDOW_WIDTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ResolutionConfiguration extends JFrame implements ActionListener{  
    private GamePanel gamePanel;
    private JFrame selectResolution;
    
    private int width;
    private int height;
    
    private JButton[] resolutionButtons = new JButton[4];
    

    public ResolutionConfiguration(GamePanel gamePanel) { 
        this.gamePanel = gamePanel;
        selectResolution = new JFrame();
        selectResolution.setLocation(25,400);
        selectResolution.setResizable(false);
        selectResolution.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        resolutionButtons[0] = new JButton("1024 x 576");
        resolutionButtons[0].addActionListener(this);
        panel.add(resolutionButtons[0]);
        
        resolutionButtons[1] = new JButton("1280 x 720");
        resolutionButtons[1].addActionListener(this);
        panel.add(resolutionButtons[1]);
        
        resolutionButtons[2] = new JButton("1600 x 900");
        resolutionButtons[2].addActionListener(this);
        panel.add(resolutionButtons[2]);
        
        resolutionButtons[3] = new JButton("1920 x 1080");
        resolutionButtons[3].addActionListener(this);
        panel.add(resolutionButtons[3]);
        
        selectResolution.add(panel);
        selectResolution.pack();
        selectResolution.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resolutionButtons[0]){
            width = 1024;
            height = 576;
            setNewResolution(width, height);
            selectResolution.dispatchEvent(new WindowEvent(selectResolution, WindowEvent.WINDOW_CLOSING));
        } else if(e.getSource() == resolutionButtons[1]){
            width = 1280;
            height = 720;
            setNewResolution(width, height);
            selectResolution.dispatchEvent(new WindowEvent(selectResolution, WindowEvent.WINDOW_CLOSING));
        } else if(e.getSource() == resolutionButtons[2]){
            width = 1600;
            height = 900;
            setNewResolution(width, height);
            selectResolution.dispatchEvent(new WindowEvent(selectResolution, WindowEvent.WINDOW_CLOSING));
        } else if(e.getSource() == resolutionButtons[3]){
            width = 1920;
            height = 1080;
            setNewResolution(width, height);
            selectResolution.dispatchEvent(new WindowEvent(selectResolution, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    public void setNewResolution(int width, int height){
        WINDOW_WIDTH = width;
        WINDOW_HEIGHT = height;
        gamePanel.getFrame().setBounds(0,0,width,height);
        gamePanel.getFrame().setLocationRelativeTo(null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    
    
}
