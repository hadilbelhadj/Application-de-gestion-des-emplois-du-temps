package emploiDuTemps_java;
/*Code écrit du 26 au 28 Mars 2021 à N'djaména au Tchad par TARGOTO CHRISTIAN
 * Contact: ct@chrislink.net / 23560316682*/
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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



public class Emploi extends JFrame {
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

	public Emploi(){
		this.setTitle("Emploi du Temps");
		this.setSize(800,500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		JPanel pn=new JPanel();
		pn.setLayout(null);
		add(pn);
		 pn.setBackground(new Color(173, 216, 230));
		
		lbtitre=new JLabel("Les séances de cours dans la semaine par classe");
		lbtitre.setBounds(120,5,800,30);
		lbtitre.setFont(new Font("Arial",Font.BOLD,18));
		pn.add(lbtitre);
		

		
		//classe
		lbclasse=new JLabel("Classe");
		lbclasse.setBounds(170,40,150,25);
		lbclasse.setFont(new Font("Arial",Font.BOLD,16));
		pn.add(lbclasse);
		
		comboclasse=new JComboBox();
		comboclasse.addItem("");
		String cldispo ="SELECT classe FROM `tb_cours` GROUP BY classe";
		try{
			 st=con.laConnection().createStatement();
			 rst=st.executeQuery(cldispo);
			 while(rst.next()){

				 comboclasse.addItem(rst.getString("classe"));
			   } 
			 }
			 
		 catch(SQLException ex){
		    	JOptionPane.showMessageDialog(null,"Erreur !",null,JOptionPane.ERROR_MESSAGE);	
		    }
		comboclasse.setBounds(130,70,150,25);
		pn.add(comboclasse);

	
		
		tfmatiere=new JTextField();
		
	
			
		//bouton recherche
		btrech=new JButton("CHERCHER");
		btrech.setBounds(360,70,120,25);
		btrech.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				String classe=comboclasse.getSelectedItem().toString();

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
					 String rq2="SELECT (SELECT CONCAT( '          ',matiere)  FROM tb_cours WHERE classe='"+classe+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '1ere%') AS matiere1,(SELECT CONCAT( '          ',matiere)  FROM tb_cours WHERE classe='"+classe+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%2eme%') AS matiere2, (SELECT CONCAT( '          ',matiere)  FROM tb_cours WHERE classe='"+classe+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%3eme%') AS matiere3, (SELECT CONCAT( '          ',matiere)  FROM tb_cours WHERE classe='"+classe+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%4eme%') AS matiere4 ,(SELECT CONCAT( '          ',matiere)  FROM tb_cours WHERE classe='"+classe+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%5eme%') AS matiere5, (SELECT CONCAT( '          ',matiere)  FROM tb_cours WHERE classe='"+classe+"' AND Jour='"+jourSelectionne+"' AND heure LIKE '%6eme%') AS matiere6";
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
	        ImageIO.write(image.getSubimage(9, 140, 770, 320), "png", new File("emploi_du_temps.png"));
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
	Emploi rq=new Emploi();
	rq.setVisible(true);
	/*Code écrit du 26 au 28 Mars 2021 à N'djaména au Tchad par TARGOTO CHRISTIAN
	 * Contact: ct@chrislink.net / 23560316682*/
	}

}
