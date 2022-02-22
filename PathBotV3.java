package brain;

import actor.BotBrain;
import actor.GameObject;
import grid.Grid;
import grid.Location;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// row, col
public class PathBotV3 extends BotBrain {

    private ArrayList<Node> queue = new ArrayList<>();
    private ArrayList<Location> usedLocs = new ArrayList<>();
    private Location loc;
    private ArrayList<Location> route = new ArrayList<>();
    private int[][] grid = new int[24][24];
    private int steps = 0;
    private boolean tic = false;
    Location[] ticTacToe = {
        new Location(3, 8),
        new Location(6, 6),
        new Location(19, 6),
        new Location(19, 17),
        new Location(3, 16)
    };
    int ticNum = 0;

    //Resets variables every round
    public void initForRound() {
        ticNum = 0;
    }

    //Called every step
    public int chooseAction() {
        loc = new Location(getRow(), getCol());
        int dir = 0;

        if (ticNum < ticTacToe.length) {
            if (steps == 0) {
                route = pathFind(ticTacToe[ticNum]);
            }
        }
        else{
            tic = false;
            if(steps == 0){
                route = pathFind(findNearestRock());
            }
        }

        if (route.size() > 0) {
            dir = loc.getDirectionToward(route.get(0));
            route.remove(0);
            steps++;
        } else {
            steps = 0;
            if(!tic)
                ticNum++;
        }

        return dir;
    }
    
    public Location findNearestRock(){
        GameObject[][] grid = getArena();
        Location best = grid[0][0].getLocation();
        int bestDist = 500;
        
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; i < grid[i].length; i++){
                int d = loc.distanceTo(grid[j][i].getLocation());
                if(d < bestDist){
                    bestDist = d;
                    best = grid[j][i].getLocation();
                }
            }
        }
        return best;
    }

    //Returns a path the bot needs to follow to get to a given target
    public ArrayList<Location> pathFind(Location target) {
        queue.clear();
        Node start = new Node(loc, new Location(0, 0));
        int amt = 0;
        if (getArena()[target.getRow()][target.getCol()] == null) {
            while (amt < 500 && !findBest().loc.equals(target)) {
                Node n = findBest();
                grid[n.loc.getRow()][n.loc.getCol()] += 5;
                n.initAdj();
            }
        } else {
            while (amt < 400 && findBest().loc.distanceTo(target) < 2) {
                Node n = findBest();
                grid[n.loc.getRow()][n.loc.getCol()] += 5;
                n.initAdj();
            }
        }
        Node last = findBest();
        ArrayList<Location> path = new ArrayList<>();
        while (last.parent != null) {
            path.add(0, last.loc);
            last = last.parent;
        }
        return path;
    }

    //A class that keeps track of each "node" on the grid
    class Node {

        Location loc;
        Node parent;
        Location target;

        double dStart;
        double dEnd;

        //Constructor for the basic node

        public Node(Location loc_, Node parent_) {
            loc = loc_;
            parent = parent_;
            target = parent.target;

            dStart = parent.dStart + 1;
            dEnd = loc.distanceTo(target);
            addToQueue();
        }

        //Constructor for the origin node

        public Node(Location loc_, Location target_) {
            loc = loc_;
            parent = null;
            target = target_;
            dStart = 0;
            dEnd = loc.distanceTo(target);
            addToQueue();
        }

        //Adds node to queue after initilization

        public void addToQueue() {
            queue.add(this);
        }

        //Initializes adjacent grid spaces

        public void initAdj() {
            ArrayList<Location> l = getNeighs(loc);
            for (Location lo : l) {
                queue.add(new Node(lo, this));
            }
        }
    }

    //Finds all adjacent neighbors
    public ArrayList<Location> getNeighs(Location lo) {
        ArrayList<Location> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Location l = lo.getAdjacentLocation(i * 45);
            if (l.isValidLocation() && getArena()[l.getRow()][l.getCol()] == (null)) {
                list.add(l);
            }
        }
        return list;
    }

    //Finds node with least weight in queue
    public Node findBest() {
        Node best = queue.get(0);
        int remove = 0;
        for (int i = 0; i < queue.size(); i++) {
            Node n = queue.get(i);
            if (n.dEnd + n.dStart + getGridAmt(n) < best.dEnd + best.dStart + getGridAmt(best)) {
                best = n;
                remove = i;
            }
        }
        return best;
    }

    //Gets weight from grid
    public int getGridAmt(Node n) {
        return grid[n.loc.getRow()][n.loc.getCol()];
    }
}
