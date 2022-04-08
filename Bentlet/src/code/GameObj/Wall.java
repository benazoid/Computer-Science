package code.GameObj;

import code.Camera;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class Wall extends GameObject{

    public Wall(Point p, int mod) {
        super(p, mod);
    }
    
    public void show(Graphics g, Image i, ImageObserver io){
        g.drawRect(getShow().x, getShow().y, Camera.getSize(), Camera.getSize());
    }
    
}
