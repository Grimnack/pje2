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

import listeners.SaveConfigListener;
import annotations.Annotation;

public class ConfigFrame extends JFrame implements WindowListener{

	private JTextField nbTweetsField;
	private JComboBox<String> algosBox;
	private JTextField nMots;
	public JTextArea ngrammePos;
	public JTextArea ngrammeNeg;
	public JTextField kCrossValidationField;
	
	
	public ConfigFrame(){
		setTitle("Configuration des algorithmes");
		
		nbTweetsField = new JTextField(Annotation.nbTweets + "", 14);
		
		algosBox = new JComboBox<String>(new String[] {"", "Mot clef", "KNN", "Bayes - Présence", "Bayes - Fréquence"});
				
		nMots = new JTextField(Annotation.moinsDeNMots + "", 14);

		String stringPos = new String(),
				stringNeg = new String();
		for(String pos : Annotation.poss){
			stringPos = stringPos + pos + ",";
		}
		
		for(String neg : Annotation.negs){
			stringNeg = stringNeg + neg + ",";
		}
		
		ngrammePos = new JTextArea(stringPos, 10, 14);
		ngrammePos.setBorder(new LineBorder(Color.black));
		
		ngrammeNeg = new JTextArea(stringNeg, 10, 14);
		ngrammeNeg.setBorder(new LineBorder(Color.black));
		
		kCrossValidationField = new JTextField(Annotation.kCrossValidation + "", 14);
		
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 200;
		c.gridwidth = 200;
		c.gridheight = 200;
		
		this.add(new JLabel("Algorithme"), c);
		
		c.gridx = 300;
		c.gridy = 200;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(algosBox, c);
		
		c.gridx = 0;
		c.gridy = 400;
		c.gridwidth = 200;
		c.gridheight = 200;
		
		this.add(new JLabel("Ne pas accepter les mots de moins n  lettres"), c);
		
		

		c.gridx = 300;
		c.gridy = 400;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(nMots, c);
		
		c.gridx = 0;
		c.gridy = 600;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(new JLabel("Bigramme positif"), c);
		
		c.gridx = 200;
		c.gridy = 600;
		//c.gridwidth = 200;
		//c.gridheight = 200;

		this.add(ngrammePos, c);
		
		c.gridx = 0;
		c.gridy = 800;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(new JLabel("Bigramme negatif"), c);
		
		c.gridx = 200;
		c.gridy = 800;
		//c.gridwidth = 200;
		//c.gridheight = 200;

		this.add(ngrammeNeg, c);
		
		c.gridx = 180;
		c.gridy = 1200;
		c.gridwidth = 40;
		c.gridheight = 20;

		JButton button = new JButton("Sauvegarder les modifications");
		button.addActionListener(new SaveConfigListener(this));
		this.add(button, c);
		
		

		setResizable(false);
		setSize(800, 400);
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
