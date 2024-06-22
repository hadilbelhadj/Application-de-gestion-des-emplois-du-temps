package emploiDuTemps_java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Enseignant extends JFrame {
    Statement st;
    Conneccion con = new Conneccion();
    ResultSet rst;
    JTable table, table2;
    JScrollPane scroll, scroll2;
    JLabel lbtitre, lbtitre2, lbmatricule, lbnom, lbcontact, lbclasse, lbmatiere, lbjour, lbheure, lbmatri_ens;
    JTextField tfmatricule, tfnom, tfcontact, tfmatiere, tfmatri_ens;
    JButton btrech, btenrg, btmodif, btsupp, btenrg2, btreq;
    JComboBox comboclasse, combojour, comboheure;

    public Enseignant() {
        this.setTitle("Enseignant");
        this.setSize(900, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        JPanel pn = new JPanel();
        pn.setLayout(null);
        add(pn);

        pn.setBackground(new Color(173, 216, 230));

        // Create a button or label with a home icon
        ImageIcon homeIcon = new ImageIcon("C:\\Users\\User\\Downloads\\icon.png"); // Replace with your icon's path
        Image img = homeIcon.getImage();
        Image newImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(newImg);
        JButton homeButton = new JButton(homeIcon);
        homeButton.setBorderPainted(false);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.setOpaque(false);
        homeButton.setBounds(850, 10, 30, 30); // Positioning to the top-right corner

        // Add ActionListener to the button
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Action to return to the main page
                dispose(); // Close current window
                new MenuInterface().setVisible(true); // Open the main menu again
            }
        });

        // Add the home button to the panel
        pn.add(homeButton);

        lbtitre = new JLabel("Formulaire d'enregistrement des enseignants");
        lbtitre.setBounds(20, 10, 1400, 30);
        lbtitre.setFont(new Font("Arial", Font.BOLD, 18));
        pn.add(lbtitre);

        // Matricule
        lbmatricule = new JLabel("Matricule");
        lbmatricule.setBounds(60, 50, 333, 25);
        lbmatricule.setFont(new Font("Arial", Font.BOLD, 16));
        pn.add(lbmatricule);

        tfmatricule = new JTextField();
        tfmatricule.setBounds(143, 50, 100, 25);
        pn.add(tfmatricule);

        // bouton recherche matricule
        btrech = new JButton("CHERCHER");
        btrech.setBounds(245, 50, 222, 25);
        btrech.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String matricule = tfmatricule.getText(), nom = tfnom.getText(), contact = tfcontact.getText();
                // nom like '%$rech%'
                String rq = "select * from tb_enseignant where nom like '%" + nom + "%'";
                try {
                    st = con.laConnection().createStatement();
                    rst = st.executeQuery(rq);
                    if (rst.next()) {
                        tfmatricule.setText(rst.getString("matricule"));
                        tfnom.setText(rst.getString("nom"));
                        tfcontact.setText(rst.getString("contact"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Enregistrement inexistant!", null, JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!", null, JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
            
            
            
        });
        pn.add(btrech);

        // nom
        lbnom = new JLabel("Nom");
        lbnom.setBounds(91, 80, 1500, 25);
        lbnom.setFont(new Font("Arial", Font.BOLD, 16));
        pn.add(lbnom);

        tfnom = new JTextField();
        tfnom.setBounds(143, 80, 200, 25);
        pn.add(tfnom);

        // contact
        lbcontact = new JLabel("Contact");
        lbcontact.setBounds(68, 110, 1500, 25);
        lbcontact.setFont(new Font("Arial", Font.BOLD, 16));
        pn.add(lbcontact);

        tfcontact = new JTextField();
        tfcontact.setBounds(143, 110, 200, 25);
        pn.add(tfcontact);

        // bouton enregistrement enseignant
        btenrg = new JButton("ENREGISTRER");
        btenrg.setBounds(35, 160, 120, 25);
        btenrg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String matricule, nom, contact;
                matricule = tfmatricule.getText();
                nom = tfnom.getText();
                contact = tfcontact.getText();
                String rq1 = "insert into tb_enseignant(matricule,nom,contact) values('" + matricule + "','" + nom + "','" + contact + "')";
                try {
                    st = con.laConnection().createStatement();
                    if (!matricule.equals("") && !nom.equals("") && !contact.equals("")) {
                        st.executeUpdate(rq1);
                        JOptionPane.showMessageDialog(null, "Insertion reussie!", null, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Completez le formulaire!", null, JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!", null, JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                Enseignant crs = new Enseignant();
                crs.setVisible(true);
            }
        });
        pn.add(btenrg);

        // bouton modification enseignant
        btmodif = new JButton("MODIFIER");
        btmodif.setBounds(170, 160, 120, 25);
        btmodif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String matricule = tfmatricule.getText(), nom = tfnom.getText(), contact = tfcontact.getText();
                String rq = "update tb_enseignant set nom='" + nom + "',contact='" + contact + "' where matricule='" + matricule + "'";
                try {
                    st = con.laConnection().createStatement();
                    if (!matricule.equals("") && !nom.equals("") && !contact.equals("")) {
                        st.executeUpdate(rq);
                        JOptionPane.showMessageDialog(null, "Modification reussie!", null, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Completez le formulaire!", null, JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!", null, JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                Enseignant crs = new Enseignant();
                crs.setVisible(true);
            }
        });
        pn.add(btmodif);

        // bouton suppression enseignant
        btsupp = new JButton("SUPPRIMER");
        btsupp.setBounds(100, 200, 120, 25);
        btsupp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                String matricule = tfmatricule.getText();
                // nom=tfnom.getText(),
                // contact=tfcontact.getText();
                String rq = "delete from tb_enseignant where matricule='" + matricule + "'";
                try {
                    st = con.laConnection().createStatement();
                    if (JOptionPane.showConfirmDialog(null, "Voulez vous supprimer? ", null, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        st.executeUpdate(rq);
                        JOptionPane.showMessageDialog(null, "Suppression reussie!", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur!", null, JOptionPane.ERROR_MESSAGE);
                }
                dispose();
                Enseignant crs = new Enseignant();
                crs.setVisible(true);
            }
        });
        pn.add(btsupp);

        // liste enseignant
        DefaultTableModel df = new DefaultTableModel();
        init();
        pn.add(scroll);
        df.addColumn("Matricule");
        df.addColumn("Nom");
        df.addColumn("Contact");
        table.setModel(df);
        String rq = "select * from tb_enseignant order by nom";
        try {
            st = con.laConnection().createStatement();
            rst = st.executeQuery(rq);
            while (rst.next()) {
                df.addRow(new Object[] { rst.getString("matricule"), rst.getString("nom"), rst.getString("contact") });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur !", null, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void init() {
        table = new JTable();
        scroll = new JScrollPane();
        // Define the column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Matricule");
        model.addColumn("Nom");
        model.addColumn("Contact");

        // Add mock data to the table
        model.addRow(new Object[]{"ens3", "AHIMAT IDRISS", "664280533 / 99354120"});
        model.addRow(new Object[]{"ens10", "ALI", "2344566"});
        model.addRow(new Object[]{"ens8", "ALLADOU ADAM CHRISTOPHE", "66909170"});
        model.addRow(new Object[]{"ens6", "ALLASSA NESTOR", "66801199"});
        model.addRow(new Object[]{"ens2", "CEPHAS DIGBE", "66284152"});
        model.addRow(new Object[]{"ens5", "DOUMBE RODRIGUE", "62080911"});
        model.addRow(new Object[]{"ens4", "NGOUBE JEROME", "66919419"});
        model.addRow(new Object[]{"ens1", "ISABAT BLAISE", "66814315"});
        model.addRow(new Object[]{"ens7", "TOMITE FERDINAND", "66478901"});

        table.setModel(model);
        scroll.setBounds(140, 250, 620, 200);
        scroll.setViewportView(table);
    }
    private void init2() {
        table2 = new JTable();
        scroll2 = new JScrollPane();
        scroll2.setBounds(320, 300, 540, 200);
        scroll2.setViewportView(table2);
    }


    
    
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Enseignant cr = new Enseignant();
        cr.setVisible(true);
        /* Code écrit du 26 au 28 Mars 2021 à N'djaména au Tchad par TARGOTO CHRISTIAN
         * Contact: ct@chrislink.net / 23560316682 */
    }
}
