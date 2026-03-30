//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Menu extends JPanel implements ActionListener {
    private static final int INITIAL_NUM_CARS = 6;
    private static final double STEP_TIME = 1.0 / 30.0;

    private static final Color[] POSSIBLE_COLORS = {
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK,
        Color.BLACK, Color.ORANGE, Color.WHITE, Color.GRAY, Color.CYAN
    };

    //FIXME: maybe implement this in RaceTrack instead
    private static final StatusEffect[][] TRACK_EFFECTS = new StatusEffect[][] {
        {StatusEffect.CONCRETE}, {}, {}, {StatusEffect.UPHILL, StatusEffect.SNOW}, {StatusEffect.SNOW},
        {StatusEffect.DOWNHILL, StatusEffect.SNOW}, {StatusEffect.DOWNHILL}, {StatusEffect.SAND}, {StatusEffect.SAND}, {StatusEffect.UPHILL, StatusEffect.SAND}
    };

    private Random rand;

    private final Box carsPane;
    private final MapPanel mapPanel;
    private final RaceTrack raceTrack;
    private final LeaderBoard leaderBoard;

    private int carsFinished;

    private final ArrayList<CarComponent> carComponents;
    private boolean isRaceRunning = false;
    private final Timer timer;
    private double lastTime = 0;
    private double totalTime = 0; //ANDREW: added another attribute to calculate the time elapsed

    public Menu() {
        this(null);
    }

    public Menu(MapPanel mapPanel) {
        rand = new Random();

        this.mapPanel = mapPanel;
        // FIXME: idk if this is the best place to hardcode it or if it should be hardcoded at all but I added the right lengths for now
        this.raceTrack = new RaceTrack(new double[] {
            110.0, 117.0, 69.0, 67.0, 87.0,
            162.0, 115.0, 105.0, 83.0, 55.0,
            },
            null);
        this.leaderBoard = new LeaderBoard();
        this.carComponents = new ArrayList<CarComponent>();

        this.timer = new Timer((int) (STEP_TIME * 1000.0), this);
        timer.setActionCommand("step");

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

        //ANDREW: added LeaderBoard
        leaderBoard.setLocation(10, 10);
        mapPanel.add(leaderBoard);

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

    private void resetRace() {
        isRaceRunning = false;
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
        timer.stop();
        leaderBoard.resetLeaderBoard();

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

            totalTime = 0;
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

                    StatusEffect[] sectionEffects = TRACK_EFFECTS[car.getCurrentTrackIndex()];
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
        } catch (RuntimeException exception) {
            resetRace();

            JOptionPane.showMessageDialog(null, exception.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stepRace() {
        double curTime = Utility.secondsElapsed();
        double timeElapsed = curTime - lastTime;

        if (!isRaceRunning) {
            return;
        }

        for (Car car : raceTrack.getCars()) {
            if (!car.hasFinished()) {
                int curTrack = car.getCurrentTrackIndex();
                double deltaDist = timeElapsed * car.getSpeed();

                StatusEffect[] sectionEffects = TRACK_EFFECTS[curTrack];

                //roll to apply chance effects
                if (!(car.getStatusEffects().contains(StatusEffect.MUDDIED)) && rand.nextInt(500) == 0) {
                    car.addEffect(StatusEffect.MUDDIED);
                }

                if ((car.getStatusEffects().contains(StatusEffect.MUDDIED)) && rand.nextInt(100) == 0) {
                    car.removeEffect(StatusEffect.MUDDIED);
                }

                if (!(car.getStatusEffects().contains(StatusEffect.TIRE_POPPED)) && rand.nextInt(2000) == 0) {
                    car.addEffect(StatusEffect.TIRE_POPPED);
                }

                //apply car status effects
                Iterator<StatusEffect> effectIterator = car.getStatusEffects().iterator();

                while (effectIterator.hasNext()) {
                    deltaDist *= effectIterator.next().multiplier;
                }

                //apply position change
                double totalTrackDist = raceTrack.getTrackSections()[curTrack];
                //FIXME: if this is more than 1, the car will move onto the next track with a speed proportional to the current track
                double newPosition = car.getPosition() + (deltaDist / totalTrackDist);
                car.setPosition(newPosition);

                if (newPosition >= 1.0) {
                    //set new track section effects if the car changed track sections
                    for (int i = 0; i < sectionEffects.length; ++i) {
                        car.removeEffect(sectionEffects[i]);
                    }

                    sectionEffects = TRACK_EFFECTS[car.getCurrentTrackIndex()];
                    for (int i = 0; i < sectionEffects.length; ++i) {
                        car.addEffect(sectionEffects[i]);
                    }

                    if (car.hasFinished()) {
                        carsFinished += 1;
                        leaderBoard.addCarEntry(car, totalTime);
                    }
                }
            }
        }
        lastTime = curTime;

        if (carsFinished == raceTrack.getCars().size()) {
            //TODO: add win screen

            //FIXME: placeholder code
            timer.stop();
            JOptionPane.showMessageDialog(null, "(Placeholder) Race finished!",
                    "(Placeholder) Finished", JOptionPane.INFORMATION_MESSAGE);
        }
        
        totalTime += timeElapsed;
        //FIXME:
        // leaderBoard.calculateCarOrder(raceTrack.getCars(), totalTime);

        redrawRace();
    }

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
}
