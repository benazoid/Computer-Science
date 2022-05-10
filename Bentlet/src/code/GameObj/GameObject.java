package code.GameObj;

import code.Camera;
import code.Camera;
import code.Map;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class GameObject{

    private Point loc = new Point(0,0);
    private int type;
    private Point showPoint = new Point();
    private int health = 100;
    private int keys = 0;
    private int gold = 0;
    
    public GameObject(Point p, int mod){
        loc = p;
        type = mod;
    }
    
    //Override functions
    public void show(Graphics g, Image i, ImageObserver io){}
    public void move(ArrayList<Boolean> keys){}
    public void shoot(){}
    
    //Getters and setters
    public Point getLoc(){return loc;}
    public void setLoc(int x, int y){loc = new Point(x,y);}
    public Point getShow(){return showPoint;}
    public Rectangle getRect(){
        return new Rectangle(loc.x,loc.y,32,32);
    }
    public int getType(){
        return type;
    }
    
    //Other fun stuff
    public void updateCam(Map m){
        showPoint.x = loc.x - Camera.getLoc().x + 560/2;
        showPoint.y = loc.y - Camera.getLoc().y + 700/2;
    }

    public void collide(GameObject gObj){}
    
    public void changeHealth(int c){
        health += c;
    }
    public void changeKeys(int c){
        keys += c;
    }
    public void changeGold(int c){
        gold += c;
    }
    public int getKeys(){
        return keys;
    }
    public int getGold(){
        return gold;
    }
    public int getHealth(){
        return health;
    }
}