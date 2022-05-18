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

public class BentletGame extends AnimationPanel {

    //Constants
    //-------------------------------------------------------
    //Instance Variables
    //-------------------------------------------------------
    private ArrayList<Point> psList = new ArrayList<>();
    private ArrayList<BufferedImage> mapImages = new ArrayList<>();
    private ArrayList<Boolean> input = new ArrayList<>();
    private int plH;
    private int level = 0;
    private Map[] maps = {new Map(0, 100), new Map(1, 100), new Map(2, 100)};
    private int mode = 1;

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

        if (mode == 1) {
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
            g.drawString("Bentlet", 200, 100);
            g.setColor(Color.red);
            g.fillRect(175, 200, 200, 100);
            g.setColor(Color.black);
            g.drawString("START", 205, 260);
        }
        if (mode == 2) {
            Map mapNum = getMap();
            int num = 0;
            if (mode == 2) {
                num = mapNum.drawMap(g, this, input);
                switch (num) {
                    case (1):
                        level++;
                        if (level > maps.length - 1) {
                            mode = 4;
                            break;
                        }

                        maps[level] = new Map(level, plH);
                        break;
                    case (-1):
                        level = 0;
                        maps[level] = new Map(0, plH);
                }
                g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
                Projectile.updateAll(getMap(), g);
                plH = maps[level].getPlayer().getHealth();
            }
        }

        if (mode == 3) {
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
        }

        if (mode == 4) {
            g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
            g.drawString("You Won!", 200, 300);
        }
    }//--end of renderFrame method--

    public double getDist(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public Map getMap() {
        if (maps.length > level) {
            return maps[level];
        }
        mode = 4;
        return null;
    }

    //-------------------------------------------------------
    //Respond to Mouse Events
    //-------------------------------------------------------
    public void mouseClicked(MouseEvent e) {
        if (level < maps.length) {
            maps[level].getPlayer().shoot();
        }

        if (new Rectangle(175, 200, 200, 100).contains(new Point(mouseX, mouseY)) && mode == 1) {
            mode = 2;
        }
    }

    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------
    public void keyTyped(KeyEvent e) {

    }

    //Makes a list of key input
    public void keyPressed(KeyEvent e) {
        int v = e.getKeyCode();

        while (input.size() < v) {
            input.add(false);
        }
        input.set(v, true);
    }
    
    //List of cheat codes
    int[] code1 = {KeyEvent.VK_P, KeyEvent.VK_O, KeyEvent.VK_O, KeyEvent.VK_P};
    int n1 = 0;
    int[] code2 = {KeyEvent.VK_P, KeyEvent.VK_E, KeyEvent.VK_E};
    int n2 = 0;
    int[] code3 = {KeyEvent.VK_F, KeyEvent.VK_A, KeyEvent.VK_R, KeyEvent.VK_T};
    int n3 = 0;

    public void keyReleased(KeyEvent e) {
        int v = e.getKeyCode();
        input.set(v, false);
        if (e.getKeyCode() == KeyEvent.VK_K) {
            maps[level].getPlayer().shoot();
        }

        if (level < maps.length) {
            if (e.getKeyCode() == code1[n1]) {
                n1++;
            } else {
                n1 = 0;
            }
            if (n1 > code1.length - 1) {
                maps[level].cheat(1);
                n1 = 0;
            }

            if (e.getKeyCode() == code2[n2]) {
                n2++;
            } else {
                n2 = 0;
            }
            if (n2 > code2.length - 1) {
                maps[level].cheat(2);
                n2 = 0;
            }

            if (e.getKeyCode() == code3[n3]) {
                n3++;
            } else {
                n3 = 0;
            }
            if (n3 > code3.length - 1) {
                level++;
                if (level > maps.length - 1) {
                    return;
                }
                maps[level] = new Map(level, plH);
                n3 = 0;
            }
        }

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

