//Lior Sapir, Joshua Staub, Roberto Baez and Andrew Larrazabal

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

// Main container panel for the program. Configures all other components and updates them based on simulation state
public class Menu extends JPanel implements ActionListener {
    // Constants
    private static final int INITIAL_NUM_CARS = 6;
    private static final double STEP_TIME = 1.0 / 30.0;
    private static final Color[] POSSIBLE_COLORS = {
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK,
        Color.BLACK, Color.ORANGE, Color.WHITE, Color.GRAY, Color.CYAN
    };

    // Components
    private final Box carsPane;
    private final MapPanel mapPanel;
    private final LeaderBoard leaderBoard;
    private final JButton addCarButton;
    private final JButton runRaceButton;
    private final JButton resetButton;
    private final ArrayList<CarComponent> carComponents;

    // State
    private final RaceTrack raceTrack;
    private int carsFinished;
    private boolean isRaceRunning = false;

    // Timing
    private final Timer timer;
    private double lastTime = 0;
    private double totalTime = 0; //ANDREW: added another attribute to calculate the time elapsed

    public Menu() {
        this(new MapPanel(), new RaceTrack());
    }
    
    public Menu(MapPanel mapPanel, RaceTrack raceTrack) {
        this.mapPanel = mapPanel;
        this.raceTrack = raceTrack;
        this.leaderBoard = new LeaderBoard();
        this.carComponents = new ArrayList<>();

        this.timer = new Timer((int) (STEP_TIME * 1000.0), this);
        timer.setActionCommand("step");

        // styling
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 1));

        //top pane
        JPanel top = new JPanel();
        addCarButton = new JButton("Add Car");
        addCarButton.setActionCommand("add_car");
        addCarButton.addActionListener(this);
        top.add(addCarButton);

        //ANDREW: added LeaderBoard
        leaderBoard.setLocation(10, 10);
        mapPanel.add(leaderBoard);

        //bottom pane
        JPanel bottom = new JPanel();

        runRaceButton = new JButton("Run Race");
        runRaceButton.setActionCommand("run_race");
        runRaceButton.addActionListener(this);
        bottom.add(runRaceButton);

        resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(this);
        resetButton.setEnabled(false);
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

    // Add a config panel for a new car
    private void addConfigPanel() {
        if (isRaceRunning) {
            return;
        }

        try {
            int carCount = carsPane.getComponentCount() + 1;
            if (carCount > raceTrack.getTrackSections().length) {
                String message = String.format("Cannot add more than %d cars.\n", raceTrack.getTrackSections().length);
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

    // Clears all simulation state and CarComponents
    private void clearRaceData() {
        carsFinished = 0;
        raceTrack.clearCars();
        carComponents.clear();
        for (Component comp : mapPanel.getComponents()) {
            if (comp instanceof CarComponent carComp) {
                mapPanel.remove(carComp);
            }
        }

        mapPanel.revalidate();
        mapPanel.repaint();
        leaderBoard.resetLeaderBoard();
    }

    // Stops the current race and re-enables configuration
    private void resetRace() {
        clearRaceData();
        isRaceRunning = false;
        timer.stop();

        for (Component comp : carsPane.getComponents()) {
            if (comp instanceof CarConfigPanel configPanel) {
                configPanel.setReadOnly(false);
            }
        }

        resetButton.setEnabled(false);
        runRaceButton.setEnabled(true);
        addCarButton.setEnabled(true);
    }

    // Starts a new race
    private void startRace() {
        if (isRaceRunning) {
            return;
        }

        try {
            clearRaceData();

            totalTime = 0;

            //iterate through each existing CarConfigPanel and prepare a corresponding CarComponent and Car
            int carIndex = 0;
            for (Component comp : carsPane.getComponents()) {
                if (comp instanceof CarConfigPanel configPanel) {
                    if (carIndex >= raceTrack.getTrackSections().length) {
                        String message = String.format("Cannot add more than %d cars.\n", raceTrack.getTrackSections().length);
                        throw new IllegalStateException(message);
                    }

                    configPanel.setReadOnly(true);

                    Car car = configPanel.makeCar(carIndex, raceTrack.getTrackSections().length);
                    raceTrack.addCar(car);

                    StatusEffect[] sectionEffects = raceTrack.getEffectsForSection(car.getCurrentTrackIndex());
                    for (int i = 0; i < sectionEffects.length; ++i) {
                        car.addEffect(sectionEffects[i]);
                    }

                    CarComponent mapCar = new CarComponent(car, POSSIBLE_COLORS[carIndex]);
                    mapPanel.add(mapCar);
                    carComponents.add(mapCar);
                    mapCar.setLocation((int)mapPanel.getLocationCoords(carIndex).getX(), (int)mapPanel.getLocationCoords(carIndex).getY());

                    carIndex++;
                }
            }

            if (carIndex < 2) {
                throw new IllegalStateException("Need at least 2 cars to start the race!");
            }

            redrawRace();
            isRaceRunning = true;
            timer.start();
            leaderBoard.resetLeaderBoard(); //ANDREW: reset leaderboard when race starts
            lastTime = Utility.secondsElapsed();

            addCarButton.setEnabled(false);
            runRaceButton.setEnabled(false);
            resetButton.setEnabled(true);
        } catch (RuntimeException exception) {
            resetRace();

            JOptionPane.showMessageDialog(null, exception.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Updates the race simulation
    private void stepRace() {
        double curTime = Utility.secondsElapsed();
        double timeElapsed = curTime - lastTime;

        if (!isRaceRunning) {
            return;
        }

        //iterate through each Car object to update its status effects and position
        for (Car car : raceTrack.getCars()) {
            if (!car.hasFinished()) {
                int curTrack = car.getCurrentTrackIndex();
                double deltaDist = timeElapsed * car.getSpeed();

                StatusEffect[] sectionEffects = raceTrack.getEffectsForSection(curTrack);

                //roll to apply chance effects
                for (StatusEffect effect : StatusEffect.values()) {
                    // this is not mathematically accurate to how probability actually works, but it's good enough
                    double startProb = effect.startChancePerSec * timeElapsed;
                    double endProb = effect.endChancePerSec * timeElapsed;

                    if (!car.hasEffect(effect) && Utility.random.nextDouble() < startProb) {
                        car.addEffect(effect);
                    } else if (car.hasEffect(effect) && Utility.random.nextDouble() < endProb) {
                        car.removeEffect(effect);
                    }
                }

                //apply car status effects
                Iterator<StatusEffect> effectIterator = car.getStatusEffects().iterator();

                while (effectIterator.hasNext()) {
                    deltaDist *= effectIterator.next().speedMultiplier;
                }

                //apply position change
                double totalTrackDist = raceTrack.getTrackSections()[curTrack];
                double newPosition = car.getPosition() + (deltaDist / totalTrackDist);
                car.setPosition(newPosition);

                if (newPosition >= 1.0) {
                    //set new track section effects if the car changed track sections
                    for (int i = 0; i < sectionEffects.length; ++i) {
                        car.removeEffect(sectionEffects[i]);
                    }

                    sectionEffects = raceTrack.getEffectsForSection(car.getCurrentTrackIndex());
                    for (int i = 0; i < sectionEffects.length; ++i) {
                        car.addEffect(sectionEffects[i]);
                    }

                    //add any finished cars to leaderboard
                    if (car.hasFinished()) {
                        carsFinished += 1;
                        leaderBoard.addCarEntry(car, totalTime);
                        car.clearEffects();
                    }
                }
            }
        }
        lastTime = curTime;

        if (carsFinished == raceTrack.getCars().size()) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "Race finished!",
                    "Finished", JOptionPane.INFORMATION_MESSAGE);
        }
        
        totalTime += timeElapsed;

        redrawRace();
    }

    // Repositions and repaints all CarComponents
    private void redrawRace() {
        for (CarComponent carComponent : carComponents) {
            Car car = carComponent.getCar();

            Point p1 = mapPanel.getLocationCoords(car.getCurrentTrackIndex());
            Point p2 = mapPanel.getLocationCoords((car.getCurrentTrackIndex() + 1) % raceTrack.getTrackSections().length);

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
            case "step" -> stepRace();
        }
    }

    @Override
    public String toString() {
        return "Menu[number of CarConfigPanels: " + carsPane.getComponentCount() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Menu)) {
            return false;
        }

        Menu otherMenu = (Menu)obj;

        if (otherMenu.carsPane.getComponentCount() == (carsPane.getComponentCount())) {
            return true;
        }

        return false;
    }
}
