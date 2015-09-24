/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public class UntitledGame extends JPanel {

    ArrayList player2OutputStream;
    PrintWriter writerWritingToPlayer;
    PrintWriter writerWritingToServer;
    Socket playerSocket;
    BufferedReader readerReadingFromServer;
    BufferedReader readerReadingFromPlayer;
    Thread readerThread;

    String iP = "25.100.96.95";
    final int GAME_WIDTH = 500;
    final int GAME_HEIGHT = 520;
    final int LEFT = 1;
    final int RIGHT = 2;
    final int DOWN = 3;
    final int UP = 4;
    final int NOT_MOVING = 0;
    
    final int ROCK = 1;
    final int METAL = 2;
    final int EMPTY = 0;
    final int GRID_X = 48;
    final int GRID_Y = 48;
    
    int mapX = 5;
    int mapY = 0;
    

    final int missileVelocity = 5;
    final int chargedShotVelocity = 30;
    boolean host = true;
    boolean initialSetup = false;

    boolean setupMap = true;
    String picURL = "sprites/";
    String wavURL = "src/untitled/game/music/";

    Player p;
    Player emily;
    Player william;
    Player chris;
    Player annie;
    Player dusty;
    DarkSuit d;
    Coin c;
    ArrayList<Missile> missiles;
    ArrayList<ChargedShot> chargedShots;
    ArrayList<Entity> players;
    
    int[][] map;
    Image rock = new ImageIcon(getClass().getResource(picURL + "rock.png")).getImage();
    Image empty = new ImageIcon(getClass().getResource(picURL + "empty.png")).getImage();
    Image metal = new ImageIcon(getClass().getResource(picURL + "metal.png")).getImage();

    Font menuFont = new Font("Bradley Hand ITC", Font.PLAIN, 25);
    //Image gameBackground = new ImageIcon(getClass().getResource(picURL + "background.png")).getImage();
    //Image gameUnderFeet = new ImageIcon(getClass().getResource(picURL + "gameunderfeet.png")).getImage();
    //Image menuBackground = new ImageIcon(getClass().getResource(picURL + "menuBackground1.png")).getImage();
    Image theseGuys = new ImageIcon(getClass().getResource(picURL + "theseguys.png")).getImage();
    
    int mapYIncrementor = 0;
    int mapXIncrementor = 1;
    int mapYDecrementor = 0;

    public static void main(String args[]) {
        System.out.println("lets go");
    }

    public UntitledGame(String characterName) {
        p = new Player(GAME_WIDTH / 2, GAME_HEIGHT - (48 * 7), RIGHT, characterName);
        emily = new Player((GAME_WIDTH / 2) + 100, GAME_HEIGHT - (48 * 3), RIGHT, "emily");
        william = new Player((GAME_WIDTH / 2) + 50, GAME_HEIGHT - (48 * 3), RIGHT, "william");
        chris = new Player((GAME_WIDTH / 2) - 50, GAME_HEIGHT - (48 * 3), RIGHT, "chris");
        annie = new Player((GAME_WIDTH / 2) - 80, GAME_HEIGHT - (48 * 3), RIGHT, "annie");
        dusty = new Player((GAME_WIDTH / 2) + 120, GAME_HEIGHT - (48 * 3), LEFT, "dusty");
        
        setMap();
        
        d = new DarkSuit((GAME_WIDTH / 2) - 48, GAME_HEIGHT - (48 * 3));
        c = new Coin((GAME_WIDTH / 2) + 48, GAME_HEIGHT - (48 * 3));
        missiles = new ArrayList<>();
        chargedShots = new ArrayList<>();
        players = new ArrayList<>();
        setFocusable(true);
        requestFocus();
        //playSound("mainthemelong");
        addKeyListener(new GameKeyListener());
        
        players.add(p);
        players.add(emily);
        players.add(william);
        players.add(chris);
        players.add(annie);
        players.add(dusty);
        players.add(d);

    }

    public UntitledGame(boolean onlineHost) {
        iP = JOptionPane.showInputDialog(null, "Enter IP");
        p = new Player(GAME_WIDTH / 2, GAME_HEIGHT - (48 * 3));
        d = new DarkSuit((GAME_WIDTH / 2) - 48, GAME_HEIGHT - (48 * 3));
        c = new Coin((GAME_WIDTH / 2) + 48, GAME_HEIGHT - (48 * 3));
        missiles = new ArrayList<>();
        chargedShots = new ArrayList<>();
        setFocusable(true);
        requestFocus();
        //playSound("mainthemelong");
        addKeyListener(new GameKeyListener());
        host = onlineHost;
        initialSetup = true;
//            

    }

    @Override
    public void paintComponent(Graphics g) {
        //super.paint(g);
//
//            Graphics2D g2d = (Graphics2D) g;
//
//            g2d.drawImage(background, 0, 0, null);
    }
    
    public void paintPlayers(Graphics g){
        for(Entity e: players){
            g.drawImage(e.getCurrentImage(), e.getX(), e.getY(), this);
        }
    }

    public void paint(Graphics g) {

        if (initialSetup == true) {
            startOnline();
        }
        super.paint(g);

        //g.drawImage(gameBackground, 450 - p.backgroundX, 0, this);
        //g.drawImage(theseGuys, 0, 0, this);
        if (p.getX() > 400) {
            p.backgroundX2 = 0;
        }
        if (p.getX() > 800) {
            p.backgroundX = 0;
            //g.drawImage(gameBackground, 450 - p.backgroundX2, 0, this);
        }
        //paintPlayers(g);
        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == ROCK) {
                    g.drawImage(rock, mapX + i * GRID_X, mapY + j * GRID_Y, this);

                } else if (map[i][j] == METAL) {
                    g.drawImage(metal, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else {
                    g.drawImage(empty, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                }

            }
        }
        
        canPlayerMoveLeft();
        canPlayerMoveRight();
        if(playerIsFalling()){
            mapYDecrementor = 10;
            //mapY = mapY - mapYDecrementor;
            //p.setY(p.getY() + mapYDecrementor);
            p.fall();
        }
        g.drawImage(p.getCurrentImage(), p.getX(), p.getY(), this);
        g.drawImage(emily.getCurrentImage(), emily.getX(), emily.getY(), this);
        g.drawImage(william.getCurrentImage(), william.getX(), william.getY(), this);
        g.drawImage(chris.getCurrentImage(), chris.getX(), chris.getY(), this);
        g.drawImage(annie.getCurrentImage(), annie.getX(), annie.getY(), this);
        g.drawImage(dusty.getCurrentImage(), dusty.getX(), dusty.getY(), this);
        g.drawImage(d.getCurrentImage(), d.getX(), d.getY(), this);
        g.drawImage(c.getCurrentImage(), c.getX(), c.getY(), this);

        for (Missile m : missiles) {

            g.drawImage(m.getCurrentImage(), m.getX(), m.getY(), this);
            if(m.getDirectionFacing() == LEFT){
                m.goLeft(missileVelocity);
            }
            else{
                m.goRight(missileVelocity);
            }
            
        }
        for (ChargedShot cS : chargedShots) {
            g.drawImage(cS.getCurrentImage(), cS.getX(), cS.getY(), this);
            cS.goRight();
        }

        try {
            Thread.sleep(50);
            if(dusty.isPlayerMovingRight() == false || dusty.playerHasReachedBoundsMax() == true){
                dusty.goLeft();
            }
            
            if(dusty.playerHasReachedBoundsMin() == true && dusty.isPlayerMovingLeft() == false){
                dusty.goRight();
            }
            c.rotate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void startOnline() {

        setUpGameNetworking(host);

        readerThread = new Thread(new IncomingReader());
        readerThread.start();

        initialSetup = false;
    }

    public void setUpGameNetworking(boolean host) {

        try {

            playerSocket = new Socket(iP, 3074);
            InputStreamReader streamReader = new InputStreamReader(playerSocket.getInputStream());
            readerReadingFromServer = new BufferedReader(streamReader);
            writerWritingToServer = new PrintWriter(playerSocket.getOutputStream());
            writerWritingToServer.println("player joined");
            writerWritingToServer.flush();
            System.out.println("networking established");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public class GameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {

            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                if (host == true) {
                    if(canPlayerMoveLeft()){
                       p.goLeft();
                      // p.setX(p.getX() - mapXIncrementor);
                       //mapXIncrementor = 1;
                       //mapX = mapX + mapXIncrementor;
                    emily.goLeft();
                    william.goLeft();
                    chris.goLeft();
                    annie.goLeft(); 
                    }
                    
                } else {
                    d.goLeft();
                   
                }
                if (writerWritingToServer != null) {

                    writerWritingToServer.println(KeyEvent.VK_LEFT);
                }

            }
            if (key == KeyEvent.VK_RIGHT) {
                if (host == true) {
                    if(canPlayerMoveRight()){
                    //mapXIncrementor = 1;
                    //mapX = mapX - mapXIncrementor; 
                    //p.setX(p.getX() + mapXIncrementor);
                   p.goRight();
                    emily.goRight();
                    william.goRight();
                    chris.goRight();
                    annie.goRight();
                    }
                    
                } else {
                    d.goRight();
                    

                }
                if (writerWritingToServer != null) {
                    writerWritingToServer.println(KeyEvent.VK_RIGHT);
                }

            }
            if (key == KeyEvent.VK_SPACE) {
                if (host == true) {

                    playSound("aduh");
                    Missile m = new Missile(p.getX(), p.getY() + 15, p.LEFT);
                    if(p.getDirectionFacing() == LEFT){
                    
                        m.goLeft(missileVelocity);
                    }
                    else{
                        m.goRight(missileVelocity);
                    }
                    
                    missiles.add(m);

                } else {

                    ChargedShot cS = new ChargedShot(d.getX(), d.getY(), d.RIGHT);
                    chargedShots.add(cS);
                }
                if (writerWritingToServer != null) {
                    writerWritingToServer.println(KeyEvent.VK_SPACE);
                }

            }
            if (key == KeyEvent.VK_ESCAPE) {
                System.out.println("removing game");

            }
            if(key == KeyEvent.VK_ENTER){
                if(canPlayerGoHigher()){
                   
                     p.jump();
                }
               
                //p.setY(p.getY() - mapYIncrementor);
                //mapYIncrementor = 5;
                //mapYDecrementor = 0;
                //mapY = mapY + mapYIncrementor;
            }
            if (writerWritingToServer != null) {
                writerWritingToServer.flush();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_ENTER) {
                if (host == true) {
                    p.stop();
                    emily.stop();
                    william.stop();
                    chris.stop();
                    annie.stop();
                    //mapXIncrementor = 0;
                    //mapYIncrementor = 0;
                    //mapYDecrementor = 0;
                    
                } else if(host == false){
                    d.stop();
                }
                if (writerWritingToServer != null) {
                    writerWritingToServer.println(p.NOT_MOVING);
                    writerWritingToServer.flush();
                }

            }

        }

    }

    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String message;
            try {

                while ((message = readerReadingFromServer.readLine()) != null) {

                    System.out.println(message);
                    int whatIsPlayerDoing = Integer.parseInt(message);
                    if (host == true) {
                        handlePlayer2Movement(whatIsPlayerDoing);
                    } else if (host == false) {
                        handlePlayer1Movement(whatIsPlayerDoing);
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handlePlayer1Movement(int movement) {
        if (movement == KeyEvent.VK_LEFT) {
            p.goLeft();
            //System.out.println("can't go left yet");
        } else if (movement == KeyEvent.VK_RIGHT) {

            p.goRight();
        } else if (movement == 0) {
            p.stop();
        } else if (movement == KeyEvent.VK_SPACE) {
            Missile m = new Missile(p.getX(), p.getY() + 15, p.LEFT);
            if(p.getDirectionFacing() == LEFT){
                    
                        m.goLeft(missileVelocity);
                    }
                    else{
                        m.goRight(missileVelocity);
                    }
            missiles.add(m);
        }
    }

    public void handlePlayer2Movement(int movement) {

        if (movement == KeyEvent.VK_LEFT) {
            d.goLeft();
            
        } else if (movement == KeyEvent.VK_RIGHT) {

            d.goRight();
        } else if (movement == 0) {
            d.stop();
        } else if (movement == KeyEvent.VK_SPACE) {
            ChargedShot cS = new ChargedShot(d.getX(), d.getY(), d.RIGHT);
            chargedShots.add(cS);
        }
    }

    private void playSound(String file) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(wavURL + file + ".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
    
    public boolean canPlayerMoveRight(){
        int mapX = p.getX()/GRID_X;
        int mapY = p.getY()/GRID_Y;
        System.out.println("To my right there is " + map[mapX+1][mapY]);
        System.out.println("I am at " + map[mapX][mapY]);
        
        
        if(mapY -1 > 0){
            System.out.println("Above me there is " + map[mapX][mapY -1]);
        return (map[(p.getX()/GRID_X) + 1][p.getY()/GRID_Y]) == 0;
        }
        else{
            return true;
        }
    }
    public boolean canPlayerMoveLeft(){
        int mapX = p.getX()/GRID_X;
        int mapY = p.getY()/GRID_Y;
        System.out.println("To my left there is " + map[mapX-1][mapY]);
        return (map[(p.getX()/GRID_X) ][p.getY()/GRID_Y]) == 0;
    }
    
    public boolean canPlayerGoHigher(){
       int mapX = p.getX()/GRID_X;
       int mapY = p.getY()/GRID_Y;
       if(mapY - 1 > 0 ){
           return (map[(p.getX()/GRID_X) ][p.getY()/GRID_Y -1 ]) == 0; 
       }
       else{
          return true; 
       }
       
    }
    
    public boolean playerIsFalling(){
        int mapX = p.getX()/GRID_X;
        int mapY = p.getY()/GRID_Y;
        System.out.println("Below me there is " + map[mapX][mapY+1]);
        
        if(mapY + 1 < map.length){
           
           return (map[(mapX) ][(mapY + 1)])  == 0; 
           
        }
        else{
            
            return true;
        }
        
        
    }
    
    private void setMap() {
        map = new int[40][40];
        

            try {

                BufferedReader reader = new BufferedReader(new FileReader("src/untitled/game/map/map.txt"));
                
                for (int i = 0; i < map.length; i++) {
		    String[] items = reader.readLine().split(" ");
                    System.out.println(items.toString());
                  for (int j = 0; j < map.length; j++) {
                      map[j][i] = Integer.parseInt(items[j]);
                  }
              }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        

    }

}
