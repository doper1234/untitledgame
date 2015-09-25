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

    final int GRID_X = 48;
    final int GRID_Y = 48;

    int frameWidth = 768;
    int frameHeight = 576;

    int mapX = 0;
    int mapY = 0;

    final int missileVelocity = 5;
    final int chargedShotVelocity = 30;
    boolean host = true;
    boolean initialSetup = false;

    boolean setupMap = true;
    String picURL = "sprites/";
    String wavURL = "src/untitled/game/music/";
    String characterName;

    Player p;
    Player emily;
    Player william;
    Player chris;
    Player annie;
    Player dusty;

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

    public UntitledGame(String characterName, Frame frame) {

        this.characterName = characterName;
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
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

    public void paintPlayers(Graphics g) {
        for (Entity e : players) {
            g.drawImage(e.getCurrentImage(), e.getX(), e.getY(), this);
        }
    }

    public void drawMap(Graphics g) {
        for (int i = 0; i < map.length; i++) {

            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == ROCK) {
                    g.drawImage(rock, mapX + i * GRID_X, mapY + j * GRID_Y, this);

                } else if (map[i][j] == METAL) {
                    g.drawImage(metal, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == PIPE) {
                    g.drawImage(pipe, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == PIPE_END) {
                    g.drawImage(pipeEnd, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == MONSTER_HEAD) {
                    g.drawImage(monsterHead, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == SWIRLY_THING) {
                    g.drawImage(swirlyThing, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_BOTTOM) {
                    g.drawImage(doorBottom, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_BOTTOM_MIDDLE) {
                    g.drawImage(doorBottomMiddle, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_TOP_MIDDLE) {
                    g.drawImage(doorTopMiddle, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else if (map[i][j] == DOOR_TOP) {
                    g.drawImage(doorTop, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                } else {
                    g.drawImage(backgroundBlock, mapX + i * GRID_X, mapY + j * GRID_Y, this);
                }

            }
        }
    }

    public void paint(Graphics g) {

        if (initialSetup == true) {
            startOnline();
        }
        super.paint(g);

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
        playerGotPowerUp();
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

        g.drawImage(dusty.getCurrentImage(), dusty.getX(), dusty.getY(), this);
        g.drawImage(willPowerUp.getCurrentImage(), willPowerUp.getX(), willPowerUp.getY(), this);
        g.drawImage(milkPowerUp.getCurrentImage(), milkPowerUp.getX(), milkPowerUp.getY(), this);
        g.drawImage(mommyPowerUp.getCurrentImage(), mommyPowerUp.getX(), mommyPowerUp.getY(), this);
        g.drawImage(myPowerUp.getCurrentImage(), myPowerUp.getX(), myPowerUp.getY(), this);
        g.drawImage(dustyPowerUp.getCurrentImage(), dustyPowerUp.getX(), dustyPowerUp.getY(), this);
        g.drawImage(p.getCurrentImage(), p.getX(), p.getY(), this);
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
                    if (canPlayerMoveRight()) {
                    //mapXIncrementor = 3;
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

                    playSound(p.getName() + "aduh");
                    Missile m = new Missile(p.getX(), p.getY() + 15, p.LEFT);
                    if (p.getDirectionFacing() == LEFT) {

                        m.goLeft(missileVelocity);
                    } else {
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
                    emily.stop();
                    william.stop();
                    chris.stop();
                    annie.stop();
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
            Missile m = new Missile(p.getX(), p.getY() + 15, p.LEFT);
            if (p.getDirectionFacing() == LEFT) {

                m.goLeft(missileVelocity);
            } else {
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

    private void setMap() {
        map = new int[54][54];

        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/untitled/game/map/map.txt"));

            for (int i = 0; i < map.length; i++) {
                String[] items = reader.readLine().split(" ");
                System.out.println(items.toString());
                for (int j = 0; j < map.length; j++) {
                    map[j][i] = Integer.parseInt(items[j]);
                    if (map[j][i] == 9) {
                        p = new Player(mapX + i * GRID_X, mapY + j * GRID_Y, RIGHT, characterName);
                        map[j][i] = 0;
                    } else if (map[j][i] == 10) {

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
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void playerGotPowerUp() {

        Rectangle pBounds = p.getBounds();
        Rectangle wBounds = willPowerUp.getBounds();
        Rectangle eBounds = milkPowerUp.getBounds();
        Rectangle mBounds = mommyPowerUp.getBounds();
        Rectangle myBounds = myPowerUp.getBounds();
        Rectangle dBounds = dustyPowerUp.getBounds();

        if (pBounds.intersects(wBounds)) {

            p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "william");
            willPowerUp.setCurrentImage(backgroundBlock);
        }
        if (pBounds.intersects(eBounds)) {
            p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "emily");
            milkPowerUp.setCurrentImage(backgroundBlock);
        }
        if (pBounds.intersects(mBounds)) {
            p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "annie");
            mommyPowerUp.setCurrentImage(backgroundBlock);
        }
        if (pBounds.intersects(myBounds)) {
            p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "chris");
            myPowerUp.setCurrentImage(backgroundBlock);
        }
        if (pBounds.intersects(dBounds)) {
            p = new Player(p.getX(), p.getY(), p.getDirectionFacing(), "dusty");
            dustyPowerUp.setCurrentImage(backgroundBlock);
        }

    }

}
