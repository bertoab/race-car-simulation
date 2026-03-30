//Bandana Kadel

import java.util.ArrayList;
import java.util.List;

//This class stores the track sections and the cars racing on them so the program can manage the race
public class RaceTrack {
    private final double[] trackSections; // track section lengths
    private final StatusEffect[][] trackEffects; // array of StatusEffects to be applied at each corresponding track section
    private final ArrayList<Car> cars; // cars in the race

    // constructor
    public RaceTrack(double[] trackSections,  StatusEffect[][] trackEffects) {
        this.trackSections = trackSections;
        this.trackEffects = trackEffects;
        this.cars = new ArrayList<>();
    }

    // gets track sections
    public double[] getTrackSections() {
        return trackSections;
    }

    public StatusEffect[] getEffectsForSection(int sectionIndex) {
        return trackEffects[sectionIndex];
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

    @Override
    public String toString() {
        return "RaceTrack[number of track sections: " + trackSections.length + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RaceTrack)) {
            return false;
        }

        RaceTrack otherCar = (RaceTrack)obj;

        if (otherCar.trackSections.length == trackSections.length) {
            return true;
        }

        return false;
    }
}
