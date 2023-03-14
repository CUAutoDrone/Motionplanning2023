public class Point {
    public double x;
    public double y;
    public double distanceToParent;
    public Point parent;

    Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double cost(){
        if (parent == null) return 0;
        return distanceToParent + parent.cost();
    }

    @Override
    public String toString(){
        return x + ", " + y;
    }

}
