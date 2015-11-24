package listeners;

import graphics.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Polarite;
import models.Tweet;

public class ValidationListener implements ActionListener {
	
	protected MainFrame mainframe ;
	protected Tweet tweet ;
	protected JPanel tweetPanel;

	public ValidationListener(MainFrame frame, Tweet tweet, JPanel tweetPanel) {
		this.mainframe = frame;
		this.tweet = tweet;
		this.tweetPanel = tweetPanel;
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		String resultat = (String) ((JComboBox<String>) tweetPanel.getComponent(2)).getSelectedItem();
		Polarite pol;
		if(resultat == "non defini") {
			pol = Polarite.UNDEFINED;
		}else if (resultat == "positif"){
			pol = Polarite.POSITIF;
		}else if (resultat == "negatif"){
			pol = Polarite.NEGATIF;
		}else {
			pol = Polarite.NEUTRE ;
		}
		tweet.setPolarite(pol);
		tweetPanel.removeAll();
		tweetPanel.add(new JLabel(tweet.getUser()));
		tweetPanel.add(new JLabel(tweet.getText()));
		mainframe.revalidate(); 
		mainframe.repaint();
		System.out.println(resultat);
	}

}
