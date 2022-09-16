import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class GraphingRunner extends JPanel implements KeyListener {

    /*
     * classic
     * complex
     * field
     * 
     */
    String mode = "classic";

    public void renderFrame(Graphics g) {
        g.drawRect(10, 10, 50, 50);
        // paintComponent(g);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) throws Exception {

        GraphingRunner gr = new GraphingRunner();

        var frame = new JFrame("A simple graphics program");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gr, BorderLayout.CENTER);
        frame.setVisible(true);

        String fun = "(12+34)-567*(6+78)";// "(-b+sqrt(b^2-4ac))/2a";
        System.out.println(fun);
        Function f = Function.createFunction(fun);

        for (int i = 0; i < Function.funcs.size(); i++) {
            if (Function.funcs.get(i).getNum() != null) {
                System.out.println(Function.funcs.get(i).getNum());
            }
        }

        f.printFun();
    }

    public GraphingRunner() {

        this.setSize(600, 600);
        this.setLocation(80, 80); // move to the right
        this.setVisible(true); // make it visible to the user
        this.setFocusable(true);

        this.addKeyListener(this);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.requestFocusInWindow();
        renderFrame(g);
    }

}
