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
public class DarkSuit extends Entity{
    
    public DarkSuit(int x, int y){
        this.x = x;
        this.y = y;
        rightImage = new ImageIcon(getClass().getResource(picURL + "darkright.png"));
        rightImageMove = new ImageIcon(getClass().getResource(picURL + "darkrightmove1.png"));
        rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "darkrightmove2.png"));
        rightImageMove3 = new ImageIcon(getClass().getResource(picURL + "darkrightmove3.png"));
        rightImageMove4 = new ImageIcon(getClass().getResource(picURL + "darkrightmove4.png"));
        rightImageMove5 = new ImageIcon(getClass().getResource(picURL + "darkrightmove5.png"));
        rightImageMove6 = new ImageIcon(getClass().getResource(picURL + "darkrightmove6.png"));
        rightImageMove7 = new ImageIcon(getClass().getResource(picURL + "darkrightmove7.png"));
        rightImageMove8 = new ImageIcon(getClass().getResource(picURL + "darkrightmove8.png"));
        rightImageMove9 = new ImageIcon(getClass().getResource(picURL + "darkrightmove9.png"));
        rightImageMove10 = new ImageIcon(getClass().getResource(picURL + "darkrightmove10.png"));
        
        leftImage = new ImageIcon(getClass().getResource(picURL + "darkleft.png"));
        leftImageMove = new ImageIcon(getClass().getResource(picURL + "darkleftmove1.png"));
        leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "darkleftmove2.png"));
        leftImageMove3 = new ImageIcon(getClass().getResource(picURL + "darkleftmove3.png"));
        leftImageMove4 = new ImageIcon(getClass().getResource(picURL + "darkleftmove4.png"));
        leftImageMove5 = new ImageIcon(getClass().getResource(picURL + "darkleftmove5.png"));
        leftImageMove6 = new ImageIcon(getClass().getResource(picURL + "darkleftmove6.png"));
        leftImageMove7 = new ImageIcon(getClass().getResource(picURL + "darkleftmove7.png"));
        leftImageMove8 = new ImageIcon(getClass().getResource(picURL + "darkleftmove8.png"));
        leftImageMove9 = new ImageIcon(getClass().getResource(picURL + "darkleftmove9.png"));
        leftImageMove10 = new ImageIcon(getClass().getResource(picURL + "darkleftmove10.png"));
        
        currentImage = rightImage.getImage();
        
    }
    
    @Override
    public void goRight(){
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
            rightInts = 0;
        }

        rightInts++;
        x ++;
    }
    
    @Override
    public void goLeft(){
        if (leftInts % 10 == 0) {

            currentImage = leftImageMove.getImage();
            
        } else if (leftInts % 10 == 1) {
            currentImage = leftImageMove2.getImage();
            
        } else if (leftInts % 10 == 2) {
            currentImage = leftImageMove3.getImage();
        }
         else if (leftInts % 10 == 3) {
            currentImage = leftImageMove4.getImage();
        }
         else if (leftInts % 10 == 4) {
            currentImage = leftImageMove5.getImage();
        }
         else if (leftInts % 10 == 5) {
            currentImage = leftImageMove6.getImage();
        }
         else if (leftInts % 10 == 6) {
            currentImage = leftImageMove7.getImage();
        }
         else if (leftInts % 10 == 7) {
            currentImage = leftImageMove8.getImage();
        }
         else if (leftInts % 10 == 8) {
            currentImage = leftImageMove9.getImage();
        }
         else if (leftInts % 10 == 9) {
            currentImage = leftImageMove10.getImage();
            leftInts = 0;
        }
        x--;
    }
    
}
