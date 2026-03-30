//Lior Sapir, Joshua Staub

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// CarConfigPanel displays the input fields that allow a user to configure the parameters of a Car before each race
public class CarConfigPanel extends JPanel implements ActionListener {
    private final JTextField nameField;
    private final NumberField speedField;
    private final JButton removeButton;
    private boolean readOnly = false;


    public CarConfigPanel() {
        this("Car");
    }

    public CarConfigPanel(String defaultName) {
        // styling and layout
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setLayout(new GridBagLayout());

        // add labels and input fields
        JLabel carNameLabel = new JLabel("Car Name:");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.ipadx = 4;
        c.weightx = 1.0; // ensures that excess horizontal space is placed between label and text fields
        add(carNameLabel, c);
        nameField = new JTextField(9);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        add(nameField, c);

        JLabel speedLabel = new JLabel("Speed:");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 4;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.LINE_START;
        add(speedLabel, c);

        int numDigits = (int) Math.ceil(Math.log10(Car.MAX_SPEED));
        speedField = new NumberField(0, 1, (int) Car.MAX_SPEED);
        speedField.setColumns(numDigits);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        add(speedField, c);

        // adds remove button to upper left corner
        removeButton = new JButton("X");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        add(removeButton, c);

        // sets default values for fields
        speedField.setCurValue(Utility.random.nextInt(20, (int) Car.MAX_SPEED + 1));
        nameField.setText(defaultName);
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        nameField.setEnabled(!readOnly);
        speedField.setEnabled(!readOnly);
        removeButton.setEnabled(!readOnly);
    }

    public boolean getReadOnly() {
        return readOnly;
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(super.getMaximumSize().width, getPreferredSize().height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("remove")) {
            Container parent = getParent();
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }

    // Creates a new Car instance based on the fields of the ConfigPanel
    public Car makeCar(int startTrack, int totalTracks) {
        Car car = new Car(nameField.getText(), startTrack, Math.floorMod(startTrack - 1, totalTracks), totalTracks);

        car.setSpeed(speedField.getCurValue());
        return car;
    }

    @Override
    public String toString() {
        return "CarConfigPanel[name:" + nameField.getText() + ", speed:" + speedField.getCurValue() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CarConfigPanel)) {
            return false;
        }

        CarConfigPanel otherPanel = (CarConfigPanel)obj;

        if (otherPanel.nameField.getText().equals(nameField.getText()) && (otherPanel.speedField.getCurValue() == (speedField.getCurValue()))) {
            return true;
        }

        return false;
    }
}
