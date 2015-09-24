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
public class Missile extends Projectile{
 
    
    public Missile(int x, int y, int direction){
        this.x = x;
        this.y = y;
        this.directionFacing = direction;
        leftImage = new ImageIcon(getClass().getResource(picURL + "legomissleleft.png"));
        leftImageMove = new ImageIcon(getClass().getResource(picURL + "legomissleleft2.png"));
        rightImage = new ImageIcon(getClass().getResource(picURL + "legomissleright.png"));
        rightImageMove = new ImageIcon(getClass().getResource(picURL + "legomissleright2.png"));
        if(direction == LEFT){
            currentImage = leftImage.getImage();
        }
        else if(direction == RIGHT){
            currentImage = rightImage.getImage();
        }
        
        
    }
    
}
