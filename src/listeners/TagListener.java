package listeners;

import graphics.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Model;

public class TagListener implements ActionListener {

	protected Frame mainFrame;
	
	public TagListener(Frame frame) {
		this.mainFrame = frame ;
	}

	public void actionPerformed(ActionEvent e) {
		mainFrame.addTweetsWithTag(Model.lesTweets);
	}

}
