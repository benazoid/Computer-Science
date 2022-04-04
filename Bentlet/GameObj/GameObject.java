package code.GameObj;

import code.Camera;
import code.Camera;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class GameObject{
    
    private Point loc;
    
    public GameObject(){
        
    }
    
    public void show(Graphics g, Image i, ImageObserver io){
        g.drawImage(i, loc.x - Camera.getLoc().x, loc.y - Camera.getLoc().y, io);
    }
    
    public Point getLoc(){
        return loc;
    }
    
    public void update(){}
    
    public void setLoc(Point p){
        this.loc = p;
    }
}