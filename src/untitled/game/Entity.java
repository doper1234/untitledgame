/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Chris
 */
public class Entity {
    String picURL = "sprites/";
    protected String name;
    protected int x;
    protected int y;
    int leftInts = 0;
    int rightInts = 0;
    private int downInts = 0;
    protected int directionFacing = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    final int DOWN = 3;
    final int UP = 4;
    final int NOT_MOVING = 0;
    
    final int GAME_WIDTH = 450;
    final int GAME_HEIGHT = 520;
    int backgroundX = 450;
    int backgroundX2 = 0;
    
    int jumpStartY;
    
    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;
    private boolean playerIsJumping = false;
    private boolean reachedBoundsMin = false;
    private boolean reachedBoundsMax = false;
    private boolean jumpStart = false;
            
    
    ImageIcon leftImage;
    ImageIcon leftImageMove;
    ImageIcon leftImageMove2;
    ImageIcon leftImageMove3;
    ImageIcon leftImageMove4;
    ImageIcon leftImageMove5;
    ImageIcon leftImageMove6;
    ImageIcon leftImageMove7;
    ImageIcon leftImageMove8;
    ImageIcon leftImageMove9;
    ImageIcon leftImageMove10;
    ImageIcon rightImage;
    ImageIcon rightImageMove;
    ImageIcon rightImageMove2;
    ImageIcon rightImageMove3;
    ImageIcon rightImageMove4;
    ImageIcon rightImageMove5;
    ImageIcon rightImageMove6;
    ImageIcon rightImageMove7;
    ImageIcon rightImageMove8;
    ImageIcon rightImageMove9;
    ImageIcon rightImageMove10;
    ImageIcon downImage;
    ImageIcon downImageMove;
    ImageIcon downImageMove2;
    Image currentImage; 
    
    public void goLeft() {

        directionFacing = LEFT;
        if (leftInts % 3 == 1) {

            currentImage = leftImage.getImage();
            
        } else if (leftInts % 3 == 0) {
            currentImage = leftImageMove2.getImage();
            
        } else if (leftInts % 3 == 2) {
            currentImage = leftImageMove.getImage();
            
        }
        leftInts++;
        if(x > 3){
           x -= 3; 
           //backgroundX-=3;
           reachedBoundsMin = false;
        }
        else{
            reachedBoundsMin = true;
        }
            
        isMovingLeft = true;
        
        
    }
    
    public void jump(){
        playerIsJumping = true;
        jumpStart = true;
        y--;
        
    }
    
    public void fall(){
        if(playerIsJumping == false){
        
            y = y + 10;
        }
        
    }
    
    public void goLeftMap(){
        directionFacing = LEFT;
        if (leftInts % 3 == 1) {

            currentImage = leftImage.getImage();

        } else if (leftInts % 3 == 0) {
            currentImage = leftImageMove2.getImage();

        } else if (leftInts % 3 == 2) {
            currentImage = leftImageMove.getImage();

        }
        leftInts++;

        x--;

        isMovingLeft = true;
    }

    public void goRight() {

        directionFacing = RIGHT;
        if (rightInts % 3 == 1) {

            currentImage = rightImage.getImage();
            
        } else if (rightInts % 3 == 0) {
            currentImage = rightImageMove2.getImage();
            
        } else if (rightInts % 3 == 2) {
            currentImage = rightImageMove.getImage();
            
        }
        rightInts++;
        //if(x < GAME_WIDTH){
        
            x += 3;
            reachedBoundsMax = false;
            //backgroundX+=3;
        //}
//        else{
//            reachedBoundsMax = true;
//        }
        isMovingRight = true;
        
        
    }
    
    public void goRightMap() {
        directionFacing = RIGHT;
        if (rightInts % 60 == 20) {

            currentImage = rightImage.getImage();

        } else if (rightInts % 60 == 40) {
            currentImage = rightImageMove2.getImage();

        } else if (rightInts % 60 == 0) {
            currentImage = rightImageMove.getImage();

        }
        rightInts++;

        x++;

        isMovingRight = true;
    }
    
    public void goRight(int velocity) {

        directionFacing = RIGHT;
        if (rightInts % 2 == 1) {

            currentImage = rightImage.getImage();
            
        }
        else if (rightInts % 2 == 0) {
            currentImage = rightImageMove.getImage();
            
        }
        rightInts++;
        x  = x +(1* velocity);
        
    }
    
    public void goLeft(int velocity) {

        directionFacing = LEFT;
        if (leftInts % 2 == 1) {

            currentImage = leftImage.getImage();
            
        }
        else if (leftInts % 2 == 0) {
            currentImage = leftImageMove.getImage();
            
        }
        leftInts++;
        x  = x -(1* velocity);
        
    }

    public void goUp() {

        if(jumpStart == true){
          
            jumpStartY = y;
            jumpStart = false;
        }
        if((y >= (jumpStartY - 200))){
          
            y = y - 5;
        }
        else{
            playerIsJumping = false;
        }
        
        
        
    }

    public void goDown() {

        directionFacing = DOWN;
        if (downInts % 40 == 0) {

            currentImage = downImageMove.getImage();
            
        } else if (downInts % 40 == 20) {
            currentImage = downImageMove2.getImage();
            
        } 
        
        isMovingLeft = false;
        isMovingRight = false;
//        else if (downInts % 3 == 2) {
//            currentImage = downImage.getImage();
//            
//        }
        
        downInts++;
    }
    
    public void stop(){
        if(directionFacing == LEFT){
            currentImage = leftImage.getImage();
        }
        else if(directionFacing == RIGHT){
            currentImage = rightImage.getImage();
        }
        isMovingLeft = false;
        isMovingRight = false;
        //playerIsJumping = false;
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirectionFacing() {
        return directionFacing;
    }

    public void setDirectionFacing(int directionFacing) {
        this.directionFacing = directionFacing;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image image) {
        this.currentImage = image;
    }
    
    public int getLeftInts(){
        return leftInts;
    }
    
    public boolean isPlayerMovingLeft(){
        return isMovingLeft;
    }
    public boolean isPlayerMovingRight(){
        return isMovingRight;
    }
    
    public boolean isPlayerJumping(){
        return playerIsJumping;
    }
    public boolean playerStoppedMoving(){
        
        return isMovingLeft == false && isMovingRight == false;
    }
    public boolean playerHasReachedBoundsMin(){
        isMovingLeft = false;
        isMovingRight = false;
        return reachedBoundsMin;
    }
    public boolean playerHasReachedBoundsMax(){
        isMovingLeft = false;
        isMovingRight = false;
        return reachedBoundsMax;
    }
    
    public String getName(){
        
        return name;
    }
    
    public Rectangle getBounds(){
        
        return new Rectangle(x,y,this.currentImage.getWidth(null), this.currentImage.getHeight(null));
    }
}
