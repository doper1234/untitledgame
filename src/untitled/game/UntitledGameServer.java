/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Chris
 */
public class UntitledGameServer {
    
    ArrayList playerOutputStreams;
    PrintWriter player1OutputStream;
    PrintWriter player2OutputStream;
    ArrayList playerReaders;
    BufferedReader player1Reader;
    BufferedReader player2Reader;
    
    int whatIsPlayer1Doing;
    int whatIsPlayer2Doing;
    final int STOPPED_MOVING = 0;
    final int GOING_LEFT = 1;
    final int GOING_RIGHT = 2;
    final int SHOOTING = 3;
    
    public static void main(String args[]){
        new UntitledGameServer().setUpGameServer();
    }
    
    public void setUpGameServer() {

        playerReaders = new ArrayList();
        playerOutputStreams = new ArrayList();
        try {
           ServerSocket serverSock = new ServerSocket(3074);
            
            
            while (true) {
                if (playerOutputStreams.size() < 2) {
                    Socket clientSocket = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    playerOutputStreams.add(writer);

                    Thread t = new Thread(new ClientHandler((clientSocket)));
                    t.start();
                    System.out.println("got a connection from " + clientSocket.getInetAddress());
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public class ClientHandler implements Runnable {

        Socket sock;
        InputStream inStream;
        BufferedReader reader;
        

        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
                playerReaders.add(reader);
                if(playerReaders.size() == 2){
                    player1Reader = (BufferedReader) playerReaders.get(0);
                    player2Reader = (BufferedReader) playerReaders.get(1);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        public void run() {
            
            String message;
            try {

                while ((message = reader.readLine()) != null) {
                    System.out.println("Message in: " + message);
                    if(reader == player1Reader){
                        System.out.println("player 1 did something");
                        
                        try{
                            whatIsPlayer1Doing = Integer.parseInt(message);
                            updatePlayer1Location();
                        } catch(NumberFormatException ex){
                            
                        }
                        
                    }
                    if(reader == player2Reader){
                        System.out.println("player 2 did something");
                        try{
                          whatIsPlayer2Doing = Integer.parseInt(message);
                        updatePlayer2Location();  
                        }catch(NumberFormatException ex){
                            
                        }
                        
                    }
                    

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void updatePlayer1Location(){
        if (playerOutputStreams.size() == 2) {
            player2OutputStream = (PrintWriter) playerOutputStreams.get(1);
            player2OutputStream.println(whatIsPlayer1Doing);
            player2OutputStream.flush();
            
        }
          
    }
    
    public void updatePlayer2Location(){
        if(playerOutputStreams.size() == 2){
          
            player1OutputStream = (PrintWriter) playerOutputStreams.get(0);
            player1OutputStream.println(whatIsPlayer2Doing);
            player1OutputStream.flush();
        }
            
    }
    
    
    
}
