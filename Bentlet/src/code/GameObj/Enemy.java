package code.GameObj;

import code.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Enemy extends Moveable {

    private String type;
    private double speed = 2;

    public Enemy(Point p, int mod) {
        super(p, mod);
    }

    public void show(Graphics g, Image i, ImageObserver io) {
        g.setColor(Color.red);
        int s = Camera.getSize() * 2 / 3;
        g.drawRect(getShow().x, getShow().y, s, s);
    }

    public void updateCam(Map m) {
        super.updateCam(m);
        GameObject player = m.getPlayer();
        double ang = BentletGame.findAng(getLoc().x, getLoc().y, player.getLoc().x, player.getLoc().y);
        setLoc(getLoc().x + (int) (speed * Math.cos(ang)), getLoc().y + (int) (speed * Math.sin(ang)));
    }

    public Rectangle getRect() {
        int s = Camera.getSize() * 2 / 3;
        return new Rectangle(getLoc().x, getLoc().y, s, s);
    }
    
    
}
