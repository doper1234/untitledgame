/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Chris
 */
public class PowerUp extends Entity{
    
    Image willFace = new ImageIcon(getClass().getResource(picURL + "williamheadfront48.png")).getImage();
    Image milkFace = new ImageIcon(getClass().getResource(picURL + "emilyheadfront48.png")).getImage();
    Image mommyFace = new ImageIcon(getClass().getResource(picURL + "annieheadfront48.png")).getImage();
    Image myFace = new ImageIcon(getClass().getResource(picURL + "chrisheadfront48.png")).getImage();
    Image dustyFace = new ImageIcon(getClass().getResource(picURL + "dustyheadfront48.png")).getImage();
    
    public PowerUp(String name, int x, int y){
        this.x = x;
        this.y = y;
        if(name.equalsIgnoreCase("will")){
          currentImage = willFace;  
        } else if(name.equalsIgnoreCase("milk")){
            currentImage = milkFace;
        } else if (name.equalsIgnoreCase("mommy")){
            currentImage = mommyFace;
        } else if (name.equalsIgnoreCase("me")){
            currentImage = myFace;
        }else{
            currentImage = dustyFace;
        }
        
    }
    
    
}
