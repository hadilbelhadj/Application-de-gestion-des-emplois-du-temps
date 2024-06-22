package emploiDuTemps_java;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(Image image) {
        backgroundImage = new ImageIcon(image).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Dessiner l'image de fond
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
