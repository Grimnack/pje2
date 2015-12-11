package listeners;

import graphics.ConfigFrame;
import graphics.JMessagePopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Configuration;
import annotations.Annotation;

public class SaveConfigListener implements ActionListener{

	private ConfigFrame configFrame;
	
	public SaveConfigListener(ConfigFrame configFrame){
		this.configFrame = configFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			Configuration.moinsDeNMots = Integer.parseInt(configFrame.getNMots().getText());
			Configuration.poss = configFrame.ngrammePos.getText().split(",");
			Configuration.negs = configFrame.ngrammeNeg.getText().split(",");
			Configuration.proxy = (Boolean)configFrame.proxyBox.getSelectedItem(); 
		} catch(Exception exception){
			exception.printStackTrace();
			JMessagePopup.showMessage("Erreur", "Attention ! Certaines options ont mal été sauvegardées");
		}
		for(String string : Configuration.poss){
			System.out.println(string);
		}
	}

	


}
