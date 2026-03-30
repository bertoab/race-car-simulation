//Lior Sapir

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

// The MapPanel is responsible for drawing the map, and for converting between track indices and pixel positions on the screen.
public class MapPanel extends JPanel {
    private Image mapImage;
    private final double[] xRatios;
    private final double[] yRatios;

    public MapPanel() {
        this(
            new File("map.png"), 
            new double[] {0.7052, 0.7104, 0.4592, 0.4417, 0.2865, 0.3969, 0.3417, 0.5573, 0.8031, 0.825}, 
            new double[] {0.4681, 0.2042, 0.0792, 0.2431, 0.2806, 0.4569, 0.8403, 0.6708, 0.7236, 0.525}
        );
    }

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
        return "MapPanel";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapPanel)) {
            return false;
        }

        return true;
    }
}
