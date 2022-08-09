package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

public class Minesweeper extends AnimationPanel {
    
    Game game;
    ArrayList<Boolean> keys = new ArrayList<>();
    
    public Minesweeper() {   //Enter the name and width and height.  
        super("Minesweeper", 600, 600);
        game = new Game(10,10,15);
        game.setImages(ballImage,starImage);
        for(int i = 0; i < 500; i++){
            keys.add(false);
        }
    }

    protected void renderFrame(Graphics g) {
        game.showGrid(g, this, 50);
    }
    public void mouseClicked(MouseEvent e) {
        game.click(mouseX,mouseY,keys.get(KeyEvent.VK_SHIFT), 50);
    }

    public void keyPressed(KeyEvent e) {
        keys.set(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        keys.set(e.getKeyCode(), false);
    }

    //-------------------------------------------------------
    //Initialize Graphics
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Image section... 
     *  To add a new image to the program, do three things.
     *  1.  Make a declaration of the Image by name ...  Image imagename;
     *  2.  Actually make the image and store it in the same directory as the code.
     *  3.  Add a line into the initGraphics() function to load the file. 
     //-----------------------------------------------------------------------*/
    Image ballImage;
    Image starImage;

    public void initGraphics() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        ballImage = toolkit.getImage("src/images/ball.gif");
        starImage = toolkit.getImage("src/images/star.gif");
    } //--end of initGraphics()--

    //-------------------------------------------------------
    //Initialize Sounds
    //-------------------------------------------------------
//-----------------------------------------------------------------------
/*  Music section... 
     *  To add music clips to the program, do four things.
     *  1.  Make a declaration of the AudioClip by name ...  AudioClip clipname;
     *  2.  Actually make/get the .wav file and store it in the same directory as the code.
     *  3.  Add a line into the initMusic() function to load the clip. 
     *  4.  Use the play(), stop() and loop() functions as needed in your code.
     //-----------------------------------------------------------------------*/
    Clip themeMusic;
    Clip bellSound;

    public void initMusic() {
        themeMusic = loadClip("src/audio/under.wav");
        bellSound = loadClip("src/audio/ding.wav");
        if (themeMusic != null) //            themeMusic.start(); //This would make it play once!
        {
            //themeMusic.loop(2); //This would make it loop 2 times.
        }
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
