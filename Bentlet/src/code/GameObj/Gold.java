package code.GameObj;

import code.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Gold extends GameObject{

    public Gold(Point p, int mod) {
        super(p, mod);
    }

    public void show(Image i, Graphics g, ImageObserver io){
        //g.drawRect(pos.x, pos.y, width, height);
    }
    
    public void show(Graphics g, Image i, ImageObserver io){
        g.setColor(Color.orange);
        int s = Camera.getSize();
        g.drawRect(getShow().x, getShow().y, s, s);
    }
    
}

