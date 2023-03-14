import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;

public class ObstaclePolygon {
    double[][] outline;
    double[] lowerLeft;
    double[] upperRight;

    /**
     * Takes in a list of points of the form [ [x1, y1], ... [xn, yn] ]
     * Builds a polygon made of points connected in order.
     */
    ObstaclePolygon(double[][] points){
        outline = new double[points.length+1][2];
        lowerLeft = new double[] { points[0][0], points[0][1]};
        upperRight = new double[] { points[0][0], points[0][1]};;
        int i = 0;
        for (double[] point : points){
            outline[i++] = new double[] { point[0], point[1]};
            if (point[0] < lowerLeft[0]) lowerLeft[0] = point[0];
            if (point[1] < lowerLeft[1]) lowerLeft[1] = point[1];
            if (point[0] > upperRight[0]) upperRight[0] = point[0];
            if (point[1] > upperRight[1]) upperRight[1] = point[1];
        }

        outline[i] = new double[] { points[0][0], points[0][1]};;
    }

    /**
     * Takes in a line segment of the form [ [x1, y1], [x2, y2] ]
     * Returns true if the line intersects with this polygon.
     */
    public boolean doesLineIntersect(double[][] line){
        if (line[0][0] > upperRight[0] && line[1][0] > upperRight[0]) return false;
        if (line[0][1] > upperRight[1] && line[1][1] > upperRight[1]) return false;
        if (line[0][0] < lowerLeft[0] && line[1][0] < lowerLeft[0]) return false;
        if (line[0][1] < lowerLeft[1] && line[1][1] > lowerLeft[1]) return false;
        for (int i = 0; i < outline.length-1; i++){
            double[][] polyLine = {outline[i], outline[i+1]};
            if (lineIntersect(line, polyLine)) return true;
        }
        return false;
    }

    /**
     * Takes in a point [x, y]
     * Returns true if the point is inside the polygon.
     */

    public boolean doesPointIntersect(double[] point){

        if (point[0] > upperRight[0] || point[1] > upperRight[1] || point[0] < lowerLeft[0] || point[1] < lowerLeft[1]){
            return false;
        }
        int intersections = 0;
        double[][] line = {point, {upperRight[0]+5,lowerLeft[1]-10}};
        for (int i = 0; i < outline.length-1; i++){
            double[][] polyLine = {outline[i], outline[i+1]};
            if (lineIntersect(line, polyLine)) intersections++;
        }
        return intersections % 2 == 1;
    }

    /**
     * Takes 3 co-linear points p, q, r of the form [x, y]
     * Returns true if q is on the line from p to r
     */
    private boolean onSegment(double[] p, double[] q, double[] r)
    {
        return q[0] <= Math.max(p[0], r[0]) && q[0] >= Math.min(p[0], r[0]) &&
                q[1] <= Math.max(p[1], r[1]) && q[1] >= Math.min(p[1], r[1]);
    }

    /**
     * Returns true if line1 and line2 intersect
     */
    private boolean lineIntersect(double[][] line1, double[][] line2)
    {
        Line2D l1 = new Double(line1[0][0], line1[0][1], line1[1][0], line1[1][1]);
        Line2D l2 = new Double(line2[0][0], line2[0][1], line2[1][0], line2[1][1]);
        return l1.intersectsLine(l2);
    }

}
