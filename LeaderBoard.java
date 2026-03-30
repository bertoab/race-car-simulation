//Andrew Larrazabal

import javax.swing.JPanel;
import javax.swing.JTextArea;

//Displays the ranking of each Car in the race
public class LeaderBoard extends JPanel {
    //ATTRIBUTES
    private static final String TITLE = "LeaderBoard\n";
    private final JTextArea placements;
    private String resultText;
    private int listNum;

    //CONSTRUCTORS
    public LeaderBoard() {
        this(0);
    }

    public LeaderBoard(int listNum) {
        this.listNum = listNum;
        resultText = TITLE;
        placements = new JTextArea();
        placements.setFocusable(false);
        placements.setText(resultText);
        placements.setEditable(false);
        add(placements);
        setBackground(placements.getBackground());
        setSize(placements.getPreferredSize());
    }

    //Display final time of each car in the LeaderBoard
    public void addCarEntry(Car car, double time) {
        listNum += 1;

        resultText += listNum + ". " + car.getName() + " - " + String.format("%.2f seconds\n", time);
        placements.setText(resultText);

        setSize(placements.getPreferredSize());
        revalidate();
    }

    //Reset LeaderBoard whenever "Reset" or "Run Race" buttons are pressed
    public void resetLeaderBoard() {
        listNum = 0;
        resultText = TITLE;
        placements.setText(resultText);
        setSize(placements.getPreferredSize());
        revalidate();
    }

    //GETTERS
    public String getLeaderBoardText() {
        return resultText;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LeaderBoard)) {
            return false;
        }

        LeaderBoard otherBoard = (LeaderBoard)obj;

        return resultText.equals(otherBoard.getLeaderBoardText());
    }

    @Override
    public String toString() {
        String s = "";

        s += resultText; 

        return s;
    }
}
