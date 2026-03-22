//Lior Sapir

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarConfigPanel extends JPanel implements ActionListener {
    
    public CarConfigPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        add(new JTextField("placeholder"));
        JButton removeButton = new JButton("X");
        removeButton.addActionListener(this);
        add(removeButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Container parent = getParent(); 
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }
}
