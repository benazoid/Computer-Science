package code;

import code.GameObj.GameObject;
import code.GameObj.Spawner;
import code.GameObj.Wall;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Projectile {

    private Point loc = new Point();
    private Point direction = new Point();
    private double dX;
    private double dY;
    private GameObject parent;

    private static ArrayList<Projectile> projs = new ArrayList<>();

    public Projectile(Point p, double d, GameObject pa) {
        loc.x = p.x;
        loc.y = p.y;
        dX = (Math.cos(d * Math.PI/2));
        dY = (Math.sin(d * Math.PI/2));
        parent = pa;
    }

    public void update(Graphics g) {
        int sp = 7;
        loc.x += sp*dX;
        loc.y += sp*dY;
        g.fillRect(loc.x- Camera.getLoc().x + 560/2,loc.y- Camera.getLoc().y + 720/2,7,7);
    }

    public static void addProj(Point p, double d, GameObject pa) {
        projs.add(new Projectile(p, d, pa));
    }

    public static void updateAll(Map gr, Graphics g) {
        ArrayList<GameObject> gos = gr.getMoves();
        GameObject[][] grid = gr.getGrid();
        for (int j = 0; j < projs.size(); j++) {
            projs.get(j).update(g);
            for (int i = 0; i < gos.size(); i++) {
                GameObject go = gos.get(i);
                if(!go.equals(projs.get(j).parent) && go.getRect().intersects(projs.get(j).getRect())){
                    projs.remove(j);
                    go.changeHealth(-50);
                    return;
                }
            }
            for(int i = 0; i < grid.length; i++){
                for(int k = 0; k < grid[i].length; k++){
                    if(grid[i][k].getRect().intersects(projs.get(j).getRect())){
                        if(grid[i][k] instanceof Spawner){
                            projs.remove(j);
                            grid[i][k].changeHealth(-10);
                            return;
                        }
                        else if(grid[i][k] instanceof Wall){
                            projs.remove(j);
                            return;
                        }
                    }
                }
            }
        }

    }

    public Rectangle getRect(){
        return new Rectangle(loc.x-3,loc.y+10,13,13);
    }
}
