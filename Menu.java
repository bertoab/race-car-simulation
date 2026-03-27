//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;

public class Menu extends JPanel implements ActionListener {
    private static final int INITIAL_NUM_CARS = 6;
    private static final int TOTAL_TRACKS = 10;
    private static final int LAST_TRACK_INDEX = TOTAL_TRACKS - 1;

    private Box carsPane;
    private MapPanel mapPanel;
    /*
     * FIXME: `cars` should be of RaceTrack type
     * currently, RaceTrack isn't implemented. however, even
     * if it was, it does not support an array of variable size,
     * thus it is incompatible with the behavior of the "add_car"
     * button
     */
    private ArrayList<Car> cars;
    private HashSet<Integer> carStartTracks;
    private boolean isRaceRunning = false;

    public Menu() {
        this(null);
    }

    public Menu(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        cars = new ArrayList<Car>();
        carStartTracks = new HashSet<Integer>();

        // styling
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 1));

        //top pane
        JPanel top = new JPanel();
        JButton addCarButton = new JButton("Add Car");
        addCarButton.setActionCommand("add_car");
        addCarButton.addActionListener(this);
        top.add(addCarButton);

        //bottom pane
        JPanel bottom = new JPanel();

        JButton runRaceButton = new JButton("Run Race");
        runRaceButton.setActionCommand("run_race");
        runRaceButton.addActionListener(this);
        bottom.add(runRaceButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(this);
        bottom.add(resetButton);

        //car pane section
        carsPane = new Box(BoxLayout.Y_AXIS);
        JScrollPane carsScroll = new JScrollPane(carsPane);

        //add children
        add(top, BorderLayout.NORTH);
        add(carsScroll);
        add(bottom, BorderLayout.SOUTH);

        for (int i = 0; i < INITIAL_NUM_CARS; i++) {
            addConfigPanel();
        }
    }

    private void addConfigPanel() {
        try {
            int carCount = carsPane.getComponentCount() + 1;
            if (carCount >= TOTAL_TRACKS) {
                String message = String.format("Cannot add more than %d cars.\n", TOTAL_TRACKS);
                throw new IllegalStateException(message);
            }

            CarConfigPanel carPanel = new CarConfigPanel(String.format("Car %d", carCount));
            carsPane.add(carPanel);
            carsPane.revalidate();
        } catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetRace() {
        isRaceRunning = false;
        cars.clear();
        mapPanel.removeAll();
        mapPanel.repaint();

        for (Component comp : carsPane.getComponents()) {
            if (comp instanceof CarConfigPanel configPanel) {
                configPanel.setReadOnly(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("add_car")) {
            addConfigPanel();
        } else if (action.equals("run_race")) {
            if (isRaceRunning) {
                return;
            }

            try {
                cars.clear();
                mapPanel.removeAll();
                Random randGen = new Random();

                int carIndex = 0;
                for (Component comp : carsPane.getComponents()) {
                    if (comp instanceof CarConfigPanel configPanel) {
                        if (carIndex >= TOTAL_TRACKS) {
                            String message = String.format("Cannot add more than %d cars.\n", TOTAL_TRACKS);
                            throw new IllegalStateException(message);
                        }

                        configPanel.setReadOnly(true);

                        Car car = configPanel.makeCar(carIndex, TOTAL_TRACKS);
                        cars.add(car);

                        //FIXME: eventually replace this statement with an arraylist
                        CarComponent mapCar = new CarComponent();

                        mapPanel.add(mapCar);
                        mapCar.setLocation(randGen.nextInt(mapPanel.getWidth()), randGen.nextInt(mapPanel.getHeight()));

                        carIndex++;
                    }
                }

                if (carIndex < 2) {
                    throw new IllegalStateException("Need at least 2 cars to start the race!");
                }

                mapPanel.repaint();
                isRaceRunning = true;
            } catch (RuntimeException exception) {
                resetRace();

                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            // TODO: actually run the race
        } else if (action.equals("reset")) {
            resetRace();
        }
    }
}
