//Andrew Larrazabal

import java.util.List;

public class LeaderBoard {
    //ATTRIBUTES
    private final Car[] cars;

    //CONSTRUCTORS
    public LeaderBoard() {
        cars = new Car[4];
    }

    public LeaderBoard(Car[] cars) {
        this.cars = cars;
    }

    //METHODS
    public void calculateCarOrder(List<Car> cars) {
        
    }

    public boolean equals(Car[] other) {

        for (int i = 0; i < cars.length; i++) {
            if ( !cars[i].equals(other[i]) || cars.length != other.length ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        String s = "";

        for (int i = 0; i < cars.length; i++) {
            s += "Car " + (i + 1) + ": " + cars[i] + "\n";
        }

        return s;
    }
}
