package emploiDuTemps_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuInterface extends JFrame {

    public MenuInterface() {
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

        JButton enseignantButton = createStyledButton("Enseignant");
        enseignantButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir l'interface de gestion des enseignants
                new MenuEns().setVisible(true);
            }
        });

        JButton etudiantButton = createStyledButton("Etudiant");
        etudiantButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir l'interface de gestion des étudiants
                new MenuEtud().setVisible(true);
            }
        });

        buttonPanel.add(enseignantButton, gbc);
        buttonPanel.add(etudiantButton, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }
    

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(100, 149, 237));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setFocusPainted(false);
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
                new MenuInterface().setVisible(true);
            }
        });
    }
}

