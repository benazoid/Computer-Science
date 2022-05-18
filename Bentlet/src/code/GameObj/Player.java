package code.GameObj;

import code.Camera;
import code.Projectile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Player extends Moveable {
    
    public Player(Point p, int mod) {
        super(p, mod);
    }

    public void show(Graphics g, Image i, ImageObserver io) {
        g.setColor(Color.blue);
        int s = Camera.getSize() * 3 / 4;
        g.fillRect(getShow().x, getShow().y, s, s);
        g.drawRect(getShow().x, getShow().y, s, s);
        g.drawString("Health: "+ (getHealth()), s, s);
        g.drawString("Gold: "+ (getGold()), s+200, s);
        g.drawString("Keys: "+ (getKeys()), s+400, s);
    }

    public void move(ArrayList<Boolean> keys) {
        if(keys.get(KeyEvent.VK_W)){
            setLoc(getLoc().x, getLoc().y - 5);
        }
        if(keys.get(KeyEvent.VK_A)){
            setLoc(getLoc().x - 5, getLoc().y);
        }
        if(keys.get(KeyEvent.VK_S)){
            setLoc(getLoc().x, getLoc().y + 5);
        }
        if(keys.get(KeyEvent.VK_D)){
            setLoc(getLoc().x + 5, getLoc().y);
        }
        
        if (keys.get(KeyEvent.VK_W)) {
            setDirection(3);
            if(keys.get(KeyEvent.VK_A)){
                setDirection(2.5);
                return;
            }
        }
        if (keys.get(KeyEvent.VK_A)) {
            setDirection(2);
            if(keys.get(KeyEvent.VK_S)){
                setDirection(1.5);
                return;
            }
        }
        if (keys.get(KeyEvent.VK_S)) {
            setDirection(1);
            if(keys.get(KeyEvent.VK_D)){
                setDirection(0.5);
                return;
            }
        }
        if (keys.get(KeyEvent.VK_D)) {
            setDirection(0);
            if(keys.get(KeyEvent.VK_W)){
                setDirection(3.5);
            }
        }
    }
    
    public void shoot(){
        Projectile.addProj(new Point(getLoc().x+8,getLoc().y), getDirection(), this);
    }
}
