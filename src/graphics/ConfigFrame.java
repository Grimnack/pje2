package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import annotations.Annotation;

public class ConfigFrame extends JFrame implements WindowListener{

	private JComboBox<String> algosBox;
	private JTextField nMots;
	
	
	public ConfigFrame(){
		setTitle("Configuration des algorithmes");
		
		algosBox = new JComboBox<String>(new String[] {"", "Mot clef", "KNN", "Bayes - Présence", "Bayes - Fréquence"});
				
		nMots = new JTextField("3", 15);
		nMots.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				try {
					Annotation.moinsDeNMots = Integer.parseInt(nMots.getText());
					
				} catch(Exception exception){
					JMessagePopup.showMessage("Erreur", "Ceci ne peut être parsé en integer. Erreur");
				}
				
			}
			
		});
		
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 200;
		c.gridheight = 200;
		
		this.add(new JLabel("Algorithme"), c);
		
		c.gridx = 0;
		c.gridy = 200;
		c.gridwidth = 200;
		c.gridheight = 200;
		
		this.add(new JLabel("Ne pas accepter les mots de moins n  lettres"), c);
		
		c.gridx = 300;
		c.gridy = 0;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(algosBox, c);

		c.gridx = 300;
		c.gridy = 200;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(nMots, c);

		setResizable(false);
		setSize(500, 200);
		setLocation(400, 300);
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
