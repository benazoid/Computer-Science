package code.GameObj;

import code.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class Door extends GameObject{

    public Door(Point p, int mod) {
        super(p, mod);
    }

    public void show(Graphics g, Image i, ImageObserver io){
        g.setColor(Color.LIGHT_GRAY);
        int s = Camera.getSize();
        g.drawRect(getShow().x, getShow().y, s, s);
    }
    
}
