/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Chris
 */
public class NewGameCharacterSelection extends JPanel {

    

    JLabel selectYourCharacter;
    JLabel emilyLabel;
    JLabel williamLabel;
    JLabel annieLabel;
    JLabel chrisLabel;
    JLabel backLabel;
    JLabel iconArea;

    JLabel emptyLabel1;
    JLabel emptyLabel2;
    JLabel emptyLabel3;
    JLabel emptyLabel4;

    Player emily;
    Player william;
    Player annie;
    Player chris;

    Color fontColor = Color.red;
    Font menuFont = new Font("Bradley Hand ITC", Font.BOLD, 24);
    Font emptyFont = new Font("Courier", Font.BOLD, 34);
    Color black = Color.black;

    final int LEFT = 1;
    final int RIGHT = 2;

    final int EMILY_X = 40;
    final int EMILY_Y = 60;
    final int EMILY = 111;
    final int WILLIAM_X = 40;
    final int WILLIAM_Y = 135;
    final int WILLIAM = 222;
    final int ANNIE_X = 40;
    final int ANNIE_Y = 210;
    final int ANNIE = 333;
    final int CHRIS_X = 40;
    final int CHRIS_Y = 285;
    final int CHRIS = 444;
    final int BACK_X = 40;
    final int BACK_Y = 360;
    final int BACK = 555;

    private int menuMove;
    private final ImageIcon menuIcon1 = new ImageIcon(getClass().getResource("sprites/menuicon1a.png"));
    private final ImageIcon menuIcon2 = new ImageIcon(getClass().getResource("sprites/menuicon2a.png"));
    private final ImageIcon menuIcon3 = new ImageIcon(getClass().getResource("sprites/menuicon3a.png"));
    Image menuIcon = menuIcon1.getImage();

    int menuIconX = EMILY_X;
    int menuIconY = EMILY_Y;
    int menuWhere = EMILY;
    
    JPanel mainPanel;

    public NewGameCharacterSelection() {

        
        
        mainPanel = new JPanel();
        selectYourCharacter = new JLabel("Select your character");
        selectYourCharacter.setFont(new Font("Bradley Hand ITC", Font.BOLD, 45));
        selectYourCharacter.setForeground(fontColor);
        iconArea = new JLabel("this is where icons go");
        iconArea.setForeground(Color.black);
        emilyLabel = new JLabel("Emily");
        williamLabel = new JLabel("William");
        annieLabel = new JLabel("Annie");
        chrisLabel = new JLabel("Chris");
        backLabel = new JLabel("Back");

        emptyLabel1 = new JLabel("e");
        emptyLabel2 = new JLabel("e");
        emptyLabel3 = new JLabel("e");
        emptyLabel4 = new JLabel("e");

        setEmptyLabel(emptyLabel1);
        setEmptyLabel(emptyLabel2);
        setEmptyLabel(emptyLabel3);
        setEmptyLabel(emptyLabel4);

        setLabelFontAndColor(emilyLabel);
        setLabelFontAndColor(williamLabel);
        setLabelFontAndColor(annieLabel);
        setLabelFontAndColor(chrisLabel);
        setLabelFontAndColor(backLabel);

        emily = new Player(80, 60, LEFT, "emily");
        william = new Player(80, 135, LEFT, "william");
        annie = new Player(80, 210, LEFT, "annie");
        chris = new Player(80, 285, LEFT, "chris");

        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(emilyLabel);
        mainPanel.add(emptyLabel1);
        mainPanel.add(williamLabel);
        mainPanel.add(emptyLabel2);
        mainPanel.add(annieLabel);
        mainPanel.add(emptyLabel3);
        mainPanel.add(chrisLabel);
        mainPanel.add(emptyLabel4);
        mainPanel.add(backLabel);

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        add(BorderLayout.CENTER, mainPanel);
        add(BorderLayout.WEST, iconArea);
        add(BorderLayout.NORTH, selectYourCharacter);

        //addKeyListener(new NewGameListener());
        setFocusable(true);
        requestFocus();

    }

    public void setLabelFontAndColor(JLabel l) {
        l.setForeground(fontColor);
        l.setFont(menuFont);

    }

    public void setEmptyLabel(JLabel l) {
        l.setForeground(black);
        l.setFont(emptyFont);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(menuIcon, menuIconX, menuIconY, this);
        g.drawImage(emily.getCurrentImage(), emily.getX(), emily.getY(), this);
        g.drawImage(william.getCurrentImage(), william.getX(), william.getY(), this);
        g.drawImage(annie.getCurrentImage(), annie.getX(), annie.getY(), this);
        g.drawImage(chris.getCurrentImage(), chris.getX(), chris.getY(), this);

        if (menuMove == 0) {
            menuIcon = menuIcon1.getImage();
        } else if (menuMove == 100) {
            menuIcon = menuIcon2.getImage();
        } else if (menuMove == 200) {
            menuIcon = menuIcon3.getImage();

        } else if (menuMove == 300) {
            menuMove = -1;
        }
        menuMove++;

    }
    
    
    

    
}
