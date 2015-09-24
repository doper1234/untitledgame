/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Chris
 */
public class WorldMap extends JPanel{
    Timer playerMoveTimer;
    JLabel currentWorldLabel;
    Image backGround = new ImageIcon(getClass().getResource("sprites/worldmap.png")).getImage();
    
    Player b;
    
    
    final int LEVEL_ONE = 1;
    final int LEVEL_TWO = 2;
    final int LEVEL_THREE = 3;
    final int IN_TRANSIT_TO_NEXT_LEVEL = 0;
    
    final int DOWN = 3;
    
    final int levelOneX = 80;
    final int levelOneY = 255;
    final int levelTwoX = 220;
    final int levelThreeX = 345;
    
    int playerLocation = LEVEL_ONE;
    
    int ticks = 0;
    Font menuFont = new Font("Bradley Hand ITC", Font.PLAIN, 25);
    String characterName;
    
    
    public WorldMap(String characterName){
        this.characterName = characterName;
        currentWorldLabel = new JLabel("World 1-1");
        currentWorldLabel.setForeground(Color.red);
        currentWorldLabel.setBackground(Color.black);
        currentWorldLabel.setFont(menuFont);
        add(currentWorldLabel);
        
        playerMoveTimer = new Timer(5, new PlayerMoveTimerListener());
        b = new Player(levelOneX, levelOneY, DOWN, characterName);
        playerMoveTimer.start();
        setFocusable(true);
        requestFocus();
        
    }
    
    
    public class PlayerMoveTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (b.isPlayerMovingRight()) {
                b.goRightMap();
                currentWorldLabel.setText("World 1-_");
                if (b.getX() == levelTwoX) {
                    b.setX(levelTwoX);
                    currentWorldLabel.setText("World 1-2");
                    b.goDown();
                }
                if (b.getX() == levelThreeX) {
                    b.setX(levelThreeX);
                    currentWorldLabel.setText("World 1-3");
                    b.goDown();
                }
            }
            else if(b.isPlayerMovingLeft()){
                b.goLeftMap();
                currentWorldLabel.setText("World 1-_");
                if (b.getX() == levelTwoX) {
                    b.setX(levelTwoX);
                    currentWorldLabel.setText("World 1-2");
                    b.goDown();
                }
                if (b.getX() == levelOneX) {
                    b.setX(levelOneX);
                    currentWorldLabel.setText("World 1-1");
                    b.goDown();
                }
            }
            else{
                b.goDown();
            }
            
            
            
            
            
            
            
            
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, this);
        g.drawImage(b.getCurrentImage(), b.getX(), b.getY(), this);
        
        
        
    }
    
}
