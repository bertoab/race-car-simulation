//Lior Sapir, Joshua Staub

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

public class CarConfigPanel extends JPanel implements ActionListener {
    private final JTextField nameField;
    private final NumberField speedField;
    private final JButton removeButton;
    private boolean readOnly = false;

    public CarConfigPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        setLayout(new GridBagLayout());

        JLabel carNameLabel = new JLabel("Car Name:");
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.ipadx = 4;
        add(carNameLabel, c);
        nameField = new JTextField(10);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        add(nameField, c);

        JLabel speedLabel = new JLabel("Speed:");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        add(speedLabel, c);

        int numDigits = (int) Math.ceil(Math.log10(Car.MAX_SPEED));
        speedField = new NumberField(0, 0, (int) Car.MAX_SPEED);
        speedField.setColumns(numDigits);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_END;
        add(speedField, c);

        removeButton = new JButton("X");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        add(removeButton, c);
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

    public Car makeCar(int goalTrack, int totalTracks) {
        Car car = new Car(nameField.getText(), (goalTrack + 1) % totalTracks, goalTrack, totalTracks);

        car.setSpeed(speedField.getCurValue());
        return car;
    }
}
