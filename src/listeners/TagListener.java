package listeners;

import graphics.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Model;

public class TagListener implements ActionListener {

	protected Frame mainframe;
	
	public TagListener(Frame frame) {
		this.mainframe = frame ;
	}

	public void actionPerformed(ActionEvent e) {
		mainframe.addTweetsWithTag(Model.lesTweets.tweetlist);
		
	}

}
