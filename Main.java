//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    private static final String WINDOW_TITLE = "Race Car Simulation";
    private static final Dimension WINDOW_DIMENSION = new Dimension(700, 500);

    public static void main(String[] args) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setSize(WINDOW_DIMENSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //FIXME: placeholders
        double[] xRatios = {0.5, 0.8, 0.3};
        double[] yRatios = {0.3, 0.6, 0.6};

        MapPanel map = new MapPanel(new File("map.png"), xRatios, yRatios);

        Menu menu = new Menu();

        frame.add(map);
        frame.add(menu, BorderLayout.EAST);

        frame.setLocationRelativeTo(null); // center `frame` on the screen
        frame.setVisible(true);
    }
}
