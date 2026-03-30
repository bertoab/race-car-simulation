//Lior Sapir, Roberto Baez, Joshua Staub

import java.awt.*;
import javax.swing.*;

// Entry point for the program, initializes Swing window and loads data for RaceTrack
public class Main {
    private static final String WINDOW_TITLE = "Race Car Simulation";
    private static final Dimension WINDOW_DIMENSION = new Dimension(800, 486);

    public static void main(String[] args) {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setSize(WINDOW_DIMENSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MapPanel map = new MapPanel();
        RaceTrack raceTrack = new RaceTrack();

        Menu menu = new Menu(map, raceTrack);

        frame.add(map);
        frame.add(menu, BorderLayout.EAST);

        frame.setLocationRelativeTo(null); // center `frame` on the screen
        frame.setVisible(true);
    }
}
