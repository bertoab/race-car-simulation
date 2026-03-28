import java.awt.*;

public class Utility {
    public static int floorInt(double num) {
        return (int) Math.floor(num);
    }

    public static Point lerp(Point p1, Point p2, double t) {
        return new Point(floorInt(p1.x + t * (p2.x - p1.x)), floorInt(p1.y + t * (p2.y - p1.y)));
    }
}
