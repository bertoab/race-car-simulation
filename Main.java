//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    private static final String WINDOW_TITLE = "Race Car Simulation";
    private static final Dimension WINDOW_DIMENSION = new Dimension(800, 486);

    public static void main(String[] args) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setSize(WINDOW_DIMENSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //TODO: move into config file, calculate from pixel coordinates instead of hardcoding
        double[] xRatios = {0.7052, 0.7104, 0.4592, 0.4417, 0.2865, 0.3969, 0.3417, 0.5573, 0.8031, 0.825};
        double[] yRatios = {0.4681, 0.2042, 0.0792, 0.2431, 0.2806, 0.4569, 0.8403, 0.6708, 0.7236, 0.525};
        double[] trackLengths = {110.0, 117.0, 69.0, 67.0, 87.0, 162.0, 115.0, 105.0, 83.0, 55.0};

        MapPanel map = new MapPanel(new File("map.png"), xRatios, yRatios);
        RaceTrack raceTrack = new RaceTrack(trackLengths);

        Menu menu = new Menu(map, raceTrack);

        frame.add(map);
        frame.add(menu, BorderLayout.EAST);

        frame.setLocationRelativeTo(null); // center `frame` on the screen
        frame.setVisible(true);
    }
}
