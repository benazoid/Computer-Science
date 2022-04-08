package code.GameObj;

import code.Camera;
import code.Camera;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class GameObject{

    private Point loc = new Point(0,0);
    private Point showPoint = new Point();
    
    public GameObject(Point p, int mod){
        loc = p;
    }
    
    //Override functions
    public void show(Graphics g, Image i, ImageObserver io){}
    public void move(ArrayList<Boolean> keys){}
    
    //Getters and setters
    public Point getLoc(){return loc;}
    public void setLoc(int x, int y){loc = new Point(x,y);}
    public Point getShow(){return showPoint;}
    
    //Other fun stuff
    public void updateCam(){
        showPoint.x = loc.x - Camera.getLoc().x + 560/2;
        showPoint.y = loc.y - Camera.getLoc().y + 700/2;
    }
    
    public Rectangle getRect(){
        return new Rectangle(loc.x,loc.y,32,32);
    }

    public void collide(GameObject gObj){
        
    }
    
    
}