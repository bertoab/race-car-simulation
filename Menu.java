//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Menu extends JPanel implements ActionListener {

    private Box carsPane;
    private MapPanel mapPanel;

    public Menu() {
        this(null);
    }

    public Menu(MapPanel mapPanel) {
        this.mapPanel = mapPanel;

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 1));

        //top pane
        JPanel top = new JPanel();
        JButton addCarButton = new JButton("Add Car");
        addCarButton.setActionCommand("add_car");
        addCarButton.addActionListener(this);
        top.add(addCarButton);

        //bottom pane
        JPanel bottom = new JPanel();

        //car pane section
        carsPane = new Box(BoxLayout.Y_AXIS);
        JScrollPane carsScroll = new JScrollPane(carsPane);

        //add children
        add(top, BorderLayout.NORTH);
        add(carsScroll);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add_car")) {
            //FIXME: placeholder testing code
            Random randGen = new Random();

            //FIXME: eventually replace this statement with an arraylist
            CarComponent mapCar = new CarComponent();

            mapPanel.add(mapCar);
            mapCar.setLocation(randGen.nextInt(mapPanel.getWidth()), randGen.nextInt(mapPanel.getHeight()));
            CarConfigPanel carPanel = new CarConfigPanel(mapCar);
            carsPane.add(carPanel);
            carsPane.revalidate();
        }
    }
}
