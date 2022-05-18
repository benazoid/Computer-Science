package code;

import code.GameObj.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Map {

    private GameObject[][] grid = new GameObject[32][32];
    private ArrayList<GameObject> moves = new ArrayList<>();

    private GameObject player;

    //Initializes the map
    public Map(int level, int health) {
        grid = getObjs(level,health);
    }

    //Draws/updates everything
    public int drawMap(Graphics g, ImageObserver io, ArrayList<Boolean> input) {
        int n = 0;
        boolean b = collideAll();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].show(g, null, io);
                grid[i][j].updateCam(this);
                if (grid[i][j].getHealth() <= 0) {
                    grid[i][j] = new GameObject(new Point(i, j), 0);
                }
            }
        }
        for (int i = 0; i < moves.size(); i++) {
            GameObject go = moves.get(i);
            go.show(g, null, io);
            go.updateCam(this);
            if (go.getHealth() <= 0) {
                moves.remove(i);
            }
            if (go instanceof Player) {
                Camera.moveCamera(go);
                go.move(input);
                n++;
            }
        }

        if (b)
            return 1;
        if (n == 0) 
            return -1;
        return 0;
    };
    
    //Checks collisions with all objects
    private boolean collideAll() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                GameObject gObj = grid[i][j];
                if (gObj instanceof Wall || gObj instanceof Spawner || gObj instanceof Door) {
                    for (int k = 0; k < moves.size(); k++) {
                        if (moves.get(k) instanceof Player && grid[i][j] instanceof Door && player.getRect().intersects(grid[i][j].getRect()) && player.getKeys() > 0) {
                            grid[i][j] = new GameObject(new Point(i, j), 0);
                            player.changeKeys(-1);
                        }
                        Rectangle rect = new Rectangle(moves.get(k).getRect());
                        collideRectRect(gObj.getRect(), rect);
                        moves.get(k).setLoc(rect.x, rect.y);
                    }
                }

                if (gObj.getRect().intersects(player.getRect())) {
                    if (gObj instanceof Key) {
                        player.changeKeys(1);
                        grid[i][j] = new GameObject(new Point(i, j), 0);
                    } else if (gObj instanceof Gold) {
                        player.changeGold(10);
                        grid[i][j] = new GameObject(new Point(i, j), 0);
                    } else if (gObj instanceof Food) {
                        player.changeHealth(30);
                        grid[i][j] = new GameObject(new Point(i, j), 0);
                    } else if (gObj instanceof End && getLeft() == 0) {
                        return true;
                    }

                }
            }

        }

        for (int j = 0; j < moves.size(); j++) {
            GameObject mo1 = moves.get(j);
            for (int i = 0; i < moves.size(); i++) {
                GameObject mo2 = moves.get(i);
                if (!mo1.equals(mo2) && mo1.getRect().intersects(mo2.getRect())) {
                    if (mo1.equals(player)) {
                        moves.remove(i);
                        player.changeHealth(-5);
                    }
                    Rectangle rect = new Rectangle(mo1.getRect());
                    collideRectRect(mo2.getRect(), rect);
                    mo1.setLoc(rect.x, rect.y);
                }
            }
        }
        return false;
    }

    //Gets what side the collision is on
    private String getColPos(Rectangle r1, Rectangle r2) {
        if (r1.intersects(r2) && !r1.equals(r2)) {
            double x1 = (r1.x + Math.floor((r1.width) / 2));
            double y1 = (r1.y + Math.floor((r1.height) / 2));
            double x2 = (r2.x + Math.floor((r2.width) / 2));
            double y2 = (r2.y + Math.floor((r2.height) / 2));
            double distH, distV;
            String horiz, vert;

            distH = Math.abs(x1 - x2);
            distV = Math.abs(y1 - y2);

            if (x1 > x2) {
                horiz = "left";
            } else {
                horiz = "right";
            }

            if (y1 > y2) {
                vert = "top";
            } else {
                vert = "bottom";
            }
            if (distH == distV) {
                return "null";
            }
            if (distH >= distV) {
                return horiz;
            } else {
                return vert;
            }
        }
        return "null";
    }

    //Collides with rectangles
    public void collideRectRect(Rectangle r1, Rectangle r2) {
        Rectangle rect = new Rectangle(r2);
        String loc = getColPos(r1, rect);
        if (loc.equals("top")) {
            r2.y = r1.y - r2.height;
        }
        if (loc.equals("bottom")) {
            r2.y = r1.y + r1.height;
        }
        if (loc.equals("left")) {
            r2.x = r1.x - r2.width;
        }
        if (loc.equals("right")) {
            r2.x = r1.x + r1.width;
        }
    }

    //Checks if a coordinate is available in the map
    public boolean isAvailable(int x, int y) {
        if (!grid[x][y].getClass().toString().equals("class code.GameObj.GameObject")) {
            return false;
        }
        for (GameObject mov : getMoves()) {
            if (mov.getRect().intersects(grid[x][y].getRect())) {
                return false;
            }
        }
        return true;
    }
    
    //Makes the grid of a map object
    public GameObject[][] getObjs(int l, int health) {
        GameObject[][] go = new GameObject[32][32];
        int[][] level = getLevel(l);
        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                int num = level[i][j];
                String n = String.valueOf(num);
                int objType = Integer.parseInt(n.substring(n.length() - 1));
                int mod = Integer.parseInt(n.substring(0));
                GameObject obj = getObj(new Point(i * Camera.getSize(), j * Camera.getSize()), objType, mod);
                if (obj instanceof Moveable) {
                    moves.add(obj);
                    go[i][j] = getObj(new Point(i * Camera.getSize(), j * Camera.getSize()), 0, 0);
                    if (obj instanceof Player) {
                        player = obj;
                    }
                } else {
                    go[i][j] = obj;
                }
            }
        }
        return go;
    }

    // Gets called when you type a cheat code
    public void cheat(int l) {
        if (l == 1) {
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i) instanceof Enemy) {
                    moves.remove(i);
                    i -= 2;
                }
            }
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] instanceof Spawner) {
                        grid[i][j] = new GameObject(new Point(i, j), 0);
                    }
                }
            }
        } else if (l == 2) {
            double d = player.getDirection();
            player.setLoc(player.getLoc().x + 100 * (int) (Math.cos(d * Math.PI / 2)), player.getLoc().y + 100 * (int) (Math.sin(d * Math.PI / 2)));
        }

    }

    //Makes a new GameObject based on a string
    public static GameObject getObj(Point p, int objType, int mod) {
        switch (objType) {
            case 0:
                return new GameObject(p, mod);
            case 1:
                return new Wall(p, mod);
            case 2:
                return new Door(p, mod);
            case 3:
                return new Gold(p, mod);
            case 4:
                return new Spawner(p, mod);
            case 5:
                return new Key(p, mod);
            case 6:
                return new Food(p, mod);
            case 7:
                return new Trader(p, mod);
            case 8:
                return new End(p, mod);
            case 9:
                return new Player(p, mod);
        }
        return new GameObject(p, mod);
    }

    /*
     0 - nothing
     1 - wall
     2 - door
     X3 - gold
     X4 - spawner
     X5 - key
     X6 - food
     7 - trader
     8 - end
     9 - player
     */
    private static final int[][][] tower = {
        MapStorage.lvl0, MapStorage.lvl1, MapStorage.lvl3
    };

    public int[][] getLevel(int l) {
        return tower[l];
    }

    public GameObject[][] getGrid() {
        return grid;
    }

    public ArrayList<GameObject> getMoves() {
        return moves;
    }

    public GameObject getPlayer() {
        return player;
    }

    // Gets the amount of enemies left
    public int getLeft() {
        int count = moves.size() - 1;
        for (GameObject[] gos : grid) {
            for (GameObject go : gos) {
                if (go instanceof Spawner) {
                    count++;
                }
            }
        }
        return count;
    }
}
