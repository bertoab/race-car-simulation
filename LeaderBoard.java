//Andrew Larrazabal

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LeaderBoard extends JPanel {
    //ATTRIBUTES
    private static final String TITLE = "LeaderBoard\n";
    private JTextArea placements;
    private String resultText;

    private int listNum;

    //CONSTRUCTORS
    public LeaderBoard() {
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));
        listNum = 0;
        //cars = new Car[4];
        resultText = TITLE;
        placements = new JTextArea();
        placements.setFocusable(false);
        placements.setText(resultText);
        placements.setEditable(false);
        add(placements);
        setBackground(placements.getBackground());
        setSize(placements.getPreferredSize());
    }

    //FIXME:
    //METHODS
    // public void calculateCarOrder(List<Car> cars, double time) {
        
    //     for (int i = 0; i < cars.size(); i++) {
    //         if (cars.get(i).hasFinished() && !cars.get(i).getFinishedFlag()) {
    //             cars.get(i).setFinishedFlag(true);
    //             resultText += cars.get(i).getName() + " Time: " + String.format("%.2f seconds\n", time);
    //             placements.setText(resultText);
    //         }
    //     }
    // }

    public void addCarEntry(Car car, double time) {
        listNum += 1;

        resultText += listNum + ". " + car.getName() + " - " + String.format("%.2f seconds\n", time);
        placements.setText(resultText);

        setSize(placements.getPreferredSize());
        revalidate();
    }

    public void resetLeaderBoard() {
        listNum = 0;
        resultText = TITLE;
        placements.setText(resultText);
        setSize(placements.getPreferredSize());
        revalidate();
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
