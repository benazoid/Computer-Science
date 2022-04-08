package code.GameObj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class Gold extends GameObject{

    public Gold(Point p, int mod) {
        super(p, mod);
    }

    public void show(Image i, Graphics g, ImageObserver io){
        //g.drawRect(pos.x, pos.y, width, height);
    }
    
}
