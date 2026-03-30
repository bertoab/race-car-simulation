//Andrew Larrazabal

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LeaderBoard extends JPanel {
    //ATTRIBUTES
    private static final String TITLE = "LeaderBoard\n";
    private final JTextArea placements;
    private String resultText;

    private int listNum;

    //CONSTRUCTORS
    public LeaderBoard() {
        listNum = 0;
        resultText = TITLE;
        placements = new JTextArea();
        placements.setFocusable(false);
        placements.setText(resultText);
        placements.setEditable(false);
        add(placements);
        setBackground(placements.getBackground());
        setSize(placements.getPreferredSize());
    }

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
