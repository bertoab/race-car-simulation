//Lior Sapir

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setSize(700,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Menu menu = new Menu();
        JPanel placeholderMap = new JPanel();
        placeholderMap.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        frame.add(placeholderMap);
        frame.add(menu, BorderLayout.EAST);
        frame.setVisible(true);
    }
}
