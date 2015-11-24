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
		
		Annotation.predictTweetsClass(Model.base);
		
		
	}

}
