import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RrtStar {

    /**
     * Defines the total x size and y size of the space
     */
    double xDim, yDim;
    ArrayList<ObstaclePolygon> obstacles= new ArrayList<>();
    RrtStar(double xDimension, double yDimension){
        xDim = xDimension;
        yDim = yDimension;
    }

    /**
     * Takes in a Point start and end to find path between.
     * Requires start and end within bound.
     * Iterations is the total number of iterations run by RRT*
     * Radius is the radius of an iteration search for RRT*
     *
     * Returns an array of Points if
     */
    public Point[] findPath(Point start, Point end, int iterations, double radius){
        KdTree points = new KdTree();
        Random r = new Random();
        points.add(start);
        //start.cost = 0;
        for (int i = 0; i < iterations; i++) {
            Point pNew;
            Point pNearest;
            do {
                pNew = new Point(r.nextDouble() * xDim, r.nextDouble() * yDim);
                pNearest = points.nearestNeighbor(pNew);
                pNew = shortenVector(pNearest, pNew, radius);
            } while (pointInObstacles(pNew));


            if (lineInObstacles(pNearest, pNew)) continue;
            //pNew.cost = pNearest.cost + distance(pNearest, pNew);
            pNew.distanceToParent = distance(pNearest, pNew);
            pNew.parent = pNearest;
            Point[] closePoints = points.radiusNearestNeighbors(pNew, radius);
            points.add(pNew);
            for (Point p : closePoints){
                if (lineInObstacles(pNew, p)) continue;
                if (p.cost() > pNew.cost() + distance(p, pNew) && !lineInObstacles(p, pNew)){
                    //p.cost = pNew.cost + distance(p, pNew);
                    p.distanceToParent = distance(p, pNew);
                    p.parent = pNew;
                }
            }

        }


        Point finalPoint = points.nearestNeighbor(end);
        if (distance(finalPoint, end) <= radius){
            return buildPath(finalPoint);
        }
        return null;
    }

    public void makeObstacle(Point[] obstacleOutline){
        double[][] obstacle = new double[obstacleOutline.length][2];
        int i = 0;
        for (Point p : obstacleOutline){
            obstacle[i][0] = obstacleOutline[i].x;
            obstacle[i][1] = obstacleOutline[i].y;
            i += 1;
        }
        obstacles.add(new ObstaclePolygon(obstacle));
    }

    private Point shortenVector(Point origin, Point vector, double radius){
        double vectorLength = Math.hypot(vector.x-origin.x, vector.y-origin.y);
        if (vectorLength <= radius) return vector;
        double normalizedX = (vector.x-origin.x)/vectorLength;
        double normalizedY = (vector.y-origin.y)/vectorLength;
        return new Point(normalizedX*radius + origin.x,normalizedY*radius + origin.y);
    }

    private double distance(Point p1, Point p2){
        return Math.hypot(p1.x-p2.x, p1.y-p2.y);
    }

    private boolean pointInObstacles(Point p){
        for (ObstaclePolygon shape : obstacles) {
            double[] pList = {p.x, p.y};
            if (shape.doesPointIntersect(pList)) return true;
        }
        return false;
    }

    private boolean lineInObstacles(Point p1, Point p2){
        for (ObstaclePolygon shape : obstacles) {
            double[][] line = {{p1.x, p1.y}, {p2.x, p2.y}};
            if (shape.doesLineIntersect(line)) return true;
        }
        return false;
    }

    private Point[] buildPath(Point end){
        LinkedList<Point> pathList = new LinkedList<>();
        while (end != null){
            pathList.add(0, end);
            end = end.parent;
        }
        Point[] path = new Point[pathList.size()];
        int i = 0;
        for (Point p : pathList){
            path[i++] = p;
        }
        return path;
    }


}
