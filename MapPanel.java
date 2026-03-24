//Lior Sapir

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MapPanel extends JPanel {

    Image mapImage;

    public MapPanel(File f) {
        //remove layout manager so that CarComponent coordinates can be set manually
        setLayout(null);

        //convert the map image file to an Image object
        try {
            mapImage = ImageIO.read(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //draw the image to match the size of the map panel
        g.drawImage(mapImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
    }
}
