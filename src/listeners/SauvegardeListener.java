package listeners;

import graphics.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import models.Model;
import models.TweetList;

public class SauvegardeListener implements ActionListener {
	protected Frame mainFrame ;
	protected TweetList lesTweets;

	public SauvegardeListener(Frame frame, TweetList lesTweets) {
		mainFrame = frame ;
		this.lesTweets = lesTweets ;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser choix = new JFileChooser();
		int retour=choix.showOpenDialog(null);
		if(retour!=JFileChooser.APPROVE_OPTION){
			return;
		}

		String pathName = choix.getSelectedFile().getAbsolutePath();
		File file = new File(pathName);

		if(file.exists() && file.canRead()){
			// Si le fichier existe deja
			// LECTURE ET FUSION
			try {
				ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(file));
				Object lu = flotLecture.readObject();
				flotLecture.close();
				if(lu instanceof TweetList) {
					TweetList bdd = (TweetList) lu;
					Model.lesTweets.fusionne(bdd) ;
				}

			} catch (Exception e2) {
				System.out.println("erreur2 : ");
				e2.printStackTrace();
			}

		}


		try {
			ObjectOutputStream flotEcriture = 
					new ObjectOutputStream(
							new FileOutputStream(pathName)); 
			flotEcriture.writeObject(Model.lesTweets);
			flotEcriture.close();
		} catch (IOException e3) {
			System.out.println(" erreur3 :");
			e3.printStackTrace();
		}   

	}

}
