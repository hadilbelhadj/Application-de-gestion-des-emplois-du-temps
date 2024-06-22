package emploiDuTemps_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuEtud extends JFrame {

    public MenuEtud() {
        setTitle("Menu Principal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Utiliser ImagePanel comme panel principal avec une image de fond
        ImagePanel mainPanel = new ImagePanel(new ImageIcon("C:\\\\Users\\\\User\\\\Downloads\\\\background.png").getImage());
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // Ajouter le logo en haut à droite
        ImageIcon originalLogoIcon = new ImageIcon("C:\\Users\\User\\Downloads\\emploiDuTemps_java\\src\\emploiDuTemps_java\\logo.png");
        Image originalLogoImage = originalLogoIcon.getImage();
        Image scaledLogoImage = originalLogoImage.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setHorizontalAlignment(JLabel.RIGHT);

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel, BorderLayout.EAST);
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        // Panel pour les boutons au centre avec GridBagLayout pour centrer les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        

     // Charger l'icône
        ImageIcon icon = new ImageIcon("C:\\Users\\User\\Downloads\\recherche.png");

        // Redimensionner l'icône
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImg);

        // Créer le bouton avec l'icône redimensionnée
        JButton enseignantButton = createStyledButton("Recherche", newIcon);
        enseignantButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir l'interface de gestion des enseignants
                new Emploi().setVisible(true);
            }
        });
     
        ImageIcon icon1 = new ImageIcon("C:\\\\Users\\\\User\\\\Downloads\\\\parametre.png");

        // Redimensionner l'icône
        Image img1 = icon1.getImage();
        Image newImg1 = img1.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon1 = new ImageIcon(newImg1);

        // Créer le bouton avec l'icône redimensionnée
        JButton parametreButton = createStyledButton("Parametre", newIcon1);
        parametreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir l'interface de gestion des enseignants
                new etudiant().setVisible(true);
            }
        });


        buttonPanel.add(enseignantButton, gbc);
        buttonPanel.add(parametreButton, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, ImageIcon icon) {
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(300, 80));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 149, 237));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuEtud().setVisible(true);
            }
        });
    }
}
