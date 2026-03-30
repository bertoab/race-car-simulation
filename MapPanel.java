//Lior Sapir

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

// The MapPanel is responsible for drawing the map, and for converting between track indices and pixel positions on the screen.
public class MapPanel extends JPanel {
    private Image mapImage;
    private final double[] xRatios;
    private final double[] yRatios;

    public MapPanel(File f, double[] xRatios, double[] yRatios) {
        this.xRatios = xRatios;
        this.yRatios = yRatios;

        //remove layout manager so that CarComponent coordinates can be set manually
        setLayout(null);

        //convert the map image file to an Image object
        try {
            mapImage = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Point getLocationCoords(int locationIndex) {
        return new Point((int)(xRatios[locationIndex] * getWidth()), (int)(yRatios[locationIndex] * getHeight()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw the image to match the size of the map panel
        g.drawImage(mapImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
    }

    @Override
    public String toString() {
        return "MapPanel[number of locations: " + xRatios.length + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapPanel)) {
            return false;
        }

        return true;
    }
}
