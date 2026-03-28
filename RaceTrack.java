//Bandana Kadel
//RaceTrack.java
//This class stores the track sections and the cars racing on them so the program can manage the race


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaceTrack {
    private double[] trackSections; // track section lengths
    private ArrayList<Car> cars; // cars in the race

    // default constructor
    public RaceTrack() {
        trackSections = new double[0];
        cars = new ArrayList<>();
    }

    // constructor
    public RaceTrack(double[] trackSections, Car[] cars) {
        if (trackSections == null) {
            this.trackSections = new double[0];
        } else {
            this.trackSections = trackSections;
        }

        if (cars == null) {
            this.cars = new ArrayList<>();
        } else {
            this.cars = new ArrayList<>(Arrays.asList(cars));
        }
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