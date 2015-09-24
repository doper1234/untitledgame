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
public class ChargedShot extends Projectile{
    
    
    
    public ChargedShot(int x, int y, int direction){
        this.x = x;
        this.y = y;
        this.directionFacing = direction;
        rightImage = new ImageIcon(getClass().getResource(picURL + "chargedshot1.png"));
        rightImageMove = new ImageIcon(getClass().getResource(picURL + "chargedshot2.png"));
        rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "chargedshot3.png"));
        if(direction == LEFT){
            currentImage = leftImage.getImage();
        }
        if(direction == RIGHT){
            currentImage = rightImage.getImage();
        }
        
        
    }
    
}
