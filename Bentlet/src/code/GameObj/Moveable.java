package code.GameObj;

import java.awt.Point;
import java.awt.Rectangle;

public class Moveable extends GameObject {

    //Depends on type
    private int speed;
    private int defense;
    private int strength;

    private int dir;

    public Moveable(Point p, int mod) {
        super(p, mod);
    }

    public Rectangle getRect() {
        return new Rectangle(getLoc().x, getLoc().y, 24, 24);
    }
}
