package emploiDuTemps_java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class etudiant extends JFrame {
    private Statement st;
    private Conneccion con = new Conneccion();
    private ResultSet rst;
    private JTable table2;
    private JScrollPane scroll2;
    private JLabel lbTitle, lbClasse, lbMatiere, lbJour, lbHeure, lbMatriEnseignant;
    private JTextField tfMatiere, tfMatriEnseignant;
    private JButton btEnregistrer, btRequetes;
    private JComboBox<String> comboClasse, comboJour, comboHeure;

    public etudiant() {
        setTitle("Etudiants");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(173, 216, 230)); // Couleur bleu clair pour le background
        add(panel);

        setupUI(panel);

        setVisible(true);
    }

    private void setupUI(JPanel panel) {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(new Color(173, 216, 230)); // Couleur bleu clair pour le formPanel
        panel.add(formPanel, BorderLayout.NORTH);

        lbTitle = new JLabel("Formulaire d'enregistrement des séances de cours");
        lbTitle.setFont(new Font("Arial", Font.BOLD, 17));
        lbTitle.setBounds(120, 5, 1400, 30);
        formPanel.add(lbTitle);
        formPanel.add(new JLabel()); // Placeholder for grid alignment

        lbClasse = new JLabel("Classe");
        lbClasse.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lbClasse);

        comboClasse = new JComboBox<>(new String[]{"", "6eme", "5eme", "4eme", "3eme", "2nde L", "2nde S", "1ere L", "1ere S", "TA", "TD", "TC"});
        formPanel.add(comboClasse);

        lbMatiere = new JLabel("Matière");
        lbMatiere.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lbMatiere);

        tfMatiere = new JTextField();
        formPanel.add(tfMatiere);

        lbJour = new JLabel("Jour");
        lbJour.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lbJour);

        comboJour = new JComboBox<>(new String[]{"", "LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"});
        formPanel.add(comboJour);

        lbHeure = new JLabel("Heure");
        lbHeure.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lbHeure);

        comboHeure = new JComboBox<>(new String[]{"", "1ere H", "2eme H", "3eme H", "4eme H", "5eme H", "6eme H", "1ere et 2eme H", "3eme et 4eme H", "5eme et 6eme H", "2eme et 3eme H", "4eme et 5eme H"});
        formPanel.add(comboHeure);

        lbMatriEnseignant = new JLabel("Matricule enseignant");
        lbMatriEnseignant.setFont(new Font("Arial", Font.BOLD, 16));
        formPanel.add(lbMatriEnseignant);

        tfMatriEnseignant = new JTextField();
        formPanel.add(tfMatriEnseignant);

        btEnregistrer = new JButton("ENREGISTRER");
        btEnregistrer.addActionListener(this::enregistrerAction);
        formPanel.add(btEnregistrer);

        btRequetes = new JButton("REQUETES");
        btRequetes.addActionListener(e -> {
            Requetes requetes = new Requetes();
            requetes.setVisible(true);
        });
        formPanel.add(btRequetes);

        // Setup table
        setupTable(panel);
    }

    private void setupTable(JPanel panel) {
        table2 = new JTable();
        scroll2 = new JScrollPane(table2);
        panel.add(scroll2, BorderLayout.CENTER);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Classe");
        model.addColumn("Matiere");
        model.addColumn("Jour");
        model.addColumn("Heure");
        model.addColumn("Enseignant");
        table2.setModel(model);

        loadDataIntoTable(model);
    }

    private void loadDataIntoTable(DefaultTableModel model) {
        String query = "SELECT * FROM tb_cours ORDER BY id DESC";
        try {
            st = con.laConnection().createStatement();
            rst = st.executeQuery(query);
            while (rst.next()) {
                model.addRow(new Object[]{
                        rst.getString("classe"),
                        rst.getString("matiere"),
                        rst.getString("jour"),
                        rst.getString("heure"),
                        rst.getString("matricule_ens")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des données!", query, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enregistrerAction(ActionEvent e) {
        String classe = comboClasse.getSelectedItem().toString();
        String matiere = tfMatiere.getText();
        String jour = comboJour.getSelectedItem().toString();
        String heure = comboHeure.getSelectedItem().toString();
        String matriEns = tfMatriEnseignant.getText();

        if (!matiere.isEmpty() && !classe.isEmpty() && !jour.isEmpty() && !heure.isEmpty()) {
            String insertQuery = "INSERT INTO tb_cours(classe, matiere, jour, heure, matricule_ens) VALUES('" + classe + "','" + matiere + "','" + jour + "','" + heure + "','" + matriEns + "')";
            try {
                st = con.laConnection().createStatement();
                st.executeUpdate(insertQuery);
                JOptionPane.showMessageDialog(null, "Insertion réussie!", null, JOptionPane.INFORMATION_MESSAGE);
                updateDayNumbers();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion des données!", insertQuery, JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez compléter tous les champs du formulaire!", matriEns, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDayNumbers() {
        try {
            String[] days = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"};
            for (int i = 0; i < days.length; i++) {
                String updateQuery = "UPDATE tb_cours SET num_jour=" + (i + 1) + " WHERE jour='" + days[i] + "'";
                st.executeUpdate(updateQuery);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour des jours!", getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        etudiant cr = new etudiant();
        cr.setVisible(true);
    }
}
