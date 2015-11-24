package graphics;

import javax.swing.JComboBox;

public class ConfigFrame {

	private JComboBox<String> algosBox;
	private JComboBox<String> moinsDeTroisMots;
	
	
	public ConfigFrame(){
		algosBox = new JComboBox<String>(new String[] {"Mot clef", "KNN", "Bayes - Présence", "Bayes - Fréquence"});
				
		moinsDeTroisMots = new JComboBox<String>(new String[]{"Non", "Oui"});
		
			
	}
}
