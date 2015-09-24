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
public class Projectile extends Entity{
    
    
    
    
    public void go(int velocity){
       
       x = x - (1* velocity);
    }
}
