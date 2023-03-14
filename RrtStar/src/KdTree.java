import java.util.ArrayList;

public class KdTree {
    ArrayList<Point> points = new ArrayList<>();
    void add(Point p){
        points.add(p);
    }

    Point[] radiusNearestNeighbors(Point p, double radius){
        ArrayList<Point> closePoints = new ArrayList<>();
        for (Point c : points){
            if (Math.hypot(p.x - c.x, p.y - c.y) <= radius) closePoints.add(c);
        }
        Point[] r = new Point[closePoints.size()];
        int i = 0;
        for (Point c : closePoints){
            r[i++] = c;
        }
        return r;
    }

    Point nearestNeighbor(Point p){
        Point closest = points.get(0);
        double dist = Math.hypot(p.x - closest.x, p.y - closest.y);
        for (Point c : points){
            if (Math.hypot(p.x - c.x, p.y - c.y) <= dist){
                dist = Math.hypot(p.x - c.x, p.y - c.y);
                closest = c;
            }
        }
        return closest;
    }

}
