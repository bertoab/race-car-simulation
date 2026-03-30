//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class CarComponent extends JPanel {
    private Car car;

    private final String name;
    private static final int CIRCLE_SIZE = 20;
    private static final int LABEL_SIZE = 88;
    private static final int EXTRA_HEIGHT = 150;

    private Color color;

    public CarComponent(Car car, Color color) {
        setOpaque(false);
        setSize(CIRCLE_SIZE + LABEL_SIZE, CIRCLE_SIZE + EXTRA_HEIGHT);

        this.car = car;
        name = car.getName();

        this.color = color;
    }

    @Override
    public void setLocation(Point position) {
        position = (Point) position.clone();

        position.x -= CIRCLE_SIZE / 2;
        position.y -= CIRCLE_SIZE / 2;
        super.setLocation(position);
    }

    public Car getCar() {
        return car;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //FIXME: placeholder
        g.setColor(color);
        g.fillOval(0, 0, CIRCLE_SIZE, CIRCLE_SIZE);
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, CIRCLE_SIZE, CIRCLE_SIZE);
        g.drawString(name, CIRCLE_SIZE, 3 * CIRCLE_SIZE / 4);

        Iterator<StatusEffect> effectIterator = car.getStatusEffects().iterator();

        int i = 0;
        while (effectIterator.hasNext()) {
            g.drawString(effectIterator.next().name, 0, CIRCLE_SIZE + g.getFontMetrics().getHeight() / 2 + (i * g.getFontMetrics().getHeight()));
            ++i;
        }
    }

}
