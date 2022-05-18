package code.GameObj;

import code.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class Food extends GameObject {

    public Food(Point p, int mod) {
        super(p, mod);
    }

    public void show(Graphics g, Image i, ImageObserver io) {
        g.setColor(Color.red);
        int s = Camera.getSize();
        g.fillRect(getShow().x, getShow().y, s, s);
        g.drawRect(getShow().x, getShow().y, s, s);
    }
    
}
