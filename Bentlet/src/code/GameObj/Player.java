package code.GameObj;

import code.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Player extends Moveable{

    public Player(Point p, int mod) {
        super(p, mod);
    }

    public void show(Graphics g, Image i, ImageObserver io){
        g.setColor(Color.blue);
        int s = Camera.getSize()*3/4;
        g.fillRect(getShow().x,getShow().y,s,s);
        g.drawRect(getShow().x,getShow().y,s,s);
    }
    
    public void move(ArrayList<Boolean> keys){
        if(keys.get(KeyEvent.VK_W)){
            this.setLoc(getLoc().x, getLoc().y - 5);
        }
        if(keys.get(KeyEvent.VK_A)){
            this.setLoc(getLoc().x - 5, getLoc().y);
        }
        if(keys.get(KeyEvent.VK_S)){
            this.setLoc(getLoc().x, getLoc().y + 5);
        }
        if(keys.get(KeyEvent.VK_D)){
            this.setLoc(getLoc().x + 5, getLoc().y);
        }
    }
    
        public Rectangle getRect(){
            return new Rectangle(getLoc().x,getLoc().y,24,24);
        }
}
