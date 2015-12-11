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
import annotations.Annotation;

public class ConfigFrame extends JFrame implements WindowListener{

	public JComboBox<Boolean> proxyBox;
	private JTextField nbTweetsField;
	private JComboBox<String> algosBox;
	private JTextField nMots;
	public JTextArea ngrammePos;
	public JTextArea ngrammeNeg;
	public JTextField kCrossValidationField;
	
	
	public ConfigFrame(){
		setTitle("Configuration des algorithmes");
		
		proxyBox = new JComboBox<Boolean>(new Boolean[] {true, false});
		proxyBox.setSelectedItem(Configuration.proxy);
		
		nbTweetsField = new JTextField(Configuration.nbTweets + "", 14);
		
		algosBox = new JComboBox<String>(new String[] {"", "Mot clef", "KNN", "Bayes - Présence", "Bayes - Fréquence"});
				
		nMots = new JTextField(Configuration.moinsDeNMots + "", 14);

		String stringPos = new String(),
				stringNeg = new String();
		for(String pos : Configuration.poss){
			stringPos = stringPos + pos + ",";
		}
		
		for(String neg : Configuration.negs){
			stringNeg = stringNeg + neg + ",";
		}
		
		ngrammePos = new JTextArea(stringPos, 10, 14);
		ngrammePos.setBorder(new LineBorder(Color.black));
		
		ngrammeNeg = new JTextArea(stringNeg, 10, 14);
		ngrammeNeg.setBorder(new LineBorder(Color.black));
		
		kCrossValidationField = new JTextField(Configuration.kCrossValidation + "", 14);
		
		JButton load = new JButton("Load");
		load.addActionListener(new LoadListener());
		load.setBackground(new Color(0x00aced));
		load.setForeground(Color.WHITE);
		
		
		setLayout(new GridBagLayout());
		
		// Proxy
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 200;
		c.gridheight = 150;
		
		this.add(new JLabel("Utiliser le proxy Lille1"), c);
		
		c.gridx = 200;
		c.gridy = 0;

		this.add(proxyBox, c);
		
		// NbTweets
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 150;
		
		this.add(new JLabel("Nombre de tweets"), c);
		
		c.gridx = 200;
		c.gridy = 150;

		this.add(nbTweetsField, c);
		
		// Algo
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 300;
		
		this.add(new JLabel("Algorithme"), c);
		
		c.gridx = 200;
		c.gridy = 300;


		this.add(algosBox, c);
		
		//Accepter mots moins de n lettres
		c.gridx = 0;
		c.gridy = 400;
		
		this.add(new JLabel("Ne pas accepter les mots de moins n  lettres"), c);
		
		c.gridx = 200;
		c.gridy = 400;

		this.add(nMots, c);

		// N-gramme pos
		c.gridx = 0;
		c.gridy = 600;
		
		this.add(new JLabel("N-gramme positif"), c);
		
		c.gridx = 200;
		c.gridy = 600;
		

		this.add(ngrammePos, c);
		
		c.gridx = 0;
		c.gridy = 800;
		

		this.add(new JLabel("N-gramme negatif"), c);
		
		c.gridx = 200;
		c.gridy = 800;

		this.add(ngrammeNeg, c);
		
		c.gridx = 0;
		c.gridy = 1200;
		c.gridwidth = 40;
		c.gridheight = 20;

		this.add(load, c);
		
		
		
		c.gridx = 180;
		c.gridy = 1200;
		c.gridwidth = 40;
		c.gridheight = 20;

		JButton button = new JButton("Sauvegarder les modifications");
		button.addActionListener(new SaveConfigListener(this));
		this.add(button, c);
		
		

		setResizable(false);
		setSize(800, 768);
		setLocation(100, 0);
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
