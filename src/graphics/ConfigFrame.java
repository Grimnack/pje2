package graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConfigFrame extends JFrame implements WindowListener{

	private JComboBox<String> algosBox;
	private JComboBox<String> moinsDeTroisMotsBox;
	
	
	public ConfigFrame(){
		setTitle("Configuration des algorithmes");
		
		algosBox = new JComboBox<String>(new String[] {"", "Mot clef", "KNN", "Bayes - Présence", "Bayes - Fréquence"});
				
		moinsDeTroisMotsBox = new JComboBox<String>(new String[]{"Non", "Oui"});	
		
		
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
		
		this.add(new JLabel("Accepter les mots de moins de 3 lettres"), c);
		
		c.gridx = 300;
		c.gridy = 0;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(algosBox, c);

		c.gridx = 300;
		c.gridy = 200;
		c.gridwidth = 200;
		c.gridheight = 200;

		this.add(moinsDeTroisMotsBox, c);

		//setResizable(false);
		setSize(500, 200);
		setLocation(400, 300);
	}
	
	
	public JComboBox<String> getAlgosBox(){
		return algosBox;
	}
	
	public JComboBox<String> getMoinsDeTroisMotsBox(){
		return moinsDeTroisMotsBox;
	}


	public void windowOpened(WindowEvent e) { }


	public void windowClosing(WindowEvent e) { }


	public void windowClosed(WindowEvent e) {  setVisible(false); }


	public void windowIconified(WindowEvent e) { }


	public void windowDeiconified(WindowEvent e) { }


	public void windowActivated(WindowEvent e) { }


	public void windowDeactivated(WindowEvent e) { }
}
