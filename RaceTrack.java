//Bandana Kadel
//RaceTrack.java
//This class stores the track sections and the cars racing on them so the program can manage the race


public class RaceTrack {
    private double[] trackSections; // track section lengths
    private Car[] cars; // cars in the race

    // default constructor
    public RaceTrack() {
        trackSections = new double[0];
        cars = new Car[0];
    }

    // constructor
    public RaceTrack(double[] trackSections, Car[] cars) {
        if (trackSections == null) {
            this.trackSections = new double[0];
        } else {
            this.trackSections = trackSections;
        }

        if (cars == null) {
            this.cars = new Car[0];
        } else {
            this.cars = cars;
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
    public Car[] getCars() {
        return cars;
    }

    // sets cars
    public void setCars(Car[] cars) {
        this.cars = cars;
    }
}