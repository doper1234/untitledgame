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
public class Boss extends Entity{
    
    
    public Boss(String name, int x, int y){
    
        this.x = x;
        this.y = y;
        if(name.equals("croc")){
          
            leftImage = new ImageIcon(getClass().getResource(picURL + "boss1a.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "boss2a.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "boss3a.png"));
        } else if(name.equals("chris")){
            leftImage = new ImageIcon(getClass().getResource(picURL + "chrisboss1a.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "chrisboss2a.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "chrisboss3a.png"));
            
        } else if(name.equals("metroid")){
            leftImage = new ImageIcon(getClass().getResource(picURL + "metroid1.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "metroid2.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "metroid3.png"));
        }
        
    }
    
    
}
