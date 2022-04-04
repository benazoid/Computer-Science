package code;

/**
 * Class ArcadeDemo This class contains demos of many of the things you might
 * want to use to make an animated arcade game.
 *
 * Adapted from the AppletAE demo from years past.
 */

import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BentletGame extends AnimationPanel {

    //Constants
    //-------------------------------------------------------
    //Instance Variables
    //-------------------------------------------------------
    
    ArrayList<Point> psList = new ArrayList<>();
    
    ArrayList<BufferedImage> mapImages = new ArrayList<>();
    
    ArrayList<Boolean> input = new ArrayList<>();
    
    int level = 0;
    
    Map lvl0 = new Map(0);
    
    //Constructor
    //-------------------------------------------------------
    public BentletGame() {   //Enter the name and width and height.  
        super("ArcadeDemo", 560, 700);
        
        for(int i = 0; i < 200; i++){
            input.add(false);
        }
    }

    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected void renderFrame(Graphics g) {
        
        lvl0.drawMap(g,this);
        
    }//--end of renderFrame method--

    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e) {
        
    }

    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------

    public void keyPressed(KeyEvent e) {
        int v = e.getKeyCode();

        while(input.size() < v){
            input.add(false);
        }
        input.set(v, true);
    }

    public void keyReleased(KeyEvent e) {
        int v = e.getKeyCode();
        input.set(v, false);
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
    

    public void initGraphics() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        File file1 = new File("src/images/Map1.png");
        BufferedImage bf = null;
        try {
            bf = (ImageIO.read(file1));
        } catch (IOException ex) {
            Logger.getLogger(BentletGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    } //--end of initGraphics()--

    public static void tint(Image img) {

    }

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
            themeMusic.loop(2); //This would make it loop 2 times.
        }
    }
    
    
    
    public static double findAng(double x1, double y1, double x2, double y2) {
        double angle = (Math.atan2(x2 - x1, y2 - y1));
        // Keep angle between 0 and 360
        angle = angle + Math.ceil(-angle / Math.PI * 2) * Math.PI*2;

        return -angle + Math.PI/2;
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

