//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Menu extends JPanel implements ActionListener {
    private static final int INITIAL_NUM_CARS = 6;
    // FIXME: this belongs in RaceTrack, as something like RaceTrack.getTrackCount()
    private static final int TOTAL_TRACKS = 10;
    private static final double STEP_TIME = 1.0;

    private final Box carsPane;
    private final MapPanel mapPanel;
    private final RaceTrack raceTrack;
    private final LeaderBoard leaderBoard;
    // TODO: is this the best way to do this? wouldn't it be better for each CarComponent to just store a reference to its corresponding Car?
    private final HashMap<Car, CarComponent> carComponents;
    private boolean isRaceRunning = false;

    public Menu() {
        this(null);
    }

    public Menu(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        // FIXME: placeholder track distances
        this.raceTrack = new RaceTrack(new double[] {
            120.0, 120.0, 120.0, 120.0, 120.0, 120.0,
            120.0, 120.0, 120.0, 120.0, 120.0, 120.0,
        }, null);
        // TODO: add leaderboard component to view when complete
        this.leaderBoard = new LeaderBoard();
        this.carComponents = new HashMap<>();

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

        JButton stepButton = new JButton("Step");
        stepButton.setActionCommand("step");
        stepButton.addActionListener(this);
        top.add(stepButton);

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
        if (isRaceRunning) {
            return;
        }

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
        raceTrack.clearCars();
        carComponents.clear();
        mapPanel.removeAll();
        mapPanel.repaint();

        for (Component comp : carsPane.getComponents()) {
            if (comp instanceof CarConfigPanel configPanel) {
                configPanel.setReadOnly(false);
            }
        }
    }

    private void startRace() {
        if (isRaceRunning) {
            return;
        }

        try {
            raceTrack.clearCars();
            carComponents.clear();
            mapPanel.removeAll();

            int carIndex = 0;
            for (Component comp : carsPane.getComponents()) {
                if (comp instanceof CarConfigPanel configPanel) {
                    if (carIndex >= TOTAL_TRACKS) {
                        String message = String.format("Cannot add more than %d cars.\n", TOTAL_TRACKS);
                        throw new IllegalStateException(message);
                    }

                    configPanel.setReadOnly(true);

                    Car car = configPanel.makeCar(carIndex, TOTAL_TRACKS);
                    raceTrack.addCar(car);

                    CarComponent mapCar = new CarComponent(car.getName());
                    mapPanel.add(mapCar);
                    carComponents.put(car, mapCar);
                    mapCar.setLocation((int)mapPanel.getLocationCoords(carIndex).getX(), (int)mapPanel.getLocationCoords(carIndex).getY());

                    carIndex++;
                }
            }

            if (carIndex < 2) {
                throw new IllegalStateException("Need at least 2 cars to start the race!");
            }

            redrawRace();
            isRaceRunning = true;
        } catch (RuntimeException exception) {
            resetRace();

            JOptionPane.showMessageDialog(null, exception.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stepRace(double timeElapsed) {
        if (!isRaceRunning) {
            return;
        }

        for (Car car : raceTrack.getCars()) {
            if (!car.hasFinished()) {
                int curTrack = car.getCurrentTrackIndex();
                double deltaDist = timeElapsed * car.getSpeed();

                double totalTrackDist = raceTrack.getTrackSections()[curTrack];

                // FIXME: if this is more than 1, the car will move onto the next track with a speed proportional to the current track
                double newPosition = car.getPosition() + (deltaDist / totalTrackDist);
                car.setPosition(newPosition);
            }
        }

        // TODO: detect when race is over
        leaderBoard.calculateCarOrder(raceTrack.getCars());
        redrawRace();
    }

    private void redrawRace() {
        for (Map.Entry<Car, CarComponent> entry : carComponents.entrySet()) {
            Car car = entry.getKey();
            CarComponent carComponent = entry.getValue();

            Point p1 = mapPanel.getLocationCoords(car.getCurrentTrackIndex());
            Point p2 = mapPanel.getLocationCoords((car.getCurrentTrackIndex() + 1) % TOTAL_TRACKS);

            carComponent.setLocation(Utility.lerp(p1, p2, car.getPosition()));
        }

        mapPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "add_car" -> addConfigPanel();
            case "run_race" -> startRace();
            case "reset" -> resetRace();
            case "step" -> stepRace(STEP_TIME);
        }
    }
}
