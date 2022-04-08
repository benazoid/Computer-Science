package code;

import code.GameObj.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Map{

    private GameObject[][] grid = new GameObject[32][32];
    private ArrayList<GameObject> moves = new ArrayList<>();
    
    public Map(int level){
        grid = getObjs(level);
    }
    
    public void drawMap(Graphics g, ImageObserver io, ArrayList<Boolean> input){
        for(int i = 0; i < moves.size(); i++){
            GameObject go = moves.get(i);
            go.show(g, null, io);
            go.updateCam();
            if(go instanceof Player){
                Camera.moveCamera(go);
                go.move(input);
            }
        }
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j].show(g,null,io);
                grid[i][j].updateCam();
            }
        }
    };
    
    public GameObject[][] getObjs(int l){
        GameObject[][] go = new GameObject[32][32];
        int[][] level = getLevel(l);
        for(int i = 0; i < level.length; i++){
            for(int j = 0; j < level[i].length; j++){
                int num = level[i][j];
                String n = String.valueOf(num);
                int objType = Integer.parseInt(n.substring(n.length()-1));
                int mod = Integer.parseInt(n.substring(0));
                GameObject obj = getObj(new Point(i*Camera.getSize(),j*Camera.getSize()),objType,mod);
                if(obj instanceof Moveable){
                    moves.add(obj);
                    go[i][j] = getObj(new Point (i*Camera.getSize(),j*Camera.getSize()),0,0);
                }
                else{
                    go[i][j] = obj;
                }
            }
        }
        return go;
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
    
    public GameObject getObj(Point p, int objType, int mod){
        switch(objType){
            case 0:
                return new GameObject(p,mod);
            case 1:
                return new Wall(p,mod);
            case 2:
                return new Door(p,mod);
            case 3:
                return new Gold(p,mod);
            case 4:
                return new Spawner(p,mod);
            case 5:
                return new Key(p,mod);
            case 6:
                return new Food(p,mod);
            case 7:
                return new Trader(p,mod);
            case 8: 
                return new End(p, mod);
            case 9:
                return new Player(p,mod);
        }
        return new GameObject(p,mod);
    }
    
    public static int[][] lvl0 = {
        {01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,01,01,01,01,01,01,00,00,01,01,01,01,01,01,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,9,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,01},
        {01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01,01},
    };

    private static int[][][] tower = {
        lvl0
    };
    
    public int[][] getLevel(int l){
        return tower[l];
    }
    public GameObject[][] getGrid(){
        return grid;
    }
    public ArrayList<GameObject> getMoves(){
        return moves;
    }

}