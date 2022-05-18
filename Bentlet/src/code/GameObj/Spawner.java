package code.GameObj;

import code.Camera;
import code.Map;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Spawner extends GameObject {

    private int timer = 0;

    public Spawner(Point p, int mod) {
        super(p, mod);
    }

    public void updateCam(Map m) {
        super.updateCam(m);
        timer++;
        if (timer > 300) {
            timer = 0;
            spawn(m);
        }
    }
    
    public void spawn(Map m) {
        GameObject[][] grid = m.getGrid();
        Point[] ps = {new Point(0,1),new Point(1,1),new Point(1,0),new Point(1,-1),new Point(0,-1),new Point(-1,-1),new Point(-1,0),new Point(0,0)};
        int r = (int)Math.floor(Math.random()*ps.length);
        int c = 0;
        while(!m.isAvailable(getLoc().x/32+ps[r].x, getLoc().y/32+ps[r].y) && c < 5){
            r = (int)Math.floor(Math.random()*ps.length);
            c++;
        }
        if(m.isAvailable(getLoc().x/32+ps[r].x, getLoc().y/32+ps[r].y)){
            m.getMoves().add(new Enemy(new Point(getLoc().x+(ps[r].x*32), getLoc().y+(ps[r].y*32)), getType()));
        }
    }
    
    public void show(Graphics g, Image i, ImageObserver io) {
        g.setColor(Color.magenta);
        int s = Camera.getSize();
        g.fillRect(getShow().x, getShow().y, s, s);
        g.drawRect(getShow().x, getShow().y, s, s);
    }
}
