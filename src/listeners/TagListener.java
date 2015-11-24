package listeners;

import graphics.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Model;

public class TagListener implements ActionListener {

	protected MainFrame mainFrame;
	
	public TagListener(MainFrame frame) {
		this.mainFrame = frame ;
	}

	public void actionPerformed(ActionEvent e) {
		mainFrame.addTweetsWithTag(Model.lesTweets);
	}

}
