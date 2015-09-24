/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author Chris
 */
public class LoadSavedGameScreen extends JPanel {

    final int NEW_GAME_ONE_X = 10;
    final int NEW_GAME_ONE_Y = 50;
    final int NEW_GAME = 0;
    String picURL ="sprites/";
    int menuIconX = NEW_GAME_ONE_X;
    int menuIconY = NEW_GAME_ONE_Y;
    int menuWhere = NEW_GAME;
    
    int menuMove = 0;
    
    ImageIcon menuIcon1 = new ImageIcon(getClass().getResource(picURL + "menuIcon1a.png"));
    ImageIcon menuIcon2 = new ImageIcon(getClass().getResource(picURL + "menuIcon2a.png"));
    ImageIcon menuIcon3 = new ImageIcon(getClass().getResource(picURL + "menuIcon3a.png"));
    Image menuIcon = new ImageIcon(getClass().getResource(picURL + "menuIcon1a.png")).getImage();
    
    JLabel saveGameOne;
    JLabel saveGameOneStats;
    JLabel saveGameTwo;
    JLabel saveGameTwoStats;
    JLabel saveGameThree;
    JLabel saveGameThreeStats;
    JLabel header;
    JLabel backToMenuScreen;
    File file1;
    File file2;
    File file3;
    String saveOneText = "";
    Image backGround = new ImageIcon(getClass().getResource("sprites/emptybackground.png")).getImage();

    Font menuFont = new Font("Bradley Hand ITC", Font.BOLD, 24);
    Color fontColor = Color.red;
    
    public LoadSavedGameScreen(boolean newGame) {
        setSize(500, 500);
        setBackground(Color.black);
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        try {

            file1 = new File("src/untitled/game/savegamefiles/save1");
            FileReader fReader = new FileReader(file1);
            BufferedReader reader = new BufferedReader(fReader);
            String readerInput = null;

            while ((readerInput = reader.readLine()) != null) {
                if (readerInput != null) {
                    saveOneText = saveOneText + readerInput;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (saveOneText == null) {
            saveOneText = "File 1";
        }

        header = new JLabel("Choose a game");
        saveGameOne = new JLabel(saveOneText);
        saveGameTwo = new JLabel("saveTwoText");
        saveGameThree = new JLabel("        New Game");
        saveGameOneStats = new JLabel("        ...............");
        saveGameTwoStats = new JLabel("        ...............");
        saveGameThreeStats = new JLabel("        ...............");
        backToMenuScreen = new JLabel("                        Back to main menu");
        

        setLabelFontAndColor(header);
        setLabelFontAndColor(saveGameOne);
        setLabelFontAndColor(saveGameOneStats);
        setLabelFontAndColor(saveGameTwo);
        setLabelFontAndColor(saveGameTwoStats);
        setLabelFontAndColor(saveGameThree);
        setLabelFontAndColor(saveGameThreeStats);
        setLabelFontAndColor(backToMenuScreen);
        
        header.setFont(new Font("Bradley Hand ITC", Font.BOLD, 48));
        backToMenuScreen.setFont(new Font("Bradley Hand ITC", Font.PLAIN, 24));
        
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(saveGameOne);
        mainPanel.add(saveGameOneStats);
        mainPanel.add(saveGameTwo);
        mainPanel.add(saveGameTwoStats);
        mainPanel.add(saveGameThree);
        mainPanel.add(saveGameThreeStats);
        mainPanel.setBackground(Color.BLACK);
        
        add(BorderLayout.CENTER, mainPanel);
        add(BorderLayout.NORTH, header);
        add(BorderLayout.SOUTH, backToMenuScreen);
        
        setFocusable(true);
        requestFocus();

    }

    public void setLabelFontAndColor(JLabel l) {
        l.setForeground(fontColor);
        l.setFont(menuFont);
        

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.drawImage(menuIcon, menuIconX, menuIconY, this);

        if (menuMove == 0) {
            menuIcon = menuIcon1.getImage();
        } else if (menuMove == 500) {
            menuIcon = menuIcon2.getImage();
        } else if (menuMove == 1000) {
            menuIcon = menuIcon3.getImage();
            menuMove = 0;
        }
        menuMove++;

    }

    

    
}
