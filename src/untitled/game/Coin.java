/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import javax.swing.ImageIcon;

/**
 *
 * @author Chris
 */
public class Coin extends Entity{
    
    public Coin(int x, int y){
        
        rightImageMove = new ImageIcon(getClass().getResource(picURL + "coin1.png"));
        rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "coin2.png"));
        rightImageMove3 = new ImageIcon(getClass().getResource(picURL + "coin3.png"));
        rightImageMove4 = new ImageIcon(getClass().getResource(picURL + "coin4.png"));
        rightImageMove5 = new ImageIcon(getClass().getResource(picURL + "coin5.png"));
        rightImageMove6 = new ImageIcon(getClass().getResource(picURL + "coin6.png"));
        rightImageMove7 = new ImageIcon(getClass().getResource(picURL + "coin7.png"));
        rightImageMove8 = new ImageIcon(getClass().getResource(picURL + "coin8.png"));
        rightImageMove9 = new ImageIcon(getClass().getResource(picURL + "coin9.png"));
        rightImageMove10 = new ImageIcon(getClass().getResource(picURL + "coin10.png"));
        currentImage = rightImageMove.getImage();
    }
    
    public void rotate(){
        if (rightInts % 10 == 0) {

            currentImage = rightImageMove.getImage();
            
        } else if (rightInts % 10 == 1) {
            currentImage = rightImageMove2.getImage();
            
        } else if (rightInts % 10 == 2) {
            currentImage = rightImageMove3.getImage();
        }
         else if (rightInts % 10 == 3) {
            currentImage = rightImageMove4.getImage();
        }
         else if (rightInts % 10 == 4) {
            currentImage = rightImageMove5.getImage();
        }
         else if (rightInts % 10 == 5) {
            currentImage = rightImageMove6.getImage();
        }
         else if (rightInts % 10 == 6) {
            currentImage = rightImageMove7.getImage();
        }
         else if (rightInts % 10 == 7) {
            currentImage = rightImageMove8.getImage();
        }
         else if (rightInts % 10 == 8) {
            currentImage = rightImageMove9.getImage();
        }
         else if (rightInts % 10 == 9) {
            currentImage = rightImageMove10.getImage();
        }

        rightInts++;
    
    }
}
