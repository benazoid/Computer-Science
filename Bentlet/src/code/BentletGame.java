package code;

/**
 * Class ArcadeDemo This class contains demos of many of the things you might
 * want to use to make an animated arcade game.
 *
 * Adapted from the AppletAE demo from years past.
 */
import code.GameObj.GameObject;
import code.GameObj.Wall;
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
import javafx.scene.shape.Line;

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
    
    Map[] maps = {lvl0};

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
        maps[level].drawMap(g,this,input);
        GameObject[][] grid = maps[level].getGrid();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                for(int k = 0; k < maps[level].getMoves().size(); k++){
                    GameObject gObj = grid[i][j];
                    Rectangle rect = new Rectangle(maps[level].getMoves().get(k).getRect());
                    if(gObj instanceof Wall){
                        collideRectRect(gObj.getRect(), rect);
                        maps[level].getMoves().get(k).setLoc(rect.x, rect.y);
                    }
                }
            }
        }
        
    }//--end of renderFrame method--

    private String getColPos(Rectangle r1, Rectangle r2) {
        String s;
        if ((r1.y + r1.height + 1 > r2.y && r2.y + r2.height + 1 > r1.y) && (r1.x + r1.width > r2.x - 1 && r2.x + r2.width + 1 > r1.x) && !r1.equals(r2)) {
            double x1 = (r1.x + Math.floor((r1.width) / 2));
            double y1 = (r1.y + Math.floor((r1.height) / 2));
            double x2 = (r2.x + Math.floor((r2.width) / 2));
            double y2 = (r2.y + Math.floor((r2.height) / 2));
            double distH, distV;
            String dir, horiz, vert;

            if (x1 > x2) {
                horiz = "left";
            } else {
                horiz = "right";
            }

            distH = Math.abs(x1 - x2);
            if (y1 > y2) {
                vert = "top";
            } else {
                vert = "bottom";
            }

            distV = Math.abs(y1 - y2);
            if (distH > distV) {
                return horiz;
            } else {
                return vert;
            }
        }
        return "null";
    }

    public void collideRectRect(Rectangle r1, Rectangle r2){
        Rectangle rect = new Rectangle(r2);
        String loc = getColPos(r1,rect);
        while(loc.equals("top")){
            r2.y-=1;
            loc = getColPos(r1,r2);
        }
        while(loc.equals("bottom")){
            r2.y+=1;
            loc = getColPos(r1,r2);
        }
        while(loc.equals("left")){
            r2.x-=1;
            loc = getColPos(r1,r2);
        }
        while(loc.equals("right")){
            r2.x+=1;
            loc = getColPos(r1,r2);
        }
    }
    
    public double getDist(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

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

        while (input.size() < v) {
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
        angle = angle + Math.ceil(-angle / Math.PI * 2) * Math.PI * 2;

        return -angle + Math.PI / 2;
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}//--end of ArcadeDemo class--

