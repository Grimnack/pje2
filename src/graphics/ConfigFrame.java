package graphics;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import listeners.LoadListener;
import listeners.SaveConfigListener;
import models.Configuration;

public class ConfigFrame extends JFrame implements WindowListener{

	public JComboBox<String> proxyBox;
	public JTextField nbTweetsField;
	
	public JComboBox<String> algosBox;
	public JTextField nMots;
	
	public JComboBox<String> nGrammeBox;
	public JTextArea nGrammes;
	
	public JButton loadButton;
	
	
	public ConfigFrame(){
		setTitle("Configuration des algorithmes");
		
		proxyBox = new JComboBox<String>(new String[] {"Oui", "Non"});
		if(Configuration.proxy)
			proxyBox.setSelectedItem("Oui");
		else 
			proxyBox.setSelectedItem("Non");
		
		nbTweetsField = new JTextField(Configuration.nbTweets + "", 14);
		
		algosBox = new JComboBox<String>(new String[] {"", "Mot clef", "KNN", "Bayes - Présence", "Bayes - Fréquence", "Vérifier la base"});
		algosBox.setSelectedItem(Configuration.selectedAlgo);
				
		nMots = new JTextField(Configuration.moinsDeN + "", 14);
		
		nGrammeBox = new JComboBox<String>(new String[] {"Oui", "Non"});
		if(Configuration.useNGramme)
			nGrammeBox.setSelectedItem("Oui");
		else
			nGrammeBox.setSelectedItem("Non");

		String stringPos = new String();
		if(Configuration.nGrammes != null){
			for(String pos : Configuration.nGrammes){
				stringPos = stringPos + pos + ",";
			}
		}
		
		
		
		nGrammeBox = new JComboBox<String>(new String[]{"Oui", "Non"});
		
		nGrammes = new JTextArea(stringPos, 10, 14);
		nGrammes.setBorder(new LineBorder(Color.black));
				
		JButton saveButton = new JButton("Sauvegarder les modifications");
		saveButton.addActionListener(new SaveConfigListener(this));
		
		loadButton = new JButton("Load base");
		loadButton.addActionListener(new LoadListener());
		
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		
		this.add(new JLabel("Utiliser le proxy de Lille 1"), c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;

		this.add(proxyBox, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		
		this.add(new JLabel("Nombre de tweets"), c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		c.gridheight = 1;

		this.add(nbTweetsField, c);
		
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 2;
		this.add(new JLabel("Algorithme"), c);
		
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		c.gridheight = 1;
		this.add(algosBox, c);
		
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 2;
		this.add(new JLabel("Ne pas accepter les mots de moins n lettres"), c);
				

		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 3;
		c.gridheight = 1;
		this.add(nMots, c);
		
		
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 2;
		this.add(new JLabel("Accepter les n-grammes"), c);
		
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 3;
		c.gridheight = 1;
		this.add(nGrammeBox, c);
		
		
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 2;
		this.add(new JLabel("N-gramme"), c);
		
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 3;
		c.gridheight = 1;
		this.add(nGrammes, c);
		
		
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(loadButton, c);
		
		c.gridx = 0;
		c.gridy = 13;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(saveButton, c);

				

		setResizable(false);
		setSize(600, 600);
		setLocation(100,100);
	}
	
	
	public JComboBox<String> getAlgosBox(){
		return algosBox;
	}
	
	public JTextField getNMots(){
		return nMots;
	}


	public void windowOpened(WindowEvent e) { }


	public void windowClosing(WindowEvent e) { }


	public void windowClosed(WindowEvent e) {  setVisible(false); }


	public void windowIconified(WindowEvent e) { }


	public void windowDeiconified(WindowEvent e) { }


	public void windowActivated(WindowEvent e) { }


	public void windowDeactivated(WindowEvent e) { }
}
