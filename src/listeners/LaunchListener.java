package listeners;

import graphics.ConfigFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Configuration;
import models.Model;
import annotations.Annotation;

public class LaunchListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		String selectedAlgo = Configuration.selectedAlgo; 
		System.out.println(selectedAlgo);

		if(selectedAlgo == "Mot clef"){
			Annotation.annoteNaif();
			
		} else if(selectedAlgo == "KNN"){
			Annotation.annoteKNN();

		} else if(selectedAlgo == "Bayes - Présence"){
			Configuration.frequence = false;
			Annotation.predictTweetsClass(Model.base);
		} else if(selectedAlgo == "Bayes - Fréquence"){
			Configuration.frequence = true;
			Annotation.predictTweetsClass(Model.base);
		}


	}

}
