//Andrew Larrazabal

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LeaderBoard extends JPanel {
    //ATTRIBUTES
    //private final Car[] cars;
    private static final String TITLE = "LeaderBoard\n";
    private JTextArea placements;
    private String resultText;

    //CONSTRUCTORS
    public LeaderBoard() {
        //cars = new Car[4];
        resultText = TITLE;
        placements = new JTextArea();
        placements.setText(resultText);
        add(placements);
    }

    //METHODS
    public void calculateCarOrder(List<Car> cars, double time) {
        
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).hasFinished() && !cars.get(i).getFinishedFlag()) {
                cars.get(i).setFinishedFlag(true);
                resultText += cars.get(i).getName() + " Time: " + String.format("%.2f seconds\n", time);
                placements.setText(resultText);
            }
        }
    }

    public void resetLeaderBoard() {
        resultText = TITLE;
        placements.setText(resultText);
    }

    public String getLeaderBoardText() {
        return resultText;
    }

    public boolean equals(LeaderBoard other) {
        return resultText.equals(other.getLeaderBoardText());
    }

    @Override
    public String toString() {
        String s = "";

        s += resultText; 

        return s;
    }
}
