package code;

/**
 * Class ArcadeDemo This class contains demos of many of the things you might
 * want to use to make an animated arcade game.
 *
 * Adapted from the AppletAE demo from years past.
 */

import code.GameObj.GameObject;
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

public class BentletGame extends AnimationPanel {

    //Constants
    //-------------------------------------------------------
    //Instance Variables
    //-------------------------------------------------------
    private ArrayList<Point> psList = new ArrayList<>();
    private ArrayList<BufferedImage> mapImages = new ArrayList<>();
    private ArrayList<Boolean> input = new ArrayList<>();
    
    
    private int level = 0;
    private Map[] maps = {new Map(0)};
    

    //Constructor
    //-------------------------------------------------------
    public BentletGame() {   //Enter the name and width and height.  
        super("ArcadeDemo", 560, 700);

        for (int i = 0; i < 200; i++) {
            input.add(false);
        }
                
    }
 
    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected void renderFrame(Graphics g) {

        int num = getMap().drawMap(g, this, input);
        Projectile.updateAll(getMap(), g);
        
        switch(num){
            case(1):
                if(level == maps.length-1)
                    break;
                level++;
                maps[level] = new Map(level);
            case(-1):
                maps[level] = new Map(0);
        }
        
    }//--end of renderFrame method--

    public double getDist(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public Map getMap(){
        return maps[level];
    }
    
    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e) {
        maps[level].getPlayer().shoot();
    }

    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------
    public void keyTyped(KeyEvent e){
        //System.out.println(e.toString());
    }
    
    public void keyPressed(KeyEvent e) {
        int v = e.getKeyCode();

        while (input.size() < v) {
            input.add(false);
        }
        input.set(v, true);
    }

    public void keyReleased(KeyEvent e) {
        int v = e.getKeyCode();
        input.set(v, false);
        if(e.getKeyCode() == KeyEvent.VK_K)
            maps[level].getPlayer().shoot();
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
        angle = angle + Math.ceil(-angle / Math.PI * 2) * Math.PI * 2;

        return -angle + Math.PI / 2;
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

