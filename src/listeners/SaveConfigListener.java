package listeners;

import graphics.ConfigFrame;
import graphics.JMessagePopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Configuration;

public class SaveConfigListener implements ActionListener{

	private ConfigFrame configFrame;
	
	public SaveConfigListener(ConfigFrame configFrame){
		this.configFrame = configFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			Configuration.moinsDeN = Integer.parseInt(configFrame.getNMots().getText());
			Configuration.nGrammes = configFrame.nGrammes.getText().split(",");
			if(configFrame.proxyBox.getSelectedItem() == "Oui")
				Configuration.proxy = true;
			else
				Configuration.proxy = false;
			
			if(configFrame.nGrammeBox.getSelectedItem() == "Oui")
				Configuration.useNGramme = true;
			else
				Configuration.useNGramme = false;
			
			Configuration.selectedAlgo = (String)configFrame.algosBox.getSelectedItem();
			
			Configuration.nbTweets = Integer.parseInt(configFrame.nbTweetsField.getText());
			
			System.out.println(Configuration.nbTweets);
		
		
		} catch(Exception exception){
			exception.printStackTrace();
			JMessagePopup.showMessage("Erreur", "Attention ! Certaines options ont mal été sauvegardées");
		}
		
	}

	


}
