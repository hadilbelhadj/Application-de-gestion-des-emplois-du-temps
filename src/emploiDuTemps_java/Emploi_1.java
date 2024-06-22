package emploiDuTemps_java;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
//bibliotheque necessaire pour generer les capture PNG
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class Emploi_1 extends JFrame {
	Statement st;
	Conneccion con=new Conneccion();
	ResultSet rst;
	ResultSet rst1;
	JTable table,table2;
	JScrollPane scroll,scroll2;
	JLabel lbclasse,lbmatiere,lbtitre,lbtitre2,lbid,lbclasse2;
	JTextField tfmatiere,tfid;
	JComboBox comboclasse,comboclasse2;
	JButton btrech,btsupp,btrech2;
    JButton btpng; // Ajout d'un bouton pour générer le PNG
    String prof2,prof,prof1;
    int verif = 0 ;
	public Emploi_1(){
		this.setTitle("Emploi du Temps des enseignant");
		this.setSize(800,500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel pn=new JPanel();
		pn.setLayout(null);
		add(pn);
        pn.setBackground(new Color(173, 216, 230));
		 ImageIcon homeIcon = new ImageIcon("C:\\Users\\User\\Downloads\\icon.png");  // Assurez-vous que le chemin est correct
	        JButton homeButton = new JButton(new ImageIcon(homeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
	        homeButton.setBounds(10, 10, 30, 30);
	        homeButton.setBorderPainted(false);
	        homeButton.setContentAreaFilled(false);
	        homeButton.setFocusPainted(false);
	        homeButton.addActionListener(e -> {
	            dispose();
	            new MenuInterface().setVisible(true);
	        });
	        pn.add(homeButton);
		lbtitre=new JLabel("Les séances de cours dans la semaine par enseignant");
		lbtitre.setBounds(120,5,800,30);
		lbtitre.setFont(new Font("Arial",Font.BOLD,18));
		pn.add(lbtitre);
		

		
		//classe
		lbclasse=new JLabel("Matiere:");
		lbclasse.setBounds(130,40,110,25);
		lbclasse.setFont(new Font("Arial",Font.BOLD,16));
		pn.add(lbclasse);
		
		
		
		
		JTextField tfmatiere = new JTextField();
		JComboBox<String> comboclasse = new JComboBox<>();
		JComboBox<String> comboclasse2 = new JComboBox<>();

		String prof_m = "SELECT matiere FROM `tb_cours` GROUP BY matiere";
		try {
		    st = con.laConnection().createStatement();
		    rst = st.executeQuery(prof_m);
		    comboclasse.addItem("");
		    while (rst.next()) {
		        comboclasse.addItem(rst.getString("matiere"));
		    }
		    // Sélectionner le premier élément par défaut, s'il existe
		    if (comboclasse.getItemCount() > 0) {
		        comboclasse.setSelectedIndex(0);
		        
		    } else {
		        System.out.println("Aucune matière disponible.");
		    }
		    
		} catch (SQLException ex) {
		    JOptionPane.showMessageDialog(null, "Erreur !", null, JOptionPane.ERROR_MESSAGE);
		}
		comboclasse.setBounds(150, 70, 150, 25);
		pn.add(comboclasse);


		// Ajout d'un écouteur d'items pour le JComboBox
		comboclasse.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		             prof = (String) comboclasse.getSelectedItem();
		             if (!prof.isEmpty()) {
		            	 if (verif == 0) {
		             prof1 = prof.toString() ; 
		              verif = verif +1 ;
		            	 }
		            	 else
		            	 {
				             prof2 = prof.toString() ; 

		            	 }
		             }
		            if (!prof.isEmpty()) {
		                String mdispo = "SELECT tb_enseignant.nom FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_cours.matiere = '" + prof + "' GROUP BY tb_enseignant.nom";
		                try {
		                    st = con.laConnection().createStatement();
		                    rst = st.executeQuery(mdispo);
		                    comboclasse.removeAllItems(); // Supprime tous les éléments actuels du JComboBox
		                    comboclasse.addItem(""); // Ajoute l'élément vide
		                    while (rst.next()) {
		                        comboclasse.addItem(rst.getString("nom")); // Ajoute les résultats de la requête à la liste déroulante
		                    }
		                } catch (SQLException ex) {
		                    JOptionPane.showMessageDialog(null, "Erreur !", null, JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        }
		    }
		});

			
		//bouton recherche

		btrech=new JButton("CHERCHER");
		btrech.setBounds(360,70,120,25);
		btrech.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent ev){
		    	 prof = prof1;
// Récupération de la matière sélectionnée
		    		DefaultTableModel df2=new  DefaultTableModel();
				  init2();
				  pn.add(scroll2);
				 df2.addColumn("Jour/Seance");
				 df2.addColumn("1ere");
				 df2.addColumn("2eme");
				 df2.addColumn("3eme");
				 df2.addColumn("4eme");
				 df2.addColumn("5eme");
				 df2.addColumn("6eme");
				 table2.setModel(df2);
				 for (int i = 0; i < 6; i++) {
					 		 ArrayList<String> joursSemaine = new ArrayList<>();	        
					        // Ajout des jours de la semaine à la liste
					        joursSemaine.add("LUNDI");
					        joursSemaine.add("MARDI");
					        joursSemaine.add("MERCREDI");
					        joursSemaine.add("JEUDI");
					        joursSemaine.add("VENDREDI");
					        joursSemaine.add("SAMEDI");				        
					        // Accès à un jour spécifique
					        String jourSelectionne = joursSemaine.get(i);	
 String rq2="SELECT (SELECT CONCAT( ' ',classe) FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_enseignant.nom = '"+prof2+"' AND tb_cours.matiere = '"+prof+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%1%') AS matiere1,(SELECT CONCAT( ' ',classe) FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_enseignant.nom = '"+prof2+"' AND tb_cours.matiere = '"+prof+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%2eme%') AS matiere2,(SELECT CONCAT( ' ',classe) FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_enseignant.nom = '"+prof2+"' AND tb_cours.matiere = '"+prof+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%3eme%') AS matiere3,(SELECT CONCAT( ' ',classe) FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_enseignant.nom = '"+prof2+"' AND tb_cours.matiere = '"+prof+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%4eme%') AS matiere4 ,(SELECT CONCAT( ' ',classe) FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_enseignant.nom = '"+prof2+"' AND tb_cours.matiere = '"+prof+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%5eme%') AS matiere5,(SELECT CONCAT( ' ',classe) FROM tb_cours , tb_enseignant WHERE tb_enseignant.matricule = tb_cours.matricule_ens AND tb_enseignant.nom = '"+prof2+"' AND tb_cours.matiere = '"+prof+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%6eme%') AS matiere6";

 try{
					 st=con.laConnection().createStatement();
					 rst=st.executeQuery(rq2);
					 while(rst.next()){

						 df2.addRow(new Object[]{
								 "         "+jourSelectionne,rst.getString("matiere1"),rst.getString("matiere2"),rst.getString("matiere3"),rst.getString("matiere4"),rst.getString("matiere5"),rst.getString("matiere6")
								 });
					   } 
					 }
					 
				 catch(SQLException ex){
				    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
				    }

				 }
			}
		});
		pn.add(btrech);
		
		btpng = new JButton("Enregistrer en PNG");
		btpng.setBounds(500, 70, 200, 25);
		btpng.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent ev) {
		        // Méthode pour générer le PNG
		        generatePNG(pn);
		    }
		});
		pn.add(btpng);

	}
	private void generatePNG(JComponent component) {
	    // Récupérer la capture d'écran du panneau contenant votre tableau
	    BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = image.createGraphics();
	    component.print(graphics2D);

	    // Enregistrer l'image en tant que fichier PNG
	    try {
	        ImageIO.write(image.getSubimage(9, 140, 770, 320), "png", new File("emploi_du_temps_prof.png"));
	        JOptionPane.showMessageDialog(null, "Image PNG enregistrée avec succès !");
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement de l'image PNG !");
	    }
	}

	private void init2(){
		table2=new JTable();
		table2.setRowHeight(50);
		scroll2=new JScrollPane();
		scroll2.setBounds(10,140,770,500);
		scroll2.setViewportView(table2);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	Emploi_1 rq=new Emploi_1();
	rq.setVisible(true);

	}

}
