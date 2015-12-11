package listeners;

import graphics.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import models.Configuration;
import models.Model;
import models.TweetList;
import twitterHelper.TwitterHelper;


public class SearchListener implements ActionListener{

	protected JTextField textField;

	public SearchListener(MainFrame mainFrame, JTextField textField, boolean fromScratch){
		this.textField = textField;
	}

	public void actionPerformed(ActionEvent e) {

		// Mise a jour du theme dans le model
		Model.theme =  textField.getText();
		
		TweetList tweetsList = TwitterHelper.getTweets(Configuration.proxy, Configuration.nbTweets);
		
		Model.lesTweets = tweetsList;	
		
		Model.frame.addTweets(Model.lesTweets);
	}
}
