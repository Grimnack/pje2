package listeners;

import graphics.JMessagePopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import models.Model;
import models.Tweet;
import models.TweetList;

public class LoadListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JFileChooser choix = new JFileChooser();
		int retour = choix.showOpenDialog(null);
		if(retour != JFileChooser.APPROVE_OPTION)
			return;

		String pathName = choix.getSelectedFile().getAbsolutePath();
		File file = new File(pathName);
		// Si le fichier existe deja
		// LECTURE ET SAUVEGARDE DANS Model.base
		if(file.exists()){
			if(!file.canRead()){
				String message = "La base ne peut être chargée. Vérifiez que le fichier est bien celui de la base et veuillez réessayer.",
						titre = "Échec !";
				JMessagePopup.showMessage(message, titre);
				return;
			}
			try {
				ObjectInputStream flotLecture = new ObjectInputStream(new FileInputStream(pathName));
				Object lu = flotLecture.readObject();
				flotLecture.close();
				if(lu instanceof TweetList) {
					TweetList bdd = (TweetList) lu;
					Model.base = bdd ;
				}

			} catch (Exception e2) {
				String message = "La base ne peut être chargée. Vérifiez que le fichier est bien celui de la base et veuillez réessayer.",
						titre = "Échec !";
				JMessagePopup.showMessage(message, titre);
				e2.printStackTrace();
				return;
			}

		}
		
		System.out.println(Model.base);

		String message = "Base chargée ! " + Model.base.size() + " tweets chargés !",
				titre = "Succès !";
		JMessagePopup.showMessage(message, titre);


	}
}
