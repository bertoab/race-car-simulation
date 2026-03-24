//Lior Sapir

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class CarConfigPanel extends JPanel implements ActionListener, DocumentListener {
    private final JTextField carName;
    private final JSlider speedSlider;
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
        add(carNameLabel, c);
        carName = new JTextField(10);
        carName.getDocument().addDocumentListener(this);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_END;
        add(carName, c);

        JLabel speedLabel = new JLabel("Speed:");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        add(speedLabel, c);
        speedSlider = new JSlider(JSlider.HORIZONTAL, 0, (int) Car.MAX_SPEED, 0);
        c.gridx = 1;
        add(speedSlider, c);

        removeButton = new JButton("X");
        c.gridx = 1;
        c.gridy = 0;
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        add(removeButton);
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;

        carName.setEnabled(!readOnly);
        speedSlider.setEnabled(!readOnly);
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
        Car car = new Car(carName.getText(), (goalTrack + 1) % totalTracks, goalTrack, totalTracks);
        car.setSpeed(speedSlider.getValue());
        return car;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
