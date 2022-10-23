import java.awt.Point;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        int[][] br = {
                { 1, 1, 1 }, // x should go top left
                { 0, 2, 1 },
                { 1, 2, 2 }
        };

        Game game = new Game();
        // System.out.println((int) (Math.random() * 9));

        Long startTime = System.nanoTime();
        aiVsRan(game, true, 50);
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);
    }

    public static void aiVsRan(Game game, boolean ranFirst, int trials) {
        int winAi = 0;
        int winRan = 0;
        int drawCt = 0;
        for (int i = 0; i < trials; i++) {
            game = new Game();
            while (Game.getWinner(game) == 0 && Game.blankCt(game) > 0) {
                if (ranFirst) {
                    int input = (int) (Math.random() * 9);
                    while (game.board[(int) (input / 3)][input % 3] != 0) {
                        input = (int) (Math.random() * 9);
                    }
                    game.move((int) (input / 3), input % 3, false);
                }
                ranFirst = true;
                Game.play(game, true);
            }
            int result = Game.getWinner(game);
            if (result == 1)
                winAi++;
            else if (result == 0)
                drawCt++;
            else
                winRan++;
        }
        System.out.println(winAi + ", " + winRan + ", " + drawCt);
    }

    public static void aiVsUser(Game game, boolean userFirst, boolean ai) {
        Scanner scanner = new Scanner(System.in);

        while (Game.getWinner(game) == 0 && Game.blankCt(game) > 0) {

            if (userFirst) {
                int input = scanner.nextInt() - 1;
                while (game.board[(int) (input / 3)][input % 3] != 0) {
                    input = scanner.nextInt() - 1;
                }
                game.move((int) (input / 3), input % 3, !ai);
            }
            userFirst = true;
            Game.play(game, ai);
            game.boardOut();
            System.out.println("-------");
        }
        System.out.println();
        if (Game.getWinner(game) == 0)
            System.out.println("Draw");
        if (Game.getWinner(game) == 1)
            System.out.println("X wins");
        if (Game.getWinner(game) == 2)
            System.out.println("O wins");

        scanner.close();
    }

    public static class Game {

        public static Game mainGame;
        public static int[][] staticBoard = {
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        // 0's are blank, 1's are X's, true. 2's are O's, false
        public int[][] board = {
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 }
        };

        public int chanceCt = 0;

        public Game() {

        }

        public Game(int[][] b) {
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b[i].length; j++) {
                    board[i][j] = b[i][j];
                }
            }
        }

        // Returns chance sum, count
        public static Point play(Game g, boolean player) {

            // Returns 1 if win
            if ((getWinner(g) == 1 && player) || (getWinner(g) == 2 && !player)) {
                Point pt = new Point();
                pt.setLocation(1.0, 1.0);
                return (pt);
            }
            // Returns -1 if loss
            if ((getWinner(g) == 1 && !player) || (getWinner(g) == 2 && player)) {
                Point pt = new Point();
                pt.setLocation(-2, 1.0);
                return (pt);
            }
            // Returns 0 if stalemate
            if (blankCt(g) == 0) {
                Point pt = new Point();
                pt.setLocation(0, 1.0);
                return (pt);
            }

            // Alternate Universe Creation Center (AUCC)
            Point p = new Point(0, 0);
            double maxAve = 0;
            double total = 0;
            int ct = 0;
            boolean plsGo = true;
            for (int i = 0; i < g.board.length; i++) {
                for (int j = 0; j < g.board[i].length; j++) {
                    if (g.board[i][j] != 0)
                        continue;
                    Game game = new Game(g.board);
                    game.move(i, j, player);

                    Point playOut = play(game, !player);

                    total += -playOut.getX();
                    ct += playOut.getY();
                    if ((-playOut.getX() / playOut.getY()) >= maxAve || plsGo) {
                        plsGo = false;
                        maxAve = (-playOut.getX() / playOut.getY());
                        p.setLocation(i, j);
                    }

                }
            }

            g.move(p.x, p.y, player);

            Point out = new Point();
            out.setLocation(total / 2, ct + 1);
            return out;
        }

        public static int getWinner(Game g) {
            int[][] gr = g.board;

            // Finds vertical and horizontals
            for (int i = 0; i < gr.length; i++) {
                int hCt = 0;
                int vCt = 0;

                for (int j = 1; j < gr[i].length; j++) {
                    if (gr[i][0] != 0 && gr[i][0] == gr[i][j]) {
                        hCt++;
                    }
                    if (gr[0][i] != 0 && gr[0][i] == gr[j][i]) {
                        vCt++;
                    }
                }
                if (hCt == 2)
                    return gr[i][0];
                if (vCt == 2)
                    return gr[0][i];
            }

            // Finds diagonals
            int[][][] dias = { { { 0, 0 }, { 1, 1 }, { 2, 2 } }, { { 0, 2 }, { 1, 1 }, { 2, 0 } } };
            for (int i = 0; i < dias.length; i++) {
                int ct = 0;
                int[] dia0 = dias[i][0];
                for (int j = 1; j < dias[i].length; j++) {
                    int[] cur = dias[i][j];
                    if (gr[dia0[0]][dia0[1]] == gr[cur[0]][cur[1]]) {
                        ct++;
                    }
                }
                if (ct == 2) {
                    return gr[dia0[0]][dia0[1]];
                }
            }

            return 0;
        }

        public static int blankCt(Game g) {
            int ct = 0;
            for (int i = 0; i < g.board.length; i++) {
                for (int j = 0; j < g.board[i].length; j++) {
                    if (g.board[i][j] == 0) {
                        ct++;
                    }
                }
            }
            return ct;
        }

        public void move(int r, int c, boolean move) {
            if (move)
                board[r][c] = 1;
            else
                board[r][c] = 2;
        }

        public void boardOut() {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == 1)
                        System.out.print("|X");
                    else if (board[i][j] == 2)
                        System.out.print("|O");
                    else if (board[i][j] == 0)
                        System.out.print("| ");
                }
                System.out.println("|");
            }
        }
    }

}
