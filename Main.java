//Lior Sapir

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final String WINDOW_TITLE = "Race Car Simulation";
    private static final Dimension WINDOW_DIMENSION = new Dimension(700, 700);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Race Car Simulation");
        frame.setSize(WINDOW_DIMENSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menu = new Menu();
        JPanel placeholderMap = new JPanel();
        placeholderMap.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        frame.add(placeholderMap);
        frame.add(menu, BorderLayout.EAST);

        frame.setLocationRelativeTo(null); // center `frame` on the screen
        frame.setVisible(true);
    }
}
