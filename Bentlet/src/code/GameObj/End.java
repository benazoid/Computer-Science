package code.GameObj;

import code.Camera;
import java.awt.*;
import java.awt.image.ImageObserver;

public class End extends GameObject{

    public End(Point p, int mod) {
        super(p, mod);
    }

    public void show(Graphics g, Image i, ImageObserver io) {
        g.setColor(Color.green);
        int s = Camera.getSize();
        g.fillRect(getShow().x, getShow().y, s, s);
        g.drawRect(getShow().x, getShow().y, s, s);
    }
    
}
