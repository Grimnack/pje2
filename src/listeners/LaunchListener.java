package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import models.Configuration;
import models.Model;
import models.Polarite;
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
			
			Map<Polarite, Integer> predicted = Annotation.predictTweetsClass(Model.base, Model.lesTweets);
			
			Model.frame.addTweetsWithPolarite(Model.lesTweets);
			Model.frame.updateStats("Bayes", predicted.get(Polarite.NEGATIF), predicted.get(Polarite.NEUTRE), predicted.get(Polarite.POSITIF));
		} else if(selectedAlgo == "Bayes - Fréquence"){
			Configuration.frequence = true;
			Map<Polarite, Integer> predicted = Annotation.predictTweetsClass(Model.base, Model.lesTweets);
			
			Model.frame.addTweetsWithPolarite(Model.lesTweets);
			Model.frame.updateStats("Bayes", predicted.get(Polarite.NEGATIF), predicted.get(Polarite.NEUTRE), predicted.get(Polarite.POSITIF));
		} else if(selectedAlgo == "Vérifier la base"){
			Annotation.checkBase();
		}


	}

}
