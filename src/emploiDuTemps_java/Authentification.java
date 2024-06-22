package emploiDuTemps_java;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class Authentification extends JFrame implements ActionListener {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    Statement st;
    Conneccion con = new Conneccion();
    ResultSet rst;

    public Authentification() {
        setTitle("Interface de Connexion");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\User\\Downloads\\background.png");
                Image backgroundImage = backgroundIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Load the icon image
        ImageIcon iconImage = new ImageIcon("C:\\Users\\User\\Downloads\\login.png");
        Image scaledIconImage = iconImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        iconImage = new ImageIcon(scaledIconImage);

        JLabel iconLabel = new JLabel(iconImage);
        iconLabel.setBounds((800 - 100) / 2, 30, 100, 100); // Center the icon horizontally, 30px from the top
        panel.add(iconLabel);

        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setBounds((800 - 300) / 2 - 100, 150, 80, 30); // Center the login field horizontally
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(Color.decode("#0D9AFC"));
 // Set text color
        panel.add(loginLabel);

        loginField = new JTextField();
        loginField.setBounds((800 - 300) / 2, 150, 300, 30); // Center the login field horizontally
        loginField.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(loginField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds((800 - 300) / 2 - 100, 200, 120, 30); // Center the password field horizontally
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel.setForeground(Color.decode("#0D9AFC")); // Set text color
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds((800 - 300) / 2, 200, 300, 30); // Center the password field horizontally
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(passwordField);

        loginButton = new JButton("Se connecter");
        loginButton.setBounds((800 - 150) / 2, 250, 150, 40); // Center the button horizontally
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        int verif = 0;
        String rq = "SELECT * FROM users WHERE login = '" + login + "' AND password = '" + password + "'";

        try {
            st = con.laConnection().createStatement();
            rst = st.executeQuery(rq);
            if (rst.next()) {
                verif = 1;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur !", null, JOptionPane.ERROR_MESSAGE);
        }

        if (verif == 1) {
            new MenuInterface().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login ou mot de passe incorrect !");
        }
    }

    public static void main(String[] args) {
        // Set a modern look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Authentification();
    }
}
