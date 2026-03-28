//Lior Sapir

import javax.swing.*;
import java.awt.*;

public class CarComponent extends JPanel {
    private final String name;
    private static final int CIRCLE_SIZE = 20;
    private static final int LABEL_SIZE = 40;

    public CarComponent(String name) {
        setOpaque(false);
        //FIXME: placeholder
        setSize(CIRCLE_SIZE + LABEL_SIZE, CIRCLE_SIZE);
        this.name = name;
    }

    @Override
    public void setLocation(Point position) {
        position = (Point) position.clone();

        position.x -= CIRCLE_SIZE / 2;
        position.y -= CIRCLE_SIZE / 2;
        super.setLocation(position);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //FIXME: placeholder
        g.setColor(Color.RED);
        g.fillOval(0, 0, CIRCLE_SIZE, CIRCLE_SIZE);
        g.setColor(Color.BLACK);
        g.drawString(name, CIRCLE_SIZE, 3 * CIRCLE_SIZE / 4);
    }

}
