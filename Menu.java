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

    public Menu() {
        this(null);
    }

    public Menu(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        cars = new ArrayList<Car>();
        carStartTracks = new HashSet<Integer>();

        // do Model stuff: create initial Cars Array
        for (int i = 0; i < INITIAL_NUM_CARS; i++) {
            cars.add(createRandomizedCar());
        }

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("add_car")) {
            try {
                Car newCar = createRandomizedCar();
                cars.add(newCar);
                //FIXME: uncomment when method exists in MapPanel
                //mapPanel.update(cars);
                //FIXME: placeholder testing code

                Random randGen = new Random();

                //FIXME: eventually replace this statement with an arraylist
                CarComponent mapCar = new CarComponent();

                mapPanel.add(mapCar);
                mapCar.setLocation(randGen.nextInt(mapPanel.getWidth()), randGen.nextInt(mapPanel.getHeight()));

                CarConfigPanel carPanel = new CarConfigPanel(mapCar);
                carsPane.add(carPanel);
                carsPane.revalidate();
            } catch (IllegalStateException exception) {
              String message = String.format("Cannot add more than %d cars.\n", TOTAL_TRACKS);
              String title = "Error";
               JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Car createRandomizedCar() {
        int totalTracks = LAST_TRACK_INDEX + 1;
        // ensures every car starts and stops on a different stop
        if (cars.size() >= totalTracks)
           throw new IllegalStateException("Maximum number of Cars for RaceTrack");

        String name = String.format("Car %d", cars.size() + 1);

        Random randGen = new Random();
        int startTrackIndex = randGen.nextInt(TOTAL_TRACKS);
        while (carStartTracks.contains(startTrackIndex))
          startTrackIndex = randGen.nextInt(TOTAL_TRACKS);
        carStartTracks.add(startTrackIndex);

        int goalTrackIndex = (startTrackIndex + LAST_TRACK_INDEX) % totalTracks; //FIXME

        return new Car(name, startTrackIndex, goalTrackIndex, totalTracks);
    }
}
