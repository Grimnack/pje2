package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.TweetList;
import annotations.Annotation;

public class BayesListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		// load base d'apprentissage
		TweetList learningBase = null;
		Annotation.predictTweetsClass(learningBase);
	}

}
