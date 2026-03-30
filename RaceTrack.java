//Bandana Kadel
//RaceTrack.java
//This class stores the track sections and the cars racing on them so the program can manage the race

import java.util.ArrayList;
import java.util.List;

public class RaceTrack {
    private double[] trackSections; // track section lengths
    private final ArrayList<Car> cars; // cars in the race

    // constructor
    public RaceTrack(double[] trackSections) {
        this.trackSections = trackSections;
        this.cars = new ArrayList<>();
    }

    // gets track sections
    public double[] getTrackSections() {
        return trackSections;
    }

    // sets track sections
    public void setTrackSections(double[] trackSections) {
        this.trackSections = trackSections;
    }

    // gets cars
    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void clearCars() {
        cars.clear();
    }
}