package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Game {

    private int[][] grid;
    private int[][] visible;
    private int rows;
    private int cols;
    private int diff;
    private boolean dead = false;
    private int bombs = 0;
    private int bombFlags = 0;

    private Image ballImage;
    private Image starImage;

    public Game(int rows_, int cols_, int diff_) {
        rows = rows_;
        cols = cols_;
        diff = diff_;

        grid = generateGrid(cols, rows, diff);
        visible = new int[cols][rows];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                visible[i][j] = 0;
                if (grid[i][j] == -1) {
                    bombs++;
                }
            }
        }
    }

    public Game(Game game) {
        rows = game.rows;
        cols = game.cols;
        diff = game.diff;

        grid = new int[cols][rows];
        visible = new int[cols][rows];

        for (int i = 0; i < game.visible.length; i++) {
            for (int j = 0; j < game.visible[0].length; j++) {
                if (game.visible[i][j] == 1) {
                    grid[i][j] = game.grid[i][j];
                    visible[i][j] = 1;
                } else if (game.visible[i][j] == 2) {
                    grid[i][j] = -1;
                    visible[i][j] = 2;
                } else {
                    visible[i][j] = -1;
                }
            }
        }
    }

    //Private methods
    
    private static int[][] generateGrid(int cols, int rows, int diff) {
        int[][] gr = new int[cols][rows];

        for (int i = 0; i < (int) (diff / 100.0 * (rows * cols)); i++) {
            int col = (int) (Math.random() * cols);
            int row = (int) (Math.random() * cols);
            gr[col][row] = -1;
        }

        for (int i = 0; i < gr.length; i++) {
            for (int j = 0; j < gr[0].length; j++) {
                if (gr[i][j] != -1) {
                    gr[i][j] = checkPos(gr, i, j);
                }
            }
        }

        return gr;
    }
    
    private static int checkPos(int[][] grid, int col, int row) {
        int ct = 0;
        for (int i = 0; i < 8; i++) {
            int newC = col + (int) Math.round(Math.sin(i * Math.PI / 4));
            int newR = row + (int) Math.round(Math.cos(i * Math.PI / 4));
            if (newC >= 0 && newR >= 0 && newC < grid.length && newR < grid[0].length && grid[newC][newR] == -1) {
                ct++;
            }
        }
        return ct;
    }

    private void findZeros(int col, int row, boolean first) {
        if (visible[col][row] != 0) {
            return;
        }
        visible[col][row] = 1;
        if (!first && grid[col][row] != 0) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            int newC = col + (int) Math.round(Math.sin(i * Math.PI / 4));
            int newR = row + (int) Math.round(Math.cos(i * Math.PI / 4));
            if (newC >= 0 && newR >= 0 && newC < grid.length && newR < grid[0].length) {
                if (first && grid[newC][newR] == 0) {
                    findZeros(newC, newR, false);
                }
                if (!first) {
                    findZeros(newC, newR, false);
                }
            }
        }
    }

    private int[] getAdjNums(int col, int row) {
        int[] adjs = new int[8];
        for (int i = 0; i < 8; i++) {
            adjs[i] = -1;
            int newC = col + (int) Math.round(Math.sin(i * Math.PI / 4));
            int newR = row + (int) Math.round(Math.cos(i * Math.PI / 4));
            if (newC >= 0 && newR >= 0 && newC < grid.length && newR < grid[0].length) {
                adjs[i] = (grid[newC][newR]);
            }
        }
        return adjs;
    }
    
    //Public methods

    public void showGrid(Graphics g, ImageObserver io, int gridSize) {
        for (int i = 0; i < visible.length; i++) {
            for (int j = 0; j < visible[0].length; j++) {
                switch (visible[i][j]) {
                    case 0:
                        g.setColor(Color.GRAY);
                        g.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
                        break;
                    case 1:
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
                        g.setColor(Color.BLACK);
                        g.drawString(grid[i][j] + "", j * gridSize + gridSize / 2, i * gridSize + gridSize / 2);
                        break;
                    case 2:
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(j * gridSize, i * gridSize, gridSize, gridSize);
                        g.setColor(Color.BLACK);
                        g.drawString("flag", j * gridSize + gridSize / 2, i * gridSize + gridSize / 2);
                        break;
                }
                g.setColor(Color.BLACK);
                g.drawRect(j * gridSize, i * gridSize, gridSize, gridSize);
            }
        }
    }

    public void click(int mouseX, int mouseY, Boolean flag, int size) {
        for (int i = 0; i < visible.length; i++) {
            for (int j = 0; j < visible[0].length; j++) {
                if (new Rectangle(j * size, i * size, size, size).contains(mouseX, mouseY)) {
                    if (flag && visible[i][j] != 1) {
                        if (visible[i][j] == 2) {
                            visible[i][j] = 0;
                        } else {
                            visible[i][j] = 2;
                        }
                    } else {
                        findZeros(i, j, true);
                    }
                }
            }
        }
    }

    public void setImages(Image bi, Image si) {
        ballImage = bi;
        starImage = si;
    }
    
    public int[][] getVisibleGrid(){
        int[][] gr = new int[rows][cols];
        for(int i = 0; i < gr.length; i++){
            for(int j = 0; j < gr[0].length; j++){
                if(visible[j][i] != 0){
                    gr[i][j] = grid[i][j];
                }
                else{
                    gr[i][j] = -1;
                }
            }
        }
        return gr;
    }
    
    public void updateGame(Game g){
        int[][] gr = g.getVisibleGrid();
        for(int i = 0; i < gr.length; i++){
            for(int j = 0; j < gr[0].length; j++){
                if(grid[i][j] == -1 && gr[i][j] != -1){
                    grid[i][j] = gr[i][j];
                }
            }
        }
    }
}
