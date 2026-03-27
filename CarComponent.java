//Lior Sapir

import javax.swing.*;
import java.awt.*;

public class CarComponent extends JPanel {
    public CarComponent() {
        setOpaque(false);
        //FIXME: placeholder
        setSize(30, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //FIXME: placeholder
        g.setColor(Color.RED);
        g.fillOval(0, 0, getWidth(), getHeight());
    }

}
