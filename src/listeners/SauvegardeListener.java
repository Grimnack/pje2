package listeners;

import graphics.JMessagePopup;
import graphics.MainFrame;
import graphics.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import models.Model;
import models.Polarite;
import models.Tweet;
import models.TweetList;

public class SauvegardeListener implements ActionListener {
	protected MainFrame mainFrame ;
	protected Table table;

	public SauvegardeListener(MainFrame frame, Table table) {
		mainFrame = frame ;
		this.table = table;
	}

	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<Model.lesTweets.size();i++){
			Tweet tweet = Model.lesTweets.get(i);

			tweet.setPolarite(table.getValueAt(i, 2).toString());
		}

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

				ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
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
			String message = "La base ne peut être sauvegardée. Vérifiez que le fichier est bien celui de la base et veuillez réessayer.",
					titre = "Échec !";
			JMessagePopup.showMessage(message, titre);
			exception.printStackTrace();
		}   

		String message = "La base a bien été sauvegardée.",
				titre = "Succès !";
		JMessagePopup.showMessage(message, titre);

	}

}
