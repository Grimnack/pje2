package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import models.Model;
import models.TweetList;
import annotations.Annotation;

public class BayesListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		String pathname = Model.theme + "-etiquette.csv";
		File file = new File(pathname);

		try {
			ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(file));
			Object lu = flotLecture.readObject();
			flotLecture.close();
			if(lu instanceof TweetList) {
				TweetList bdd = (TweetList) lu;
				Model.lesTweets.fusionne(bdd) ;
			}
			TweetList learningBase = null;
			Annotation.predictTweetsClass(learningBase);
			
		} catch(Exception exception){
			// Si une erreur, on ne fait rien
		}
	}

}
