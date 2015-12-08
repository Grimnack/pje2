package listeners;

import graphics.ConfigFrame;
import graphics.JMessagePopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import annotations.Annotation;

public class SaveConfigListener implements ActionListener{

	private ConfigFrame configFrame;
	
	public SaveConfigListener(ConfigFrame configFrame){
		this.configFrame = configFrame;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			Annotation.moinsDeNMots = Integer.parseInt(configFrame.getNMots().getText());
			Annotation.poss = configFrame.ngrammePos.getText().split(",");
			Annotation.negs = configFrame.ngrammeNeg.getText().split(",");
		} catch(Exception exception){
			exception.printStackTrace();
			JMessagePopup.showMessage("Erreur", "Attention ! Certaines options ont mal été sauvegardées");
		}
		for(String string : Annotation.poss){
			System.out.println(string);
		}
	}

	


}
