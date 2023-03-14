import org.junit.Test;

public class RrtStarTest {

    @Test
    public void findPathTest(){
        RrtStar test = new RrtStar(1000, 1000);
        test.makeObstacle(new Point[] {new Point(20, 20),new Point(20, 400), new Point(400, 400), new Point(400, 20)});
        Point[] out = test.findPath(new Point(1, 1), new Point(999, 999), 100000, 40);

        for (Point p : out){
            if (p.x > 20 && p.x < 400 && p.y > 20 && p.y < 400) {
                System.out.println(p);
            }
        }
        System.out.println(out[out.length-1].cost());
    }
}