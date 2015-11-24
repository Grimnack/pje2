package listeners;

import graphics.MainFrame;
import graphics.JMessagePopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import models.Model;
import models.TweetList;

public class SauvegardeListener implements ActionListener {
	protected MainFrame mainFrame ;
	protected TweetList lesTweets;

	public SauvegardeListener(MainFrame frame, TweetList lesTweets) {
		mainFrame = frame ;
		this.lesTweets = lesTweets ;
	}

	public void actionPerformed(ActionEvent e) {

		try {
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

				ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(file));
				Object lu = flotLecture.readObject();
				flotLecture.close();
				if(lu instanceof TweetList) {
					TweetList bdd = (TweetList) lu;
					Model.lesTweets.fusionne(bdd) ;
				}
			}

			ObjectOutputStream flotEcriture = 
					new ObjectOutputStream(
							new FileOutputStream(pathName)); 
			flotEcriture.writeObject(Model.lesTweets);
			flotEcriture.close();
		} catch (Exception exception) {
			String message = "La base ne peut être chargée. Vérifiez que le fichier est bien celui de la base et veuillez réessayer.",
					titre = "Échec !";
			JMessagePopup.showMessage(message, titre);
			exception.printStackTrace();
		}   

		String message = "La base a bien été sauvegardée.",
				titre = "Succès !";
		JMessagePopup.showMessage(message, titre);

	}

}
