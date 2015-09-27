/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;

/**
 *
 * @author Chris
 */
public class GameFrame {

    String picURL = "sprites/";
    String wavURL = "src/untitled/game/music/";

    UntitledGame game;
    MenuScreen menu;
    LoadSavedGameScreen loadScreen;
    NewGameCharacterSelection newGameCharacterSelection;
    WorldMap worldMap;

    JFrame frame;
    JPanel mainPanel;
    PrintWriter writerWritingToPlayer;
    PrintWriter writerWritingToServer;
    Socket playerSocket;
    BufferedReader readerReadingFromServer;
    BufferedReader readerReadingFromPlayer;
    Thread readerThread;

    ArrayList<JButton> buttons;
    final String iP = "192.168.1.104";
    int GAME_WIDTH = 500;
    int GAME_HEIGHT = 520;
    int FRAME_WIDTH = GAME_WIDTH;
    int FRAME_HEIGHT = 520;
    final int missileVelocity = 15;
    boolean host = false;

    Player p;
    DarkSuit d;
    Coin c;
    Missile m;
    ChargedShot cS;

    Font menuFont = new Font("Bradley Hand ITC", Font.PLAIN, 25);
    Image gameBackground = new ImageIcon(getClass().getResource(picURL + "background.png")).getImage();
    Image menuBackground = new ImageIcon(getClass().getResource(picURL + "menuBackground1.png")).getImage();

    ImageIcon menuIcon1 = new ImageIcon(getClass().getResource(picURL + "menuIcon1a.png"));
    ImageIcon menuIcon2 = new ImageIcon(getClass().getResource(picURL + "menuIcon2a.png"));
    ImageIcon menuIcon3 = new ImageIcon(getClass().getResource(picURL + "menuIcon3a.png"));
    ImageIcon eraseGameIcon = new ImageIcon(getClass().getResource(picURL + "erasegameIcon.png"));
    Image menuIcon = new ImageIcon(getClass().getResource(picURL + "menuIcon1a.png")).getImage();

//    ImageIcon waitingForPlayer = new ImageIcon(getClass().getResource(picURL + "waitingforplayer.png"));
//    ImageIcon playerConnected = new ImageIcon(getClass().getResource(picURL + "playerconnected.png"));
//    ImageIcon startingGame = new ImageIcon(getClass().getResource(picURL + "startinggame.png"));
//    ImageIcon connectingToPlayer = new ImageIcon(getClass().getResource(picURL + "connectingtoplayer.png"));
//    ImageIcon connectionEstablished = new ImageIcon(getClass().getResource(picURL + "connectionestablished.png"));
//    ImageIcon notConnected = new ImageIcon(getClass().getResource(picURL + "notconnected.png"));
//    ImageIcon disconnected = new ImageIcon(getClass().getResource(picURL + "disconnected.png"));
//    Image connectionStatus = notConnected.getImage();
    int menuMove = 0;
    final int NEW_GAME = 1;
    final int LOAD_GAME = 2;
    final int EXIT_TO_WINDOWS = 3;
    final int HOST_GAME = 4;
    final int JOIN_GAME = 5;
    final int NEW_GAME_X = 185;
    final int NEW_GAME_Y = 286;
    final int LOAD_GAME_X = 235;
    final int LOAD_GAME_Y = 310;
    final int EXIT_TO_WINDOWS_X = 285;
    final int EXIT_TO_WINDOWS_Y = 335;
    final int HOST_GAME_X = 188;
    final int HOST_GAME_Y = 388;
    final int JOIN_GAME_X = 225;
    final int JOIN_GAME_Y = 415;
    int menuIconX = NEW_GAME_X;
    int menuIconY = NEW_GAME_Y;
    int menuWhere = NEW_GAME;

    int fileNumber;

    public static void main(String[] args) {

        GameFrame gf = new GameFrame();
        gf.go(true);

    }

    public void go(boolean startUpMenu) {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem returnToMenu = new JMenuItem("Return to main menu");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        saveMenuItem.addActionListener(new SaveMenuListener());
        loadMenuItem.addActionListener(new LoadMenuListener());
        returnToMenu.addActionListener(new ReturnToMenuListener());
        exitMenuItem.addActionListener(new ExitMenuListener());
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.add(returnToMenu);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        if (startUpMenu == true) {
            menu = new MenuScreen();
            menu.addKeyListener(new MenuScreenKeyListener());
            menu.setLayout(null);

            mainPanel.add(menu);

            menu.start();

        } else {
            System.out.println("starting new game");
            game = new UntitledGame("bob", frame, "1-1", "no theme");
            System.out.println("new game created");
            //game.addKeyListener(new UntitledGame.GameKeyListener());

            mainPanel.add(game);
            System.out.println("new game added to panel");

        }

        //setButtons(menuScreen);
        frame = new JFrame("My Game");
        //frame.setJMenuBar(menuBar);
        frame.setResizable(false);
        //frame.setUndecorated(true);
        frame.setSize(GAME_WIDTH, FRAME_HEIGHT);
        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while (true) {
            if (game != null) {
                game.repaint();
            }
            if (menu != null) {
                menu.repaint();

            }
            if (worldMap != null) {
                worldMap.repaint();
            }
            if (loadScreen != null) {
                loadScreen.repaint();
            }
            if (newGameCharacterSelection != null) {
                newGameCharacterSelection.repaint();
            }

        }

    }

    public class MenuScreen extends JPanel {

        public void start() {
            playSound("WandE");
            setFocusable(true);
            requestFocus();

        }

        @Override
        public void paint(Graphics g) {

            g.drawImage(menuBackground, 0, 0, this);
            g.drawImage(menuIcon, menuIconX, menuIconY, this);
            //g.drawImage(connectionStatus, 0, 400, this);
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

    public class MenuScreenKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT) {
                if (menuWhere == NEW_GAME) {
                    menuIconX = LOAD_GAME_X;
                    menuIconY = LOAD_GAME_Y;
                    menuWhere = LOAD_GAME;
                } else if (menuWhere == LOAD_GAME) {
                    menuIconX = EXIT_TO_WINDOWS_X;
                    menuIconY = EXIT_TO_WINDOWS_Y;
                    menuWhere = EXIT_TO_WINDOWS;
                } else if (menuWhere == EXIT_TO_WINDOWS) {
                    menuIconX = HOST_GAME_X;
                    menuIconY = HOST_GAME_Y;
                    menuWhere = HOST_GAME;

                } else if (menuWhere == HOST_GAME) {
                    menuIconX = JOIN_GAME_X;
                    menuIconY = JOIN_GAME_Y;
                    menuWhere = JOIN_GAME;
                } else if (menuWhere == JOIN_GAME) {
                    menuIconX = NEW_GAME_X;
                    menuIconY = NEW_GAME_Y;
                    menuWhere = NEW_GAME;
                }

            }
            if (key == KeyEvent.VK_ENTER) {
                if (menuWhere == NEW_GAME) {

                    worldMap = new WorldMap("bob");
                    worldMap.addKeyListener(new WorldMapKeyListener());
                    worldMap.validate();

                    //game.addKeyListener(new GameKeyListener());
                    mainPanel.add(worldMap);
                    mainPanel.remove(menu);

                    System.out.println("frame disposed");
                    //go(false);
                    frame.validate();
                    frame.repaint();
                }
                if (menuWhere == LOAD_GAME) {
                    loadScreen = new LoadSavedGameScreen(false);
                    loadScreen.addKeyListener(new LoadSavedGameListener());
                    loadScreen.validate();
                    mainPanel.add(loadScreen);
                    mainPanel.remove(menu);
                    frame.validate();
                    frame.repaint();

                }
                if (menuWhere == EXIT_TO_WINDOWS) {
                    System.exit(0);
                }
                if (menuWhere == HOST_GAME) {

                    //connectionStatus = waitingForPlayer.getImage();
                    try {
                        //setUpHostGameServer();
                        startGame(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //connectionStatus = disconnected.getImage();
                    }
//                    
                }
                if (menuWhere == JOIN_GAME) {
                    //connectionStatus = connectingToPlayer.getImage();
                    try {
                        //setUpJoinGameNetworking();
                        startGame(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        //connectionStatus = disconnected.getImage();
                    }
//                    game = new UntitledGame(false);
//                    game.addKeyListener(new GameKeyListener());
//                    mainPanel.add(game);
//                    mainPanel.remove(menu);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

        public void setUpJoinGameNetworking() throws Exception {

            playerSocket = new Socket(iP, 3074);

            InputStreamReader streamReader = new InputStreamReader(playerSocket.getInputStream());
            readerReadingFromPlayer = new BufferedReader(streamReader);
            writerWritingToServer = new PrintWriter(playerSocket.getOutputStream());
            System.out.println("networking established");
            startGame(false);

        }

        public void setUpHostGameServer() throws Exception {

            System.out.println("Hosting new game");
            ServerSocket server = new ServerSocket(3074);
            while (true) {
                System.out.println("waiting for player");
                Socket playerJoinedSocket = server.accept();
                System.out.println("Player entered");
                PrintWriter writer = new PrintWriter(playerJoinedSocket.getOutputStream());
                writer.println("you have joined the game");
                writer.flush();
                System.out.println("Player joined");
                startGame(true);
                server.close();
                return;

//                writerWritingToServer = new PrintWriter(playerJoinedSocket.getOutputStream());
//                writerWritingToPlayer.println("Player joined game");
//                writerWritingToPlayer.flush();
//                Thread t = new Thread(new ClientHandler((playerJoinedSocket)));
//                t.start();
            }

        }

        public void startGame(boolean host) {
            Socket s;
            String iP = "fuck you";
            try {
                s = new Socket("google.com", 80);
                iP = (s.getLocalAddress().getHostAddress());
                s.close();
            } catch (IOException ex) {
                //Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(iP);
            //String iP = JOptionPane.showInputDialog(null, "Enter IP");
            game = new UntitledGame("bob", frame, "1-1", "no theme", host, iP);
            //game.addKeyListener(new GameKeyListener());
            mainPanel.add(game);
            mainPanel.remove(menu);
            System.out.println("starting game");
            //go(false);
            frame.validate();
            frame.repaint();
        }
    }

    public class LoadSavedGameScreen extends JPanel {

        final int SAVED_GAME_ONE_X = 40;
        final int SAVED_GAME_ONE_Y = 65;
        final int SAVED_GAME_ONE = 1;
        final int SAVED_GAME_TWO_X = 40;
        final int SAVED_GAME_TWO_Y = 130;
        final int SAVED_GAME_TWO = 2;
        final int SAVED_GAME_THREE_X = 40;
        final int SAVED_GAME_THREE_Y = 200;
        final int SAVED_GAME_THREE = 3;
        final int ERASE_GAME_X = 40;
        final int ERASE_GAME_Y = 300;
        final int ERASE_GAME = 4;
        final int BACK_TO_MENU_X = 40;
        final int BACK_TO_MENU_Y = 420;
        final int BACK_TO_MENU = 5;

        JLabel saveGameOne;
        JLabel saveGameOneStats;
        JLabel saveGameTwo;
        JLabel saveGameTwoStats;
        JLabel saveGameThree;
        JLabel saveGameThreeStats;
        JLabel eraseGame;
        JLabel emptyLabel;
        JLabel header;
        JLabel backToMenuScreen;
        JLabel IconLabel;
        File file1;
        File file2;
        File file3;
        String saveOneText = "";
        String saveTwoText = "";
        String saveThreeText = "";
        Image backGround = new ImageIcon(getClass().getResource("sprites/emptybackground.png")).getImage();

        Font menuFont = new Font("Bradley Hand ITC", Font.BOLD, 24);
        Color fontColor = Color.red;
        JPanel mainPanelLoadScreen;

        public LoadSavedGameScreen(boolean newGame) {
            
            menuIconX = SAVED_GAME_ONE_X;
            menuIconY = SAVED_GAME_ONE_Y;
            menuWhere = SAVED_GAME_ONE;
            setSize(500, 500);
            setBackground(Color.black);
            setLayout(new BorderLayout());
            mainPanelLoadScreen = new JPanel();
            readFile1(file1);
            readFile2(file2);
            readFile3(file3);

            header = new JLabel("Choose a game");
            IconLabel = new JLabel("Icon goes here");
            IconLabel.setForeground(Color.black);
            saveGameOne = new JLabel(saveOneText);
            saveGameTwo = new JLabel(saveTwoText);
            saveGameThree = new JLabel(saveThreeText);
            saveGameOneStats = new JLabel("...............");
            saveGameTwoStats = new JLabel("...............");
            saveGameThreeStats = new JLabel("...............");
            eraseGame = new JLabel("Erase Game");
            emptyLabel = new JLabel("E");
            backToMenuScreen = new JLabel("             Back to main menu");

            setLabelFontAndColor(header);
            setLabelFontAndColor(saveGameOne);
            setLabelFontAndColor(saveGameOneStats);
            setLabelFontAndColor(saveGameTwo);
            setLabelFontAndColor(saveGameTwoStats);
            setLabelFontAndColor(saveGameThree);
            setLabelFontAndColor(saveGameThreeStats);
            setLabelFontAndColor(eraseGame);
            setLabelFontAndColor(backToMenuScreen);

            header.setFont(new Font("Bradley Hand ITC", Font.BOLD, 48));
            backToMenuScreen.setFont(new Font("Bradley Hand ITC", Font.PLAIN, 24));
            emptyLabel.setFont(new Font("Bradley Hand ITC", Font.BOLD, 48));
            emptyLabel.setForeground(Color.black);

            mainPanelLoadScreen.setLayout(new BoxLayout(mainPanelLoadScreen, BoxLayout.Y_AXIS));
            mainPanelLoadScreen.add(saveGameOne);
            mainPanelLoadScreen.add(saveGameOneStats);
            mainPanelLoadScreen.add(saveGameTwo);
            mainPanelLoadScreen.add(saveGameTwoStats);
            mainPanelLoadScreen.add(saveGameThree);
            mainPanelLoadScreen.add(saveGameThreeStats);
            mainPanelLoadScreen.add(emptyLabel);
            mainPanelLoadScreen.add(eraseGame);
            mainPanelLoadScreen.setBackground(Color.BLACK);

            add(BorderLayout.WEST, IconLabel);
            add(BorderLayout.CENTER, mainPanelLoadScreen);
            add(BorderLayout.NORTH, header);
            add(BorderLayout.SOUTH, backToMenuScreen);

            setFocusable(true);
            requestFocus();

        }

        public void readFile1(File file1) {
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

            if (saveOneText.equalsIgnoreCase("")) {
                saveOneText = "New Game";
            }
        }

        public void readFile2(File file2) {
            try {

                file2 = new File("src/untitled/game/savegamefiles/save2");
                FileReader fReader = new FileReader(file2);
                BufferedReader reader = new BufferedReader(fReader);
                String readerInput = null;

                while ((readerInput = reader.readLine()) != null) {
                    if (readerInput != null) {
                        saveTwoText = saveTwoText + readerInput;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (saveTwoText.equalsIgnoreCase("")) {
                saveTwoText = "New Game";
            }
        }

        public void readFile3(File file3) {
            try {

                file3 = new File("src/untitled/game/savegamefiles/save3");
                FileReader fReader = new FileReader(file3);
                BufferedReader reader = new BufferedReader(fReader);
                String readerInput = null;

                while ((readerInput = reader.readLine()) != null) {
                    if (readerInput != null) {
                        saveThreeText = saveThreeText + readerInput;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (saveThreeText.equalsIgnoreCase("")) {
                saveThreeText = "New Game";
            }
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
            g.drawImage(menuIcon, menuIconX, menuIconY, mainPanelLoadScreen);
            mainPanelLoadScreen.repaint();

            if (menuIcon != eraseGameIcon.getImage()) {

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

    }

    public class LoadSavedGameListener implements KeyListener {

        boolean erasingGame = false;

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {

            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_DOWN) {
                if (menuWhere == loadScreen.SAVED_GAME_ONE) {
                    menuIconX = loadScreen.SAVED_GAME_TWO_X;
                    menuIconY = loadScreen.SAVED_GAME_TWO_Y;
                    menuWhere = loadScreen.SAVED_GAME_TWO;
                } else if (menuWhere == loadScreen.SAVED_GAME_TWO) {
                    menuIconX = loadScreen.SAVED_GAME_THREE_X;
                    menuIconY = loadScreen.SAVED_GAME_THREE_Y;
                    menuWhere = loadScreen.SAVED_GAME_THREE;
                } else if (menuWhere == loadScreen.SAVED_GAME_THREE) {
                    menuIconX = loadScreen.ERASE_GAME_X;
                    menuIconY = loadScreen.ERASE_GAME_Y;
                    menuWhere = loadScreen.ERASE_GAME;
                } else if (menuWhere == loadScreen.ERASE_GAME) {
                    menuIconX = loadScreen.BACK_TO_MENU_X;
                    menuIconY = loadScreen.BACK_TO_MENU_Y;
                    menuWhere = loadScreen.BACK_TO_MENU;
                } else if (menuWhere == loadScreen.BACK_TO_MENU) {
                    menuIconX = loadScreen.SAVED_GAME_ONE_X;
                    menuIconY = loadScreen.SAVED_GAME_ONE_Y;
                    menuWhere = loadScreen.SAVED_GAME_ONE;
                }
            }
            if (key == KeyEvent.VK_ENTER) {
                if (menuWhere == loadScreen.SAVED_GAME_ONE) {

                    if (erasingGame == false) {
                        loadSavedGame(1);
                    } else {
                        eraseSavedGame(1);
                    }

                } else if (menuWhere == loadScreen.SAVED_GAME_TWO) {

                    if (erasingGame == false) {
                        loadSavedGame(2);
                    } else {
                        eraseSavedGame(2);
                    }

                } else if (menuWhere == loadScreen.SAVED_GAME_THREE) {

                    if (erasingGame == false) {
                        loadSavedGame(3);
                    } else {
                        eraseSavedGame(3);
                    }

                } else if (menuWhere == loadScreen.ERASE_GAME) {
                    menuIcon = eraseGameIcon.getImage();
                    loadScreen.header.setText("Erase a Game");
                    erasingGame = true;
                    loadScreen.backToMenuScreen.setText("             Cancel");

                } else if (menuWhere == loadScreen.BACK_TO_MENU) {
                    //menu = new MenuScreen();
                    //menu.addKeyListener(new MenuScreenKeyListener());
                    //menu.validate();

                    if (erasingGame == true) {
                        erasingGame = false;
                        menuIcon = menuIcon1.getImage();
                        loadScreen.backToMenuScreen.setText("             Back to main menu");
                        loadScreen.header.setText("Choose a Game");
                    } else {
                        mainPanel.add(menu);
                        mainPanel.remove(loadScreen);

                        frame.validate();
                        frame.repaint();
                        menuWhere = NEW_GAME;
                        menuIconX = NEW_GAME_X;
                        menuIconY = NEW_GAME_Y;
                    }
                }
            }
        }

        public void loadSavedGame(int fileNumberLoad) {
            JLabel l;
            if (fileNumberLoad == 1) {
                l = loadScreen.saveGameOne;
            } else if (fileNumberLoad == 2) {
                l = loadScreen.saveGameTwo;
            } else {
                l = loadScreen.saveGameThree;
            }

            fileNumber = fileNumberLoad;
            if (l.getText().equalsIgnoreCase("New Game")) {

                //String saveName = JOptionPane.showInputDialog(null, "Enter name: ");
                newGameCharacterSelection = new NewGameCharacterSelection();
                newGameCharacterSelection.addKeyListener(new NewGameListener());
                newGameCharacterSelection.validate();

                System.out.println("menu where is going to emily");
                
                
                
                
                
                mainPanel.add(newGameCharacterSelection);
                mainPanel.remove(loadScreen);
                

                frame.validate();
                frame.repaint();
                
                newGameCharacterSelection.menuWhere = newGameCharacterSelection.EMILY;
                newGameCharacterSelection.menuIconX = newGameCharacterSelection.EMILY_X;
                newGameCharacterSelection.menuIconY = newGameCharacterSelection.EMILY_Y;

            } else {
                String temp[] = l.getText().split(" ");
                
                worldMap = new WorldMap(temp[0]);
                worldMap.addKeyListener(new WorldMapKeyListener());
                worldMap.validate();

                mainPanel.add(worldMap);
                mainPanel.remove(loadScreen);

                frame.validate();
                frame.repaint();
            }
        }

        public void eraseSavedGame(int fileNumber) {

            int deleteOrNotToDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to erase this saved game?", "Warning", JOptionPane.YES_NO_OPTION);

            if (deleteOrNotToDelete == JOptionPane.YES_OPTION) {
                JLabel l;
                if (fileNumber == 1) {
                    l = loadScreen.saveGameOne;
                } else if (fileNumber == 2) {
                    l = loadScreen.saveGameTwo;
                } else {
                    l = loadScreen.saveGameThree;
                }

                if (!l.getText().equalsIgnoreCase("New Game")) {

                    try {

                        FileWriter writer = new FileWriter("src/untitled/game/savegamefiles/save" + fileNumber);
                        writer.write("");
                        writer.flush();
                        l.setText("New Game");
                    } catch (IOException e) {

                    }

                }
                erasingGame = false;
                menuIcon = menuIcon1.getImage();
                loadScreen.backToMenuScreen.setText("             Back to main menu");
                loadScreen.header.setText("Choose a Game");
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }

    public class WorldMapKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();
            if ((key == KeyEvent.VK_RIGHT && worldMap.b.playerStoppedMoving()) && worldMap.b.getX() != worldMap.levelThreeX) {
                worldMap.b.goRightMap();
            }
            if ((key == KeyEvent.VK_LEFT && worldMap.b.playerStoppedMoving()) && worldMap.b.getX() != worldMap.levelOneX) {
                worldMap.b.goLeftMap();
            }
            if (key == KeyEvent.VK_ENTER && (worldMap.b.getX() == worldMap.levelOneX || worldMap.b.getX() == worldMap.levelTwoX || worldMap.b.getX() == worldMap.levelThreeX)) {
                if(worldMap.b.getX() == worldMap.levelOneX){
                   game = new UntitledGame(worldMap.characterName, frame, "1-1", "no theme"); 
                } else if (worldMap.b.getX() == worldMap.levelTwoX){
                   game = new UntitledGame(worldMap.characterName, frame, "1-2", "norfair"); 
                }else if (worldMap.b.getX() == worldMap.levelThreeX){
                   game = new UntitledGame(worldMap.characterName, frame, "1-3", "end"); 
                }
                
                //game.addKeyListener(new WorldMapKeyListener());
                mainPanel.add(game);
                mainPanel.remove(worldMap);
                frame.validate();
                frame.repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }

    public class NewGameListener implements KeyListener {

        String emilyDescription = "Emily. The youngest warrior of the Hoffs. Specializes in spitting on people.";
        String williamDescription = "William. The hth(cat) slayer. Specializes in slaying hths.";
        String annieDescription = "Annie. The beautiful polka, and soon to be wife to Chris. Specializes in hand to hand combat. ";
        String chrisDescription = "Chris. The designer of the game. Specializes in eating pears.";

        String characterDescription;

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();

            if (key == KeyEvent.VK_DOWN) {

                
                if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.EMILY) {
                    newGameCharacterSelection.menuIconX = newGameCharacterSelection.WILLIAM_X;
                    newGameCharacterSelection.menuIconY = newGameCharacterSelection.WILLIAM_Y;
                    newGameCharacterSelection.menuWhere = newGameCharacterSelection.WILLIAM;
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.WILLIAM) {
                    newGameCharacterSelection.menuIconX = newGameCharacterSelection.ANNIE_X;
                    newGameCharacterSelection.menuIconY = newGameCharacterSelection.ANNIE_Y;
                    newGameCharacterSelection.menuWhere = newGameCharacterSelection.ANNIE;
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.ANNIE) {
                    newGameCharacterSelection.menuIconX = newGameCharacterSelection.CHRIS_X;
                    newGameCharacterSelection.menuIconY = newGameCharacterSelection.CHRIS_Y;
                    newGameCharacterSelection.menuWhere = newGameCharacterSelection.CHRIS;

                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.CHRIS) {
                    newGameCharacterSelection.menuIconX = newGameCharacterSelection.BACK_X;
                    newGameCharacterSelection.menuIconY = newGameCharacterSelection.BACK_Y;
                    newGameCharacterSelection.menuWhere = newGameCharacterSelection.BACK;
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.BACK) {
                    newGameCharacterSelection.menuIconX = newGameCharacterSelection.EMILY_X;
                    newGameCharacterSelection.menuIconY = newGameCharacterSelection.EMILY_Y;
                    newGameCharacterSelection.menuWhere = newGameCharacterSelection.EMILY;
                }
            }
            if (key == KeyEvent.VK_ENTER) {

                if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.EMILY) {
                    startGame("Emily");
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.WILLIAM) {
                    startGame("William");
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.ANNIE) {
                    startGame("Annie");
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.CHRIS) {
                    startGame("Chris");
                } else if (newGameCharacterSelection.menuWhere == newGameCharacterSelection.BACK) {

                    mainPanel.add(loadScreen);
                    mainPanel.remove(newGameCharacterSelection);

                    frame.validate();
                    frame.repaint();
                    menuWhere = loadScreen.SAVED_GAME_ONE;
                    menuIconX = loadScreen.SAVED_GAME_ONE_X;
                    menuIconY = loadScreen.SAVED_GAME_ONE_X;
                }
            }
        }

        public void startGame(String characterName) {
            if (characterName.equalsIgnoreCase("emily")) {
                characterDescription = emilyDescription;
            } else if (characterName.equalsIgnoreCase("william")) {
                characterDescription = williamDescription;
            } else if (characterName.equalsIgnoreCase("annie")) {
                characterDescription = annieDescription;
            } else if (characterName.equalsIgnoreCase("chris")) {
                characterDescription = chrisDescription;
            }
            JOptionPane.showMessageDialog(null, "You have chosen " + characterDescription);
            int startGame = JOptionPane.showConfirmDialog(null, "Do you want to play as " + characterName + "?", "Start Game?", JOptionPane.YES_NO_OPTION);
            if (startGame == JOptionPane.YES_OPTION) {

                try {

                    FileWriter writer = new FileWriter("src/untitled/game/savegamefiles/save" + fileNumber);
                    writer.write(characterName + " World 1-1");
                    writer.flush();

                    game = new UntitledGame(characterName, frame, "1-1", "no theme");
                    mainPanel.add(game);
                    mainPanel.remove(newGameCharacterSelection);

                    frame.validate();
                    frame.repaint();
                } catch (IOException e) {

                }

            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }

    public class SaveMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }

    }

    public class LoadMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            JFileChooser fileLoad = new JFileChooser();
            fileLoad.showOpenDialog(frame);
            loadFile(fileLoad.getSelectedFile());
        }

    }

    public class ReturnToMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            mainPanel.remove(loadScreen);
            mainPanel.remove(worldMap);
            mainPanel.remove(game);
            mainPanel.add(menu);
            frame.validate();
            frame.repaint();
            menuWhere = NEW_GAME;
            menuIconX = NEW_GAME_X;
            menuIconY = NEW_GAME_Y;
        }

    }

    public class ExitMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");

            if (exit == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

    }

    private void saveFile(File file) {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("bomberman " + "/");
            writer.write(p.getX() + "/");
            writer.write(p.getY() + "/");
            writer.write(p.getLeftInts() + "\n");
            writer.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void loadFile(File file) {
        String character;
        int xLocation = -1;
        int yLocation = -1;
        int image = -1;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String stuffThatWasRead = null;

            while ((stuffThatWasRead = reader.readLine()) != null) {

                String[] input = stuffThatWasRead.split("/");
                character = input[0];
                xLocation = Integer.parseInt(input[1]);
                yLocation = Integer.parseInt(input[2]);
                image = Integer.parseInt(input[3]);

            }
            reader.close();
            if (xLocation > -1 && yLocation > -1 && image > -1) {
                p = new Player(xLocation, yLocation, image);
            } else {
                JOptionPane.showMessageDialog(null, "File could not be read");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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

}
