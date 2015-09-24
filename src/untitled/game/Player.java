/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untitled.game;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Chris
 */
public class Player extends Entity {

    public Player(int bomberManX, int bomberManY) {
        this.x = bomberManX;
        this.y = bomberManY;
        leftImage = new ImageIcon(getClass().getResource(picURL + "bombermanleftone.png"));
        leftImageMove = new ImageIcon(getClass().getResource(picURL + "bombermanlefttwo.png"));
        leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "bombermanleftthree.png"));
        rightImage = new ImageIcon(getClass().getResource(picURL + "bombermanrightone.png"));
        rightImageMove = new ImageIcon(getClass().getResource(picURL + "bombermanrighttwo.png"));
        rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "bombermanrightthree.png"));
        downImage = new ImageIcon(getClass().getResource("sprites/bombermanforewardone.png"));
        downImageMove = new ImageIcon(getClass().getResource("sprites/bombermanforewardtwo.png"));
        downImageMove2 = new ImageIcon(getClass().getResource("sprites/bombermanforewardthree.png"));
        currentImage = leftImage.getImage();

    }

    public Player(int bomberManX, int bomberManY, int directionFacing) {
        this.x = bomberManX;
        this.y = bomberManY;
        this.directionFacing = directionFacing;
        leftImage = new ImageIcon(getClass().getResource(picURL + "bombermanleftone.png"));
        leftImageMove = new ImageIcon(getClass().getResource(picURL + "bombermanlefttwo.png"));
        leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "bombermanleftthree.png"));
        rightImage = new ImageIcon(getClass().getResource(picURL + "bombermanrightone.png"));
        rightImageMove = new ImageIcon(getClass().getResource(picURL + "bombermanrighttwo.png"));
        rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "bombermanrightthree.png"));
        downImage = new ImageIcon(getClass().getResource("sprites/bombermanforewardone.png"));
        downImageMove = new ImageIcon(getClass().getResource("sprites/bombermanforewardtwo.png"));
        downImageMove2 = new ImageIcon(getClass().getResource("sprites/bombermanforewardthree.png"));
        if (directionFacing == DOWN) {

            currentImage = downImage.getImage();
        } else if (directionFacing == LEFT) {
            currentImage = leftImage.getImage();
        } else if (directionFacing == RIGHT) {
            currentImage = rightImage.getImage();
        }

    }

    public Player(int bomberManX, int bomberManY, int directionFacing, String name) {
        this.x = bomberManX;
        this.y = bomberManY;
        this.directionFacing = directionFacing;

        if (name.equalsIgnoreCase("william")) {
            leftImage = new ImageIcon(getClass().getResource(picURL + "williamermanleftone.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "williamermanlefttwo.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "williamermanleftthree.png"));
            rightImage = new ImageIcon(getClass().getResource(picURL + "williamermanrightone.png"));
            rightImageMove = new ImageIcon(getClass().getResource(picURL + "williamermanrighttwo.png"));
            rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "williamermanrightthree.png"));
            downImage = new ImageIcon(getClass().getResource("sprites/williamermanforewardone.png"));
            downImageMove = new ImageIcon(getClass().getResource("sprites/williamermanforewardtwo.png"));
            downImageMove2 = new ImageIcon(getClass().getResource("sprites/williamermanforewardthree.png"));

        } else if (name.equalsIgnoreCase("emily")) {
            leftImage = new ImageIcon(getClass().getResource(picURL + "emilyermanleftone.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "emilyermanlefttwo.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "emilyermanleftthree.png"));
            rightImage = new ImageIcon(getClass().getResource(picURL + "emilyermanrightone.png"));
            rightImageMove = new ImageIcon(getClass().getResource(picURL + "emilyermanrighttwo.png"));
            rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "emilyermanrightthree.png"));
            downImage = new ImageIcon(getClass().getResource("sprites/emilyermanforewardone.png"));
            downImageMove = new ImageIcon(getClass().getResource("sprites/emilyermanforewardtwo.png"));
            downImageMove2 = new ImageIcon(getClass().getResource("sprites/emilyermanforewardthree.png"));

        } 
        else if(name.equalsIgnoreCase("annie")){
            
            leftImage = new ImageIcon(getClass().getResource(picURL + "annieleftone.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "annielefttwo.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "annieleftthree.png"));
            rightImage = new ImageIcon(getClass().getResource(picURL + "annierightone.png"));
            rightImageMove = new ImageIcon(getClass().getResource(picURL + "annierighttwo.png"));
            rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "annierightthree.png"));
            downImage = new ImageIcon(getClass().getResource("sprites/anniermanforewardone.png"));
            downImageMove = new ImageIcon(getClass().getResource("sprites/anniermanforewardtwo.png"));
            downImageMove2 = new ImageIcon(getClass().getResource("sprites/anniermanforewardthree.png"));
        }
        else if (name.equalsIgnoreCase("chris")) {
            leftImage = new ImageIcon(getClass().getResource(picURL + "chrisermanleftone.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "chrisermanlefttwo.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "chrisermanleftthree.png"));
            rightImage = new ImageIcon(getClass().getResource(picURL + "chrisermanrightone.png"));
            rightImageMove = new ImageIcon(getClass().getResource(picURL + "chrisermanrighttwo.png"));
            rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "chrisermanrightthree.png"));
            downImage = new ImageIcon(getClass().getResource("sprites/chrisermanforewardone.png"));
            downImageMove = new ImageIcon(getClass().getResource("sprites/chrisermanforewardtwo.png"));
            downImageMove2 = new ImageIcon(getClass().getResource("sprites/chrisermanforewardthree.png"));
        } else if (name.equalsIgnoreCase("dusty")) {
            leftImage = new ImageIcon(getClass().getResource(picURL + "dustyleft1.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "dustyleft2.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "dustyleft3.png"));
            rightImage = new ImageIcon(getClass().getResource(picURL + "dustyright1.png"));
            rightImageMove = new ImageIcon(getClass().getResource(picURL + "dustyright2.png"));
            rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "dustyright3.png"));
            downImage = new ImageIcon(getClass().getResource("sprites/bombermanforewardone.png"));
            downImageMove = new ImageIcon(getClass().getResource("sprites/bombermanforewardtwo.png"));
            downImageMove2 = new ImageIcon(getClass().getResource("sprites/bombermanforewardthree.png"));

        } else {
            leftImage = new ImageIcon(getClass().getResource(picURL + "bombermanleftone.png"));
            leftImageMove = new ImageIcon(getClass().getResource(picURL + "bombermanlefttwo.png"));
            leftImageMove2 = new ImageIcon(getClass().getResource(picURL + "bombermanleftthree.png"));
            rightImage = new ImageIcon(getClass().getResource(picURL + "bombermanrightone.png"));
            rightImageMove = new ImageIcon(getClass().getResource(picURL + "bombermanrighttwo.png"));
            rightImageMove2 = new ImageIcon(getClass().getResource(picURL + "bombermanrightthree.png"));
            downImage = new ImageIcon(getClass().getResource("sprites/bombermanforewardone.png"));
            downImageMove = new ImageIcon(getClass().getResource("sprites/bombermanforewardtwo.png"));
            downImageMove2 = new ImageIcon(getClass().getResource("sprites/bombermanforewardthree.png"));
        }

        if (directionFacing == DOWN) {

            currentImage = downImage.getImage();
        } else if (directionFacing == LEFT) {
            currentImage = leftImage.getImage();
        } else if (directionFacing == RIGHT) {
            currentImage = rightImage.getImage();
        }

    }

}
