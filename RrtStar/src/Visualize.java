import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Visualize extends JFrame {
    int width = 1000, height = 1000;
    RrtStar test = new RrtStar(width, height);
    Point[] obstacle = new Point[] {new Point(100, 100),new Point(100, 400), new Point(400, 400), new Point(400, 100)};;


    public Visualize() {
        super("RRT*");

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    void drawLines(Graphics g) {

        test.makeObstacle(obstacle);
        Point[] out = test.findPath(new Point(1, 1), new Point(600, 600), 10000, 40);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(199, 38, 8));
        for (int i = 0; i <= obstacle.length; i++){
            g2d.draw(new Line2D.Double(obstacle[i%obstacle.length].x, obstacle[i%obstacle.length].y, obstacle[(i+1)%obstacle.length].x, obstacle[(i+1)%obstacle.length].y));
        }
        g2d.setColor(new Color(0, 0, 0));
        for (int i = 0; i < out.length-1; i++){
            g2d.draw(new Line2D.Double(out[i].x, out[i].y, out[(i+1)].x, out[(i+1)].y));
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Visualize().setVisible(true);
            }
        });
    }

}
