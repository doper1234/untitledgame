/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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

    final int EMPTY = 0;
    final int ROCK = 1;
    final int METAL = 2;
    final int PIPE = 3;
    final int PIPE_END = 4;
    final int MONSTER_HEAD = 5;
    final int SWIRLY_THING = 6;

    final int DOOR_BOTTOM = 14;
    final int DOOR_BOTTOM_MIDDLE = 12;
    final int DOOR_TOP_MIDDLE = 13;
    final int DOOR_TOP = 11;
    
    final int DOOR_EXIT_BOTTOM = 15;
    final int DOOR_EXIT_BOTTOM_MIDDLE = 17;
    final int DOOR_EXIT_TOP_MIDDLE = 16;
    final int DOOR_EXIT_TOP = 18;

    final int GRID_X = 48;
    final int GRID_Y = 48;

    int frameWidth = 768;
    int frameHeight = 576;

    int mapX = 0;
    int mapY = 0;
    int movingX = mapX;
    int movingY = mapY;
    int endOfMovingX = -2592;

    final int missileVelocity = 5;
    final int chargedShotVelocity = 30;
    boolean host = true;
    boolean initialSetup = false;

    boolean setupMap = true;
    String picURL = "sprites/";
    String wavURL = "src/untitled/game/music/";
    String characterName;

    Player p;
    Player p2;
    Player emily;
    Player william;
    Player chris;
    Player annie;
    Player dusty;
    
    Boss crocBoss;
    Boss chrisBoss;
    Boss metroidBoss;

    PowerUp willPowerUp;
    PowerUp milkPowerUp;
    PowerUp mommyPowerUp;
    PowerUp myPowerUp;
    PowerUp dustyPowerUp;

    DarkSuit d;
    Coin c;
    ArrayList<Missile> missiles;
    ArrayList<ChargedShot> chargedShots;
    ArrayList<Entity> players;

    int[][] map;
    int[][] currentMap;
    Image rock = new ImageIcon(getClass().getResource(picURL + "rockb.png")).getImage();
    Image empty = new ImageIcon(getClass().getResource(picURL + "empty.png")).getImage();
    Image metal = new ImageIcon(getClass().getResource(picURL + "metalb.png")).getImage();
    Image pipe = new ImageIcon(getClass().getResource(picURL + "pipeb.png")).getImage();
    Image pipeEnd = new ImageIcon(getClass().getResource(picURL + "pipeendb.png")).getImage();
    Image monsterHead = new ImageIcon(getClass().getResource(picURL + "monsterhead.png")).getImage();
    Image swirlyThing = new ImageIcon(getClass().getResource(picURL + "swirlyb.png")).getImage();
    Image backgroundBlock = new ImageIcon(getClass().getResource(picURL + "OldBrinstar.png")).getImage();

    Image doorBottom = new ImageIcon(getClass().getResource(picURL + "doorbottom.png")).getImage();
    Image doorBottomMiddle = new ImageIcon(getClass().getResource(picURL + "doorbottommiddle.png")).getImage();
    Image doorTopMiddle = new ImageIcon(getClass().getResource(picURL + "doortopmiddle.png")).getImage();
    Image doorTop = new ImageIcon(getClass().getResource(picURL + "doortop.png")).getImage();
    Image doorExitBottom = new ImageIcon(getClass().getResource(picURL + "doorexitbottom.png")).getImage();
    Image doorExitBottomMiddle = new ImageIcon(getClass().getResource(picURL + "doorexitbottommiddle.png")).getImage();
    Image doorExitTopMiddle = new ImageIcon(getClass().getResource(picURL + "doorexittopmiddle.png")).getImage();
    Image doorExitTop = new ImageIcon(getClass().getResource(picURL + "doorexittop.png")).getImage();
    
    

    Font menuFont = new Font("Bradley Hand ITC", Font.PLAIN, 25);
    Image gameBackground = new ImageIcon(getClass().getResource(picURL + "/SNES - Super Metroid - Backgrounds/Brinstar/Rocks.bmp")).getImage();
    //Image gameUnderFeet = new ImageIcon(getClass().getResource(picURL + "gameunderfeet.png")).getImage();
    //Image menuBackground = new ImageIcon(getClass().getResource(picURL + "menuBackground1.png")).getImage();
    Image theseGuys = new ImageIcon(getClass().getResource(picURL + "theseguys.png")).getImage();

    int mapYIncrementor = 0;
    int mapXIncrementor = 1;
    int mapYDecrementor = 0;

    public static void main(String args[]) {
        System.out.println("lets go");
    }

    public UntitledGame(String characterName, Frame frame, String mapLevel, String theme) {

        playSound("leveltheme");
        this.characterName = characterName;
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
//        p = new Player(GAME_WIDTH / 2, GAME_HEIGHT - (48 * 7), RIGHT, characterName);
//        emily = new Player((GAME_WIDTH / 2) + 100, GAME_HEIGHT - (48 * 3), RIGHT, "emily");
//        william = new Player((GAME_WIDTH / 2) + 50, GAME_HEIGHT - (48 * 3), RIGHT, "william");
//        chris = new Player((GAME_WIDTH / 2) - 50, GAME_HEIGHT - (48 * 3), RIGHT, "chris");
//        annie = new Player((GAME_WIDTH / 2) - 80, GAME_HEIGHT - (48 * 3), RIGHT, "annie");
        dusty = new Player((GAME_WIDTH / 2) + 120, GAME_HEIGHT - (48 * 3), LEFT, "dusty");
        
        if(theme.equalsIgnoreCase("norfair")){
            initializeNorfBlocks();
        }
        if(theme.equalsIgnoreCase("end")){
            initializeEndBlocks();
        }

        setMap(mapLevel);

        d = new DarkSuit((GAME_WIDTH / 2) - 48, GAME_HEIGHT - (48 * 3));
        c = new Coin((GAME_WIDTH / 2) + 48, GAME_HEIGHT - (48 * 3));
        missiles = new ArrayList<>();
        chargedShots = new ArrayList<>();
        players = new ArrayList<>();
        setFocusable(true);
        requestFocus();
        //playSound("mainthemelong");
        addKeyListener(new GameKeyListener());

//        players.add(p);
//        players.add(emily);
//        players.add(william);
//        players.add(chris);
//        players.add(annie);
//        players.add(dusty);
//        players.add(d);

    }

    public UntitledGame(String characterName, Frame frame, String mapLevel, String theme, boolean onlineHost, String iP) {
        this(characterName, frame, mapLevel, theme);
        this.iP = iP;
//        p = new Player(GAME_WIDTH / 2, GAME_HEIGHT - (48 * 3));
//        d = new DarkSuit((GAME_WIDTH / 2) - 48, GAME_HEIGHT - (48 * 3));
//        c = new Coin((GAME_WIDTH / 2) + 48, GAME_HEIGHT - (48 * 3));
//        missiles = new ArrayList<>();
//        chargedShots = new ArrayList<>();
//        setFocusable(true);
//        requestFocus();
//        //playSound("mainthemelong");
//        addKeyListener(new GameKeyListener());
        host = onlineHost;
        initialSetup = true;
//            

    }

    public void initializeNorfBlocks(){
        
    rock = new ImageIcon(getClass().getResource(picURL + "rockc.png")).getImage();
    empty = new ImageIcon(getClass().getResource(picURL + "empty.png")).getImage();
    metal = new ImageIcon(getClass().getResource(picURL + "metalc.png")).getImage();
    pipe = new ImageIcon(getClass().getResource(picURL + "pipec.png")).getImage();
    pipeEnd = new ImageIcon(getClass().getResource(picURL + "pipeendc.png")).getImage();
    monsterHead = new ImageIcon(getClass().getResource(picURL + "monsterheadc.png")).getImage();
    swirlyThing = new ImageIcon(getClass().getResource(picURL + "swirlyc.png")).getImage();
    backgroundBlock = new ImageIcon(getClass().getResource(picURL + "norfairblock.png")).getImage();
    }
    
    public void initializeEndBlocks() {
        rock = new ImageIcon(getClass().getResource(picURL + "rockend.png")).getImage();
        empty = new ImageIcon(getClass().getResource(picURL + "empty.png")).getImage();
        metal = new ImageIcon(getClass().getResource(picURL + "metalend.png")).getImage();
        pipe = new ImageIcon(getClass().getResource(picURL + "pipeend.png")).getImage();
        pipeEnd = new ImageIcon(getClass().getResource(picURL + "pipeendend.png")).getImage();
        monsterHead = new ImageIcon(getClass().getResource(picURL + "monsterheadend.png")).getImage();
        swirlyThing = new ImageIcon(getClass().getResource(picURL + "swirlyend.png")).getImage();
        backgroundBlock = new ImageIcon(getClass().getResource(picURL + "backgroundend.png")).getImage();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        //super.paint(g);
//
//            Graphics2D g2d = (Graphics2D) g;
//
//            g2d.drawImage(background, 0, 0, null);
    }

    public void paintPlayers(Graphics g) {
        for (Entity e : players) {
            g.drawImage(e.getCurrentImage(), e.getX(), e.getY(), this);
        }
    }

    public void drawMap(Graphics g) {
        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map.length; j++) {
                g.drawImage(backgroundBlock, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                if (map[i][j] == ROCK) {
                    g.drawImage(rock, movingX + i * GRID_X, mapY + j * GRID_Y, this);

                } else if (map[i][j] == METAL) {
                    g.drawImage(metal, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == PIPE) {
                    g.drawImage(pipe, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == PIPE_END) {
                    g.drawImage(pipeEnd, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == MONSTER_HEAD) {
                    g.drawImage(monsterHead, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == SWIRLY_THING) {
                    g.drawImage(swirlyThing, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_BOTTOM) {
                    g.drawImage(doorBottom, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_BOTTOM_MIDDLE) {
                    g.drawImage(doorBottomMiddle, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_TOP_MIDDLE) {
                    g.drawImage(doorTopMiddle, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_TOP) {
                    g.drawImage(doorTop, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                }else if (map[i][j] == DOOR_EXIT_BOTTOM) {
                    g.drawImage(doorExitBottom, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_EXIT_BOTTOM_MIDDLE) {
                    g.drawImage(doorExitBottomMiddle, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_EXIT_TOP_MIDDLE) {
                    g.drawImage(doorExitTopMiddle, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_EXIT_TOP) {
                    g.drawImage(doorExitTop, movingX + i * GRID_X, mapY + j * GRID_Y, this);
                } 
//                else {
//                    g.drawImage(backgroundBlock, movingX + i * GRID_X, mapY + j * GRID_Y, this);
//                }

            }
        }
    }

    public void paint(Graphics g) {

        if (initialSetup == true) {
            startOnline();
        }
        super.paint(g);

        //movingX = ((48*6));
        g.drawImage(gameBackground, 0, 0, this);
        //g.drawImage(gameBackground, 450 - p.backgroundX, 0, this);
        //g.drawImage(theseGuys, 0, 0, this);
//        if (p.getX() > 400) {
//            p.backgroundX2 = 0;
//        }
//        if (p.getX() > 800) {
//            p.backgroundX = 0;
//            //g.drawImage(gameBackground, 450 - p.backgroundX2, 0, this);
//        }
//        //paintPlayers(g);

        drawMap(g);
        playerGotPowerUp(g);
        hitDusty();
//        if(mapX - frameWidth >= -2592){
//          
//            mapXIncrementor = 5;
//            mapX = mapX - mapXIncrementor;
//        }

        canPlayerMoveLeft();
        canPlayerMoveRight();
        if (playerIsFalling()) {
            mapYDecrementor = 10;
            //mapY = mapY - mapYDecrementor;
            //p.setY(p.getY() + mapYDecrementor);
            p.fall();
        }
        if (p.isPlayerJumping()) {

            p.goUp();
        }

        if(crocBoss != null){
        
            crocBoss.goLeft();
            g.drawImage(crocBoss.getCurrentImage(), crocBoss.getX() + movingX, crocBoss.getY(), this);
        }
        if(chrisBoss != null){
            chrisBoss.goLeft();
            g.drawImage(chrisBoss.getCurrentImage(), chrisBoss.getX() + movingX, chrisBoss.getY(), this);
        }
        if(metroidBoss != null){
            metroidBoss.goLeft();
            g.drawImage(metroidBoss.getCurrentImage(), metroidBoss.getX() + movingX, metroidBoss.getY(), this);
        }
        
        g.drawImage(dusty.getCurrentImage(), dusty.getX(), dusty.getY(), this);
        
        if(host == true){
            
            if((movingX > (endOfMovingX + frameWidth)) && p.isPlayerMovingRight() && p.getX() >= 48*6){
                
                g.drawImage(p.getCurrentImage(), 48*6, p.getY(), this); 
            }
                            
                        
            else{
               g.drawImage(p.getCurrentImage(), p.getX() + movingX, p.getY(), this); 
            }
            if(p2 != null){
              
               g.drawImage(p2.getCurrentImage(), p2.getX() + movingX, p2.getY(), this);
            }
            
        }else{
            
            g.drawImage(p.getCurrentImage(), p.getX() + movingX, p.getY(), this);
            g.drawImage(p2.getCurrentImage(), 48*6, p2.getY(), this);
        }
        
        
//        g.drawImage(emily.getCurrentImage(), emily.getX(), emily.getY(), this);
//        g.drawImage(william.getCurrentImage(), william.getX(), william.getY(), this);
//        g.drawImage(chris.getCurrentImage(), chris.getX(), chris.getY(), this);
//        g.drawImage(annie.getCurrentImage(), annie.getX(), annie.getY(), this);
        

        //g.drawImage(d.getCurrentImage(), d.getX(), d.getY(), this);
        //g.drawImage(c.getCurrentImage(), c.getX(), c.getY(), this);
        for (Missile m : missiles) {

            g.drawImage(m.getCurrentImage(), m.getX(), m.getY(), this);
            if (m.getDirectionFacing() == LEFT) {
                m.goLeft(missileVelocity);
            } else {
                m.goRight(missileVelocity);
            }

        }
        for (ChargedShot cS : chargedShots) {
            g.drawImage(cS.getCurrentImage(), cS.getX(), cS.getY(), this);
            cS.goRight();
        }

        try {
            Thread.sleep(50);
            if (dusty.isPlayerMovingRight() == false || dusty.playerHasReachedBoundsMax() == true) {
                dusty.goLeft();
            }

            if (dusty.playerHasReachedBoundsMin() == true && dusty.isPlayerMovingLeft() == false) {
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
            if (key == KeyEvent.VK_ESCAPE) {
                int exit = JOptionPane.showConfirmDialog(null, "Exit game?", "Are you sure you want to exit?", JOptionPane.YES_NO_OPTION);

                if (exit == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
            if (key == KeyEvent.VK_LEFT) {
                if (host == true) {
                    if (canPlayerMoveLeft()) {
                        p.goLeft();
                      // p.setX(p.getX() - mapXIncrementor);
                        mapXIncrementor = 3;
                        
                        if(movingX <= 0){
                            movingX = movingX + mapXIncrementor; 
                        }
                        //if (movingX > 0) {
                           
                        //}
                        
//                        emily.goLeft();
//                        william.goLeft();
//                        chris.goLeft();
//                        annie.goLeft();
                    }

                } else {
                    d.goLeft();
                    p2.goLeft();

                }
                if (writerWritingToServer != null) {

                    writerWritingToServer.println(KeyEvent.VK_LEFT);
                }

            }
            if (key == KeyEvent.VK_RIGHT) {
                if (host == true) {
                    if (canPlayerMoveRight()) {
                        mapXIncrementor = 3;
                        if(movingX > (endOfMovingX + frameWidth) && p.getX() >= 48*6){
                            movingX = movingX - mapXIncrementor; 
                        }
                        //p.setX(p.getX() + mapXIncrementor);
                        p.goRight();
//                        emily.goRight();
//                        william.goRight();
//                        chris.goRight();
//                        annie.goRight();
                    }

                } else {
                    mapXIncrementor = 3;
                        if (movingX < (48*54) - (48-16)) {
                            movingX = movingX - mapXIncrementor;
                        }
                    d.goRight();
                    p2.goRight();

                }
                if (writerWritingToServer != null) {
                    writerWritingToServer.println(KeyEvent.VK_RIGHT);
                }

            }
            if (key == KeyEvent.VK_SPACE) {
                if (host == true) {

                    playSound(p.getName() + "aduh");
                    Missile m = new Missile(48*6, p.getY() + 15, p.LEFT);
                    if (p.getDirectionFacing() == LEFT) {

                        m.goLeft(missileVelocity);
                    } else {
                        m.goRight(missileVelocity);
                    }

                    missiles.add(m);

                } else {

                    playSound(p2.getName() + "aduh");
                    Missile m = new Missile(48*6, p2.getY() + 15, LEFT);
                    if (p2.getDirectionFacing() == LEFT) {

                        m.goLeft(missileVelocity);
                    } else {
                        m.goRight(missileVelocity);
                    }
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
            if (key == KeyEvent.VK_ENTER) {
                if (canPlayerGoHigher() && p.isPlayerJumping() == false) {

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
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                if (host == true) {
                    p.stop();
//                    emily.stop();
//                    william.stop();
//                    chris.stop();
//                    annie.stop();
                    //mapXIncrementor = 0;
                    //mapYIncrementor = 0;
                    //mapYDecrementor = 0;

                } else if (host == false) {
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
            Missile m = new Missile(p.getX() - movingX, p.getY() + 15, p.LEFT);
            if (p.getDirectionFacing() == LEFT) {

                m.goLeft(missileVelocity);
            } else {
                m.goRight(missileVelocity);
            }
            missiles.add(m);
        } else if(movement == KeyEvent.VK_ENTER){
            p.jump();
        }
    }

    public void handlePlayer2Movement(int movement) {

        if (movement == KeyEvent.VK_LEFT) {
            d.goLeft();
            p2.goLeft();

        } else if (movement == KeyEvent.VK_RIGHT) {

            d.goRight();
            p2.goRight();
        } else if (movement == 0) {
            d.stop();
            p2.stop();
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
            
//            do{
//              
//                while
//                clip.open(audioInputStream);
//                clip.start();  
//            }
//            while(clip.isRunning());
               
                
            
            

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public boolean canPlayerMoveRight() {
        int mapX = p.getX() / GRID_X;
        int mapY = p.getY() / GRID_Y;
        System.out.println("To my right there is " + map[mapX + 1][mapY]);
        System.out.println("I am at " + map[mapX][mapY]);

        if (mapY - 1 > 0) {
            System.out.println("Above me there is " + map[mapX][mapY - 1]);
            return (map[(p.getX() / GRID_X) + 1][p.getY() / GRID_Y]) == 0;
        } else {
            return true;
        }
    }

    public boolean canPlayerMoveLeft() {
        int mapX = p.getX() / GRID_X;
        int mapY = p.getY() / GRID_Y;
        System.out.println("To my left there is " + map[mapX - 1][mapY]);
        return (map[(p.getX() / GRID_X)][p.getY() / GRID_Y]) == 0;
    }

    public boolean canPlayerGoHigher() {
        int mapX = p.getX() / GRID_X;
        int mapY = p.getY() / GRID_Y;
        if (mapY - 1 >= 0) {
            return (map[(p.getX() / GRID_X)][p.getY() / GRID_Y - 1]) == 0;
        } else {
            return true;
        }

    }

    public boolean playerIsFalling() {
        int mapX = p.getX() / GRID_X;
        int mapY = p.getY() / GRID_Y;
        int mapXPlus = (p.getX() + p.getCurrentImage().getWidth(this)) / GRID_X;
        System.out.println("Below me there is " + map[mapX][mapY + 1]);

        if (mapY + 1 < map.length) {

            return ((map[(mapX)][(mapY + 1)]) == 0) && (map[(mapXPlus)][mapY + 1] == 0);

        } else {

            return true;
        }

    }

    private void setMap(String mapLevel) {
        map = new int[54][54];

        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/untitled/game/map/map" + mapLevel + ".txt"));

            for (int i = 0; i < map.length; i++) {
                String[] items = reader.readLine().split(" ");
                System.out.println(items.toString());
                for (int j = 0; j < map.length; j++) {
                    map[j][i] = Integer.parseInt(items[j]);
                    if (map[j][i] == 9) {
                        p = new Player(mapX + i * GRID_X, mapY + j * GRID_Y, RIGHT, characterName);
                        map[j][i] = 0;
                    } 
                    else if(map[j][i] == 91){
                       p2 = new Player(mapX + i * GRID_X, mapY + j * GRID_Y, RIGHT, characterName);
                        map[j][i] = 0; 
                    }
                    
                    else if (map[j][i] == 10) {

                        willPowerUp = new PowerUp("will", mapX + j * GRID_X, mapY + i * GRID_Y);
                        map[j][i] = 0;
                    } else if (map[j][i] == 20) {
                        milkPowerUp = new PowerUp("milk", mapX + j * GRID_X, mapY + i * GRID_Y);
                        map[j][i] = 0;
                    } else if (map[j][i] == 30) {
                        mommyPowerUp = new PowerUp("mommy", mapX + j * GRID_X, mapY + i * GRID_Y);
                        map[j][i] = 0;
                    } else if (map[j][i] == 40) {
                        myPowerUp = new PowerUp("me", mapX + j * GRID_X, mapY + i * GRID_Y);
                        map[j][i] = 0;
                    } else if (map[j][i] == 50) {
                        dustyPowerUp = new PowerUp("", mapX + j * GRID_X, mapY + i * GRID_Y);
                        map[j][i] = 0;
                    } else if(map[j][i] == 99){
                        crocBoss = new Boss("croc", mapX + j * GRID_X, mapY + i * GRID_Y);
                    }else if(map[j][i] == 101){
                        chrisBoss = new Boss("chris", mapX + j * GRID_X, mapY + i * GRID_Y);
                    } else if(map[j][i] == 111){
                        metroidBoss = new Boss("metroid", mapX + j * GRID_X, mapY + i * GRID_Y );
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    private void refreshMap(){
       currentMap = new int[17][17];
       
    }

    private void playerGotPowerUp(Graphics g) {

        
        Rectangle pBounds = p.getBounds();
        if (willPowerUp != null) {
            g.drawImage(willPowerUp.getCurrentImage(), willPowerUp.getX() + movingX, willPowerUp.getY(), this);
            Rectangle wBounds = willPowerUp.getBounds();
            if (pBounds.intersects(wBounds)) {

                p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "william");
                willPowerUp.setCurrentImage(backgroundBlock);
                willPowerUp = null;
            }
        }
        if (milkPowerUp != null) {
            g.drawImage(milkPowerUp.getCurrentImage(), milkPowerUp.getX() + movingX, milkPowerUp.getY(), this);
            Rectangle eBounds = milkPowerUp.getBounds();
            if (pBounds.intersects(eBounds)) {
                p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "emily");
                milkPowerUp.setCurrentImage(backgroundBlock);
                milkPowerUp = null;
            }
        }

        if (mommyPowerUp != null) {
            g.drawImage(mommyPowerUp.getCurrentImage(), mommyPowerUp.getX() + movingX, mommyPowerUp.getY(), this);
            Rectangle mBounds = mommyPowerUp.getBounds();
            if (pBounds.intersects(mBounds)) {
                p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "annie");
                mommyPowerUp.setCurrentImage(backgroundBlock);
                mommyPowerUp = null;
            }
        }
        if (myPowerUp != null) {
            g.drawImage(myPowerUp.getCurrentImage(), myPowerUp.getX() + movingX, myPowerUp.getY(), this);
            Rectangle myBounds = myPowerUp.getBounds();
            if (pBounds.intersects(myBounds)) {
                p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "chris");
                myPowerUp.setCurrentImage(backgroundBlock);
                myPowerUp = null;
            }
        }
        if (dustyPowerUp != null) {
            g.drawImage(dustyPowerUp.getCurrentImage(), dustyPowerUp.getX() + movingX, dustyPowerUp.getY(), this);
            Rectangle dBounds = dustyPowerUp.getBounds();
            if (pBounds.intersects(dBounds)) {
                p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "dusty");
                dustyPowerUp.setCurrentImage(backgroundBlock);
                dustyPowerUp = null;
            }
        }
        
    }
    
    private void hitDusty(){
        
        for(Missile m: missiles){
           Rectangle dustyBounds = dusty.getBounds(); 
           Rectangle missileBounds = m.getBounds(); 
           if(missileBounds.intersects(dustyBounds)){
               dusty.setCurrentImage(empty);
           }
        }
        
        
    }

}
