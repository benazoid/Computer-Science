package code.GameObj;

import code.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class Key extends GameObject{

    public Key(Point p, int mod) {
        super(p, mod);
    }

    
    public void show(Graphics g, Image i, ImageObserver io) {
        g.setColor(Color.yellow);
        int s = Camera.getSize() * 3 / 4;
        g.fillRect(getShow().x, getShow().y, s, s);
        g.drawRect(getShow().x, getShow().y, s, s);
    }
}
