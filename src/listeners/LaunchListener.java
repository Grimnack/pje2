package listeners;

import graphics.ConfigFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Model;

import annotations.Annotation;

public class LaunchListener implements ActionListener{

	ConfigFrame configFrame;

	public LaunchListener(ConfigFrame configFrame){
		this.configFrame = configFrame;
	}

	public void actionPerformed(ActionEvent e) {
		String selectedAlgo = (String)(configFrame.getAlgosBox().getSelectedItem()); 
		System.out.println(selectedAlgo);

		if(selectedAlgo == "Mot clef"){
			Annotation.annoteNaif();
			
		} else if(selectedAlgo == "KNN"){
			Annotation.annoteKNN();

		} else if(selectedAlgo == "Bayes - Présence"){
			Annotation.frequence = false;
			Annotation.predictTweetsClass(Model.base);
		} else if(selectedAlgo == "Bayes - Fréquence"){
			Annotation.frequence = true;
			Annotation.predictTweetsClass(Model.base);
		}


	}

}
